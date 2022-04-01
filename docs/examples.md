## SDK Example

Each API function take an Input struct and return an Output struct.
The Input struct consists of request params, request headers and request elements,
and the Output holds the HTTP status code, response headers, response elements and error message (if error occurred).

- [List Buckets](./example/list_buckets.md)
- Bucket

  - [Create Bucket(Put Bucket)](./example/create_bucket.md)
  - [GET Bucket(List Objects)](./example/list_objects.md)
  - [DELETE Bucket](./example/delete_bucket.md)
  - [HEAD Bucket](./example/head_bucket.md)
  - [GET Bucket Statistics](./example/get_bucket_statistics.md)
  - [List Multipart Uploads](./example/list_multipart_uploads.md)
  - Bucket ACL
    - [PUT Bucket ACL](./example/put_ACL.md)
    - [GET Bucket ACL](./example/get_bucket_acl.md)
  - Bucket Policy
    - [Bucket Policy Condition](https://docs.qingcloud.com/qingstor/api/bucket/policy/policy_condition.html)
    - [PUT Bucket Policy](./example/put_bucket_policy.md)
    - [GET Bucket Policy](./example/get_bucket_policy.md)
    - [DELETE Bucket Policy](./example/delete_bucket_policy.md)
  - Bucket CORS
    - [PUT Bucket CORS](./example/put_bucket_cors.md)
    - [GET Bucket CORS](./example/get_bucket_cors.md)
    - [DELETE Bucket CORS](./example/delete_bucket_cors.md)
  - Bucket External Mirror
    - [PUT Bucket External Mirror](./example/put_bucket_external_mirror.md)
    - [GET Bucket External Mirror](./example/get_bucket_external_mirror.md)
    - [DELETE Bucket External Mirror](./example/delete_bucket_external_mirror.md)
  - Bucket Notification
    - [PUT Bucket Notification](./example/put_bucket_notification.md)
    - [GET Bucket Notification](./example/get_bucket_notification.md)
    - [DELETE Bucket Notification](./example/delete_bucket_notification.md)
  - Bucket Lifecycle
    - [PUT Bucket Lifecycle](./example/put_bucket_lifecycle.md)
    - [GET Bucket Lifecycle](./example/get_bucket_lifecycle.md)
    - [DELETE Bucket Lifecycle](./example/delete_bucket_lifecycle.md)

- Object

  - [PUT Object](./example/put_object.md)
  - [PUT Object - Set Default Download Name](./example/put_object_and_set_default_download_name.md)
  - [PUT Object - Call Progress And Cancellation](./example/upload_progress_cancellation.md)
  - [PUT Object - Auto Upload With Upload Manager](./example/auto_upload.md)
  - [PUT Object - Copy](./example/copy_object.md)
  - [PUT Object - Move](./example/move_object.md)
  - [PUT Object - Fetch](./example/put_object_fetch.md)
  - [Append Object](./example/append_object.md)
  - [GET Object](./example/get_object.md)
  - [GET Object Download Url](./example/get_object_url.md)
  - [GET Object Multi](example/get_object_by_segment.md)
  - [DELETE Object](./example/delete_object.md)
  - [DELETE Multiple Objects](./example/delete_mulitple_objects.md)
  - [HEAD Object](./example/head_object.md)
  - [OPTIONS Object](./example/options_object.md)
  - [MultipartUpload](./example/multipart_upload.md)
    - Initiate Multipart Upload
    - Upload Multipart
    - Upload Multipart - Copy
    - List Multipart
    - Abort Multipart Upload
    - Complete Multipart Upload

- Others
  - [concurrent multipart upload](./example/concurrent_multi_upload.md)
  - [custom metadata](./example/custom_metadata.md)
  - [HTTP Headers/Parameters](./example/get_set_http_headers.md)
    - Get HTTP Headers/Parameters
    - Set HTTP Headers/Parameters
    - Define metadata
  - [Encryption](./example/encryption.md)
  - [Sign With Server](./example/sign_with_server.md)
  - Data Process
    - [Image Process](./example/image_process.md)
    - [Third Party Data](https://docs.qingcloud.com/qingstor/data_process/third_party/)
      - [Tupu Porn](https://docs.qingcloud.com/qingstor/data_process/third_party/tupu_porn.html)
