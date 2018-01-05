# Change Log
All notable changes to QingStor SDK for JAVA will be documented in this file.

## [v2.2.3] - 2018-01-05

### Fixed

- Fixed the encoding problem.

### Add

- Add some examples.

    You can see the examples here:

    [English Version](docs/more-examples.md)
    [Chinese Version](docs/guide_zh.md)

## [v2.2.2] - 2017-12-21

### Fixed

- Fixed java docs errors in the template.

- Fixed twice encode when set responseContentDisposition.

    Now you can set responseContentDisposition like this:

    ```java
        Bucket.GetObjectInput inputs = new Bucket.GetObjectInput();
        String fileName = "测试图片(测试).jpg";
        String urlName = URLEncoder.encode(fileName, "utf-8");
        //do not add blanks int the key "attachment;filename="
        inputs.setResponseContentDisposition("attachment;filename=" + urlName);
        RequestHandler handle = bucket.GetObjectBySignatureUrlRequest("测试图片(测试).jpg", inputs, System.currentTimeMillis() + 10000);
        String tempUrl = handle.getExpiresRequestUrl();
    ```

### Added

- Add config to change url style.

    Available url style:
    One is the default, when requestUrlStyle != QSConstant#PATH_STYLE}
    You may see the url like this(QSConstant#VIRTUAL_HOST_STYLE):
    https://bucket-name.zone-id.qingstor.com/object-name
    Otherwise you may see the url like this(QSConstant#PATH_STYLE):
    https://zone-id.qingstor.com/bucket-name/object-name

    - Now you can add configs in EvnContext to change url style.
    In java:

    ```java
        EvnContext evn = EvnContext.loadFromFile("config_stor.yaml");
        Bucket bucket = new Bucket(evn, zoneId, bucketName);
    ```

    About file config_stor.yaml you can add configs below:

    ```yaml
    # QingStor services configuration

    access_key_id: 'ACCESS_KEY_ID'
    secret_access_key: 'SECRET_ACCESS_KEY'

    host: 'qingstor.com'
    port: '443'
    protocol: 'https'
    connection_retries: 3
    # uri: '/iaas'

    # Valid log levels are "debug", "info", "warn", "error", and "fatal".
    log_level: 'warn'

    # Valid request url styles are "virtual_host_style"(default) and "path_style"
    request_url_style: 'path_style'
    ```

    Attention: if some of the configs you do not use, please add a "#" at the first of the line.
    Like this: ``` # port: '443' ```

    - Or set request url style with EvnContext.

    ```java
        EvnContext evn = new EvnContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
        //Valid request url styles' params are QSConstant.PATH_STYLE and QSConstant.VIRTUAL_HOST_STYLE
        evn.setRequestUrlStyle(QSConstant.PATH_STYLE);
        String zoneName = "pek3a";
        String bucketName = "testBucketName";
        Bucket bucket = new Bucket(evn, zoneKey, bucketName);
    ```


## [v2.2.1] - 2017-11-07

### Changed

- Remove  required content-length parameter for upload inputstream.

## [v2.2.0] - 2017-09-28

### Added

- Add image process API.

### Changed

- Add Last-Modified to getObject response head.
- Optimize type definition. 

## [v2.1.10] - 2017-07-08

### Changed

- Remove default zone.
- Remove deprecated method.
- Update dependency okhttp lib to 3.5.0.

## [v2.1.9] - 2017-06-23

### Changed

- Assign query signature to request params .

## [v2.1.8] - 2017-06-21

### Changed

- Modify List Multipart Uploads API.
- Add new headers to UploadMultipart.
- Add Content-Length to GetObject response headers.

## [v2.1.7] - 2017-06-04

### Fixed

- Object to jsonstring optimization.
- Adjust response message model.

## [v2.1.6] - 2017-04-24

### Fixed

- Fixed encoders are not thread-safe.

## [v2.1.5] - 2017-02-28

### Changed

- Improve the implementation of list multipart uploads.

### Fixed

- Out of int type range.

## [v2.1.4] - 2017-02-11

### Fixed

- Url inputstream buffer read.

## [v2.1.3] - 2017-01-23

### Fixed

- Assii character encode.

## [v2.1.2] - 2017-01-13

### Fixed

- Object name is contain the same as the bucket name will get error suffix path.

## [v2.1.1] - 2016-12-28

### Changed

- Android adapter.

## [v2.1.0] - 2016-12-25

### Added

- Add request parameters for GET Object.
- Add IP address conditions for bucket policy.
- Add more parameters to sign.

### Fixed

- Fix signer bug.

## [v2.0.1] - 2016-12-15

### Changed

- Improve the implementation of deleting multiple objects.

## [v2.0.0] - 2016-12-14

### Added

- QingStor SDK for the JAVA programming language.
[v2.2.1]: https://github.com/yunify/qingstor-sdk-java/compare/2.2.0...2.2.1
[v2.2.0]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.10...2.2.0
[v2.1.10]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.9...2.1.10
[v2.1.9]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.8...2.1.9
[v2.1.8]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.7...2.1.8
[v2.1.7]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.6...2.1.7
[v2.1.6]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.5...2.1.6
[v2.1.5]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.4...2.1.5
[v2.1.4]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.3...2.1.4
[v2.1.3]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.2...2.1.3
[v2.1.2]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.1...2.1.2
[v2.1.1]: https://github.com/yunify/qingstor-sdk-java/compare/2.1.0...2.1.1
[v2.1.0]: https://github.com/yunify/qingstor-sdk-java/compare/2.0.1...2.1.0
[v2.0.1]: https://github.com/yunify/qingstor-sdk-java/compare/2.0.0...2.0.1

