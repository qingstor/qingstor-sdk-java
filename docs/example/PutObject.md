## PutObjects Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can put object


```

Bucket.PutObjectInput input = new Bucket.PutObjectInput();
File f = new File("/tmp/wifi-Bq5Lr5.log");
input.setContentType("text/plain; charset=utf-8");
input.setContentLength(f.length());
input.setBodyInputFile(f);
Bucket.PutObjectOutput out = bucket2.putObject("testPutObject", input);
System.out.println(out.getMessage());


```