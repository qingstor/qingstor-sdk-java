## GET Bucket ACL

首先我们需要初始化一个 Bucket 对象来对 Bucket 进行操作：

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

对象创建完毕后，我们需要执行真正的获取 Bucket ACL 操作：

```java
    private void getBucketACL(Bucket bucket) {
        try {
            Bucket.GetBucketACLOutput output = bucket.getACL();
            if (output.getStatueCode() == 200) {
                // Success
                Types.OwnerModel owner = output.getOwner();
                if (owner != null) {
                    System.out.println("owner = {'name': " + owner.getName() + ", 'ID': " + owner.getID() + "}");
                } else {
                    System.out.println("owner = null");
                }
                System.out.println("=======Get Bucket ACL======");
                List<Types.ACLModel> acls = output.getACL();
                if (acls != null && acls.size() > 0) {
                    System.out.println("ACLs = " + new Gson().toJson(acls));
                } else {
                    System.out.println("ACLs is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to Get Bucket ACL.");
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
