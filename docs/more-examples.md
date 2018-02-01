## SDK Example

Each API function take an Input struct and return an Output struct. The Input struct consists of request params, request headers and request elements, and the Output holds the HTTP status code, response headers, response elements and error message (if error occurred).

- [QingStor](./example/QingStor.md)
- Bucket
    - [GET Bucket (List Objects)](./example/ListObjects.md)
    - PUT Bucket
    - DELETE Bucket
    - HEAD Bucket
    - GET Bucket Statistics
    - List Multipart Uploads
    - Bucket ACL
        - [PUT Bucket ACL](./example/PutACL.md)
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

    - [GET Object](./example/GetObject.md)
    - [GET Object Download Url](./example/GetObjectUrl.md)
    - [GET Object Multi](./example/GetDownObjectMulti.md)
    - [PUT Object](./example/PutObject.md)
    - [PUT Object - Set Default Download Name](./example/put_object_and_set_default_download_name.md)
    - [PUT Object - Copy](./example/CopyObject.md)
    - [PUT Object - Move](./example/MoveObject.md)
    - [PUT Object - Call Progress And Cancellation](./example/UploadProgressCancellation.md)
    - [PUT Object - Auto Upload With Upload Manager](./example/AutoUpload.md)
    - PUT Object - Fetch
    - [DELETE Object](./example/DeleteObject.md)
    - [Delete Multiple Objects](./example/DeleteMulitpleObjects.md)
    - HEAD Object
    - OPTIONS Object
    - [MultipartUpload](./example/MultipartUpload.md)
       - Initiate Multipart Upload
       - Upload Multipart
       - Upload Multipart - Copy
       - List Multipart
       - Abort Multipart Upload
       - Complete Multipart Upload
- [Encryption](./example/Encryption.md)
- [Sign With Server](./example/sign_with_server.md)

