## 获取文件的下载地址

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后你可以获得该对象的签名地址：

```java

long expiresTime = new Date().getTime() / 1000 + 60 * 10; // 600秒（10分钟）后过期
String objectUrl = bucket.GetObjectSignatureUrl(objectName, expiresTime);
// 通过已签名的URL获取对象：
Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);

```

以上方法获取到的地址直接用浏览器打开。浏览器会对已知格式直接进行预览，如果您想得到直接下载的链接并重命名文件，请使用以下方法：

```java
Bucket.GetObjectInput inputs = new Bucket.GetObjectInput();
String keyName = QSStringUtil.percentEncode("测试图片(测试).jpg", "utf-8");
inputs.setResponseContentDisposition(String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", keyName, keyName));
RequestHandler handle = bucket.GetObjectBySignatureUrlRequest("测试图片(测试).jpg", inputs, System.currentTimeMillis()/1000 + 10000);
String tempUrl = handle.getExpiresRequestUrl();

```
