## Append 对象

### 代码片段

使用 access-key-id 和 secret-access-key 来初始化您要操作的 Bucket.

```java
EnvContext env = new EnvContext("YOUR-ACCESS-KEY-ID", "YOUR-SECRET-ACCESS-KEY");
String zoneKey = "pek3a";
String bucketName = "your-bucket-name";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

现在您就可以参照下面的代码调用 helper 方法 来 append object 了. 


```java
try {
    append(bucket, "appendObj.txt");
} catch (QSException e) {
    e.printStackTrace();
}
```

helper 方法:

```java
/**
 * Create an object to specified {@code bucket} with name {@code objKey},
 * then append bytes to it multiple times.
 *
 * @param bucket the dest bucket
 * @param objKey object name you want append.
 * @throws QSException qingstor exception
 */
private static void append(Bucket bucket, String objKey) throws QSException {
    String line = "append a new line\n";
    long position = 0L;
    for (int i = 0; i < 4; i++) {
        Bucket.AppendObjectInput input = new Bucket.AppendObjectInput();
        // 根据您的需要将 inputStream 来源设置为符合您需求的实现, 这里起示例作用, 简单将 string => bytes.
        ByteArrayInputStream ins = new ByteArrayInputStream(line.getBytes());
        input.setBodyInputStream(ins);
        input.setPosition(position);
        input.setContentLength((long) line.length());
        Bucket.AppendObjectOutput output = bucket.appendObject(objKey, input);
        position = output.getXQSNextAppendPosition();
        System.out.println(output.getRequestId());
        System.out.println(output.getStatueCode());
    }
}
```

注意: append object 有追加写位置的概念, 这是您下次写入数据时的起始位置, 需要显式设置且必须等于 object 当前大小, 不可以大于或小于.

如果没有异常抛出, 说明 append 成功, 您可以检查您 bucket 中对应名称的 object, 它的内容将是通过 4 次追加写而得到的 4 行 `append a new line`.