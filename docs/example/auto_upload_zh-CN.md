## 使用 Upload Manager 自动上传文件

您可以使用类 'UploadManager' 来自动上传文件。
通过接口 'Recorder'(该接口的一个实现：'FileRecorder')，我们会为您保存上次上传的位置。
下次上传时，上传进度会从上次断点处上传。

文件大于 4 MB 时会分段进行上传（您可以通过方法 uploadManager.setPartSize(long size) 来设置默认分段上传的大小），否则只会通过表单上传(会回调进度但不能保存断点)。

UploadManagerCallback 的默认方法 (Default methods) 说明：

- onSignature() 方法：在您使用签名服务器进行签名时，请重写该方法。然后将服务端返回的已签名字符串返回。

- onAccessKey() 方法：在您使用签名服务器进行签名时，请重写该方法。然后返回 access key。

- onCorrectTime() 方法：在您需要校准本地时间时，请重写该方法，并返回服务端时间。

对于在上传客户端不保存 access key 的情况，还可以配合 “移动 App 接入方案” https://docsv4.qingcloud.com/user_guide/storage/object_storage/beat_practices/app_integration/ 使用。

注意：使用 'put()' 方法上传文件时，会创建同步请求来上传。

如果在主线程不能创建同步请求，您需要自行处理请求(如：运行在安卓设备上)。

您可以 [点击这里](https://github.com/chengwwYunify/qingstor-upload-test) 来查看安卓示例。

### 代码片段

```java
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

UploadProgressListener listener = new UploadProgressListener() {
    @Override
    public void onProgress(String objectKey, long currentSize, long totalSize) {
        float progress = (float) currentSize * 100 / totalSize;
        System.out.println(objectKey + ": uploading = " + String.format("%.2f", progress) + " %");
    }
};

UploadManagerCallback callback = new UploadManagerCallback() {
    final String TAG = "UploadManagerCallback";

    @Override
    public String onSignature(String strToSign) { // 用服务端进行签名时重写该方法，发送 'strToSign' 到服务端并返回已签名的字符串
        return null;
    }

    @Override
    public String onAccessKey() { // 用服务端进行签名时重写该方法，并返回 access key
        return null;
    }

    @Override
    public String onCorrectTime() { // 需要校准本地时间时重写该方法，并返回服务端时间
        return null;
    }

    @Override
    public void onAPIResponse(String objectKey, OutputModel output) {
        // 上传完成、暂停、失败会回调此方法
        Log.d(TAG, "objectKey = " + objectKey);
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
