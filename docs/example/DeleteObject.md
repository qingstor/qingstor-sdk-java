## DeleteObject Example



### Code Snippet

Initialize the Bucket service with accesskeyid and secretaccesskey

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can delete objects


```
    /**
     * Delete Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
     */
    private void deleteObject(Bucket bucket, String objectKey) {
        try {
            Bucket.DeleteObjectOutput output = bucket.deleteObject(objectKey);
            if (output.getStatueCode() == 204) {
                // Deleted
                System.out.println("Delete Object: Deleted. ");
                System.out.println("Delete Object: Object key = " + objectKey);
            } else {
                // Failed
                System.out.println("Failed to delete " + objectKey);
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