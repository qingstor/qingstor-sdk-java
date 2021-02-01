## PUT Bucket Policy

### 请求消息体

访问 [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html) 以查看更多关于请求消息体的信息。

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 put bucket Policy

```java
    private void putBucketPolicy(Bucket bucket) {
        try {
            Bucket.PutBucketPolicyInput input = new Bucket.PutBucketPolicyInput();
            List<Types.StatementModel> statement = new ArrayList<>();
            Types.StatementModel statementModel = new Types.StatementModel();
            // ID
            statementModel.setID("allow certain site to get objects");
            // User
            List<String> user = new ArrayList<>();
            user.add("*");
            statementModel.setUser(user);
            // Action
            List<String> action = new ArrayList<>();
            action.add("get_object");
            statementModel.setAction(action);
            // Effect
            statementModel.setEffect("allow");
            // Resource
            List<String> resource = new ArrayList<>();
            resource.add(bucketName + "/*");
            statementModel.setResource(resource);
            // Condition
            Types.ConditionModel conditionModel = new Types.ConditionModel();
            Types.StringLikeModel stringLikeModel = new Types.StringLikeModel();
            List<String> referer = new ArrayList<>();
            referer.add("*.example1.com");
            referer.add("*.example2.com");
            stringLikeModel.setReferer(referer);
            conditionModel.setStringLike(stringLikeModel);
            statementModel.setCondition(conditionModel);

            statement.add(statementModel);
            input.setStatement(statement);
            Bucket.PutBucketPolicyOutput output = bucket.putPolicy(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket Policy OK.");
            } else {
                // Failed
                System.out.println("PUT Bucket Policy failed.");
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
