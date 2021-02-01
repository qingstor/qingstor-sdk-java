## Bucket 使用统计

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

对象创建完毕后，我们需要执行真正的 Bucket 使用统计的操作：

```java
    private void getBucketStatistics(Bucket bucket) {
        try {
            Bucket.GetBucketStatisticsOutput output = bucket.getStatistics();
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("Get Bucket Statistics success.");
                System.out.println("Name = " + output.getName());
                System.out.println("Created = " + output.getCreated());
                System.out.println("Location = " + output.getLocation());
                System.out.println("Status = " + output.getStatus());
                System.out.println("URL = " + output.getURL()); // The method is different of output.getUrl().
                System.out.println("Count = " + output.getCount());
                System.out.println("Size = " + output.getSize());
            } else {
                // Failed
                System.out.println("Failed to Get Bucket Statistics.");
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
