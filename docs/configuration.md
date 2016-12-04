# EvnContext Guide

## Summary

This SDK uses a structure called "EvnContext" to store and manage configuration.

Except for AccessKeyID and SecretAccessKey, you can also configure the API servers for private cloud usage scenario. All available configureable items are list in default configuration file.

___Default EvnContext File:___

``` yaml
# QingStor services configuration

access_key_id: 'ACCESS_KEY_ID'
secret_access_key: 'SECRET_ACCESS_KEY'

host: 'qingstor.com'
port: 443
protocol: 'https'
connection_retries: 3

# Valid log levels are "debug", "info", "warn", "error", and "fatal".
log_level: 'warn'

```

## Usage

Just create a config structure instance with your API AccessKey, and initialize services that you need using getBucket() function of target bucket service.

### Code Snippet

Create default EvnContext

```
EvnContext evn = EvnContext.loadFromFile("path to yaml");
```

Create EvnContext from AccessKey

```
EvnContext  evn = new EvnContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
```

Change API server

```
EvnContext  moreEvn = new EvnContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");

moreEvn.setProtocol("https");
moreEvn.setHost("privatestor.com");
moreEvn.setPort(443);
```
