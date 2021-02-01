## 下载文件

使用密钥初始化服务

```java
import java.io.*;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.GetObjectInput;
import com.qingstor.sdk.Bucket.GetObjectOutput;

EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后设置输入, 调用 getObject 方法

```java
getObject(bucket, "folder/test.mp3", "/Users/chengww/Desktop/test.mp3");
```

```java
    /**
     * Get(Download) an object from the bucket.<br/>从该 bucket 上下载对象
     * @param bucket bucket
     * @param objectKey  looks like this: "folder/fileName".<br/>
     *                   If objectKey = "fileName", means the object is in the bucket's root folder.<br/>
     *                   objectKey 形如这种形式: "folder/fileName".<br/>
     *                   如果 objectKey = "fileName", 意味着该对象在当前 bucket 的根目录。
     * @param localKeptPath the object will be kept in this path.<br/>
     *                      对象将会被保存到该目录。
     */
    private void getObject(Bucket bucket, String objectKey, String localKeptPath) {
        try {
            Bucket.GetObjectInput input = new Bucket.GetObjectInput();
            Bucket.GetObjectOutput output = bucket.getObject(objectKey, input);
            InputStream inputStream = output.getBodyInputStream();
            if (output.getStatueCode() == 200) {
                if (inputStream != null) {
                    FileOutputStream fos = new FileOutputStream(localKeptPath);
                    int len;
                    byte[] bytes = new byte[4096];
                    while ((len = inputStream.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    System.out.println("Get object success.");
                    System.out.println("ObjectKey = " + objectKey);
                    System.out.println("LocalKeptPath = " + localKeptPath);
                } else {
                    System.out.println("Get object status code == 200, but inputStream is null, skipped.");
                }
            } else {
                // Failed
                System.out.println("Failed to get object.");
                System.out.println("StatueCode = " + output.getStatueCode());
                System.out.println("Message = " + output.getMessage());
                System.out.println("RequestId = " + output.getRequestId());
                System.out.println("Code = " + output.getCode());
                System.out.println("Url = " + output.getUrl());
            }
            if (inputStream != null) inputStream.close();
        } catch (QSException | IOException e) {
            e.printStackTrace();
        }
    }

```

上面代码中出现的函数：

- bucket.getObject(objectName, input) 获取名为 objectName 的 Object
  - objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。
  - input 用于设置 Object 的元数据。
