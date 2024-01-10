## PUT Bucket CORS

### 请求消息体

|      名称       |  类型   | 描述                                                                                                             | 是否必要 |
| :-------------: | :-----: | :--------------------------------------------------------------------------------------------------------------- | :------: |
|   cors_rules    |  Array  | 跨源的规则配置，每组配置项中的元素解释如下。                                                                     |   Yes    |
| allowed_origin  | String  | 用户所期望的跨源请求来源,可以用 ‘\*’ 来进行通配。                                                                |   Yes    |
| allowed_methods |  Array  | 设置源所允许的 HTTP 方法。可指定以下值的组合: “GET”, “PUT”, “POST”, “DELETE”, “HEAD”, 或者使用 ‘\*’ 来进行设置。 |   Yes    |
| allowed_headers |  Array  | 设置源所允许的 HTTP header 。 可以用 ‘\*’ 来进行通配。                                                           |    No    |
| expose_headers  |  Array  | 设置客户能够从其应用程序（例如，从 JavaScript XMLHttpRequest 对象）进行访问的 HTTP 响应头。                      |    No    |
| max_age_seconds | Integer | 设置在预检请求(Options)被资源、HTTP 方法和源识别之后，浏览器将为预检请求缓存响应的时间（以秒为单位）。           |    No    |

访问 [API Docs](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/bucket/cors/put_cors/) 以查看更多关于请求消息体的信息。

### 代码片段

用 access-key-id 和 secret-access-key 初始化 Bucket 服务。

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 PUT Bucket CORS

```java
    private void putBucketCORS(Bucket bucket) {
        Bucket.PutBucketCORSInput input = new Bucket.PutBucketCORSInput();
        List<Types.CORSRuleModel> corsRuleModels = new ArrayList<>();
        Types.CORSRuleModel corsRuleModel = new Types.CORSRuleModel();
        // Allowed_origin
        corsRuleModel.setAllowedOrigin("http://*.qingcloud.com");
        // Allowed_methods
        List<String> allowed_methods = new ArrayList<>();
        allowed_methods.add("PUT");
        allowed_methods.add("GET");
        allowed_methods.add("DELETE");
        allowed_methods.add("POST");
        corsRuleModel.setAllowedMethods(allowed_methods);
        // Allowed_headers
        List<String> allowed_headers = new ArrayList<>();
        allowed_headers.add("x-qs-date");
        allowed_headers.add("Content-Type");
        allowed_headers.add("Content-MD5");
        allowed_headers.add("Authorization");
        corsRuleModel.setAllowedHeaders(allowed_headers);
        // Max_age_seconds
        corsRuleModel.setMaxAgeSeconds(200);
        // Expose_headers
        List<String> expose_headers = new ArrayList<>();
        expose_headers.add("x-qs-date");
        corsRuleModel.setExposeHeaders(expose_headers);

        corsRuleModels.add(corsRuleModel);
        input.setCORSRules(corsRuleModels);
        try {
            Bucket.PutBucketCORSOutput output = bucket.putCORS(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket CORS OK.");
            } else {
                // Failed
                System.out.println("PUT Bucket CORS failed.");
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
