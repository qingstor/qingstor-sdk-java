## GET Object Download Url Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey.

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

Then you can get  object signature url.


```

long expiresTime = new Date().getTime() / 1000 + 60 * 10; // Expired in 600 seconds(10 minutes).
String objectUrl = bucket.GetObjectSignatureUrl(objectName, expiresTime);
// Get object by signature url

Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);

```

If you open the url above in the browser, you may see the file preview instead of downloading.

To get a url for downloading only, use the method below.

```
Bucket.GetObjectInput inputs = new Bucket.GetObjectInput();
String keyName = QSStringUtil.percentEncode("测试图片(测试).jpg", "utf-8");
inputs.setResponseContentDisposition(String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", keyName, keyName));
RequestHandler handle = bucket.GetObjectBySignatureUrlRequest("测试图片(测试).jpg", inputs, System.currentTimeMillis()/1000 + 10000);
String tempUrl = handle.getExpiresRequestUrl();

```
