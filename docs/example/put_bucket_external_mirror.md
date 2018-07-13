## PUT Bucket External Mirror

### Request Elements

|Name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|source_site|String|Source site of external mirror source. Source site is like this: `<protocol>://<host>[:port]/[path]` . Valid values of protocol: “http” or “https”, default “http”. Port defaults to the port corresponding to the protocol. Path can be empty. If the storage space has multiple source sites for many times, the source site of the storage space will use the last setting value.|Yes|

See [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html) for more information about request elements.

### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can PUT Bucket External Mirror


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