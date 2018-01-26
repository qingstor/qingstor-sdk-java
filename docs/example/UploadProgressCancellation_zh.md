## 上传时回调进度以及设置取消标志



### 代码片段
现在您可以添加上传进度监听以及设置取消标志。

获取 request handler 来设置进度监听.

```
// 同步请求
RequestHandler requestHandler = bucket.putObjectRequest(objectKey, input);
// 进度监听
if (progressListener != null) {
    requestHandler.setProgressListener(progressListener);
}
OutputModel out = requestHandler.send();

// 异步请求
RequestHandler requestHandler = bucket.putObjectAsyncRequest(objectKey, input, callback);
// 进度监听
if (progressListener != null) {
    requestHandler.setProgressListener(progressListener);
}
requestHandler.sendAsync();

```

仅在设置进度监听后，您可以设置取消上传标志。

在 CancellationHandler 这个接口中, 当方法 'isCancelled()' 返回 true 时,
上传将被取消。

```
// Cancellation handler.
requestHandler.setCancellationHandler(cancellationHandler);

```

