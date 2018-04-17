## 使用服务端签名



### 代码片段

以上传文件为例：
```
try {
    // 第一步: new EvnContext 并设置 zone 和 bucket
    EvnContext evn = new EvnContext("", "");
    Bucket bucket = new Bucket(evn, testZone, "sh-test");

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File fa = new File("/tmp/config.yaml");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentLength(fa.length());

    // bucket.putObject(objectName, putObjectInput);

    // 第二步：获取 RequestHandler，正常 bucket.putObject(objectName, putObjectInput); 就完成操作
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,
        output -> {
            System.out.println("getMessage = " + output.getMessage());
            System.out.println("getRequµestId = " + output.getRequestId());
            System.out.println("getCode = " + output.getCode());
            System.out.println("getStatueCode = " + output.getStatueCode());
            });

        // 第三步：获取 strToSignature。将这个字符串发送到用户的 server 端。
        String strToSignature = reqHandler.getStringToSignature();

        // 第四步：serverAuthorization。server 端处理返回信息，这里本地模拟
        String serverAuthorization = QSSignatureUtil.generateSignature("secretKey",
            strToSignature);

        // 第五步：将计算的签名设置到 request 中
        reqHandler.setSignature("accessKey", serverAuthorization);
        // 第六步：发送请求。异步请求使用 sendAsync() 方法。同步请求使用 send() 方法。
        reqHandler.sendAsync();

        } catch (QSException e) {
            e.printStackTrace();
        }
```