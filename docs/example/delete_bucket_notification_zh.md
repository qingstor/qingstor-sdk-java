## DELETE Bucket Notification

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以 DELETE Bucket Notification


```java
    private void deleteBucketNotification(Bucket bucket) {
        try {
            Bucket.DeleteBucketNotificationOutput output = bucket.deleteNotification();
            if (output.getStatueCode() == 204) {
                System.out.println("The notification of bucket deleted.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket notification.");
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