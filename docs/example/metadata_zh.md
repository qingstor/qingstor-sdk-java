## 获取文件的元数据 

使用密钥初始化服务

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

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后设置输入参数，调用 headObject 方法

``` java
    /**
     * Head Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.<br/>
     *                  objectKey 形如这种形式: "folder/fileName".<br/>
     *                  如果 objectKey = "fileName", 意味着该对象在当前 bucket 的根目录。
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

上面代码中出现的函数：
- bucket.headObject(objectName, input) 获取 key 为 objectName 的 Object
	- objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。
	- input 用于设置 Object 的属性, 更准确获取 Object。
