## Auto Upload With Upload Manager

You can use class 'UploadManager' to auto upload.
We will keep the position you last uploaded via interface 'Recorder'('FileRecorder' is an impl of it).
Next time you upload this file, upload position will start at last kept.

When a file's size is larger than 4 MB(You can change default mulit upload size by the method uploadManager.setPartSize(long size)), it will be multi uploaded,
else it will be uploaded with a form(will call progress but not save the uploaded position).

Default mehtods of UploadManagerCallback ：
- onSignature()：If you need sign with server, override the method. It will be called when need a signed string. Then return the string signed with server.

- onAccessKey()：If you need sign with server, override the method. It will be called when need the access key. Then return your access key.

- onCorrectTime(): If you need correct your local time, override this method and return the time of server.

You can also see 'Solutions Of App Integration' https://docs.qingcloud.com/qingstor/solutions/app_integration.html , while the access key is not kept in the client.

Attention: the method 'put()' used to upload a file,
will create a sync request to upload.

You must handle the request yourself if a sync request is not allowed
in main thread(such as running on the Android devices).

You can also [visit here](https://github.com/chengwwYunify/qingstor-upload-test
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
    final String TAG = "UploadManagerCallback";

    @Override
    public String onSignature(String strToSign) { // If you need sign with server, override this method, send 'strToSign' to server and return signed string.
        return null;
    }

    @Override
    public String onAccessKey() { // If you need sign with server, override this method and return the access key.
        return null;
    }

    @Override
    public String onCorrectTime() { // If you need correct your local time, override this method and return the time of server.
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