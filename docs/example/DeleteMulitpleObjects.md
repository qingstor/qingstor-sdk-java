## DeleteMultipleObjects Example



### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can delete objects


```java
    /**
     * Delete Multiple Objects once
     * @param bucket bucket
     * @param objectKeys a string list of the objectKey
     *                   objectKey looks like this: "folder/fileName".<br/>
     *                   If objectKey = "fileName", means the object is in the bucket's root folder.
     * @throws QSException exception will be thrown if (objectKeys == null || objectKeys.size() < 1)
     */
    private void deleteMultipleObjects(Bucket bucket, List<String> objectKeys) throws QSException {
        if (objectKeys == null || objectKeys.size() < 1) {
            throw new QSException("ObjectKeys list must contains at least one key.");
        }

        Bucket.DeleteMultipleObjectsInput deleteInput = new Bucket.DeleteMultipleObjectsInput();
        List<Types.KeyModel> keyList = new ArrayList<>();
        for (String key : objectKeys) {
            Types.KeyModel keyModel = new Types.KeyModel();
            keyModel.setKey(key);
            keyList.add(keyModel);
        }
        deleteInput.setObjects(keyList);
        Bucket.DeleteMultipleObjectsOutput output = bucket.deleteMultipleObjects(deleteInput);
        if (output.getStatueCode() == 200 || output.getStatueCode() == 204) {
            // Deleted
            System.out.println("Delete multiple Objects: Deleted.");
            System.out.println("Delete multiple Objects: Keys = \n" + new Gson().toJson(keyList));
        } else {
            // Failed
            System.out.println("Failed to delete multiple objects.");
            System.out.println("StatueCode = " + output.getStatueCode());
            System.out.println("Message = " + output.getMessage());
            System.out.println("RequestId = " + output.getRequestId());
            System.out.println("Code = " + output.getCode());
            System.out.println("Url = " + output.getUrl());
        }
    }

```