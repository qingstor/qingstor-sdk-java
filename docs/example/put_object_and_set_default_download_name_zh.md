## 上传文件时配置文件默认下载名称

### 代码片段
参考[上一个示例](./upload_zh.md)上传文件

现在版本暂时还不可直接设置 ContentDisposition header ，您可以通过如下方法实现自定义设定任意 header 和 body 参数。

继承相应 Input 类，以上传文件为例，添加 ContentDisposition ：
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

在上传文件时使用：

```
EnvContext env = new EnvContext("key","secret");
env.setLog_level(QSConstant.LOGGER_INFO);
Bucket bucket = new Bucket(env, testZone, "chengww-test");
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

现在您可以在下载文件时重命名:
```
//...省略Bucket创建
Bucket.GetObjectOutput output2 = bucket.getObject("1234.csv", null);
if (output2.getBodyInputStream() != null && output2.getStatueCode() == 200) {
    //自行判断是否空字符串并处理前缀"attachment; filename=..."
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

或获取下载链接，直接浏览器打开即可自动下载并处理文件名称
```
RequestHandler requestHandler = bucket.GetObjectBySignatureUrlRequest("12344.csv", null, System.currentTimeMillis()/1000 + 60*60);
System.out.println(requestHandler.getExpiresRequestUrl());

```