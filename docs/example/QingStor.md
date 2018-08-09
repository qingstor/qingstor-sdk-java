## QingStor Example


Import and initialize QingStor service with a context, and you are ready to use the initialized service.


```
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.service.*;
```

### Code Snippet

Initialize the QingStor service with access_key_id and secret_access_key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
QingStor storService = new QingStor(env, zoneKey);

```

then you can list buckets


```

ListBucketsInput input = new ListBucketsInput();
input.setLocation(zoneKey);// zone filter
ListBucketsOutput listOutput = storSerivce.listBuckets(input);


```