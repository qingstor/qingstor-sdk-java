## GET Bucket Policy

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 GET Bucket Policy

```java
    private void getBucketPolicy(Bucket bucket) {
        try {
            Bucket.GetBucketPolicyOutput output = bucket.getPolicy();
            if (output.getStatueCode() == 200) {
                System.out.println("====Get Bucket Policy====");
                List<Types.StatementModel> statements = output.getStatement();
                if (statements != null && statements.size() > 0) {
                    System.out.println("Statements = " + new Gson().toJson(statements));
                } else {
                    System.out.println("Statement list is empty, Policy is empty.");
                }
                System.out.println("========");
            } else {
                // Failed
                System.out.println("Failed to Get Bucket Policy.");
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
