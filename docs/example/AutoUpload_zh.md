## 使用 Upload Manager 自动上传文件

您可以使用类 'UploadManager' 来自动上传文件。
通过接口 'Recorder'(该接口的一个实现：'FileRecorder')，我们会为您保存上次上传的位置。
下次上传时，上传进度会从上次断点处上传。

文件大于 5 MB 时会分段进行上传，否则只会通过表单上传(会回调进度但不能保存断点)。

UploadManagerCallback 的说明：
- onSignature() 方法：在您使用签名服务器进行签名时，该方法会在每次需要签名时调用。您需要将服务端返回的已签名字符串返回。如不需服务端签名，请返回 null 即可。

- onAccessKey() 方法：在您使用签名服务器进行签名时，该方法会在每次需要 access key 时调用。您需要返回 access key 以创建请求。如不需服务端签名，请返回 null 即可。

对于在上传客户端不保存 access key 的情况，可以配合 “移动 App 接入方案” https://docs.qingcloud.com/qingstor/solutions/app_integration.html 使用。

注意：使用 'put()' 方法上传文件时，会创建同步请求来上传。

如果在主线程不能创建同步请求，您需要自行处理请求(如：运行在安卓设备上)。

您可以 [点击这里](https://github.com/chengwwYunify/qingstor-upload-test
) 来查看安卓示例。

### 代码片段

```
FileRecorder recorder = null;
try {
    recorder = new FileRecorder("records kept path");
} catch (IOException e) {
    e.printStackTrace();
}

CancellationHandler cancellationHandler = new CancellationHandler() {
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }
};

BodyProgressListener listener = new BodyProgressListener() {
    @Override
    public void onProgress(final long len, final long size) {
        int progress = (int) ((len * 100) / size);
    }
};

UploadManagerCallback callback = new UploadManagerCallback() {
    final String TAG = "onAPIResponse";

    @Override
    public String onSignature(String strToSign) {
        // 用签名服务器进行签名时, 发送 'strToSign' 到服务端并返回已签名的字符串。
        return null;
    }

    @Override
    public String onAccessKey() {
        // 用服务端进行签名时, 返回 access key.
        return null;
    }

    @Override
    public void onAPIResponse(OutputModel output) throws QSException {
        // 上传成功或失败会回调此方法
        Log.d(TAG, "code = " + output.getCode());
        Log.d(TAG, "statueCode = " + output.getStatueCode());
        Log.d(TAG, "message = " + output.getMessage());
        Log.d(TAG, "request ID = " + output.getRequestId());
        Log.d(TAG, "url = " + output.getUrl());

        if (output.getStatueCode() == 200 || output.getStatueCode() == 201) {
            Log.d(TAG, "Upload success.");
        } else if (output.getStatueCode() == QSConstant.REQUEST_ERROR_CANCELLED) {
            Log.d(TAG, "Stopped.");
        } else {
            Log.d(TAG, "Error: " + output.getMessage());
        }
    }
};

manager = new UploadManager(bucket, recorder, listener, cancellationHandler, callback);
manager.put(new File(filePath));

```