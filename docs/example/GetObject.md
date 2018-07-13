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

```java
getObject(bucket, "folder/test.mp3", "/Users/chengww/Desktop/test.mp3");
```

```java
    /**
     * Get(Download) an object from the bucket.
     *
     * @param bucket        bucket
     * @param objectKey     looks like this: "folder/fileName".<br/>
     *                      If objectKey = "fileName", means the object is in the bucket's root folder.
     * @param localKeptPath the object will be kept in this path.
     */
    private void getObject(Bucket bucket, String objectKey, String localKeptPath) {
        try {
            Bucket.GetObjectInput input = new Bucket.GetObjectInput();
            Bucket.GetObjectOutput output = bucket.getObject(objectKey, input);
            if (output.getStatueCode() == 200) {
                InputStream inputStream = output.getBodyInputStream();
                if (inputStream != null) {
                    FileOutputStream fos = new FileOutputStream(localKeptPath);
                    int len;
                    byte[] bytes = new byte[4096];
                    while ((len = inputStream.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    inputStream.close();
                    fos.flush();
                    fos.close();
                    System.out.println("Get object success.");
                    System.out.println("ObjectKey = " + objectKey);
                    System.out.println("LocalKeptPath = " + localKeptPath);
                } else {
                    System.out.println("Get object status code == 200, but inputStream is null, skipped.");
                }
            } else {
                // Failed
                System.out.println("Failed to get object.");
                System.out.println("StatueCode = " + output.getStatueCode());
                System.out.println("Message = " + output.getMessage());
                System.out.println("RequestId = " + output.getRequestId());
                System.out.println("Code = " + output.getCode());
                System.out.println("Url = " + output.getUrl());
            }

        } catch (QSException | IOException e) {
            e.printStackTrace();
        }
    }

```