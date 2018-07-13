## GET Bucket CORS

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以 GET Bucket CORS


```java
    private void getBucketCORS(Bucket bucket) {
        try {
            Bucket.GetBucketCORSOutput output = bucket.getCORS();
            if (output.getStatueCode() == 200) {
                System.out.println("====Get Bucket CORS====");
                List<Types.CORSRuleModel> corsRules = output.getCORSRules();
                if (corsRules != null && corsRules.size() > 0) {
                    System.out.println("CorsRules = " + new Gson().toJson(corsRules));
                } else {
                    System.out.println("CorsRules list is empty, CORS is empty.");
                }
                System.out.println("========");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket CORS.");
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