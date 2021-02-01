## PUT Object - Fetch

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：

- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.

After created the object, we need perform the action to PUT Object - Fetch：

```java
putObjectFetch(bucket, "https://www.qingcloud.com/static/assets/images/icons/common/footer_logo.svg", "folder-fetched/qingcloud_footer_logo.svg");
```

```java
    private void putObjectFetch(Bucket bucket, String fetchSource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSFetchSource(fetchSource); // Fetch source looks like this: "protocol://host[:port]/[path]"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-fetched/fileName"
            if (output.getStatueCode() == 201) {
                // Success
                System.out.println("Put Object - Fetch success.");
            } else {
                // Failed
                System.out.println("Put Object - Fetch failed.");
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
