# 代码示例

每一个 API 方法都包含一个 Input 对象并返回一个 Output 对象.
Input 对象由请求参数, 请求头和请求体构成, Output 对象包含 HTTP 状态码, 响应头, 响应体和错误代码 (如果有错误发生).

- [列取 Buckets(List Buckets)](./example/list_buckets_zh-CN.md)
- Bucket

  - [创建 Bucket(Put Bucket)](./example/create_bucket_zh-CN.md)
  - [列取对象(List Objects)](./example/list_objects_zh-CN.md)
  - [删除 Bucket(DELETE Bucket)](./example/delete_bucket_zh-CN.md)
  - [获取 Bucket 元信息(HEAD Bucket)](./example/head_bucket_zh-CN.md)
  - [Bucket 使用统计(GET Bucket Statistics)](./example/get_bucket_statistics_zh-CN.md)
  - [列取分段上传(List Multipart Uploads)](./example/list_multipart_uploads_zh-CN.md)
  - 用户授权(Bucket ACL)
    - [PUT Bucket ACL](./example/put_ACL_zh-CN.md)
    - [GET Bucket ACL](./example/get_bucket_acl_zh-CN.md)
  - 授权策略(Bucket Policy)
    - [策略生效条件(Bucket Policy Condition)](https://docs.qingcloud.com/qingstor/api/bucket/policy/policy_condition.html)
    - [PUT Bucket Policy](./example/put_bucket_policy_zh-CN.md)
    - [GET Bucket Policy](./example/get_bucket_policy_zh-CN.md)
    - [DELETE Bucket Policy](./example/delete_bucket_policy_zh-CN.md)
  - 跨站请求设置(Bucket CORS)
    - [PUT Bucket CORS](./example/put_bucket_cors_zh-CN.md)
    - [GET Bucket CORS](./example/get_bucket_cors_zh-CN.md)
    - [DELETE Bucket CORS](./example/delete_bucket_cors_zh-CN.md)
  - 外部镜像(Bucket External Mirror)
    - [PUT Bucket External Mirror](./example/put_bucket_external_mirror_zh-CN.md)
    - [GET Bucket External Mirror](./example/get_bucket_external_mirror_zh-CN.md)
    - [DELETE Bucket External Mirror](./example/delete_bucket_external_mirror_zh-CN.md)
  - 事件处理(Bucket Notification)
    - [PUT Bucket Notification](./example/put_bucket_notification_zh-CN.md)
    - [GET Bucket Notification](./example/get_bucket_notification_zh-CN.md)
    - [DELETE Bucket Notification](./example/delete_bucket_notification_zh-CN.md)
  - 生命周期(Bucket Lifecycle)
    - [PUT Bucket Lifecycle](./example/put_bucket_lifecycle_zh-CN.md)
    - [GET Bucket Lifecycle](./example/get_bucket_lifecycle_zh-CN.md)
    - [DELETE Bucket Lifecycle](./example/delete_bucket_lifecycle_zh-CN.md)

- Object

  - [对象上传(PUT Object)](./example/put_object_zh-CN.md)
  - [对象上传时配置文件默认下载名称](./example/put_object_and_set_default_download_name_zh-CN.md)
  - [对象上传 - 回调进度、设置取消标志](./example/upload_progress_cancellation_zh-CN.md)
  - [对象上传 - 使用 Upload Manager 自动上传](./example/auto_upload_zh-CN.md)
  - [对象拷贝(PUT Object - Copy)](./example/copy_object_zh-CN.md)
  - [对象移动(PUT Object - Move)](./example/move_object_zh-CN.md)
  - [对象导入(PUT Object - Fetch)](./example/put_object_fetch_zh-CN.md)
  - [对象下载(GET Object)](./example/get_object_zh-CN.md)
  - [对象下载 - 获取下载地址](./example/get_object_url_zh-CN.md)
  - [对象下载 - 分段下载](example/get_object_by_segment_zh-CN.md)
  - [对象追加写](./example/append_object_zh-CN.md)
  - [删除对象(DELETE Object)](./example/delete_object_zh-CN.md)
  - [删除多个对象(DELETE Multiple Objects)](./example/delete_mulitple_objects_zh-CN.md)
  - [获取对象元信息(HEAD Object)](example/head_object_zh-CN.md)
  - [OPTIONS Object](./example/options_object_zh-CN.md)
  - [分段上传(Multipart)](./example/multipart_upload_zh-CN.md)
    - Initiate Multipart Upload
    - Upload Multipart
    - Upload Multipart - Copy
    - List Multipart
    - Abort Multipart Upload
    - Complete Multipart Upload

- 其他
  - [并发分段上传示例](./example/concurrent_multi_upload.md)
  - [自定义元数据](./example/custom_metadata_zh-CN.md)
  - [HTTP Headers/Parameters](./example/get_set_http_headers_zh-CN.md)
    - 获取 HTTP Headers/Parameters
    - 设置 HTTP Headers/Parameters
    - 自定义元数据
  - [数据加密](./example/encryption_zh-CN.md)
  - [使用服务端签名](./example/sign_with_server_zh-CN.md)
  - 数据处理
    - [图片处理](./example/image_process_zh-CN.md)
    - [第三方数据处理](https://docs.qingcloud.com/qingstor/data_process/third_party/)
      - [图普科技鉴黄服务](https://docs.qingcloud.com/qingstor/data_process/third_party/tupu_porn.html)
