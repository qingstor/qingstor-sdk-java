## 并发分段上传

### 代码片段

具体步骤可以直接查看代码段的 main 方法.

```java
package com.qingstor.sdk;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Types;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConcurrentMultiUploadSample {

    private static String endpoint = "https://qingstor.com";
    private static String zone = "**your bucket zone**";
    private static String accessKeyId = "ACCESS_KEY_ID";
    private static String secretAccessKey = "SECRET_ACCESS_KEY";

    private static Bucket bucketSrv = null;

    private static String bucketName = "**your bucket name**";
    private static String key = "**your object key**";
    private static String localFilePath = "**your download path**";

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static Map<Integer, String> partETags = new ConcurrentHashMap<>();

    public static void main(String[] args) throws QSException, IOException {
        EnvContext ctx = new EnvContext(accessKeyId, secretAccessKey);
        ctx.setEndpoint(endpoint);
        ctx.setVirtualHostEnabled(true);
        bucketSrv = new Bucket(ctx, zone, bucketName);
        // 1. 初始化 multipart-upload-id
        String uploadID = initUpload();
        System.out.println("upload_id: " + uploadID);

        /*
         * 2. 确定分片大小, 然后计算需要分片上传的数量.
         * 这里设置大小为 5M,
         * qingstor 最小分片大小是 4M, 最大是 5G, 分片最大数量为 10000.
         */
        final long partSize = 5 * 1024 * 1024L;   // 5MB
        // 创建一个约 10 个分片大小的临时文件.
        final File tempFile = createTempFile();
        long fileLength = tempFile.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        } else {
            System.out.println("Total parts count " + partCount + "\n");
        }

        // 3. 提交分片上传任务到 thread-pool.
        System.out.println("Begin to upload multi-parts to qingstor from a file\n");
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            System.out.println("start: " + startPos + ", size: " + curPartSize);
            executorService.execute(new PartUploader(tempFile, startPos, curPartSize, i + 1, uploadID));
        }

        // 4. 等待所有任务完成.
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. 列取已上传的分片, 可以作为校验步骤(可选).
        System.out.println("Listing all parts......");
        listUploadParts(uploadID);

        // 6. complete-upload 来合并分段, 结束分段上传.
        System.out.println("Completing to multi-upload\n");
        completeMultipartUpload(uploadID);

        // 7. 下载文件到本地.
        System.out.println("Download the object we uploaded");
        Bucket.GetObjectOutput output = bucketSrv.getObject(key, null);
        try (InputStream body = output.getBodyInputStream()) {
            File f = new File(localFilePath);
            Files.copy(body, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String initUpload() throws QSException {
        Bucket.InitiateMultipartUploadInput initUploadInput = new Bucket.InitiateMultipartUploadInput();
        Bucket.InitiateMultipartUploadOutput output = bucketSrv.initiateMultipartUpload(key, initUploadInput);
        return output.getUploadID();
    }

    private static void listUploadParts(String uploadID) throws QSException {
        Bucket.ListMultipartInput listPartsInput = new Bucket.ListMultipartInput();
        listPartsInput.setUploadID(uploadID);
        listPartsInput.setLimit(1000);
        Bucket.ListMultipartOutput output = bucketSrv.listMultipart(key, listPartsInput);

        List<Types.ObjectPartModel> parts = output.getObjectParts();
        int partCount = parts.size();
        assert partCount == output.getCount();
        for (Types.ObjectPartModel part : parts) {
            System.out.println("\tPart#" + part.getPartNumber() + ", ETag=" + part.getEtag());
        }
        System.out.println();
    }

    private static void completeMultipartUpload(String uploadID) throws QSException {
        List<Types.ObjectPartModel> parts = partETags.keySet().stream().sorted().map(partNumber -> {
            Types.ObjectPartModel part = new Types.ObjectPartModel();
            part.setPartNumber(partNumber);
            part.setEtag(partETags.get(partNumber));
            return part;
        }).collect(Collectors.toList());

        Bucket.CompleteMultipartUploadInput input = new Bucket.CompleteMultipartUploadInput();
        input.setObjectParts(parts);
        input.setUploadID(uploadID);

        Bucket.CompleteMultipartUploadOutput output = bucketSrv.completeMultipartUpload(key, input);
        assert output.getStatueCode() == 201;
    }

    private static class PartUploader implements Runnable {

        private File localFile;
        private long startPos;

        private long partSize;
        private int partNumber;
        private String uploadId;

        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);

                Bucket.UploadMultipartInput uploadPartInput = new Bucket.UploadMultipartInput();
                uploadPartInput.setUploadID(this.uploadId);
                uploadPartInput.setBodyInputStream(instream);
                uploadPartInput.setPartNumber(this.partNumber);
                uploadPartInput.setFileOffset(this.startPos);
                uploadPartInput.setContentLength(this.partSize);

                Bucket.UploadMultipartOutput output = bucketSrv.uploadMultipart(key, uploadPartInput);
                System.out.println("Part#" + this.partNumber + " done\n");
                partETags.put(partNumber, output.getETag());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static File createTempFile() throws IOException {
        File file = File.createTempFile("upload-example", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        String s = "1234";
        String line = String.join("-", Collections.nCopies(5, s)) + "\n";
        for (int i = 0; i < 1000000; i++) {
            writer.write(line);
            writer.write(line);
        }
        writer.close();
        return file;
    }
}
```
