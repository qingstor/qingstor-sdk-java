## 列取对象

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以展示 Bucket 所有对象

```java

Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
input.setLimit(20);
Bucket.ListObjectsOutput listObjectsOutput = bucket.listObjects(input);
List<KeyModel> objectKeys = listObjectsOutput.getKeys();


```

在 List Bucket Objects 时添加筛选条件

参考[对应的 API 文档](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/bucket/basic_opt/get/)，您可以在对应的 Input 设置并添加如下筛选条件：

| 参数名称  |  类型   |                                                        描述                                                         | 是否必须 |
| :-------: | :-----: | :-----------------------------------------------------------------------------------------------------------------: | :------: |
|  prefix   | String  |                                    限定返回的 object key 必须以 prefix 作为前缀                                     |    否    |
| delimiter |  Char   | 是一个用于对 Object 名字进行分组的字符。所有名字包含指定的前缀且第一次出现 delimiter 字符之间的 object 作为一组元素 |    否    |
|  marker   | String  |                                  设定结果从 marker 之后按字母排序的第一个开始返回                                   |    否    |
|   limit   | Integer |                           限定此次返回 object 的最大数量，默认值为 200，最大允许设置 1000                           |    否    |

以下代码是展示 Bucket 内 _test_ 文件夹的所有对象，并以文件名排序。

```java
String next_marker = listObjects(bucket, "test/", "");
```

如返回值不为空，说明还有下一页数据，可以继续访问：

```Java
if (null != next_marker && !"".equals(next_marker.trim())) {
    // Page 2.  第二页
    String page2NextMarker = examples.listObjects(bucket, "", next_marker);
    // ...
}
```

```java
    /**
     * List objects start with prefix(folderName), after a object named marker. Limit up to 100 data.
     * 列取所有前缀为 prefix(folderName) ，在 marker 之后的对象。每次最多 100 条数据。
     * @param bucket bucket
     * @param prefix prefix(folderName)
     * @param marker View the data on the next page. The marker is the value of next_marker returned by the last Response
     *               查看下一页的数据。marker 为上一次 Response 返回的 next_marker 的值
     * @return next_marker
     */
    public String listObjects(Bucket bucket, String prefix, String marker) {
        Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
        if (null != prefix && !"".equals(prefix)) {
            // Only show objects name start with 'prefix'
            input.setPrefix(prefix);
        }

        if (null != marker && !"".equals(marker)) {
            // Sort by name after a object named marker
            input.setMarker(marker);
        }

        input.setDelimiter("/");
        input.setLimit(100); // Default 200, max 1000
        try {
            Bucket.ListObjectsOutput output = bucket.listObjects(input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("List Objects success.");

                System.out.println("=======List Objects:Folders======");
                List<String> commonPrefixes = output.getCommonPrefixes();
                for (String folderName : commonPrefixes) {
                    System.out.println("folderName = " + folderName);
                }
                System.out.println("=======List Objects:Files======");
                List<Types.KeyModel> keys = output.getKeys();
                if (keys != null && keys.size() > 0) {
                    System.out.println("keys = " + new Gson().toJson(keys));
                }
                System.out.println("=============");

                return output.getNextMarker();
            } else {
                // Failed
                System.out.println("List Objects failed.");
                handleError(output);
            }
        } catch (QSException e) {
            // NetWork exception
            e.printStackTrace();
        }

        return null;
    }
```
