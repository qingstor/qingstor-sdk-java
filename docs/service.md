## QingStor Example

First, we need to initialize a QingStor service to call the services provided by QingStor.

```java
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.service.*;

EnvContext ctx = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3b";
// Please use the following method for versions prior to v2.5.1.
// QingStor stor = new QingStor(env, zoneName);
QingStor stor = new QingStor(env);
Bucket bucket = stor.getBucket("your-bucket-name", zoneName);
// Or directly construct Bucket through new.
// Bucket bucket = new Bucket(ctx, zoneName, "your-bucket-name");
```

The object that appears in the above code:

- The `ctx` object carries the user's authentication information and configuration.
- The `stor` object is used to operate the QingStor object storage service, which is used to call all Service level APIs or to create a specified Bucket object to call Bucket and Object level APIs.
- The `bucket` object is bound to the specified bucket and provides a series of object storage operations for the bucket.
