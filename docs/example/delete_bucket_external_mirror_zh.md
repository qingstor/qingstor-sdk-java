## DELETE Bucket External Mirror

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 DELETE Bucket External Mirror


```java
    private void deleteBucketExternalMirror(Bucket bucket) {
        try {
            Bucket.DeleteBucketExternalMirrorOutput output = bucket.deleteExternalMirror();
            if (output.getStatueCode() == 204) {
                System.out.println("Delete bucket external mirror OK.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket external mirror.");
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