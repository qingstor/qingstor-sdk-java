## PutACL 示例

### 请求消息体

|名称|类型|描述|
|:--:|:--:|:--|
|acl|List|支持设置 0 到多个被授权者|
|grantee|Dict|支持 user, group 两种类型，当设置 user 类型时，需要给出 user id；当设置 group 类型时，目前只支持 QS_ALL_USERS，代表所有用户|
|permission|String|支持三种权限为 READ, WRITE, FULL_CONTROL|

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

然后您可以 put bucket ACL


```java
    private void putBucketACL(Bucket bucket) {
        try {
            Bucket.PutBucketACLInput input = new Bucket.PutBucketACLInput();
            Types.ACLModel acl = new Types.ACLModel();
            acl.setPermission("FULL_CONTROL");
            Types.GranteeModel gm = new Types.GranteeModel();
            gm.setName("QS_ALL_USERS");
            gm.setType("group");
            acl.setGrantee(gm);
            List<Types.ACLModel> lstACL = new ArrayList<>();
            lstACL.add(acl);
            input.setACL(lstACL);
            Bucket.PutBucketACLOutput output = bucket.putACL(input);
            if (output.getStatueCode() == 200) {
                System.out.println("Put bucket ACL OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket ACL.");
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