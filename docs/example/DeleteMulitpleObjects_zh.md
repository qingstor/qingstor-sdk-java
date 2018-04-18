## 删除多个对象



### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以删除这些对象：

```

Bucket.DeleteMultipleObjectsInput deleteInput = new Bucket.DeleteMultipleObjectsInput();
List<KeyModel> lstKey = new ArrayList();
KeyModel m1 = new KeyModel();
m1.setKey("objectName1");
lstKey.add(m1);

KeyModel m2 = new KeyModel();
m2.setKey("objectName2");
lstKey.add(m2);
deleteInput.setObjects(lstKey);

Bucket.DeleteMultipleObjectsOutput output = bucket.deleteMultipleObjects(deleteInput);
System.out.println("delete:" + output.getMessage());

```