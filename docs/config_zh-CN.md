## 配置

用户主要通过 QingStor 和 Bucket 类来使用 QingStor 服务, 但首先要提供一个可用的配置, 才能让 SDK 与对象存储正常沟通.
为此, SDK 封装了一个 `EnvContext` 来存储和管理认证和其他配置信息。

除 `AccessKeyID` 和 `SecretAccessKey` 之外是必须提供的以外，其他可用配置项可以看页面底部的默认值, 如果不符合你的需求,
可以调整为你需要的值.

您还可以配置 `API服务器` 用于私有云使用场景。所有可用的可配置项目都列在页面底部的默认配置文件中。

## 使用

### 通过代码进行配置

用你的密钥创建实例化一个配置实体, 其他配置项保持默认值。

```java
EnvContext ctx = new EnvContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
```

如果你想要修改一些默认值, 比如私有云环境更改 api 服务器:

```java
// 更换为真实目标服务的地址
ctx.setEndpoint("https://qingstor.com:443");
// 从版本 v2.5.1 开始, 下面这些配置已不再推荐使用.
// ctx.setProtocol("https");
// ctx.setHost("qingstor.com");
// ctx.setPort("443");
```

### 通过加载配置文件进行配置

如果你已经将自己喜好的配置调整为了一个 yaml 配置文件, 则可以将该配置文件放置在 `~/.config/qingstor.yaml`(`~` 代表家目录).
则可以通过下面的方式直接加载配置到 SDK:

```java
EnvContext ctx = EnvContext.loadUserConfig();
```

或者直接传入文件所在路径:

```java
EnvContext ctx = EnvContext.loadFromFile("path to yaml");
```

通过文件实例化的 EnvContext 可以继续通过代码形式调整设置.

## 默认配置

**默认的 EnvContext 配置(yaml 文件形式):**

```yaml
# QingStor services configuration

access_key_id: "ACCESS_KEY_ID"
secret_access_key: "SECRET_ACCESS_KEY"

# 如果版本低于 v2.5.1, 请分别配置 host, port, protocol.
endpoint: "https://qingstor.com"
# 从版本 v2.5.1 开始, 下面 3 个配置项已被 endpoint 取代.
# host: "qingstor.com"
# port: "443"
# protocol: "https"

# Valid request url styles are "virtual_host_style" and "path_style"(default).
# Also replace previous config item: virtual_host_enabled.
enable_virtual_host_style: false

# 如果您希望使用自定义域名而非对象域名来访问对象资源时, 可以开启 cname_support(需要先在对象存储中添加对应 cname 记录)。
# 如果此值设置为 true，注意：
# 1. endpoint 配置和 host/port/protocol 配置替换为您的自定义域名
# 2. enable_virtual_host_style 也必须为 true
cname_support: false

# optional
http_config:
  # unit: second
  read_timeout: 100
  connection_timeout: 60
  write_timeout: 100
```
