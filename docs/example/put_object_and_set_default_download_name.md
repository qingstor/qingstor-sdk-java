## PUT Object And Set Default Download Name

### Code Snippet
See [last example](./PutObject.md) to put object.

The current version of SDK can not set ContentDisposition header directly.

But you can set any one of the header and body params by the methods below.

Extends the correct Input class.Take putting object for example to add ContentDisposition:
```
import com.qingstor.sdk.annotation.ParamAnnotation;

/**
 * Created by chengww on 2017/12/26.
 */
public class MyPutObjectInput extends Bucket.PutObjectInput{
    private String contentDisposition;

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    @ParamAnnotation(paramType = "header", paramName = "content-disposition")
    public String getContentDisposition() {
        return this.contentDisposition;
    }
}
```

Use these codes when uploading:

```
EvnContext evn = new EvnContext("key","secret");
evn.setLog_level(QSConstant.LOGGER_INFO);
Bucket bucket = new Bucket(evn, testZone, "chengww-test");
try {
    MyPutObjectInput putObjectInput = new MyPutObjectInput();
    String fileName = QSStringUtil.percentEncode("测试文件名(1).test", "utf-8");
    putObjectInput.setContentDisposition(String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", fileName, fileName));
    putObjectInput.setBodyInputFile(new File("/Users/chengww/Downloads/1234.csv"));
    //first param is the original name(key) of the object when download.
    bucket.putObject("1234.csv", putObjectInput);
} catch (Exception e) {
    e.printStackTrace();
}
```

Now you can rename the objects(files) when downloading:
```
//...Omit creating the bucket
Bucket.GetObjectOutput output2 = bucket.getObject("1234.csv", null);
if (output2.getBodyInputStream() != null && output2.getStatueCode() == 200) {
    //Nullness check and handling the string: "attachment; filename=...".
    File ff = new File("/Users/chengww/Desktop/" + URLDecoder.decode(output2.getContentDisposition(), "utf-8"));
    OutputStream out = null;
    out = new FileOutputStream(ff);
    int bytesRead = 0;
    byte[] buffer = new byte[1024];
    while ((bytesRead = output2.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
    out.write(buffer, 0, bytesRead);
}
out.close();
output2.getBodyInputStream().close();
```

You can get the object url for downloading.

There will be the current name you set when uploading.
```
RequestHandler requestHandler = bucket.GetObjectBySignatureUrlRequest("12344.csv", null, System.currentTimeMillis()/1000 + 60*60);
System.out.println(requestHandler.getExpiresRequestUrl());

```