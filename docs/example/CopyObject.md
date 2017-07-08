## CopyObject Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can copy objects


```

Bucket.PutObjectInput input = new Bucket.PutObjectInput();
input.setXQSCopySource("/testBucketName/objectName");

Bucket.PutObjectOutput copyOutput = bucket.putObject("objectName-test-copy", input);
System.out.println(copyOutput.getMessage());


```