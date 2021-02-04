## List Multipart Uploads

This operation lists in-progress multipart uploads. An in-progress multipart upload is a multipart upload that has been initiated using the Initiate Multipart Upload request, but has not yet been completed or aborted.

This operation returns at most 1,000 multipart uploads in the response. 1,000 multipart uploads is the maximum number of uploads a response can include, which is also the default value. You can further limit the number of uploads in a response by specifying the max-uploads parameter in the response. If additional multipart uploads satisfy the list criteria, the response will contain an IsTruncated element with the value true. To list the additional multipart uploads, use the key-marker and upload-id-marker request parameters.

In the response, the uploads are sorted by key. If your application has initiated more than one multipart upload using the same object key, then uploads in the response are first sorted by key. Additionally, uploads are sorted in ascending order within each key by the upload initiation time.

For information on permissions required to use the multipart upload API, see Multipart Upload API and Permissions in [API Docs](https://docs.qingcloud.com/qingstor/api/object/multipart/list_multipart.html#object-storage-api-list-multipart).

### Request Parameters

You can add some options when list multipart uploads.

You can set options below in ListMultipartUploadsInput. See controlled [API Docs](https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html).

|  Parameter name  |  Type   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Required |
| :--------------: | :-----: | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | :------: |
|      prefix      | String  | Limits the response to keys that begin with the specified prefix.                                                                                                                                                                                                                                                                                                                                                                                                                                 |    No    |
|    delimiter     |  Char   | A delimiter is a character you use to group keys.<br/>If you specify a prefix, all keys that contain the same string between the prefix and the first occurrence of the delimiter after the prefix are grouped under a single result element called CommonPrefixes.                                                                                                                                                                                                                               |    No    |
|    key_marker    | String  | Together with upload-id-marker, this parameter specifies the multipart upload after which listing should begin.<br>If upload-id-marker is not specified, only the keys lexicographically greater than the specified key-marker will be included in the list.<br>If upload-id-marker is specified, any multipart uploads for a key equal to the key-marker might also be included, provided those multipart uploads have upload IDs lexicographically greater than the specified upload-id-marker. |    No    |
| upload_id_marker | String  | Together with key-marker, specifies the multipart upload after which listing should begin. If key-marker is not specified, the upload-id-marker parameter is ignored. Otherwise, any multipart uploads for a key equal to the key-marker might be included in the list only if they have an upload ID lexicographically greater than the specified upload-id-marker.                                                                                                                              |    No    |
|      limit       | Integer | Sets the maximum number of objects returned in the response body. Default is 200, maximum is 1000.                                                                                                                                                                                                                                                                                                                                                                                                |    No    |

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```java
EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
String zoneName = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);
```

Objects in above codes：

- bucket: An Object to operate Bucket. You can use all of the API with level Bucket and Object with the object.

After created the object, we need perform the action to list multipart uploads：

```java
    private void listMultipartUploads(Bucket bucket) {
        try {
            Bucket.ListMultipartUploadsInput input = new Bucket.ListMultipartUploadsInput();
            // See Request Parameters to set input
            Bucket.ListMultipartUploadsOutput output = bucket.listMultipartUploads(input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("=======List Multipart Uploads======");
                System.out.println("Name = " + output.getName());
                List<Types.UploadsModel> uploads = output.getUploads();
                if (uploads != null && uploads.size() > 0) {
                    System.out.println("Uploads = " + new Gson().toJson(uploads));
                } else {
                    System.out.println("Uploads is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to List Multipart Uploads.");
                System.out.println("StatueCode = " + output.getStatueCode());
                System.out.println("Message = " + output.getMessage());
                System.out.println("RequestId = " + output.getRequestId());
                System.out.println("Code = " + output.getCode());
                System.out.println("Url = " + output.getUrl());
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
```
