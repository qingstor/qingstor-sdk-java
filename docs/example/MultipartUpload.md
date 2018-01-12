## UploadMultipart Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

Then you can upload multipart object.


```

String objectName = "your_object_name";
		
Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
/**
* You can encryption here like the codes below when upload.<br>
* <br>
* <code>//Encryption algorithm of the object</code><br>
* <code>inputInit.setXQSEncryptionCustomerAlgorithm("AES256");</code><br>
* <code>//Encryption key of the object<br></code>
* <code>inputInit.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");</code><br>
* <code><//MD5sum of encryption key</code><br>
* <code>inputInit.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");</code><br>
*
* Get more information:
* <a href='https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers'>
*     https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers</a>
*/
InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

String multipart_upload_name = objectKey;
// Step 1: init multipart_upload_id
String multipart_upload_id = initOutput.getUploadID();

System.out.println("-multipart_upload_id----" + initOutput.getUploadID());
    
File file = new File(filePath);
long contentLength = file.length();
long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
// Step 2: Upload parts.
        StringBuilder uploadJson = new StringBuilder("{\"object_parts\":[");
        long filePosition = 0;
        for (int i = 0; filePosition < contentLength; i++) {
            // Last part can be less than 4 MB. Adjust part size.
            uploadJson.append("{\"part_number\":").append(i).append("}").append(",");
            partSize = Math.min(partSize, (contentLength - filePosition));
            Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
            input.setBodyInputFilePart(file);
            input.setFileOffset(filePosition);
            input.setContentLength(partSize);
            input.setPartNumber(i);
            input.setUploadID(multipart_upload_id);

            input.setXQSEncryptionCustomerKey(objectKey + i);
//            input.setXQSEncryptionCustomerAlgorithm("AES256");
//            input.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");
//            input.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");

            // Create request to upload a part.
            bucket.uploadMultipart(objectKey, input);

            filePosition += partSize;
        }
        //Delete last char ',' and add ']}' to complete json string.
        uploadJson.deleteCharAt(uploadJson.length() - 1).append("]}");
        System.out.println("uploadJson = " + uploadJson);

        // Step 3: Complete.
        // Write code here that turns the phrase above into concrete actions
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput = new Bucket.CompleteMultipartUploadInput();
        completeMultipartUploadInput.setUploadID(multipart_upload_id);
        completeMultipartUploadInput.setBodyInput(uploadJson.toString());
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);

}


```

If there have been many parts of a file, you can use the method below.

```
    private void multipartUpload(Bucket bucket, List<File> files, String objectKey) throws QSException {
        if (files == null || files.size() < 1)
            throw new QSException("Files' counts can not be less than one!!");

        String multipart_upload_id = "";

        Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
        /**
         * You can encryption here like the codes below when upload.<br>
         * <br>
         * <code>//Encryption algorithm of the object</code><br>
         * <code>inputInit.setXQSEncryptionCustomerAlgorithm("AES256");</code><br>
         * <code>//Encryption key of the object<br></code>
         * <code>inputInit.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");</code><br>
         * <code><//MD5sum of encryption key</code><br>
         * <code>inputInit.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");</code><br>
         *
         * Get more information:
         * <a href='https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers'>
         *     https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers</a>
         */
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

            input.setXQSEncryptionCustomerKey(objectKey + i);
//            input.setXQSEncryptionCustomerAlgorithm("AES256");
//            input.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");
//            input.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");

            UploadMultipartOutput bm = bucket.uploadMultipart(objectKey, input);
            System.out.println("-UploadMultipartOutput----" + bm.getMessage());
        }

        StringBuilder uploadJson = new StringBuilder("{\"object_parts\":[");
        for (int i = 0; i < files.size(); i++) {
            uploadJson.append("{\"part_number\":").append(i).append("}");
            if (i < files.size() - 1) uploadJson.append(",");
            else uploadJson.append("]}");
        }
        // Write code here that turns the phrase above into concrete actions
        Bucket.CompleteMultipartUploadInput completeMultipartUploadInput = new Bucket.CompleteMultipartUploadInput();
        completeMultipartUploadInput.setUploadID(multipart_upload_id);
        completeMultipartUploadInput.setBodyInput(uploadJson.toString());
        System.out.println("uploadJson = " + uploadJson);
        bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
    }
```

Also, you can set body input stream here.
When a stream have been read/wrote, we will close it.
So a same stream can be used for once.
Do not set a same stream twice please.
Attention: the example below is just to show you how to resolve some streams to multipart upload.

```
    String objectName = "your_object_name";

    Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
    /**
    * You can encryption here like the codes below when upload.<br>
    * <br>
    * <code>//Encryption algorithm of the object</code><br>
    * <code>inputInit.setXQSEncryptionCustomerAlgorithm("AES256");</code><br>
    * <code>//Encryption key of the object<br></code>
    * <code>inputInit.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");</code><br>
    * <code><//MD5sum of encryption key</code><br>
    * <code>inputInit.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");</code><br>
    *
    * Get more information:
    * <a href='https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers'>
    *     https://docs.qingcloud.com/qingstor/api/common/encryption.html#object-storage-encryption-headers</a>
    */
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
            StringBuilder uploadJson = new StringBuilder("{\"object_parts\":[");
            int len = 0;
            int count = 0;
            try {
                while ((len = fis.read(buf)) != -1) {
                    // Step 2: Upload parts.
                    uploadJson.append("{\"part_number\":").append(count).append("}").append(",");
                    Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
                    input.setBodyInputStream(new ByteArrayInputStream(buf.clone(), 0, len));
                    input.setFileOffset(0L);
                    input.setContentLength((long) len);
                    input.setPartNumber(count);
                    input.setUploadID(multipart_upload_id);
                    input.setXQSEncryptionCustomerKey(objectKey + count);
    //            input.setXQSEncryptionCustomerAlgorithm("AES256");
    //            input.setXQSEncryptionCustomerKey("MTIzNDU2NzgxMjM0NTY3ODEyMzQ1Njc4MTIzNDU2Nzg=");
    //            input.setXQSEncryptionCustomerKeyMD5("ODk5MzkzMGUyNzFjOTk4NWIzMDRkODMyMzlkMGM3MGQ=");

                    // Create request to upload a part.
                    bucket.uploadMultipart(objectKey, input);
                    count++;
                }
                fis.close();
                //Delete last char ',' and add ']}' to complete json string.
                uploadJson.deleteCharAt(uploadJson.length() - 1).append("]}");
                System.out.println("uploadJson = " + uploadJson);

                // Step 3: Complete.
                // Write code here that turns the phrase above into concrete actions
                Bucket.CompleteMultipartUploadInput completeMultipartUploadInput = new Bucket.CompleteMultipartUploadInput();
                completeMultipartUploadInput.setUploadID(multipart_upload_id);
                completeMultipartUploadInput.setBodyInput(uploadJson.toString());
                bucket.completeMultipartUpload(objectKey, completeMultipartUploadInput);
            } catch (IOException e) {
                e.printStackTrace();
            }

```
