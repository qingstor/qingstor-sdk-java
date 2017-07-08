## DeleteObject Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can delete objects


```

Bucket.DeleteObjectOutput out = bucket.deleteObject(objectName);
System.out.println(out.getMessage());

```