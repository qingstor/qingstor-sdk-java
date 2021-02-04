## HTTP Headers/Parameters

在 SDK 中您不能直接获取某个请求的 HTTP Headers/Parameters.
但是您可以通过以下方法自定义 HTTP Headers/Parameters.

`* 如您仅想设置请求头(Request Headers)，您可以通过获取对应请求的 RequestHandler。然后通过 requestHandler.getBuilder().setHeader("key", "value"); 进行设置。`

### 设置 Request Headers/Parameters

参考[上传文件时配置文件默认下载名称](./put_object_and_set_default_download_name_zh.md)，以上传文件并发送以下自定义 HTTP Headers(Parameters 同理)。

- x-qs-meta-filename
- content-disposition

第 1 步：新建一个 public 权限的 class 文件：MyPutObjectInput，继承相应 Input 类。<br>
仿照对应 Input 参数写法，写出需要的参数。

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

第 2 步：在上传文件时，使用 MyPutObjectInput 替换 PutObjectInput：

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

这样上传时就将上述自定义 Headers 发送到了 Server。

#### 获取 Response Headers/Parameters

以 HeadObject 为例，获取刚才上传的自定义 Headers/Parameters：

第 1 步：新建一个 public 权限的 class 文件：MyHeadObjectOutput，继承相应 Output 类。<br>
仿照对应 Output 参数写法，写出需要的参数。

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

第 2 步：获取对应请求的 RequestHandler：

```java
RequestHandler requestHandler = bucket.headObjectRequest("huaban.png", null);
```

第 3 步：反射设置网络请求时封装的回应体 Output 为刚才新建的 MyHeadObjectOutput。然后做网络请求即可。

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

### 自定义元数据

修改已存在对象元数据是通过对象拷贝 (PUT Object - Copy)来完成的。
对对象自身进行拷贝，同时在请求中传入 "x-qs-metadata-directive: replace" 请求头。
然后将需要修改/新增的元数据同时传入到请求头即可。

现在以修改上面上传到 Bucket(名为 "bucketName") 根目录的文件("huanban.png") 的元信息为例。

第 1 步：在 MyPutObjectInput 中加入 "x-qs-metadata-directive" 参数：

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

第 2 步：传入请求头，发送请求

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

您也可以访问 [API Docs](https://docs.qingcloud.com/qingstor/api/common/metadata) 以查看更多关于自定义元数据的信息。
