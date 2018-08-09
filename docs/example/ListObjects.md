## ListObjects Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

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
listObjects(bucket, "test/");
```

```java
    private void listObjects(Bucket bucket, String prefix) {
        Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
        if (null != prefix && !"".equals(prefix)) {
            // Only show objects name start with 'prefix'
            input.setPrefix(prefix);
            input.setMarker(prefix); // Sort by name after a object named prefix
        }
        input.setLimit(1000); // Default 200, max 1000
        try {
            bucket.listObjectsAsync(input, new ResponseCallBack<Bucket.ListObjectsOutput>() {
                @Override
                public void onAPIResponse(Bucket.ListObjectsOutput output) throws QSException {
                    if (output.getStatueCode() == 200) {
                        // Success
                        System.out.println("=======List Objects======");
                        List<Types.KeyModel> keys = output.getKeys();
                        if (keys != null && keys.size() > 0) {
                            System.out.println("List Objects success.");
                            System.out.println("keys = " + new Gson().toJson(keys));
                        } else {
                            System.out.println("List Objects: Successfully Connected. Maybe the bucket is empty.");
                            System.out.println("Bucket owner = " + output.getOwner().getName());
                        }
                        System.out.println("=============");
                    } else {
                        // Failed
                        System.out.println("List Objects failed.");
                        System.out.println("getStatueCode = " + output.getStatueCode());
                        System.out.println("getCode = " + output.getCode());
                        System.out.println("getMessage = " + output.getMessage());
                        System.out.println("getRequestId = " + output.getRequestId());
                        System.out.println("getUrl = " + output.getUrl());
                    }
                }
            });
        } catch (QSException e) {
            // NetWork exception
            e.printStackTrace();
        }
    }
```