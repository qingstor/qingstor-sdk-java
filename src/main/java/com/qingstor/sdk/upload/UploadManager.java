// +-------------------------------------------------------------------------
// | Copyright (C) 2016 Yunify, Inc.
// +-------------------------------------------------------------------------
// | Licensed under the Apache License, Version 2.0 (the "License");
// | you may not use this work except in compliance with the License.
// | You may obtain a copy of the License in the LICENSE file, or at:
// |
// | http://www.apache.org/licenses/LICENSE-2.0
// |
// | Unless required by applicable law or agreed to in writing, software
// | distributed under the License is distributed on an "AS IS" BASIS,
// | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// | See the License for the specific language governing permissions and
// | limitations under the License.
// +-------------------------------------------------------------------------
package com.qingstor.sdk.upload;

import com.google.gson.Gson;
import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.UploadModel;
import com.qingstor.sdk.request.BodyProgressListener;
import com.qingstor.sdk.request.CancellationHandler;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.utils.QSStringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;


/**
 * A manager of uploading. <br>
 * Created by chengww on 2018/1/23.
 */
public class UploadManager {

    private long partSize = 4 * 1024 * 1024L;
    private final int maxPartCounts = 10000; // Max part counts. Do not change it.
    private int partCounts;

    private Recorder recorder;
    private Bucket bucket;
    private BodyProgressListener progressListener;
    private CancellationHandler cancellationHandler;
    private UploadModel uploadModel;
    private UploadManagerCallback callBack;

    public UploadManager(Bucket bucket, Recorder recorder,
                         BodyProgressListener progressListener,
                         CancellationHandler cancellationHandler,
                         UploadManagerCallback callBack) {
        this.recorder = recorder;
        this.bucket = bucket;
        this.progressListener = progressListener;
        this.cancellationHandler = cancellationHandler;
        this.callBack = callBack;
    }

    public UploadManager(Bucket bucket, UploadManagerCallback callBack) {
        this(bucket, null, null, null, callBack);
    }

    /**
     * Upload a file with a sync request.
     * @param file file
     * @throws QSException exception
     */
    public void put(File file) throws QSException {

        if (!file.exists() || file.isDirectory())
            throw new QSException("File does not exist or it is a directory.");

        put(file, file.getName(), file.getName(), "");
    }

    /**
     * Upload a file with a sync request.
     * @param file file
     * @param objectKey key of the object in the bucket
     * @param fileName file name of response(content-disposition) when downloaded
     * @param eTag MD5 info
     * @throws QSException exception
     */
    public void put(File file, String objectKey, String fileName, String eTag) throws QSException {
        // Check the file does exist or not.
        if (!file.exists() || file.isDirectory())
            throw new QSException("File does not exist or it is a directory.");

        // Check file's length.
        long length = file.length();
        if (length < 1) throw new QSException("The size of file cannot be smaller than 1 byte.");
        if (length <= partSize) {
            partCounts = 1;
            putFile(file, objectKey, fileName, length);
        } else { // Do multi uploads.
            // Calculate part counts.
            if (length / partSize > maxPartCounts) {
                partSize = length / maxPartCounts;
                partCounts = maxPartCounts;
                // Check every part's size(max 5GB).
                if (partSize > 5 * 1024 * 1024 * 1024L)
                    throw new QSException("The size of file is too large.");
            } else {
                partCounts = (int) (length / partSize);
                if (length % partSize > 0) partCounts += 1;
            }
            putFileMulti(file, objectKey, fileName, eTag, length);
        }
    }

