## DELETE Bucket CORS

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 DELETE Bucket CORS


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