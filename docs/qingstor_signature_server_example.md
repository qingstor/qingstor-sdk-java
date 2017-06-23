# QingStor Demo - Signature Server (Java)


## Data Flow

Assume that we have a client that knows nothing about the access key and secret access key, such as a Java application runs in the clinet. If the client wants to access to non-public bucket directly, it needs the signature generated from the signature server for every request.

Here's a diagram describes the data flow:

```
+---------------------------------------------------------------------------+
|                                                                           |
|                           +-----------------------------+                 |
|                           |                             |                 |
|               +---------> |   QingStor Object Storage   |                 |
|               |           |                             |                 |
|               |           +--------------+--------------+                 |
|       3. Send |                          |                                |
|        Signed |    +---------------------+                                |
|       Request |    |   4. Response                                        |
|               |    v                                                      |
|               |                                                           |
|      +--------+--------+                                                  |
|      |                 |        2. Get Signature                          |
|      |     Client      | <-----------------------------+                  |
|      |                 |                               |                  |
|      +--------+--------+                    +----------+-----------+      |
|               |                             |                      |      |
|               +---------------------------> |   Signature Server   |      |
|                      1. Sign Request        |                      |      |
|                                             +----------------------+      |
|                                                                           |
+---------------------------------------------------------------------------+
```


## Signature Server Usage

### Sign operation by query parameters.

_Client Example:_

``` http

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File uploadFile = new File("/tmp/test.video");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentType("video/mp4; charset=utf8");
    putObjectInput.setContentLength(uploadFile.length());
    
    
    // Get request handler object
    RequestHandler reqHandler = bucket.putObjectRequest(objectName, putObjectInput);
    
    // Get string to signature
    String strToSignature = reqHandler.getStringToSignature();

    // Send 'strToSignature' to the business server which can response this request;
    String serverSignature = [response.signature];
    String accessKey = [response.accessKey];
    
    //Set response sinature and accessKey to the reqHandler
    reqHandler.setSignature(accessKey,serverSignature);
    
    OutputModel output = reqHandler.send();
    

```

_Server Example:_

``` http

	String strToSignature = request.getParameter("strToSignature");
	String serverSignature = QSServerSignatureUtil.generateSignature(
            		"Your secretKey", strToSignature);
   // Response  serverSignature and accessKey to the client;        		

```

_Client Async Example:_

``` http

    Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();
    File uploadFile = new File("/tmp/test.video");
    putObjectInput.setBodyInputFile(fa);
    putObjectInput.setContentType("video/mp4; charset=utf8");
    putObjectInput.setContentLength(uploadFile.length());
    
    
    // Get request handler object
    RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectName, putObjectInput,new ResponseCallBack<Bucket.PutObjectOutput>() {

				@Override
				public void onAPIResponse(PutObjectOutput output) throws QSException {
					// TODO Auto-generated method stub
					
				}
			});
			    
    // Get string to signature
    String strToSignature = reqHandler.getStringToSignature();

    // Send 'strToSignature' to the business server which can response this request;
    String serverSignature = [response.signature];
    String accessKey = [response.accessKey];
    
    //Set response sinature and accessKey to the reqHandler
    reqHandler.setSignature(accessKey,serverSignature);
    
    reqHandler.sendAsync();
    

```
### Notice: The asynchronous request 



## Reference Documentations

- [QingStor Documentation](https://docs.qingcloud.com/qingstor/index.html)
- [QingStor Guide](https://docs.qingcloud.com/qingstor/guide/index.html)
- [QingStor APIs](https://docs.qingcloud.com/qingstor/api/index.html)
- [QingStor Signature](https://docs.qingcloud.com/qingstor/api/common/signature.html)

## LICENSE

The Apache License (Version 2.0, January 2004).
