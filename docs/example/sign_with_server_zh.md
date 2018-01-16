## 使用服务端签名



### 代码片段

以上传文件为例：
```
try {
    EvnContext evn = new EvnContext("", "");
    Bucket bucket = new Bucket(evn, testZone, "sh-test");

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File fa = new File("/tmp/config.yaml");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentLength(fa.length());

    // bucket.putObject(objectName, putObjectInput);

    // 获取请求对象，正常 bucket.putObject(objectName, putObjectInput); 就完成操作
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,
        output -> {
            System.out.println("-testSingnature--getMessage---" + output.getMessage());
            System.out.println("-testSingnature-getRequµestId--" + output.getRequestId());
            System.out.println("-testSingnature-getCode----" + output.getCode());
            System.out.println("-testSingnature-getStatueCode----" + output.getStatueCode());
            });

        // strToSignature 将这个发送到用户的server端
        String strToSignature = reqHandler.getStringToSignature();

        // serverAuthorization server端处理返回信息，这里本地模拟
        String serverAuthorization = QSSignatureUtil.generateSignature("secretKey",
            strToSignature);

        // 将计算的签名设置到request中
        reqHandler.setSignature("accessKey", serverAuthorization);

        reqHandler.sendAsync();

        } catch (QSException e) {
            e.printStackTrace();
        }
```