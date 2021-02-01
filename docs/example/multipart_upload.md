## UploadMultipart Example

### Upload with a complete file

Initialize the Bucket service with access-key-id and secret-access-key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

Then you can upload multipart object.

```

String objectName = "your_object_name";

Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

String multipart_upload_name = objectKey;
// Step 1: init multipart_upload_id
String multipart_upload_id = initOutput.getUploadID();

System.out.println("-multipart_upload_id----" + initOutput.getUploadID());

File file = new File(filePath);
long contentLength = file.length();
//Count of file parts
int count = 0;
long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
        // Step 2: Upload parts.
        long filePosition = 0;
        for (int i = 0; filePosition < contentLength; i++) {
            // Last part can be less than 4 MB. Adjust part size.
            partSize = Math.min(partSize, (contentLength - filePosition));
            Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
            input.setBodyInputFilePart(file);
            input.setFileOffset(filePosition);
            input.setContentLength(partSize);
            input.setPartNumber(i);
            input.setUploadID(multipart_upload_id);

            // Create request to upload a part.
            bucket.uploadMultipart(objectKey, input);

            filePosition += partSize;
            count++;
        }

        // Step 3: Complete.
        // Write code here that turns the phrase above into concrete actions
        // The constructor will auto set values of upload id and body input.
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
        // You can set the Md5 info to the object.
        completeMultipartUploadInput.setETag("object-MD5");
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);

}


```

#### If a large file has been stored as separate parts on disk, you can use the method below.

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

        // Write code here that turns the phrase above into concrete actions
        // The constructor will auto set values of upload id and body input.
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
        // You can set the Md5 info to the object.
        completeMultipartUploadInput.setETag("object-MD5");
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
    }
```

#### Also, you can set body input stream here.

When a stream have been read/wrote, we will close it.
So a same stream can be used for once.
Do not set a same stream twice please.
Attention: the example below is just to show you how to resolve some streams to multipart upload.

```
    String objectName = "your-object-name";

    Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();

    InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

    String multipart_upload_name = objectKey;
    // Step 1: init multipart_upload_id
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
                    // Step 2: Upload parts.
                    Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
                    input.setBodyInputStream(new ByteArrayInputStream(buf.clone(), 0, len));
                    //If you haven't set the offset, we will upload the stream with offset 0.
                    input.setFileOffset(0L);
                    //If you haven't set content length, we will upload the full stream.
                    input.setContentLength((long) len);
                    input.setPartNumber(count);
                    input.setUploadID(multipart_upload_id);

                    // Create request to upload a part.
                    bucket.uploadMultipart(objectKey, input);
                    count++;
                }
                fis.close();

                // Step 3: Complete.
                // Write code here that turns the phrase above into concrete actions
                // The constructor will auto set values of upload id and body input.
                Bucket.CompleteMultipartUploadInput completeMultipartUploadInput =
                        new Bucket.CompleteMultipartUploadInput(multipart_upload_id, count, 0);
                // You can set the Md5 info to the object.
                completeMultipartUploadInput.setETag("object-MD5");
                bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
            } catch (IOException e) {
                e.printStackTrace();
            }

```

#### List all the parts uploaded

If upload failed, you can call this method to list all of the parts uploaded.

```java
    /**
     * If upload failed, you can call
     * this method to list all of the parts uploaded. <br>
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
                    System.out.println("No upload part exists.");
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
