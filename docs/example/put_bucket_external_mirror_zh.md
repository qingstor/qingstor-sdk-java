## PUT Bucket External Mirror

### 请求消息体

|名称|类型|描述|是否必要|
|:--:|:--:|:--|:--:|
|source_site|String|外部镜像回源的源站。源站形式为 `<protocol>://<host>[:port]/[path]` 。 protocol的值可为 “http” 或 “https”，默认为 “http”。port 默认为 protocol 对应的端口。path 可为空。 如果存储空间多次设置不同的源站，该存储空间的源站采用最后一次设置的值。|Yes|

访问 [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html) 以查看更多关于请求消息体的信息。

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 PUT Bucket External Mirror


```java
    private void putBucketExternalMirror(Bucket bucket) {
        Bucket.PutBucketExternalMirrorInput input = new Bucket.PutBucketExternalMirrorInput();
        input.setSourceSite("http://example.com:80/image/");
        try {
            Bucket.PutBucketExternalMirrorOutput output = bucket.putExternalMirror(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket External Mirror OK.");
            } else {
                // Failed
                System.out.println("PUT Bucket External Mirror failed.");
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