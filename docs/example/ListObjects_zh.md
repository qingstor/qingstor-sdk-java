## 展示 Bucket 所有对象



### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以展示Bucket所有对象

```

Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
input.setLimit(20);
Bucket.ListObjectsOutput listObjectsOutput = bucket.listObjects(input);
List<KeyModel> objectKeys = listObjectsOutput.getKeys();


```

在 List Bucket Objects 时添加筛选条件

参考[对应的 API 文档](https://docs.qingcloud.com/qingstor/api/bucket/get)，您可以在对应的 Input 设置并添加如下筛选条件：

|参数名称|类型|描述|是否必须|
|:--:|:--:|:--:|:--:|
|prefix|String|限定返回的 object key 必须以 prefix 作为前缀|否|
|delimiter|Char|是一个用于对 Object 名字进行分组的字符。所有名字包含指定的前缀且第一次出现 delimiter 字符之间的 object 作为一组元素|否|
|marker|String|设定结果从 marker 之后按字母排序的第一个开始返回|否|
|limit|Integer|限定此次返回 object 的最大数量，默认值为 200，最大允许设置 1000|否|

以下代码是展示 Bucket 内 *test* 文件夹的所有对象，并以文件名排序。

```java
    Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
    String folderName = "test/";
    // Only show objects name start with 'folderName', that is list objects in a folder named "test"
    input.setPrefix(folderName);
    input.setLimit(1000); // Default 200, max 1000
    input.setMarker(folderName); // Sort by name after a object named folderName: "test/"
```