    /**
     * Upload a file with a multi upload as a sync request.
     * @param file file
     * @param objectKey key of the object in the bucket
     * @param fileName file name of response(content-disposition) when downloaded
     * @param eTag MD5 info
     * @param length length of the file.
     * @throws QSException exception
     */
    public void putFileMulti(File file, String objectKey, String fileName, String eTag, final long length) throws QSException {

        if (partSize < 4 * 1024 * 1024) {
            throw new QSException("Every part of the file can not smaller than 4 MB.");
        }

        if (recorder == null || recorder.get(objectKey) == null) {
            Bucket.InitiateMultipartUploadInput inputInit = new Bucket.InitiateMultipartUploadInput();
            Bucket.InitiateMultipartUploadOutput initOutput = bucket.initiateMultipartUpload(objectKey, inputInit);
            uploadModel = new UploadModel();
            uploadModel.setTotalSize(length);
            uploadModel.setUploadID(initOutput.getUploadID());
        } else {
            byte[] bytes = recorder.get(objectKey);
            String json = new String(bytes);
            uploadModel = new Gson().fromJson(json, UploadModel.class);
            // Check status of the task.
            if (uploadModel.isUploadComplete()) {
                if (callBack != null) {
                    OutputModel outputModel = new OutputModel();
                    outputModel.setStatueCode(201);
                    outputModel.setMessage("This task has been uploaded.");
                    callBack.onAPIResponse(outputModel);
                }
                return;
            }
        }

        uploadModel.setTotalSize(length);


        // Check if all parts have been completely uploaded.
        if (uploadModel.isFileComplete()) {
            completeMultiUpload(objectKey, fileName, eTag, uploadModel.getUploadID(), length);
        } else {
            for (int i = uploadModel.getCurrentPart(); i < partCounts; i++) {
                // Make records when a new part starts to upload.
                uploadModel.setCurrentPart(i);
                uploadModel.setBytesWritten(i * partSize);
                setData(objectKey, recorder);
                // Request cancelled. Stop upload other parts.
                if (cancellationHandler != null && cancellationHandler.isCancelled()) {
                    // Keep parts data into the upload.
                    setData(objectKey, recorder);
                    break;
                }

                long contentLength = Math.min(partSize, (file.length() - uploadModel.getCurrentPart() * partSize));
                Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
                input.setBodyInputFilePart(file);
                input.setFileOffset(i * partSize);
                input.setContentLength(contentLength);
                input.setPartNumber(i);
                input.setUploadID(uploadModel.getUploadID());

                // Create request to upload a part.
                RequestHandler requestHandler = bucket.uploadMultipartRequest(objectKey, input);

                // Progress listener
                if (progressListener != null) {
                    requestHandler.setProgressListener(new BodyProgressListener() {
                        @Override
                        public void onProgress(long len, long size) {
                            long bytesWritten = uploadModel.getCurrentPart() * partSize + len;
                            progressListener.onProgress(bytesWritten, length);
                        }
                    });
                }


                // Cancellation handler.
                requestHandler.setCancellationHandler(cancellationHandler);

                // Sign with server if needed.
                sign(requestHandler);

                // Send the request.
                OutputModel send = requestHandler.send();

                // Check response.
                if (send.getStatueCode() != 200 && send.getStatueCode() != 201) { // Failed.
                    setData(objectKey, recorder);
                    // On upload failed
                    if (callBack != null)
                        callBack.onAPIResponse(send);

                    // Once failed, break the circle.
                    break;
                } else if (i == partCounts - 1) { // Success and it is the last part of th file.
                    // Make a record in the upload.
                    uploadModel.setBytesWritten(length);
                    uploadModel.setFileComplete(true);
                    setData(objectKey, recorder);
                }
            }

            // Finally check.
            if (uploadModel.isFileComplete()) {
                completeMultiUpload(objectKey, fileName, eTag, uploadModel.getUploadID(), length);
            }
        }



    }

    /**
     * When sending a request will call this method to sign with server.
     * @param requestHandler handler of the request.
     * @throws QSException exception
     */
    private void sign(RequestHandler requestHandler) throws QSException {
        if (callBack != null) {
            String signed = callBack.onSignature(requestHandler.getStringToSignature());
            if (!QSStringUtil.isEmpty(signed))
                requestHandler.setSignature(callBack.onAccessKey(), signed);
        }
    }

    /**
     * Set data in the upload with a recorder.
     * @param objectKey key
     * @param recorder recorder
     */
    private void setData(String objectKey, Recorder recorder) {
        if (recorder == null) return;
        String upload = new Gson().toJson(uploadModel);
        recorder.set(objectKey, upload.getBytes());
    }

