## GET Bucket Lifecycle

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

``` java
EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。


对象创建完毕后，我们需要执行真正的获取 Bucket Lifecycle 操作：

```java
    private void getBucketLifecycle(Bucket bucket) {
        try {
            Bucket.GetBucketLifecycleOutput output = bucket.getLifecycle();
            if (output.getStatueCode() == 200) {
                System.out.println("======GET Bucket Lifecycle=======");
                List<Types.RuleModel> rules = output.getRule();
                if (rules != null && rules.size() > 0) {
                    System.out.println("Rules = " + new Gson().toJson(rules));
                } else {
                    System.out.println("GET Bucket Lifecycle: Rules is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket Lifecycle.");
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