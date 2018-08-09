## 文件移动



### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以移动文件：


```
    private void putObjectMove(Bucket bucket, String moveSource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSMoveSource(moveSource); // MoveSource looks like this: "/bucketName/folder/fileName"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-moved/fileName"
            if (output.getStatueCode() == 201) {
                // Created
                System.out.println("Put Object Move: Moved.");
                System.out.println("From " + moveSource + " to " + newObjectKey);
            } else {
                // Failed
                System.out.println("Put Object Move: Failed to move.");
                System.out.println("From " + moveSource + " to " + newObjectKey);
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