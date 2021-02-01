# Config

Users mainly use QingStor services through the QingStor and Bucket classes, but they must first provide a usable
configuration to allow the SDK to communicate with the object storage normally.

For this reason, the SDK encapsulates an `EnvContext` to store and manage authentication and other configuration information.

Except for `AccessKeyID` and `SecretAccessKey`, which must be provided, other available configuration items
can be found at the default value at the bottom of the page.

If it does not meet your needs, you can adjust it to the value you need.

You can also configure `API server` for private cloud usage scenarios.
All available configurable items are listed in the default configuration file at the bottom of the page.

## Usage

### Config through code

Create and instantiate a configuration entity with your key, and keep the default values for other configuration items.

```java
EnvContext ctx = new EnvContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
```

If you want to modify some default values, such as changing the api server in a private cloud environment:

```java
// change to the address of the real target service
ctx.setEndpoint("https://qingstor.com:443");
// Starting from version v2.5.1, the following configurations are no longer recommended.
// ctx.setProtocol("https");
// ctx.setHost("qingstor.com");
// ctx.setPort("443");
```

### Config through config-file

If you have adjusted your preferred configuration to a yaml config-file,
you can place the configuration file in `~.config/qingstor.yaml` (`~` represents the home directory).
Then you can directly load the configuration to the SDK in the following way:

```java
EnvContext ctx = EnvContext.loadUserConfig();
```

Or directly pass in the path of the config-file:

```java
EnvContext ctx = EnvContext.loadFromFile("path to yaml");
```

EnvContext instantiated through files can continue to adjust settings through code.

## default values of config

**Default EnvContext configuration (yaml file format):**

```yaml
# QingStor services configuration

access_key_id: "ACCESS_KEY_ID"
secret_access_key: "SECRET_ACCESS_KEY"

# If the version is lower than v2.5.1, please configure host, port and protocol respectively.
endpoint: "https://qingstor.com"
# Starting from version v2.5.1, the following 3 configuration items have been replaced by endpoint.
# host: "qingstor.com"
# port: "443"
# protocol: "https"

# No need to define a log level in a library. We only provide slf4j: an common abstract layer of logging,
# it's user's responsibility to provide a log framework implementation in their application.

# Valid request url styles are "virtual_host_style" and "path_style"(default).
# Also replace previous config item: virtual_host_enabled.
enable_virtual_host_style: false

# cname_support is used when you want access object use your own domain.
# You can aliasing a bucket by adding cname record to map your own domain to some bucket of qingstor.
# then you can access your object of this bucket use your url with object path.
# Note: if this value is set to true, enable_virtual_host_style must be true too.
cname_support: false

# optional
http_config:
  # unit: second
  read_timeout: 100
  connection_timeout: 60
  write_timeout: 100
```
