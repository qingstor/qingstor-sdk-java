## Auto Upload With Upload Manager

You can use class 'UploadManager' to auto upload.
We will keep the position you last uploaded via interface 'Recorder'('FileRecorder' is an impl of it).
Next time you upload this file, upload position will start at last kept.

When a file's size is larger than 5 MB, it will be multi uploaded,
else it will be uploaded with a form(will call progress but not save the uploaded position).

UploadManagerCallback ：
- onSignature()：If you need sign with server, the method will be called when need signed string. You need return the string signed with server. If you do not need sign with server, just return null.

- onAccessKey()：If you need sign with server, the method will be called when need the access key. You need return the access key to create the request. If you do not need sign with server, just return null.

While the access key is not kept in the client, you can do with 'Solutions Of App Integration' https://docs.qingcloud.com/qingstor/solutions/app_integration.html .

Attention: the method 'put()' used to upload a file,
will create a sync request to upload.

You must handler the request yourself if there is not allowed to
create a sync request in the main thread(such as on the Android devices).

You can [visit here](https://github.com/chengwwYunify/qingstor-upload-test
) to see the android example.




### Code Snippet

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
        // If you need sign with server, send 'strToSign' to server and return signed string.
        return null;
    }

    @Override
    public String onAccessKey() {
        // If you need sign with server, return the access key.
        return null;
    }

    @Override
    public void onAPIResponse(OutputModel output) throws QSException {
        // The method will be called when a upload request is finished.
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