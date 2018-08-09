## PUT Object - Fetch 对象导入

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

``` java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。


对象创建完毕后，我们需要执行真正的对象导入操作：

```java
putObjectFetch(bucket, "https://www.qingcloud.com/static/assets/images/icons/common/footer_logo.svg", "folder-fetched/qingcloud_footer_logo.svg");
```

```java
    private void putObjectFetch(Bucket bucket, String fetchSource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSFetchSource(fetchSource); // Fetch source looks like this: "protocol://host[:port]/[path]"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-fetched/fileName"
            if (output.getStatueCode() == 201) {
                // Success
                System.out.println("Put Object - Fetch success.");
            } else {
                // Failed
                System.out.println("Put Object - Fetch failed.");
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
