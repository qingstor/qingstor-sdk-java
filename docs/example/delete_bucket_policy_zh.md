## DELETE Bucket Policy

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 DELETE Bucket Policy


```java
    private void deleteBucketPolicy(Bucket bucket) {
        try {
            Bucket.DeleteBucketPolicyOutput output = bucket.deletePolicy();
            if (output.getStatueCode() == 204) {
                System.out.println("The policy of bucket deleted.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket policy.");
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