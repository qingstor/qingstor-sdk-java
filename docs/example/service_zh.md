## 初始化服务

首先我们需要初始化一个 QingStor Service 来调用 QingStor 提供的各项服务。

``` java
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.*;

EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
QingStor storService = new QingStor(evn, zoneName);
```

上面代码中出现的对象：
- `evn` 对象承载了用户的认证信息及配置。
- `storService` 对象用于操作 QingStor 对象存储服务，可以创建一个 Bucket 对象或者使用所有 Service 级别的 API。
