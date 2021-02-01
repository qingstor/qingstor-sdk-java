## AppendObject Example

### Code Snippet

Use access-key-id and secret-access-key to initialize the bucket you want to operate.

```java
EnvContext env = new EnvContext("YOUR-ACCESS-KEY-ID", "YOUR-SECRET-ACCESS-KEY");
String zoneKey = "pek3a";
String bucketName = "your-bucket-name";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Now you can refer to the following code to call the helper method to append object.

```java
try {
    append(bucket, "appendObj.txt");
} catch (QSException e) {
    e.printStackTrace();
}
```

helper method:

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
        // According to your needs, set the inputStream source to the implementation that meets your needs.
        // Here is an example, simply set string => bytes.
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

Note: append object has the concept of next write position, which is the starting position when you write data next time.
It needs to be set explicitly and must be equal to the current size of the object, and cannot be greater or less than.

If no exception is thrown, it means that the append is successful.
You can check the object with the corresponding name in your bucket,
and its content will be 4 lines of append a new line obtained by 4 additional writes.
