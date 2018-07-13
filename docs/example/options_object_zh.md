## OPTIONS Object

### 请求头(Request Headers)

|名称|类型|描述|是否必要|
|:--:|:--:|:--|:--:|
|Origin|String|跨源请求的源。|Yes|
|Access-Control-Request-Method|String|跨源请求的 HTTP method 。|Yes|
|Access-Control-Request-Headers|String|跨源请求中的 HTTP headers (逗号分割的字符串)。|No|

访问 [API Docs](https://docs.qingcloud.com/qingstor/api/object/options.html) 以查看更多关于请求头的信息。

### 响应头(Response Headers)

|名称|类型|描述|
|:--:|:--:|:--|
|Access-Control-Allow-Origin|String|跨源请求所允许的源。如果跨源请求没有被允许，该头信息将不会存在于响应头中。|
|Access-Control-Max-Age|String|预检请求的结果被缓存的时间（单位为秒）。|
|Access-Control-Allow-Methods|String|跨源请求中的 HTTP method 。如果跨源请求没有被允许，该头信息将不会存在于响应头中。|
|Access-Control-Allow-Headers|String|跨源请求中可以被允许发送的 HTTP headers (逗号分割的字符串)。|
|Access-Control-Expose-Headers|String|跨源请求的响应中,客户端（如 JavaScript Client） 可以获取到的 HTTP headers (逗号分割的字符串)。|

访问 [API Docs](https://docs.qingcloud.com/qingstor/api/object/options.html) 以查看更多关于响应头的信息。

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以 OPTIONS Object


```java
    /**
     * Options Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.<br/>
     *                  objectKey 形如这种形式: "folder/fileName".<br/>
     *                  如果 objectKey = "fileName", 意味着该对象在当前 bucket 的根目录。
     */
    private void optionsObject(Bucket bucket, String objectKey) {
        try {
            Bucket.OptionsObjectInput input = new Bucket.OptionsObjectInput();
            input.setOrigin("Origin");
            input.setAccessControlRequestMethod("<http-method>");
            input.setAccessControlRequestHeaders("<request-header>");
            Bucket.OptionsObjectOutput output = bucket.optionsObject(objectKey, input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("Options Object success.");
                System.out.println("AccessControlAllowOrigin = " + output.getAccessControlAllowOrigin());
                System.out.println("AccessControlMaxAge = " + output.getAccessControlMaxAge());
                System.out.println("AccessControlAllowMethods = " + output.getAccessControlAllowMethods());
                System.out.println("AccessControlAllowHeaders = " + output.getAccessControlAllowHeaders());
                System.out.println("AccessControlExposeHeaders = " + output.getAccessControlExposeHeaders());
            } else {
                // Failed
                System.out.println("Failed to Options Object.");
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