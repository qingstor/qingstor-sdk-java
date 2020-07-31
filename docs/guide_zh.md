## 快速指南

[English](../README.md) | 中文

您可以在这里看到包含完整包结构的SDK文档：[github pages](https://yunify.github.io/qingstor-sdk-java/)。

在开始使用 SDK 之前，请确认您已经了解[青云 QingStor 对象存储基本概念](https://docs.qingcloud.com/qingstor/api/common/overview.html) ，如 Zone，Service，Bucket，Object 等。

本 SDK 与[青云 QingStor 对象存储帮助文档](https://docs.qingcloud.com/qingstor/api/)上的方法一一对应，具体每个方法的说明请查看上述链接对应章节。

- [准备工作](./example/prepare_zh.md)
- [安装](./example/install_zh.md)
- [配置](./example/config_zh.md)
- [初始化服务](./example/service_zh.md)
- [Demo 示例](./example/demo.md)

## 更多 SDK 示例

每一个 API 方法都包含一个 Input 对象并返回一个 Output 对象.
Input 对象由请求参数, 请求头和请求体构成, Output 对象包含 HTTP 状态码, 响应头, 响应体和错误代码 (如果有错误发生).

- [列取 Buckets(List Buckets)](./example/list_buckets_zh.md)
- Bucket
    - [创建 Bucket(Put Bucket)](./example/create_bucket_zh.md)
    - [列取对象(List Objects)](./example/ListObjects_zh.md)
    - [删除 Bucket(DELETE Bucket)](./example/delete_bucket_zh.md)
    - [获取 Bucket 元信息(HEAD Bucket)](./example/head_bucket_zh.md)
    - [Bucket 使用统计(GET Bucket Statistics)](./example/get_bucket_statistics_zh.md)
    - [列取分段上传(List Multipart Uploads)](./example/list_multipart_uploads_zh.md)
    - 用户授权(Bucket ACL)
        - [PUT Bucket ACL](./example/PutACL_zh.md)
        - [GET Bucket ACL](./example/get_bucket_acl_zh.md)
    - 授权策略(Bucket Policy)
        - [策略生效条件(Bucket Policy Condition)](https://docs.qingcloud.com/qingstor/api/bucket/policy/policy_condition.html)
        - [PUT Bucket Policy](./example/put_bucket_policy_zh.md)
        - [GET Bucket Policy](./example/get_bucket_policy_zh.md)
        - [DELETE Bucket Policy](./example/delete_bucket_policy_zh.md)
    - 跨站请求设置(Bucket CORS)
        - [PUT Bucket CORS](./example/put_bucket_cors_zh.md)
        - [GET Bucket CORS](./example/get_bucket_cors_zh.md)
        - [DELETE Bucket CORS](./example/delete_bucket_cors_zh.md)
    - 外部镜像(Bucket External Mirror)
        - [PUT Bucket External Mirror](./example/put_bucket_external_mirror_zh.md)
        - [GET Bucket External Mirror](./example/get_bucket_external_mirror_zh.md)
        - [DELETE Bucket External Mirror](./example/delete_bucket_external_mirror_zh.md)
    - 事件处理(Bucket Notification)
        - [PUT Bucket Notification](./example/put_bucket_notification_zh.md)
        - [GET Bucket Notification](./example/get_bucket_notification_zh.md)
        - [DELETE Bucket Notification](./example/delete_bucket_notification_zh.md)
    - 生命周期(Bucket Lifecycle)
        - [PUT Bucket Lifecycle](./example/put_bucket_lifecycle_zh.md)
        - [GET Bucket Lifecycle](./example/get_bucket_lifecycle_zh.md)
        - [DELETE Bucket Lifecycle](./example/delete_bucket_lifecycle_zh.md)

- Object

    - [对象上传(PUT Object)](./example/upload_zh.md)
    - [对象上传时配置文件默认下载名称](./example/put_object_and_set_default_download_name_zh.md)
    - [对象上传 - 回调进度、设置取消标志](./example/UploadProgressCancellation_zh.md)
    - [对象上传 - 使用 Upload Manager 自动上传](./example/AutoUpload_zh.md)
    - [对象拷贝(PUT Object - Copy)](./example/put_object_copy_zh.md)
    - [对象移动(PUT Object - Move)](./example/MoveObject_zh.md)
    - [对象导入(PUT Object - Fetch)](./example/put_object_fetch_zh.md)
    - [对象下载(GET Object)](./example/download_zh.md)
    - [对象下载 - 获取下载地址](./example/GetObjectUrl_zh.md)
    - [对象下载 - 分段下载](./example/GetDownObjectMulti_zh.md)
    - [对象追加写](./example/append_object_zh.md)
    - [删除对象(DELETE Object)](./example/delete_object_zh.md)
    - [删除多个对象(DELETE Multiple Objects)](./example/DeleteMulitpleObjects_zh.md)
    - [获取对象元信息(HEAD Object)](./example/metadata_zh.md)
    - [OPTIONS Object](./example/options_object_zh.md)
    - [分段上传(Multipart)](./example/MultipartUpload_zh.md)
       - Initiate Multipart Upload
       - Upload Multipart
       - Upload Multipart - Copy
       - List Multipart
       - Abort Multipart Upload
       - Complete Multipart Upload
- 其他

    - [HTTP Headers/Parameters](./example/get_set_http_headers_zh.md)
        - 获取 HTTP Headers/Parameters
        - 设置 HTTP Headers/Parameters
        - 自定义元数据
    - [数据加密](./example/Encryption_zh.md)
    - [使用服务端签名](./example/sign_with_server_zh.md)
    - 数据处理
        - [图片处理](./example/image_process_zh.md)
        - [第三方数据处理](https://docs.qingcloud.com/qingstor/data_process/third_party/)
            - [图普科技鉴黄服务](https://docs.qingcloud.com/qingstor/data_process/third_party/tupu_porn.html)

