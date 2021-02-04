## 自定义元数据示例

**注意**: 从 v2.5.1 以后, sdk 会在内部自动加上和去除 `x-qs-meta-` 前缀, 不再需要用户自行构造.

用户设置 name0: value0 这一 key-value 对, 只需要将这一对 kv 放到 map 中设置到 XQSMetaData,
获取时, XQSMetaData 里存储的就是去除了 `x-qs-meta-` 的 kv 对, `{"name0": "value0"}`.

首先使用密钥初始化服务

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

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

```java
try {
      putObject(bucket, "custom_meta.txt", new ByteArrayInputStream("hahaha".getBytes()));
      headObject(bucket, "custom_meta.txt");
} catch (QSException e) {
    e.printStackTrace();
}
```

然后设置输入参数，调用下面的 putObject/headObject 方法.
这段代码会上传一个简单的 txt 文件, 携带三个自定义元数据:

```
"x-qs-meta-a": "A"
"x-qs-meta-b": "B"
"x-qs-meta-c": "C"
```

headObject 会获取这个对象的元数据并打印(sdk 已经自动去除了 `x-qs-meta-` 前缀), 也可以使用 GET Object 来同时获取元数据和对象本身.

```java
/**
 * Put a file to the bucket with custom metadata。
 * @param bucket    bucket
 * @param key object key
 * @param body 要上传的文件的二进制流
*/
private static void put(Bucket bucket, String key, InputStream body) throws QSException {
    Bucket.PutObjectInput input = new Bucket.PutObjectInput();
    input.setBodyInputStream(body);
    Map<String, String> map = new HashMap<>();
    // sdk 会自动为他们加上完整元数据前缀.
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
