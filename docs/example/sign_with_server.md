## Sign With Server



### Code Snippet

Take uploading object for example:
```
try {
    // Step 1: new EvnContext and set zone and bucket
    EvnContext evn = new EvnContext("", "");
    Bucket bucket = new Bucket(evn, testZone, "sh-test");

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File fa = new File("/tmp/config.yaml");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentLength(fa.length());

    // bucket.putObject(objectName, putObjectInput);

    // Step 2: get the request object
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,
        output -> {
            System.out.println("getMessage = " + output.getMessage());
            System.out.println("getRequµestId = " + output.getRequestId());
            System.out.println("getCode = " + output.getCode());
            System.out.println("getStatueCode = " + output.getStatueCode());
            });

        // Step 3: get the strToSignature. Send this string to the server.
        String strToSignature = reqHandler.getStringToSignature();

        // Step 4: serverAuthorization. Get response from server. We just sign in local here.
        String serverAuthorization = QSSignatureUtil.generateSignature("secretKey",
            strToSignature);

        // Step 5: set the signature to the request.
        reqHandler.setSignature("accessKey", serverAuthorization);
        // Step 6: send request. Async requests use the method sendAsync(), sync requests use the method send().
        reqHandler.sendAsync();

        } catch (QSException e) {
            e.printStackTrace();
        }
```