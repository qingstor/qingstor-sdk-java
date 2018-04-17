## 快速指南

[English](../README.md) | 中文

您可以在这里看到包含完整包结构的SDK文档：[github pages](https://yunify.github.io/qingstor-sdk-java/)。

在开始使用 SDK 之前，请确认您已经了解[青云 QingStor 对象存储基本概念](https://docs.qingcloud.com/qingstor/api/common/overview.html) ，如 Zone，Service，Bucket，Object 等。

本 SDK 与[青云 QingStor 对象存储帮助文档](https://docs.qingcloud.com/qingstor/api/)上的方法一一对应，具体每个方法的说明请查看上述链接对应章节。

- [准备工作](./example/prepare_zh.md)
- [安装](./example/install_zh.md)
- [配置](./example/config_zh.md)
- [初始化服务](./example/service_zh.md)

## 更多 SDK 示例

每一个 API 方法都包含一个 Input 对象并返回一个 Output 对象.
Input 对象由请求参数, 请求头和请求体构成, Output 对象包含 HTTP 状态码, 响应头, 响应体和错误代码 (如果有错误发生).

- [QingStor](./example/service_zh.md)
- Bucket
    - [创建 Bucket](./example/create_bucket_zh.md)
    - [展示 Bucket 所有对象](./example/ListObjects_zh.md)
    - PUT Bucket
    - DELETE Bucket
    - HEAD Bucket
    - GET Bucket Statistics
    - List Multipart Uploads
    - Bucket ACL
        - [PUT Bucket ACL](./example/PutACL_zh.md)
        - GET Bucket ACL
    - Bucket CORS
        - PUT Bucket CORS
        - GET Bucket CORS
        - DELETE Bucket CORS
    - Bucket Policy
        - Bucket Policy Condition
        - PUT Bucket Policy
        - GET Bucket Policy
        - DELETE Bucket Policy
    - Bucket External Mirror
        - PUT Bucket External Mirror
        - GET Bucket External Mirror
        - DELETE Bucket External Mirror

- Object

    - [下载文件](./example/download_zh.md)
    - [获取文件下载地址并重命名](./example/GetObjectUrl_zh.md)
    - [大文件分段下载](./example/GetDownObjectMulti_zh.md)
    - [上传文件](./example/upload_zh.md)
    - [上传文件时配置文件默认下载名称](./example/put_object_and_set_default_download_name_zh.md)
    - [PUT Object - Copy](./example/CopyObject.md)
    - [移动文件](./example/MoveObject_zh.md)
    - [上传文件 - 回调进度、设置取消标志](./example/UploadProgressCancellation_zh.md)
    - [上传文件 - 使用 Upload Manager 自动上传](./example/AutoUpload_zh.md)
    - PUT Object - Fetch
    - [删除对象](./example/delete_object_zh.md)
    - [删除多个对象](./example/DeleteMulitpleObjects_zh.md)
    - [获取文件的元数据](./example/metadata_zh.md)
    - OPTIONS Object
    - [大文件分段上传](./example/MultipartUpload_zh.md)
       - Initiate Multipart Upload
       - Upload Multipart
       - Upload Multipart - Copy
       - List Multipart
       - Abort Multipart Upload
       - Complete Multipart Upload
- [数据加密](./example/Encryption_zh.md)
- [使用服务端签名](./example/sign_with_server_zh.md)
- 数据处理
    - [图片处理](./example/image_progress_zh.md)
    - [第三方数据处理](https://docs.qingcloud.com/qingstor/data_process/third_party/)
        - [图普科技鉴黄服务](https://docs.qingcloud.com/qingstor/data_process/third_party/tupu_porn.html)

