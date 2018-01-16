## 文件移动



### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以移动文件：


```

Bucket.PutObjectInput input = new Bucket.PutObjectInput();
input.setXQSMoveSource("/sh-test/sourceObject");
Bucket.PutObjectOutput copyOutput = bucket.putObject(objectName + "toMove", input);

System.out.println(objectName + "qsMoveObject:" + copyOutput.getMessage());


```