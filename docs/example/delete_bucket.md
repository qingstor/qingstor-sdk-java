## Delete a Bucket

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：

- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.

After created the object, we need perform the action to delete a Bucket：

```java
    private void deleteBucket(Bucket bucket) {
        try {
            Bucket.DeleteBucketOutput output = bucket.delete();
            if (output.getStatueCode() == 204) {
                // Deleted
                System.out.println("Delete Bucket: Deleted.");
            } else {
                // Failed
                System.out.println("Failed to Delete Bucket.");
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

Methods in above codes：

- bucket.delete() Delete a Bucket named `testBucketName` in the zone `pek3a`.

Objects in above codes：

- output is the response body of the method bucket.delete().
- output.getMessage() is a method of the Output object, used to return response error message.
