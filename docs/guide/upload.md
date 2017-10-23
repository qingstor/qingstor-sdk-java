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

``` java
Bucket.PutObjectInput input = new Bucket.PutObjectInput();
File f = new File("/tmp/wifi-Bq5Lr5.log");
input.setContentType("text/plain; charset=utf-8");
input.setContentLength(f.length());
input.setBodyInputFile(f);
try {
	Bucket.PutObjectOutput out = bucket.putObject(objectName, input);
	System.out.println(out.getMessage());
} catch(QSException e) {
	e.printStackTrace();
}
```

上面代码中出现的函数：
- bucekt.putObject(objectName, input) 向 Bucket 上传一个 Object
	- objectName 示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。
	- input 用于设置上传文件的元数据以及实体。
