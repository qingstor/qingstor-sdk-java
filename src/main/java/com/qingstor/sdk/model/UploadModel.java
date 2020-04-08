/*
 * Copyright (C) 2020 Yunify, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingstor.sdk.model;

import com.qingstor.sdk.annotation.ParamAnnotation;

/**
 * A model kept in the upload of {@link com.qingstor.sdk.upload.impl.FileRecorder} <br>
 *
 * @author chengww
 */
public class UploadModel extends OutputModel {
    private int currentPart;
    private String uploadID;
    // All parts of file has been completely uploaded or not.
    private boolean isFileComplete;
    private long bytesWritten;
    private long totalSize;
    private boolean isUploadComplete;

    public UploadModel() {}

    public UploadModel(
            int currentPart,
            String uploadID,
            boolean isFileComplete,
            long bytesWritten,
            long totalSize,
            boolean isUploadComplete) {
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
