## GET Object Download Url Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey.

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

Then you can get  object signature url.


```

long expiresTime = new Date().getTime() / 1000 + 1000;
String objectUrl = bucket.GetObjectSignatureUrl(objectName, expiresTime);

Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);

```
get object by signature url

```

Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);


```


If you open the url above in browser, you may see the file view instead of downloading.

To get a url for downloading only, use the method below.

```
Bucket.GetObjectInput inputs = new Bucket.GetObjectInput();
String fileName = "测试图片(测试).jpg";
String urlName = URLEncoder.encode(fileName, "utf-8");
inputs.setResponseContentDisposition("attachment;filename=" + urlName);
RequestHandler handle = bucket.GetObjectBySignatureUrlRequest("测试图片(测试).jpg", inputs, System.currentTimeMillis() + 10000);
String tempUrl = handle.getExpiresRequestUrl();

```
