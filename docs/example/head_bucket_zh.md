## 获取 Bucket 元信息(Head Bucket)

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

``` java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。


对象创建完毕后，我们需要执行真正的获取 Bucket 元信息操作：

```java
    private void headBucket(Bucket bucket) {
        try {
            Bucket.HeadBucketOutput output = bucket.head();
            if (output.getStatueCode() == 200) {
                System.out.println("Head Bucket success.");
                // You can print http headers/parameters you defined here.
                // See [Get/Set HTTP Headers/Parameters Of A Request](./get_set_http_headers.md)
                System.out.println("You can access the Bucket.");
                System.out.println("You can print http headers/parameters you defined here.");
            } else {
                // Failed
                System.out.println("Head Bucket: You cannot access the Bucket or it does not exist.");
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

上面代码中出现的函数：
- bucket.head() 在 `pek3a` 区域 head 一个名为 `testBucketName` 的 Bucket。 

上面代码中出现的对象：
- output 对象是 bucket.head() 函数的返回体。
- output.getMessage() 是 output 对象的一个方法，用于返回错误信息。

