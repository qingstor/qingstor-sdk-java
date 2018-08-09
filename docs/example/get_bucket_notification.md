## GET Bucket Notification

Initialize the Bucket service with accesskeyid and secretaccesskey

``` java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：
- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.


After created the object, we need perform the action to GET Bucket Notification：

```java
    private void getBucketNotification(Bucket bucket) {
        try {
            Bucket.GetBucketNotificationOutput output = bucket.getNotification();
            if (output.getStatueCode() == 200) {
                System.out.println("=======GET Bucket Notification======");
                List<Types.NotificationModel> notifications = output.getNotifications();
                if (notifications != null && notifications.size() > 0) {
                    System.out.println("Notifications = " + new Gson().toJson(notifications));
                } else {
                    System.out.println("GET Bucket Notification: Notifications is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket Notification.");
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