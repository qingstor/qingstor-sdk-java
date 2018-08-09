## GET Bucket Lifecycle

Initialize the Bucket service with accesskeyid and secretaccesskey

``` java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：
- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.


After created the object, we need perform the action to GET Bucket Lifecycle：

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