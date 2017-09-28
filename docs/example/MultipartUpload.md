## UploadMultipart Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can upload multipart object


```

String objectName = "test/mulitUpload-test";
		
Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
inputInit.setContentType("contentType");
InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);

String multipart_upload_name = objectKey;
// init multipart_upload_id
String multipart_upload_id = initOutput.getUploadID();

System.out.println("-multipart_upload_id----" + initOutput.getUploadID());
    
//UploadMultipart
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
	
// Write code here that turns the phrase above into concrete actions
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