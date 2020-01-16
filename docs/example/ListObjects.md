## ListObjects Example



### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can list objects


```

Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
input.setLimit(20);
Bucket.ListObjectsOutput listObjectsOutput = bucket.listObjects(input);
List<KeyModel> objectKeys = listObjectsOutput.getKeys();

```


Add some options when list bucket objects

You can set options below in ListObjectsInput. See controlled [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/get).

|Parameter name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|prefix|String|Limits the response to keys that begin with the specified prefix.|No|
|delimiter|Char|A delimiter is a character you use to group keys.<br/>If you specify a prefix, all keys that contain the same string between the prefix and the first occurrence of the delimiter after the prefix are grouped under a single result element called CommonPrefixes.|No|
|marker|String|Specifies the key to start with when listing objects in a bucket.|No|
|limit|Integer|Sets the maximum number of objects returned in the response body. Default is 200, maximum is 1000.|No|

Below codes show objects sort by name in the folder *test*.

```java
String next_marker = listObjects(bucket, "test/", "");
```

If the return value is not empty, the next page of data can be accessed further:

```Java
if (null != next_marker && !"".equals(next_marker.trim())) {
    // Page 2.  第二页
    String page2NextMarker = examples.listObjects(bucket, "", next_marker);
    // ...
}
```

```Java
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