## 大文件分段上传



### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以分段上传对象：


```

String objectName = "test/mulitUpload-test";

Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
inputInit.setContentType("contentType");
InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

String multipart_upload_name = objectKey;
// 初始化分段上传ID
String multipart_upload_id = initOutput.getUploadID();

System.out.println("-multipart_upload_id----" + initOutput.getUploadID());

//开始分段上传文件
File f = new File("/tmp/big_file");
for(int i = 0 ; i < 3 ; i++) { //Multiparts
	int part_number = i;
    Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
    input.setXQSEncryptionCustomerKey(objectKey+i);
    input.setContentLength("single_part_length");
    input.setBodyInputFile(f);
    input.setPartNumber((long) part_number);
    input.setUploadID(multipart_upload_id);
    UploadMultipartOutput bm = bucket.uploadMultipart(multipart_upload_name, input);
    System.out.println("-UploadMultipartOutput----" + bm.getMessage());

}

// 当分段上传完毕，需要发送此请求结束此分段上传，从而获得一个完整的对象。未调用此接口，分段上传处于未完成的状态，此时调用 GET 请求获得该对象会返回错误。该请求需要对存储空间有可写权限。
Bucket.CompleteMultipartUploadInput input = new Bucket.CompleteMultipartUploadInput();
input.setUploadID(initOutput.getUploadID());
String content = "{\n" +
        "    \"object_parts\": [\n" +
        "        {\"part_number\": 0},\n" +
        "        {\"part_number\": 1},\n" +
        "        {\"part_number\": 2}\n" +
        "    ]\n" +
        "}";
input.setBodyInput(content);
CompleteMultipartUploadOutput completeMultipartUploadOutput = bucket.completeMultipartUpload(objectKey, input);
System.out.println("-completeMultipartUploadOutput----" + completeMultipartUploadOutput.getMessage());

}


```