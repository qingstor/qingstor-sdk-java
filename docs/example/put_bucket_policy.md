## PUT Bucket Policy

### Request Elements

See [API Docs](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/bucket/policy/put_policy/) for more information about request elements.

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can put Bucket Policy

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
