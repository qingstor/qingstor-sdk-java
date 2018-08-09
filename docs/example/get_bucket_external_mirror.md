## GET Bucket External Mirror

### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can GET Bucket External Mirror


```java
    private void getBucketExternalMirror(Bucket bucket) {
        try {
            Bucket.GetBucketExternalMirrorOutput output = bucket.getExternalMirror();
            if (output.getStatueCode() == 200) {
                String sourceSite = output.getSourceSite();
                System.out.println("GET Bucket External Mirror: SourceSite = " + sourceSite);
            } else {
                // Failed
                System.out.println("Failed to GET Bucket External Mirror.");
                System.out.println("StatueCode = " + output.getStatueCode());
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