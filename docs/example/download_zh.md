## 下载文件

使用密钥初始化服务

``` java
import java.io.*;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.Bucket.GetObjectInput;
import com.qingstor.sdk.Bucket.GetObjectOutput;

EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(evn, zoneKey, bucketName);
```

上面代码中出现的对象：
- bucket 对象用于操作 Bucket，可以使用所有 Bucket 和 Object 级别的 API。

然后设置输入, 调用 getObject 方法

``` java
Bucket.GetObjectInput input = new Bucket.GetObjectInput();
try {
	Bucket.GetObjectOutput getObjectOutput = bucket.getObject(objectName, input);
	int iLength = 0;
	if (getObjectOutput != null && getObjectOutput.getBodyInputStream() != null) {
	    File downFile = new File("/tmp/get_object.txt");
	    OutputStream out = new FileOutputStream(downFile);
	    int bytesRead = 0;
	    byte[] buffer = new byte[1024];
	    while ((bytesRead = getObjectOutput.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
	        out.write(buffer, 0, bytesRead);
	        iLength += bytesRead;
	    }
	    out.close();
	    getObjectOutput.getBodyInputStream().close();
	}
} catch (QSException e) {
	e.printStackTrace();
}
```

上面代码中出现的函数：
- bucket.getObject(objectName, input) 获取名为 objectName 的 Object
	- objectName 表示 Object 的 Key，Object Key 是该 Object 在 QingStor 对象存储系统对应 Bucket 中具有唯一性的标识，相当于本地存储系统中的文件名。 
	- input 用于设置 Object 的元数据。
