## 删除文件

使用密钥初始化服务

```java
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.DeleteObjectOutput;

EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

上面代码中出现的对象：

- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后调用 deleteObject 方法

```java
    /**
     * Delete Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.<br/>
     *                  objectKey 形如这种形式: "folder/fileName".<br/>
     *                  如果 objectKey = "fileName", 意味着该对象在当前 bucket 的根目录。
     */
    private void deleteObject(Bucket bucket, String objectKey) {
        try {
            Bucket.DeleteObjectOutput output = bucket.deleteObject(objectKey, null);
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

上面代码中出现的函数：

- bucket.deleteObject(objectName, null) 删除 key 为 objectName 的 Object
  - objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。

上面代码中出现的对象：

- output 是 bucket.deleteObject(objectName, null) 返回的实体。
