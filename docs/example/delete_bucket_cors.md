## DELETE Bucket CORS

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can DELETE Bucket CORS

```java
    private void deleteBucketCORS(Bucket bucket) {
        try {
            Bucket.DeleteBucketCORSOutput output = bucket.deleteCORS();
            if (output.getStatueCode() == 204) {
                System.out.println("Delete bucket CORS OK.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket CORS.");
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
