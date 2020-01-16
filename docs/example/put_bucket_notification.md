## PUT Bucket Notification

### Request Elements

|Name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|notifications|Array|The configuration rules you set in bucket notification, <br>the elements in the configuration item are explained below.|Yes|
|id|String|Optional unique identifier for each of the configurations in the NotificationConfiguration.|Yes|
|event_types|Array|The type of event. Whenever a type of event is triggered, a notification will be sent. <br>Now available types: <br> - “create_object”: When an object has been created <br> - “delete_object”: When an object has been deleted <br> - “abort_multipart”: When abort a multipart upload <br> - “complete_multipart”: When a multipart upload has been completed|Yes|
|object_filters|Array|Object name matching rule(glob patterns)|no|
|cloudfunc|String|Event handles cloud services, receives events triggered in the notification and processes them. <br>Now available: <br> - tupu-porn: Tupu porn check service <br> - notifier: Notification service. Push the QingStor events to notify_url<br> - image: Image basic processing service|Yes|
|cloudfunc_args|Object|Custom parameters provided to cloudfunc|No|
|notify_url|String|URL, which notifies the event processing result, <br>requests the processing result to be notify_url in method POST when the event is processed. <br>If the request with method POST is timeout, it will be retried. <br>The timeout time is 5S and the retry interval is 1s.|No|

#### Parameters in image basic processing service
When cloudfunc has been set as image, we need cloudfunc_args as below parameters. <br>
Object storage will process the image according to the specified picture processing rules and save the result back to object storage.

|Name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|action|String|The specific operation parameters of the picture, see [image basic processing service](https://docs.qingcloud.com/qingstor/data_process/image_process/index.html) for more info.|Yes|
|key_prefix|String|Prefix of the name of the object processed, “gen” is the default.|No|
|key_seprate|String|A separator between key_prefix and object, “_“ is the default.|No|
|save_bucket|String|The target bucket name that is saved as. <br>The default is the current bucket where the object is located.|No|

See [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html) for more information about request elements.

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can PUT Bucket Notification


```java
    private void putBucketNotification(Bucket bucket) {
        Bucket.PutBucketNotificationInput input = new Bucket.PutBucketNotificationInput();
        List<Types.NotificationModel> notifications = new ArrayList<>();
        Types.NotificationModel notification = new Types.NotificationModel();
        // Cloudfunc
        notification.setCloudfunc("tupu-porn");
        // EventTypes
        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("create_object");
        notification.setEventTypes(eventTypes);
        // ID
        notification.setID("notification-1");
        // ObjectFilters
        List<String> objectFilters = new ArrayList<>();
        objectFilters.add("*");
        objectFilters.add("test");
        notification.setObjectFilters(objectFilters);
        // NotifyURL
        notification.setNotifyURL("http://user_notify_url");
        notifications.add(notification);
        input.setNotifications(notifications);
        try {
            Bucket.PutBucketNotificationOutput output = bucket.putNotification(input);
            if (output.getStatueCode() == 200 || output.getStatueCode() == 201) {
                System.out.println("Put bucket notification OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket notification.");
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