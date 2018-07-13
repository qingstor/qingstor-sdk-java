## OPTIONS Object

### Request Headers

|Name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|Origin|String|Identifies the origin of the cross-origin request.|Yes|
|Access-Control-Request-Method|String|Identifies what HTTP method will be used in the actual request.|Yes|
|Access-Control-Request-Headers|String|A comma-delimited list of HTTP headers that will be sent in the actual request.|No|

See [API Docs](https://docs.qingcloud.com/qingstor/api/object/options.html) for more information about request headers.

### Response Headers

|Name|Type|Description|
|:--:|:--:|:--|
|Access-Control-Allow-Origin|String|The origin you sent in your request. If the origin in your request is not allowed, QingStor will not include this header in the response.|
|Access-Control-Max-Age|String|How long, in seconds, the results of the preflight request can be cached.|
|Access-Control-Allow-Methods|String|The HTTP method that was sent in the original request. If the method in the request is not allowed, QingStor will not include this header in the response.|
|Access-Control-Allow-Headers|String|A comma-delimited list of HTTP headers that the browser can send in the actual request. If any of the requested headers is not allowed, QingStor will not include that header in the response, nor will the response contain any of the headers with the Access-Control prefix.|
|Access-Control-Expose-Headers|String|A comma-delimited list of HTTP headers. This header provides the JavaScript client with access to these headers in the response to the actual request.|

See [API Docs](https://docs.qingcloud.com/qingstor/api/object/options.html) for more information about response headers.

### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can OPTIONS Object


```java
    /**
     * Options Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
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