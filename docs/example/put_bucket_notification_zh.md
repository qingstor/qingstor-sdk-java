## PUT Bucket Notification

### 请求消息体

|名称|类型|描述|是否必要|
|:--:|:--:|:--|:--:|
|notifications|Array|bucket notification 的配置规则，配置项中的元素解释见下|Yes|
|id|String|通知配置的标识|Yes|
|event_types|Array|事件的类型，每当该类型的事件被触发时，发出通知。<br>目前支持的类型为: <br> - “create_object”: 创建对象完成 <br> - “delete_object”: 删除对象完成<br> - “abort_multipart”: 终止分段上传<br> - “complete_multipart”: 完成分段上传|Yes|
|object_filters|Array|对象名匹配规则(glob patterns)|no|
|cloudfunc|String|事件处理云服务，接收通知中触发的事件并进行处理。目前支持:<br> - tupu-porn: 图谱鉴黄服务<br> - notifier: 通知服务, 将 QingStor 事件推送到 notify_url<br> - image: 图片基本处理服务|Yes|
|cloudfunc_args|Object|提供给 cloudfunc 的自定义参数|No|
|notify_url|String|通知事件处理结果的 url ，当事件处理完成后，会将处理结果以 POST 方式向 notify_url 请求。<br>如果 POST 超时，将会重试，超时时间是 5s， 重试间隔为 1s。|No|

#### 图片基本处理服务参数
当设置 cloudfunc 为 image 时, 需要设置 cloudfunc_args 为以下参数，对象存储将按照指定的图片处理规则对图片进行处理，并将结果另存回对象存储。

|名称|类型|描述|是否必要|
|:--:|:--:|:--|:--:|
|action|String|图片的具体操作参数, 见 [图片基本处理服务](https://docs.qingcloud.com/qingstor/data_process/image_process/index.html)|Yes|
|key_prefix|String|处理后 object 名称的前缀, 默认为 “gen”|No|
|key_seprate|String|key_prefix 和 object 之间的分隔符，默认为 “_“|No|
|save_bucket|String|另存为的目标 bucket 名称，默认为当前 object 所在 bucket|No|

访问 [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html) 以查看更多关于请求消息体的信息。

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以 PUT Bucket Notification


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