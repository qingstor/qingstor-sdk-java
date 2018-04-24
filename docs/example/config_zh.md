## 配置

这个 SDK 封装一个 `EvnContext` 来存储和管理配置信息。

除 `AccessKeyID` 和 `SecretAccessKey` 之外，您还可以配置 `API服务器` 以进行私有云使用场景。 所有可用的可配置项目都列在默认配置文件中。

___默认的 EvnContext 文件:___

``` yaml
# QingStor services configuration

access_key_id: 'ACCESS_KEY_ID'
secret_access_key: 'SECRET_ACCESS_KEY'

host: 'qingstor.com'
port: '443'
protocol: 'https'
connection_retries: 3

# Valid log levels are "debug", "info", "warn", "error", and "fatal".
log_level: 'warn'

# Valid request url styles are "virtual_host_style"(default) and "path_style"
request_url_style: 'path_style'

```

## 使用

用你的密钥创建实例化一个配置实体，然后初始化服务，进行一系列的存储操作。

### 代码片段

创建一个默认的 EvnContext

``` java
EvnContext evn = EvnContext.loadFromFile("path to yaml");
```

通过密钥来创建 EvnContext

``` java
EvnContext  evn = new EvnContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
```

你也可以选择更换 API 服务器

``` java
EvnContext  moreEvn = new EvnContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");

moreEvn.setProtocol("https");
moreEvn.setHost("qingstor.com");
moreEvn.setPort("443");
```
