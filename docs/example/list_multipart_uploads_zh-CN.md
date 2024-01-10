## 列取分段上传(List Multipart Uploads)

获取正在进行的分段上传对象的列表。当一个对象通过 Initiate Multipart 接口开启了分段上传模式，在调用 Complete Multipart 或 Abort Multipart 接口之前，该对象处于“正在进行分段上传”的状态，此对象将会出现在该接口返回的列表里。

与 GET Bucket (List Objects) 接口类似，用户可以通过传递 prefix, delimiter 请求参数，指定获取某个目录下面正在进行的分段上传。列表按照对象名称的 alphanumeric 顺序从小到大排序。如果同名对象有多个分段上传，翻页被截断后只显示了一部分，下次翻页可通过 upload_id_marker 参数，获取该 upload_id 往后按创建时间排序后剩下的分段上传。

如果用户只想获取某个对象已经上传的分段，请查阅 [API Docs](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/multipart/list/).

### 请求参数

在 List Bucket Objects 时添加筛选条件

参考[对应的 API 文档](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/multipart/list/)，您可以在对应的 Input 设置并添加如下筛选条件：

|      参数名      |  类型   | 描述                                                                                                | 是否必要 |
| :--------------: | :-----: | :-------------------------------------------------------------------------------------------------- | :------: |
|      prefix      | String  | 限定返回的分段上传对象名必须以 prefix 作为前缀                                                      |    No    |
|    delimiter     |  Char   | 用于给对象名分组的字符。返回的对象名是从指定的 prefix 开始，到第一次出现 delimiter 字符之间的对象名 |    No    |
|    key_marker    | String  | 设定结果从 key 之后按字母排序的第一个分段上传开始返回                                               |    No    |
| upload_id_marker | String  | 设定结果从 upload_id 之后按时间排序的第一个分段上传开始返回                                         |    No    |
|      limit       | Integer | 限定此次返回的分段对象的最大数量，默认值为 200，最大允许设置 1000                                   |    No    |

### 代码片段

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

对象创建完毕后，我们需要执行真正的列取分段上传(List Multipart Uploads)操作：

```java
    private void listMultipartUploads(Bucket bucket) {
        try {
            Bucket.ListMultipartUploadsInput input = new Bucket.ListMultipartUploadsInput();
            // See Request Parameters to set input
            Bucket.ListMultipartUploadsOutput output = bucket.listMultipartUploads(input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("=======List Multipart Uploads======");
                System.out.println("Name = " + output.getName());
                List<Types.UploadsModel> uploads = output.getUploads();
                if (uploads != null && uploads.size() > 0) {
                    System.out.println("Uploads = " + new Gson().toJson(uploads));
                } else {
                    System.out.println("Uploads is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to List Multipart Uploads.");
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
