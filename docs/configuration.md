# EnvContext Guide

## Summary

This SDK uses a structure called "EnvContext" to store and manage configuration.

Except for AccessKeyID and SecretAccessKey, you can also configure the API servers for private cloud usage scenario. All available configureable items are list in default configuration file.

___Default EnvContext File:___

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

# Valid request url styles are "virtual_host_style"(default) and "path_style".
request_url_style: 'path_style'

```

## Usage

Just create a config structure instance with your API AccessKey, and initialize services that you need using getBucket() function of target bucket service.

### Code Snippet

Create default EnvContext

```
EnvContext env = EnvContext.loadFromFile("path to yaml");
```

Create EnvContext from AccessKey

```
EnvContext  env = new EnvContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
```

Change API server

```
EnvContext  moreEnv = new EnvContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");

moreEnv.setProtocol("https");
moreEnv.setHost("qingstor.com");
moreEnv.setPort("443");
```
