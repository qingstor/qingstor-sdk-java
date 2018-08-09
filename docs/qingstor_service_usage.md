# QingStor Service Usage Guide

Import and initialize QingStor service with a context, and you are ready to use the initialized service.

Each API function take a Input struct and return an Output struct. The Input struct consists of request params, request headers and request elements, and the Output holds the HTTP status code, response headers, response elements and error message (if error occurred).

```
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.service.*;
```

### Code Snippet

Initialize the QingStor service with a configuration

```
String zoneName = "pek3a";
QingStor storService = new QingStor(env, zoneName);

```

Initialize the Bucket  service

``` go
String bucketName = "bucketName";
Bucket bucket = storService.getBucket(bucketName);
```

InitiateMultipartUpload

```
String objectName = "qing_test.apk";

Bucket.InitiateMultipartUploadInput inputParam = new Bucket.InitiateMultipartUploadInput();
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

Bucket.InitiateMultipartUploadInput input = new Bucket.InitiateMultipartUploadInput();
bucket.initiateMultipartUploadAsync(objectName,input, callback);


```


uploadMultipart processing：

```

//Please note that Multi Segmentation is based on ContentLength；it will RandomAccessFile begin from ContentLength*PartNumber


File f = new File("upload file");
Bucket.UploadMultipartInput inputUpload = new Bucket.UploadMultipartInput();
inputUpload.setContentLength(6000000);// single multi part content length
inputUpload.setBodyInputFile(f);
inputUpload.setPartNumber(i);// part index
inputUpload.setUploadID(initMultiOutput.getUploadID());
Bucket.UploadMultipartOutput uploadMultipartOutput3 = bucket.uploadMultipart(multipart_upload_name,inputUpload);
			
			
			
			
```


### Android Example

```

EnvContext env = new EnvContext("ACCESS_KEY_ID", "SECRET_ACCESS_KEY");
        QingStor storService = new QingStor(env);
        try {
            Bucket bucket = storService.getBucket(bucketName, zoneId);
            
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            File f = new File(path);
            input.setBodyInputFile(f);
            input.setContentType("text/plain");
            input.setContentLength(f.length());
            bucket.putObjectAsync(putFileName, input, new ResponseCallBack<Bucket.PutObjectOutput>() {
                @Override
                public void onAPIResponse(Bucket.PutObjectOutput putObjectOutput) throws QSException {
                    
                }
            });
        } catch (QSException e) {
            e.printStackTrace();
        }
        	
			
			
```