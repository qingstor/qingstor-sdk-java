## 获取文件的元数据 

使用密钥初始化服务

``` java
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.HeadObjectInput;
import com.qingstor.sdk.Bucket.HeadObjectOutput;

EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后设置输入参数，调用 headObject 方法

``` java
try {
	Bucket.HeadObjectInput input = new Bucket.HeadObjectInput();
	Bucket.HeadObjectOutput output = bucket.headObject(objectName, input);
	System.out.println(output.getStatueCode());
	
} catch (QSException e) {
	System.out.println("exception");
	e.printStackTrace();	
}

```

上面代码中出现的函数：
- bucket.headObject(objectName, input) 获取 key 为 objectName 的 Object
	- objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。
	- input 用于设置 Object 的属性, 更准确获取 Object。