    /**
     * Complete the multi upload.
     * @param objectKey key of the object in the bucket
     * @param fileName file name of response(content-disposition) when downloaded
     * @param eTag MD5 info
     * @param uploadID multi upload id
     * @param length length of the file
     * @throws QSException exception
     */
    private void completeMultiUpload(String objectKey, String fileName, String eTag, String uploadID, long length) throws QSException {
        CompleteMultipartUploadInput completeMultipartUploadInput =
                new CompleteMultipartUploadInput(uploadID, partCounts, 0);

        completeMultipartUploadInput.setContentLength(length);

        // Set content disposition to the object.
        if (!QSStringUtil.isEmpty(fileName)) {
            try {
                String keyName = QSStringUtil.percentEncode(fileName, "UTF-8");
                completeMultipartUploadInput.setContentDisposition(String.format(
                        "attachment; filename=\"%s\"; filename*=utf-8''%s", keyName, keyName));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // Set the Md5 info to the object.
        if (!QSStringUtil.isEmpty(eTag)) {
            completeMultipartUploadInput.setETag(eTag);
        }

        RequestHandler requestHandler =
                bucket.completeMultipartUploadRequest(objectKey, completeMultipartUploadInput);

        sign(requestHandler);

        Bucket.CompleteMultipartUploadOutput send =
                (Bucket.CompleteMultipartUploadOutput) requestHandler.send();

        if (send.getStatueCode() == 200 || send.getStatueCode() == 201) {
            uploadModel.setUploadComplete(true);
            setData(objectKey, recorder);
        }

        // Response callback.
        if (callBack != null)
            callBack.onAPIResponse(send);
    }

    /**
     * Upload a file with a simple put object upload as a sync request. <br>
     * If a file's size is less than {@link UploadManager#partSize}, there will call this method.
     * @param file file
     * @param objectKey key of the object in the bucket
     * @param fileName file name of response(content-disposition) when downloaded
     * @param length length of the file
     * @throws QSException exception
     */
    public void putFile(File file, String objectKey,
                        String fileName, long length) throws QSException {
        PutObjectInput input = new PutObjectInput();
        input.setContentLength(length);
        input.setBodyInputFile(file);
        // Set content disposition to the object.
        if (!QSStringUtil.isEmpty(fileName)) {
            try {
                String keyName = QSStringUtil.percentEncode(fileName, "UTF-8");
                input.setContentDisposition(String.format(
                        "attachment; filename=\"%s\"; filename*=utf-8''%s", keyName, keyName));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        RequestHandler requestHandler = bucket.putObjectRequest(objectKey, input);

        // Set progress listener.
        if (progressListener != null) {
            requestHandler.setProgressListener(progressListener);
        }

        // Cancellation handler.
        requestHandler.setCancellationHandler(cancellationHandler);

        // Sign if needed.
        sign(requestHandler);

        if (callBack != null)
            callBack.onAPIResponse(requestHandler.send());
    }

    public static class CompleteMultipartUploadInput
            extends Bucket.CompleteMultipartUploadInput {
        private String contentDisposition;

        private Long contentLength;

        @ParamAnnotation(paramType = "header", paramName = "Content-Length")
        public Long getContentLength() {
            return contentLength;
        }

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        public void setContentDisposition(String contentDisposition) {
            this.contentDisposition = contentDisposition;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-disposition")
        public String getContentDisposition() {
            return this.contentDisposition;
        }

        public CompleteMultipartUploadInput(String multipart_upload_id, int partsCount, int startIndex) {
            super(multipart_upload_id, partsCount, startIndex);
        }
    }

    public static class PutObjectInput extends Bucket.PutObjectInput {
        private String contentDisposition;

        public void setContentDisposition(String contentDisposition) {
            this.contentDisposition = contentDisposition;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-disposition")
        public String getContentDisposition() {
            return this.contentDisposition;
        }

    }

    // Getter and setter methods.

    public long getPartSize() {
        return partSize;
    }

    public void setPartSize(long partSize) {
        this.partSize = partSize;
    }

    public Recorder getRecorder() {
        return recorder;
    }

    public void setRecorder(Recorder recorder) {
        this.recorder = recorder;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public BodyProgressListener getProgressListener() {
        return progressListener;
    }

    public void setProgressListener(BodyProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public CancellationHandler getCancellationHandler() {
        return cancellationHandler;
    }

    public void setCancellationHandler(CancellationHandler cancellationHandler) {
        this.cancellationHandler = cancellationHandler;
    }

    public UploadManagerCallback getCallBack() {
        return callBack;
    }

    public void setCallBack(UploadManagerCallback callBack) {
        this.callBack = callBack;
    }
}
