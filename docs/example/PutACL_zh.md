## PutACL 示例

### 代码片段

用 accesskeyid 和 secretaccesskey 初始化 Bucket 服务。

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

然后您可以 put bucket ACL


```

Bucket.PutBucketACLInput input = new Bucket.PutBucketACLInput();
Types.ACLModel acl = new Types.ACLModel();
acl.setPermission("FULL_CONTROL");
Types.GranteeModel gm = new Types.GranteeModel();
gm.setName("QS_ALL_USERS");
gm.setType("group");
acl.setGrantee(gm);
List<Types.ACLModel> lstACL = new ArrayList<Types.ACLModel>();
lstACL.add(acl);
input.setACL(lstACL);
Bucket.PutBucketACLOutput putBucketACLOutput = bucket.putACL(input);
System.out.println(putBucketACLOutput.getMessage());


```