## PUT Bucket CORS

### Request Elements

|      Name       |  Type   | Description                                                                                                                                                                         | Required |
| :-------------: | :-----: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :------: |
|   cors_rules    |  Array  | A set of origins and methods (cross-origin access that you want to allow). The elements in each set of configuration items are explained as follows.                                |   Yes    |
| allowed_origin  | String  | An origin that you want to allow cross-domain requests from. This can contain at most one \* wild character.                                                                        |   Yes    |
| allowed_methods |  Array  | An HTTP method that you want to allow the origin to execute. A combination of the following values can be specified: “GET”, “PUT”, “POST”, “DELETE”, “HEAD”, or use ‘\*’ to set up. |   Yes    |
| allowed_headers |  Array  | An HTTP header that you want to allow the origin to execute. This can contain at most one \* wild character.                                                                        |    No    |
| expose_headers  |  Array  | One or more headers in the response that you want customers to be able to access from their applications (for example, from a JavaScript XMLHttpRequest object).                    |    No    |
| max_age_seconds | Integer | The time in seconds that your browser is to cache the preflight response for the specified resource.(seconds)                                                                       |    No    |

See [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html) for more information about request elements.

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can PUT Bucket CORS

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
