## 上传文件


使用密钥初始化服务

``` java
import java.io.*;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.PutBucketInput;
import com.qingstor.sdk.Bucket.PutBucketOutput;

EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后设置输入参数，调用 put 方法


```java
try {
    putObject(bucket, "folder/test.mp3", "/Users/chengww/Downloads/test.mp3");
} catch (FileNotFoundException e) {
    e.printStackTrace();
}

```

```java
    /**
     * Put a file to the bucket. 上传文件到当前 bucket。
     * @param bucket bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", we will put the object into the bucket's root.<br/>
     *                  objectKey 形如这种形式: "folder/fileName".<br/>
     *                  如果 objectKey = "fileName", 对象将上传到 bucket 的根目录。
     * @param filePath local file path 本地文件路径
     * @throws FileNotFoundException if file does not exist, the exception will occurred.<br/>
     *                               如果文件不存在将抛出该异常。
     */
    private void putObject(Bucket bucket, String objectKey, String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException("File does not exist or it is a directory.");

        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        // Using the JDK1.7 self-bringing method to get content type(Requires JDK 1.7+, linux requires GLib.)
        try {
            String contentType = Files.probeContentType(Paths.get(filePath));
            input.setContentType(contentType);
            System.out.println("Put Object: ContentType = " + contentType);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Put Object: Get file's content type error.");
        }
        input.setContentLength(file.length());
        input.setBodyInputFile(file);
        try {
            Bucket.PutObjectOutput output = bucket.putObject(objectKey, input);
            if (output.getStatueCode() == 201) {
                System.out.println("PUT Object OK.");
                System.out.println("key = " + objectKey);
                System.out.println("path = " + filePath);
            } else {
                // Failed
                System.out.println("Failed to PUT object.");
                System.out.println("key = " + objectKey);
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

上面代码中出现的函数：
- bucekt.putObject(objectName, input) 向 Bucket 上传一个 Object
	- objectName 示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。
	- input 用于设置上传文件的元数据以及实体。
