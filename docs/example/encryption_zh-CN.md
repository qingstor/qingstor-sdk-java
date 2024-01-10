## 数据加密

### 代码片段

上传时可对数据进行加密。

访问该链接 [https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/encryption/](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/encryption/) .
以更好的理解数据加密解密的过程。

#### 上传文件时加密

```java
Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();

putObjectInput.setXQSEncryptionCustomerAlgorithm("AES256");
putObjectInput.setXQSEncryptionCustomerKey("key");
putObjectInput.setXQSEncryptionCustomerKeyMD5("MD5 of the key");
putObjectInput.setBodyInputFile(new File("file path"));

```

#### 下载时解密

下载加密文件需要对文件进行解密。请参考以下示例：

```java
Bucket.GetObjectInput putObjectInput = new Bucket.GetObjectInput();

putObjectInput.setXQSEncryptionCustomerAlgorithm("AES256");
putObjectInput.setXQSEncryptionCustomerKey("key");
putObjectInput.setXQSEncryptionCustomerKeyMD5("MD5 of the key");
Bucket.GetObjectOutput output = bucket.getObject("your-object-name", putObjectInput);
```
