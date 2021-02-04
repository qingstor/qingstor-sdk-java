## custom metadata Example

**Note**: Since v2.5.1, the sdk will automatically add and remove the `x-qs-meta-` prefix internally,
users no need to handle this themselves.

To set the key-value pair `name0: value0`, user only needs to put this pair of kv in the map and set it to XQSMetaData,
When get this metadata, the kv pair stored in XQSMetaData is the kv pair with `x-qs-meta-` removed, `{"name0": "value0"}`.

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
import java.io.*;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.PutBucketInput;
import com.qingstor.sdk.Bucket.PutBucketOutput;

EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

The bucket appearing in the above code:

- Bucket is used to manipulate Buckets, and all Bucket and Object level APIs can be used.

```java
try {
      putObject(bucket, "custom_meta.txt", new ByteArrayInputStream("hahaha".getBytes()));
      headObject(bucket, "custom_meta.txt");
} catch (QSException e) {
    e.printStackTrace();
}
```

This code will upload a simple txt file with three custom metadata:

```
"x-qs-meta-a": "A"
"x-qs-meta-b": "B"
"x-qs-meta-c": "C"
```

then you can put object with custom metadata and get those custom metadatas by use following methods.

headObject will get the metadata of this object and print it
(the prefix of `x-qs-meta-` has been stripped out automatically by SDK),
or you can use GET Object to get both the metadata and the object itself.

```java
/**
 * Put a file to the bucket with custom metadata。
 * @param bucket    bucket
 * @param key object key
 * @param body The binary stream of the file to upload
*/
private static void put(Bucket bucket, String key, InputStream body) throws QSException {
    Bucket.PutObjectInput input = new Bucket.PutObjectInput();
    input.setBodyInputStream(body);
    Map<String, String> map = new HashMap<>();
    // The sdk will automatically prefix them with `x-qs-meta-`.
    // "x-qs-meta-a": "A"
    // "x-qs-meta-b": "B"
    // "x-qs-meta-c": "C"
    map.put("a", "A");
    map.put("b", "B");
    map.put("c", "C");
    input.setXQSMetaData(map);
    Bucket.PutObjectOutput output = bucket.putObject(key, input);
    System.out.println(output.getStatueCode());
    if (output.getStatueCode() == 201) {
        System.out.println(output.getETag());
    } else {
        System.out.println(output.getCode());
        System.out.println(output.getMessage());
    }
    System.out.println(output.getRequestId());
}

private static void head(Bucket bucket, String key) {
    try {
        Bucket.HeadObjectOutput output = bucket.headObject(key, null);
        System.out.println(output.getStatueCode());
        if (output.getStatueCode() == 200) {
            System.out.println(output.getETag());
            System.out.println(output.getContentLength());
            System.out.println(output.getContentType());
            System.out.println(output.getXQSMetaData());
        } else {
            System.out.println(output.getCode());
            System.out.println(output.getMessage());
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
}
```
