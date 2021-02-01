## 使用服务端签名

一个简单的签名服务器介绍可参考: [Signature Server Guide](../qingstor_signature_server_example.md).

### 本地时间和网络时间不同步

如果用户本地时间与网络时间不同步会因签名原因造成请求失败。您还需要从服务端获取网络时间。

下面是一个关于**服务端**如何返回正确时间到客户端的示例。

```java
String gmtTime = QSSignatureUtil.formatGmtDate(new Date());
return gmtTime;
```

在您从服务端获取到正确时间后，您需要在调用 `reqHandler.sendAsync();` 方法前将时间设置到 SDK 中。

```java
reqHandler.getBuilder().setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
reqHandler.sendAsync();
```

### 代码片段

以上传文件为例：

```
try {
    // 第一步: 创建 EnvContext 并设置 zone 和 bucket
    EnvContext env = new EnvContext("", "");
    Bucket bucket = new Bucket(env, "zone 名称", "bucket 名称");

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File file = new File("/文件路径/文件名");
    putObjectInput.setBodyInputFile(file);
    putObjectInput.setContentLength(file.length());

    // bucket.putObject("对象名称", putObjectInput);

    // 第二步：获取 RequestHandler，正常 bucket.putObject("对象名称", putObjectInput); 就完成操作
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,
        new ResponseCallBack<PutObjectOutput>() {
            @Override
            public void onAPIResponse(PutObjectOutput output) {
                System.out.println("Message = " + output.getMessage());
                System.out.println("RequestId = " + output.getRequestId());
                System.out.println("Code = " + output.getCode());
                System.out.println("StatueCode = " + output.getStatueCode());
                System.out.println("Url = " + output.getUrl());
                }
            });

    // 第三步：获取 strToSignature。请将这个字符串发送到您自建的签名 server 端适当的接口。
    String strToSignature = reqHandler.getStringToSignature();

    // 第四步：serverAuthorization。
    // server 端处理返回信息，服务端接收到 strToSignature 后的处理可以参考如下代码(注意不是本地调用!!!)：
    // String serverAuthorization = QSSignatureUtil.generateSignature("您的 secretKey", strToSignature);
    String serverAuthorization = "您从服务端获取到的签名字符串";

    // 第五步：将计算的签名设置到 request 中

    // 因客户端跟服务端通讯可能有时间差，而签名计算结果跟时间密切相关。
    // 因此需要将服务端计算签名时所用的时间设置到 request 中。
    // 您可以发送 strToSignature 到服务端以获取服务端签名的时间。具体服务端示例参考上述 "本地时间和网络时间不同步"。
    reqHandler.getBuilder().setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);

    reqHandler.setSignature("您的 accessKey", serverAuthorization);

    // 第六步：发送请求。异步请求使用 sendAsync() 方法。同步请求使用 send() 方法。
    reqHandler.sendAsync();

} catch (QSException e) {
    e.printStackTrace();
}
```
