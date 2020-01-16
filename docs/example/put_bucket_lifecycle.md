## PUT Bucket Lifecycle

### Request Elements

|Name|Type|Description|Required|
|:--:|:--:|:--|:--:|
|rule|List|rule 的元素为 Lifecycle 规则。规则为 Dict 类型，有效的键为 “id”、”status”、”filter”、”expiration”、”abort_incomplete_multipart_upload” 和 “transition”。规则总数不能超过 100 条，且每条规则中只允许存在一种类型的操作。同一 bucket, prefix 和 支持操作（ expiration, abort_incomplete_multipart_upload, transition) 不能有重复，否则返回 400 invalid_request 包含重复的规则信息 参见[错误信息](https://docs.qingcloud.com/qingstor/api/common/error_code.html#object-storage-error-code)。|Yes|
|id|String|规则的标识符。可为任意 UTF-8 编码字符，长度不能超过 255 个字节，在一个 Bucket Lifecycle 中，规则的标识符必须唯一。该字符串可用来描述策略的用途。如果 id 有重复，会返回 400 invalid_request 。|Yes|
|status|String|该条规则的状态。其值可为 “enabled” (表示生效) 或 “disabled” (表示禁用)。|Yes|
|filter|Dict|用于匹配 Object 的过滤条件，有效的键为 “prefix”。|Yes|
|prefix|String|前缀匹配策略，用于匹配 Object 名称，空字符串表示匹配整个 Bucket 中的 Object。默认值为空字符串。|No|
|expiration|Dict|用于删除 Object 的规则，有效的键为 “days”。”days” 必须是正整数，否则返回 400 invalid_request。对于匹配前缀（prefix) 的对象在最后修改时间的指定天数（days）后删除该对象。|No|
|abort_incomplete_multipart_upload|Dict|用于取消未完成的分段上传的规则，有效的键为 “days_after_initiation”。”days_after_initiation” 必须是正整数，否则返回 400 invalid_request。|No|
|transition|Dict|用于变更存储级别的规则，有效的键为 “days”, “storage_class”。days 必须 >= 30, 否则返回 400 invalid_request。对于匹配前缀（prefix) 的对象在最后修改时间的指定天数（days）后变更到低频存储。|No|
|days|Integer|在对象最后修改时间的指定天数后执行操作。|No|
|days_after_initiation|Integer|在初始化分段上传的指定天数后执行操作。|Yes|
|storage_class|Integer|要变更至的 storage_class，支持的值为 "STANDARD"、"STANDARD_IA"。|Yes|

See [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html) for more information about request elements.

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can PUT Bucket Lifecycle


```java
    private void putBucketLifecycle(Bucket bucket) {
        Bucket.PutBucketLifecycleInput input = new Bucket.PutBucketLifecycleInput();
        List<Types.RuleModel> rules = new ArrayList<>();
        Types.RuleModel rule = new Types.RuleModel();
        // ID
        rule.setID("delete-logs");
        // Status
        rule.setStatus("enabled");
        // Filter
        Types.FilterModel filter = new Types.FilterModel();
        filter.setPrefix("logs/");
        rule.setFilter(filter);
        // Expiration
        Types.ExpirationModel expiration = new Types.ExpirationModel();
        expiration.setDays(180);
        rule.setExpiration(expiration);
        rules.add(rule);
        input.setRule(rules);
        try {
            Bucket.PutBucketLifecycleOutput output = bucket.putLifecycle(input);
            if (output.getStatueCode() == 200) {
                System.out.println("Put bucket lifecycle OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket lifecycle.");
                System.out.println("StatueCode = " + output.getStatueCode());
                System.out.println("Message = " + output.getMessage());
                System.out.println("RequestId = " + output.getRequestId());
                System.out.println("Code = " + output.getCode());
                System.out.println("Url = " + output.getUrl());
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
```