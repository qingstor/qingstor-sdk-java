## 删除文件

使用密钥初始化服务

``` java
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.DeleteObjectOutput;

EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后调用 deleteObject 方法

``` java
try {
	Bucket.DeleteObjectOutput output = bucket.deleteObject(objectName);
	System.out.println(out.getMessage());	
} catch(QSException e) {
	e.printStackTrace();
}
```

上面代码中出现的函数：
- bucket.deleteObject(objectName) 删除 key 为 objectName 的 Object
	- objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。


上面代码中出现的对象：
- output 是 bucket.deleteObject(objectName) 返回的实体。
