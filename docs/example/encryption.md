## Encryption Example

### Code Snippet

You can encrypt data when uploading.

To understand the process of encryption better, visit the link [https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/encryption/](https://docsv4.qingcloud.com/user_guide/storage/object_storage/api/object/encryption/) .

#### Encryption Example Of Uploading

```java
Bucket.PutObjectInput putObjectInput = new Bucket.PutObjectInput();

putObjectInput.setXQSEncryptionCustomerAlgorithm("AES256");
putObjectInput.setXQSEncryptionCustomerKey("key");
putObjectInput.setXQSEncryptionCustomerKeyMD5("MD5 of the key");
putObjectInput.setBodyInputFile(new File("file path"));

```

#### Deciphering Example Of Downloading

When download the file has been encrypted, you need decipher it like the codes below.

```java
Bucket.GetObjectInput putObjectInput = new Bucket.GetObjectInput();

putObjectInput.setXQSEncryptionCustomerAlgorithm("AES256");
putObjectInput.setXQSEncryptionCustomerKey("key");
putObjectInput.setXQSEncryptionCustomerKeyMD5("MD5 of the key");
Bucket.GetObjectOutput output = bucket.getObject("your-object-name", putObjectInput);
```
