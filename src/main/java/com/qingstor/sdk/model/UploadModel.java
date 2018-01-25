package com.qingstor.sdk.model;

import com.qingstor.sdk.annotation.ParamAnnotation;

/**
 * A model kept in the upload of {@link com.qingstor.sdk.upload.impl.FileRecorder} <br>
 * Created by chengww on 2018/1/23.
 */
public class UploadModel extends OutputModel{
    private int currentPart;
    private String uploadID;
    // All parts of file has been completely uploaded or not.
    private boolean isFileComplete;
    private long bytesWritten;
    private long totalSize;
    private boolean isUploadComplete;

    public UploadModel() {}

    public UploadModel(int currentPart, String uploadID,
                       boolean isFileComplete, long bytesWritten,
                       long totalSize, boolean isUploadComplete) {
        this.currentPart = currentPart;
        this.uploadID = uploadID;
        this.isFileComplete = isFileComplete;
        this.bytesWritten = bytesWritten;
        this.totalSize = totalSize;
        this.isUploadComplete = isUploadComplete;
    }

    @ParamAnnotation(paramType = "query", paramName = "currentPart")
    public int getCurrentPart() {
        return currentPart;
    }

    public void setCurrentPart(int currentPart) {
        this.currentPart = currentPart;
    }

    @ParamAnnotation(paramType = "query", paramName = "uploadID")
    public String getUploadID() {
        return uploadID;
    }

    public void setUploadID(String uploadID) {
        this.uploadID = uploadID;
    }

    @ParamAnnotation(paramType = "query", paramName = "isFileComplete")
    public boolean isFileComplete() {
        return isFileComplete;
    }

    public void setFileComplete(boolean fileComplete) {
        isFileComplete = fileComplete;
    }

    @ParamAnnotation(paramType = "query", paramName = "bytesWritten")
    public long getBytesWritten() {
        return bytesWritten;
    }

    public void setBytesWritten(long bytesWritten) {
        this.bytesWritten = bytesWritten;
    }

    @ParamAnnotation(paramType = "query", paramName = "totalSize")
    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @ParamAnnotation(paramType = "query", paramName = "isUploadComplete")
    public boolean isUploadComplete() {
        return isUploadComplete;
    }

    public void setUploadComplete(boolean uploadComplete) {
        isUploadComplete = uploadComplete;
    }
}
