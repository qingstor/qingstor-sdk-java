## HTTP Headers/Parameters

You cannot get/set HTTP Headers/Parameters of a request in SDK directly.
But you can define HTTP Headers/Parameters using below methods.

`* If you want set Request Headers only, you can get the appropriate RequestHandler. Then use the method: requestHandler.getBuilder().setHeader("key", "value");`

### Set Request Headers/Parameters

Take uploading a file with below HTTP Headers(Parameters is the same) as an example.
See [PUT Object And Set Default Download Name](./put_object_and_set_default_download_name_zh.md) for a practical application.

- x-qs-meta-filename
- content-disposition

Step 1: Create a class with permission 'public'：MyPutObjectInput. Extends the correct Input class.<br>
See the class you extended, and write correct parameters.

```java
package com.test;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.service.Bucket;

/**
 * Created by chengww on 2018/6/11.
 */
public class MyPutObjectInput extends Bucket.PutObjectInput {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @ParamAnnotation(paramType = "header", paramName = "x-qs-meta-filename")
    public String getFileName() {
        return this.fileName;
    }

    private String disposition;

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    @ParamAnnotation(paramType = "header", paramName = "content-disposition")
    public String getDisposition() {
        return this.disposition;
    }
}
```

Step 2: Use MyPutObjectInput to replace PutObjectInput when uploading：

```java
MyPutObjectInput putObjectInput = new MyPutObjectInput();
String fileName = QSStringUtil.percentEncode("测试文件名(1).png", "utf-8");
putObjectInput.setDisposition(String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", fileName, fileName));
putObjectInput.setBodyInputFile(new File("/Users/chengww/Downloads/image-1.png"));
putObjectInput.setFileName("huaban-test.png");
// First param is the original name(key) of the object when download.
PutObjectOutput putObjectOutput = bucket.putObject("huaban.png", putObjectInput);
if (putObjectOutput.getStatueCode() == 200 || putObjectOutput.getStatueCode() == 201) {
    System.out.println("Put Object success.");
} else {
    System.out.println("Put Object failed.");
    System.out.println("StatueCode = " + putObjectOutput.getStatueCode());
    System.out.println("Message = " + putObjectOutput.getMessage());
    System.out.println("RequestId = " + putObjectOutput.getRequestId());
    System.out.println("Code = " + putObjectOutput.getCode());
    System.out.println("Url = " + putObjectOutput.getUrl());
}
```

Then the Headers you defined will send to the server.

#### Get Response Headers/Parameters

Take HeadObject as an example, to get the Headers/Parameters above we defined：
Step 1：Create a class with permission 'public'：MyHeadObjectOutput. Extends the correct Output class.<br>
See the class you extended, and write correct parameters.

```java
package com.test;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.service.Bucket;

/**
 * Created by chengww on 2018/6/11.
 */
public class MyHeadObjectOutput extends Bucket.HeadObjectOutput {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @ParamAnnotation(paramType = "header", paramName = "x-qs-meta-filename")
    public String getFileName() {
        return this.fileName;
    }

    private String disposition;

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    @ParamAnnotation(paramType = "header", paramName = "content-disposition")
    public String getDisposition() {
        return this.disposition;
    }
}
```

Step 2: Get the appropriate RequestHandler：

```java
RequestHandler requestHandler = bucket.headObjectRequest("huaban.png", null);
```

Step 3: Set MyHeadObjectOutput instead of Output to the request response by reflecting. Then send the request.

```java
Class<?> clazz = RequestHandler.class;
Field field = clazz.getDeclaredField("outputClass");
field.setAccessible(true);
field.set(requestHandler, MyHeadObjectOutput.class);

MyHeadObjectOutput output = (MyHeadObjectOutput) requestHandler.send();
if (output.getStatueCode() == 200 || output.getStatueCode() == 201) {
    System.out.println("Head Object success.");
    System.out.println("ContentType = " + output.getContentType());
    System.out.println("ContentLength = " + output.getContentLength());
    System.out.println("FileName = " + output.getFileName());
    System.out.println("Disposition = " + output.getDisposition());
} else {
    System.out.println("Head Object failed.");
    System.out.println("StatueCode = " + output.getStatueCode());
    System.out.println("Message = " + output.getMessage());
    System.out.println("RequestId = " + output.getRequestId());
    System.out.println("Code = " + output.getCode());
    System.out.println("Url = " + output.getUrl());
}
```

### Define metadata

To change the metadata of an Object existed, you can copy itself(PUT Object - Copy).
Copy the object itself, as the same time put the http header "x-qs-metadata-directive: replace" into the request.
Then put headers(metadata) you need into the request, you'll get it.

Now take changing metadata of the object(named "huanban.png") which was uploaded into the root folder of the Bucket(named "bucketName") as an example.

Step 1：Add parameters "x-qs-metadata-directive" to the class MyPutObjectInput:

```java
package com.test;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.service.Bucket;

/**
 * Created by chengww on 2018/6/11.
 */
public class MyPutObjectInput extends Bucket.PutObjectInput {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @ParamAnnotation(paramType = "header", paramName = "x-qs-meta-filename")
    public String getFileName() {
        return this.fileName;
    }

    private String disposition;

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    @ParamAnnotation(paramType = "header", paramName = "content-disposition")
    public String getDisposition() {
        return this.disposition;
    }

    private String metadataDirective;

    @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata-directive")
    public String getMetadataDirective() {
        return metadataDirective;
    }

    public void setMetadataDirective(String metadataDirective) {
        this.metadataDirective = metadataDirective;
    }
}

```

Step 2：Set current request headers and send the request:

```java
MyPutObjectInput copyInput = new MyPutObjectInput();
String fileName = "copySelf.png";
copyInput.setFileName(fileName);
copyInput.setDisposition(String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", fileName, fileName));
copyInput.setXQSCopySource("/bucketName/huaban.png"); // "/bucket/folder/fileName"
copyInput.setMetadataDirective("replace");
PutObjectOutput copyObjectSelfOutput = bucket.putObject("huaban.png", copyInput);
if (copyObjectSelfOutput.getStatueCode() == 200 || copyObjectSelfOutput.getStatueCode() == 201) {
    System.out.println("Copy object self(Change meta data) success.");
} else {
    System.out.println("Copy object self(Change meta data) failed.");
    System.out.println("StatueCode = " + copyObjectSelfOutput.getStatueCode());
    System.out.println("Message = " + copyObjectSelfOutput.getMessage());
    System.out.println("RequestId = " + copyObjectSelfOutput.getRequestId());
    System.out.println("Code = " + copyObjectSelfOutput.getCode());
    System.out.println("Url = " + copyObjectSelfOutput.getUrl());
}
```

You can also visit [API Docs](https://docs.qingcloud.com/qingstor/api/common/metadata) for more information about how to define metadata.
