## Sign With Server



### Code Snippet

Take uploading object for example:
```
try {
    EvnContext evn = new EvnContext("", "");
    Bucket bucket = new Bucket(evn, testZone, "sh-test");

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File fa = new File("/tmp/config.yaml");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentLength(fa.length());

    // bucket.putObject(objectName, putObjectInput);

    // get the request object，nomally like this: bucket.putObject(objectName, putObjectInput);
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,
        output -> {
            System.out.println("-testSingnature--getMessage---" + output.getMessage());
            System.out.println("-testSingnature-getRequµestId--" + output.getRequestId());
            System.out.println("-testSingnature-getCode----" + output.getCode());
            System.out.println("-testSingnature-getStatueCode----" + output.getStatueCode());
            });

        // strToSignature: Send this string to the server.
        String strToSignature = reqHandler.getStringToSignature();

        // serverAuthorization: Get response from server.We just sign in local here.
        String serverAuthorization = QSSignatureUtil.generateSignature("secretKey",
            strToSignature);

        // Set the signature to the request.
        reqHandler.setSignature("accessKey", serverAuthorization);

        reqHandler.sendAsync();

        } catch (QSException e) {
            e.printStackTrace();
        }
```