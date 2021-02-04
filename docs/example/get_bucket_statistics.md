## Get Bucket Statistics

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：

- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.

After created the object, we need perform the action to Get Bucket Statistics：

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
