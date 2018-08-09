## HEAD Object

Initialize the Bucket service with accesskeyid and secretaccesskey

``` java
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.HeadObjectInput;
import com.qingstor.sdk.Bucket.HeadObjectOutput;

EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：
- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.

After created the object, we need perform the action to head object：

``` java
    /**
     * Head Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
     */
    private void headObject(Bucket bucket, String objectKey) {
        try {
            Bucket.HeadObjectInput input = new Bucket.HeadObjectInput();
            Bucket.HeadObjectOutput output = bucket.headObject(objectKey, input);
            if (output.getStatueCode() == 200) {
                System.out.println("Head Object success.");
                System.out.println("ObjectKey = " + objectKey);
                System.out.println("ContentLength = " + output.getContentLength());
                System.out.println("ContentType = " + output.getContentType());
                System.out.println("ETag = " + output.getETag());
                System.out.println("LastModified = " + output.getLastModified());
                System.out.println("XQSEncryptionCustomerAlgorithm = " + output.getXQSEncryptionCustomerAlgorithm());
                System.out.println("XQSStorageClass = " + output.getXQSStorageClass());
            } else {
                // Failed
                System.out.println("Head Object failed.");
                System.out.println("ObjectKey = " + objectKey);
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
- bucket.headObject(objectName, input) get the object which the key is objectName 的 Object
	- objectName is the key of the Object. Object's Key is the Object's unique identifier in QingStor Object Storage's bucket, equals to the file name in local storage system.
	- input Used to set the property of the Object, to get the object more accurately.
