## 大文件分段上传



### 代码片段

#### 本地已有完整文件使用分段上传

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以分段上传对象：


```

String objectName = "your_object_name";

Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

String multipart_upload_name = objectKey;
// 第1步：初始化 multipart_upload_id
String multipart_upload_id = initOutput.getUploadID();

System.out.println("-multipart_upload_id----" + initOutput.getUploadID());

File file = new File(filePath);
long contentLength = file.length();
// 文件分段计数
int count = 0;
long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
        // 第2步：上传分段
        long filePosition = 0;
        for (int i = 0; filePosition < contentLength; i++) {
            // 最后一段可以小于 4 MB. 计算每一段的大小.
            partSize = Math.min(partSize, (contentLength - filePosition));
            Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
            input.setBodyInputFilePart(file);
            input.setFileOffset(filePosition);
            input.setContentLength(partSize);
            input.setPartNumber(i);
            input.setUploadID(multipart_upload_id);

            // 创建请求上传一个分段.
            bucket.uploadMultipart(objectKey, input);

            filePosition += partSize;
            count++;
        }

        // 第3步: 完成.
        // 该构造方法将自动设置 upload id 和 body input.
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
        // 您可以设置该对象的 MD5 信息.
        completeMultipartUploadInput.setETag("object-MD5");
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);

}


```

#### 如果您本地已存在一个文件的多个分段，您可以参考如下方法:
```
    private void multipartUpload(Bucket bucket, List<File> files, String objectKey) throws QSException {
        if (files == null || files.size() < 1)
            throw new QSException("Files' counts can not be less than one!!");

        String multipart_upload_id = "";

        Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();

        InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);
        multipart_upload_id = initOutput.getUploadID();
        System.out.println("-multipart_upload_id----" + initOutput.getUploadID());

        for (int i = 0; i < files.size(); i++) {
            Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
            input.setBodyInputFilePart(files.get(i));
            input.setFileOffset(0L);
            input.setContentLength(files.get(i).length());
            input.setPartNumber(i);
            input.setUploadID(multipart_upload_id);

            UploadMultipartOutput bm = bucket.uploadMultipart(objectKey, input);
            System.out.println("-UploadMultipartOutput----" + bm.getMessage());
        }

        // 该构造方法将自动设置 upload id 和 body input.
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
        // 您可以设置该对象的 MD5 信息.
        completeMultipartUploadInput.setETag("object-MD5");
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
    }
```

#### 您也可设置 body input stream 来代替 File 对象。
在每次分段上传后，我们SDK会关闭已读写的流。
故同一个流只能使用一次。请勿将同一个流二次设置。
如下示例仅供您参考解决多个流分段上传的问题。

```
    String objectName = "your-object-name";

    Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();

    InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

    String multipart_upload_name = objectKey;
    // 第1步：初始化 multipart_upload_id
    String multipart_upload_id = initOutput.getUploadID();

    System.out.println("-multipart_upload_id----" + initOutput.getUploadID());

    File f = new File("/Users/chengww/Downloads/pub-qingstor-sdk-java.zip");
            long length = 4 * 1024 * 1024L;// 4MB/part
            if (f.length() < length) length = f.length();
            System.out.println("f.length() = " + f.length());

            FileInputStream fis = new FileInputStream(f);
            byte[] buf = new byte[(int) length];
            System.out.println("buf.length = " + buf.length);
            int len = 0;
            int count = 0;
            try {
                while ((len = fis.read(buf)) != -1) {
                    // 第2步：上传分段
                    Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
                    input.setBodyInputStream(new ByteArrayInputStream(buf.clone(), 0, len));
                    // 如果您没有设置 offset, 我们将以 offset = 0 上传该流.
                    input.setFileOffset(0L);
                    // 如果您没有设置 content length, 我们将上传整个流.
                    input.setContentLength((long) len);
                    input.setPartNumber(count);
                    input.setUploadID(multipart_upload_id);

                    // 创建请求上传一个分段.
                    bucket.uploadMultipart(objectKey, input);
                    count++;
                }
                fis.close();

                // 第3步: 完成.
                // 该构造方法将自动设置 upload id 和 body input.
                Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                        new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
                // 您可以设置该对象的 MD5 信息.
                completeMultipartUploadInput.setETag("object-MD5");
                bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
            } catch (IOException e) {
                e.printStackTrace();
            }

```

#### 查看已上传的分段

分段上传失败之后可以尝试访问以下方法查看是否是所有的分段都已上传完成

```java
       /**
        * 如果上传失败，您可以调用此方法列取当前 upload_id 和 objectName 的分段
        *
        * @param bucket bucket
        * @param uploadID   multi upload ID
        * @param objectName objectName
        */
       private void listParts(Bucket bucket, String uploadID, String objectName) {
   
           Bucket.ListMultipartInput input = new Bucket.ListMultipartInput();
           input.setUploadID(uploadID);
           try {
               Bucket.ListMultipartOutput output = bucket.listMultipart(objectName, input);
               if (output.getStatueCode() >= 200 && output.getStatueCode() < 400) {
                   // Succeed
                   List<Types.ObjectPartModel> objectParts =
                           output.getObjectParts();
                   if (objectParts != null && objectParts.size() > 0) {
                       for (Types.ObjectPartModel model : objectParts) {
                           System.out.println("part num = " +
                                   model.getPartNumber() + ", size = " +
                                   model.getSize() + ", ETag = " + model.getEtag());
                       }
                   } else {
                       System.out.println("已上传分段为空");
                   }
               } else {
                   System.out.println("StatusCode = " + output.getStatueCode());
                   System.out.println("Message = " + output.getMessage());
                   System.out.println("RequestId = " + output.getRequestId());
                   System.out.println("Code = " + output.getCode());
                   System.out.println("Url = " + output.getUrl());
               }
           } catch (QSException e) {
               e.printStackTrace();
           }
       }
```
