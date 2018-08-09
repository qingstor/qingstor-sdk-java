## 对象拷贝(PUT Object - Copy)



### 代码片段

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以拷贝对象


```
    private void putObjectCopy(Bucket bucket, String copySource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSCopySource(copySource); // CopySource looks like this: "/bucketName/folder/fileName"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-copied/fileName"
            if (output.getStatueCode() == 201) {
                // Created
                System.out.println("Put Object Copy: Copied.");
                System.out.println("From " + copySource + " to " + newObjectKey);
            } else {
                // Failed
                System.out.println("Put Object Copy: Failed to copy.");
                System.out.println("From " + copySource + " to " + newObjectKey);
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