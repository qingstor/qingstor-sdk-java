# QingStor Service Usage Guide

Import and initialize QingStor service with a context, and you are ready to use the initialized service.

Each API function take a Input struct and return an Output struct. The Input struct consists of request params, request headers and request elements, and the Output holds the HTTP status code, response headers, response elements and error message (if error occurred).

```
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.*;
```

### Code Snippet

Initialize the QingStor service with a configuration

```
String bucketName = "bucketName";
QingStor storService = new QingStor(evn, bucketName);
```

Initialize the Bucket  service

``` go
Bucket bucket = storService.getBucket(bucketName);
```

InitiateMultipartUpload

```
String objectName = "qing_test.apk";

InitiateMultipartUploadInput inputParam = new InitiateMultipartUploadInput();
Bucket.InitiateMultipartUploadOutput output = bucket.initiateMultipartUpload(objectName, inputParam);

// Print HTTP message.
System.out.println(output.getMessage());

// Print the responce upload ID.
System.out.println(output.getUploadID() + "");
        
        
```

InitiateMultipartUpload  make network request through sub thread

```
ResponseCallBack callback = new ResponseCallBack<Bucket.InitiateMultipartUploadOutput>() {
            @Override
            public void onAPIResponse(Bucket.InitiateMultipartUploadOutput output) throws QSException {
                // Print HTTP message.
                System.out.println(output.getMessage());

                // Print the responce upload ID.
                System.out.println(output.getUploadID() + "");
            }
        };


String objectName = "qing_test.apk";

bucket.initiateMultipartUpload(objectName, callback);


```
