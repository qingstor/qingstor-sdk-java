# 初始化服务

首先我们需要初始化一个 QingStor Service 来调用 QingStor 提供的各项服务。

```java
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.service.*;

EnvContext ctx = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3b";
// v2.5.1 以前的版本请使用下面这种方式.
// QingStor stor = new QingStor(env, zoneName);
QingStor stor = new QingStor(env);
Bucket bucket = stor.getBucket("你的 bucket 名字", zoneName);
// 或者直接通过 new 来构造 Bucket.
// Bucket bucket = new Bucket(ctx, zoneName, "你的 bucket 名字");
```

上面代码中出现的对象：

- `ctx` 对象承载了用户的认证信息及配置。
- `stor` 对象用于操作 QingStor 对象存储服务，用于调用所有 Service 级别的 API 或创建指定的 Bucket 对象来调用 Bucket 和 Object 级别的 API。
- `bucket` 对象绑定了指定 bucket，提供一系列针对该 bucket 的对象存储操作。
