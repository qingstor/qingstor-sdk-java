package com.qingstor.sdk.upload;

/**
 * Created by chengww on 2018/12/25.
 */
public interface UploadProgressListener {
    void onProgress(String objectKey, long currentSize, long totalSize);
}
