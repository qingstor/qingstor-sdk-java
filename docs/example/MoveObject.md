## MoveObject Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can move objects


```

Bucket.PutObjectInput input = new Bucket.PutObjectInput();
input.setXQSMoveSource("/sh-test/sourceObject");
Bucket.PutObjectOutput copyOutput = bucket.putObject(objectName + "toMove", input);

System.out.println(objectName + "qsMoveObject:" + copyOutput.getMessage());


```