## GetObject Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can get objects


```

Bucket.GetObjectInput input = new Bucket.GetObjectInput();
Bucket.GetObjectOutput getObjectOutput = bucket.getObject(objectName, input);
int iLength = 0;
if (getObjectOutput != null && getObjectOutput.getBodyInputStream() != null) {
    File downFile = new File("/tmp/get_object.txt");
    OutputStream out = new FileOutputStream(downFile);
    int bytesRead = 0;
    byte[] buffer = new byte[1024];
    while ((bytesRead = getObjectOutput.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
        out.write(buffer, 0, bytesRead);
        iLength += bytesRead;
    }
    out.close();
    getObjectOutput.getBodyInputStream().close();
}


```