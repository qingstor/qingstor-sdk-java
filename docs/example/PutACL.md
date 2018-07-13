## PutACL Example

### Request Elements

|Name|Type|Description|
|:--:|:--:|:--:|:--|
|acl|List|Supports to set 0 or more grantees|
|grantee|Dict|Specifies the Type(user, group). When type is user, need user id; when type is group, only supports QS_ALL_USERS(all of the users)|
|permission|String|Specifies the permission (READ, WRITE, FULL_CONTROL) given to the grantee.|

### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EvnContext evn = new EvnContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);

```

then you can put bucket ACL


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