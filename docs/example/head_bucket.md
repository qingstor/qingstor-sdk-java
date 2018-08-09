## Head a Bucket

Initialize the Bucket service with accesskeyid and secretaccesskey

``` java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：
- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.


After created the object, we need perform the action to get bucket statistics：

``` java
    private void headBucket(Bucket bucket) {
        try {
            Bucket.HeadBucketOutput output = bucket.head();
            if (output.getStatueCode() == 200) {
                System.out.println("Head Bucket success.");
                // You can print http headers/parameters you defined here.
                // See [Get/Set HTTP Headers/Parameters Of A Request](./get_set_http_headers.md)
                System.out.println("You can access the Bucket.");
                System.out.println("You can print http headers/parameters you defined here.");
            } else {
                // Failed
                System.out.println("Head Bucket: You cannot access the Bucket or it does not exist.");
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
- bucket.head() Head a Bucket named `testBucketName` in the zone `pek3a`.

Objects in above codes：
- output is the response body of the method bucket.head().
- output.getMessage() is a method of the Output object, used to return response error message.

