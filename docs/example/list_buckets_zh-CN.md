## 列取 Buckets

### 代码片段

用 access-key-id 和 secret-access-key 初始化 EnvContext 对象。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";

```

然后您可以展示您所有的 Buckets

```Java
    private void listBuckets(EnvContext context) {
        QingStor stor = new QingStor(context);
        try {
            QingStor.ListBucketsOutput output = stor.listBuckets(null);
            if (output.getStatueCode() == 200) {
                System.out.println("Count = " + output.getCount());

                List<Types.BucketModel> buckets = output.getBuckets();
                System.out.println("buckets = " + new Gson().toJson(buckets));

            } else {
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
```
