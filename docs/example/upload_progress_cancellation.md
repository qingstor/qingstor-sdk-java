## Call Progress And Cancellation When Uploading

### Code Snippet

Now you can add a progress listener and a cancellation handler when uploading.

Get the request handler and set a progress listener.

```java
// A sync request.
RequestHandler requestHandler = bucket.putObjectRequest(objectKey, input);
// Set progress listener.
if (progressListener != null) {
    requestHandler.setProgressListener(progressListener);
}
OutputModel out = requestHandler.send();

// An async request.
RequestHandler requestHandler = bucket.putObjectAsyncRequest(objectKey, input, callback);
// Set progress listener.
if (progressListener != null) {
    requestHandler.setProgressListener(progressListener);
}
requestHandler.sendAsync();

```

You can set a cancellation to handle cancellation only in the progress request body.

In the cancellation handler, when the method 'isCancelled()' returns true,
a upload will be cancelled.

```java
// Cancellation handler.
requestHandler.setCancellationHandler(cancellationHandler);

```
