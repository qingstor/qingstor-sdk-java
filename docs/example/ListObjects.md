## ListObjects Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

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
    Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
    String folderName = "test/";
    // Only show objects name start with 'folderName', that is list objects in a folder named "test"
    input.setPrefix(folderName);
    input.setLimit(1000); // Default 200, max 1000
    input.setMarker(folderName); // Sort by name after a object named folderName: "test/"
```