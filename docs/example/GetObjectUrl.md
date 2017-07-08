## GET Object Download Url Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can get  object signature url


```

long expiresTime = new Date().getTime() / 1000 + 1000;
String objectUrl = bucket.GetObjectSignatureUrl(objectName, expiresTime);

Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);

```
get object by signature url

```

Bucket.GetObjectOutput output = bucket.GetObjectBySignatureUrl(url);


```