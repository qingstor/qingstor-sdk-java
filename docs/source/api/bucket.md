## Bucket Class

Bucket Class provides Bucket and Object level APIs.

### Parameters
 
|Name|Type|Description|
|-|-|-|
|zone|String|Zone that belongs to which area|
|bucketName|String|bucket name|
|evnContent|EvnContent|evnContent that initializes a QingStor service|

### Attribute

|Name|Type|Description|
|-|-|-|
|zone|String|Zone that belongs to which area|
|bucketName|String|bucket name|

### Functions
 
#### deleteRequest
 
Build request for delete
See Also: https://docs.qingcloud.com/qingstor/api/bucket/delete.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### delete
 
Send deleteRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/delete.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### deleteCORSRequest
 
Build request for deleteCORS
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### deleteCORS
 
Send deleteCORSRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### deleteExternalMirrorRequest
 
Build request for deleteExternalMirror
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### deleteExternalMirror
 
Send deleteExternalMirrorRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### deletePolicyRequest
 
Build request for deletePolicy
See Also: https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### deletePolicy
 
Send deletePolicyRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### deleteMultipleObjectsRequest
 
Build request for deleteMultipleObjects
See Also: https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|content-MD5|string|Object MD5sum|
|objects|array|A list of keys to delete|
|quiet|boolean|Whether to return the list of deleted objects|

##### Returns

RequestHandler:handle the request
 
#### deleteMultipleObjects
 
Send deleteMultipleObjectsRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|content-MD5|string|Object MD5sum|
|objects|array|A list of keys to delete|
|quiet|boolean|Whether to return the list of deleted objects|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getACLRequest
 
Build request for getACL
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### getACL
 
Send getACLRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getCORSRequest
 
Build request for getCORS
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### getCORS
 
Send getCORSRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getExternalMirrorRequest
 
Build request for getExternalMirror
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### getExternalMirror
 
Send getExternalMirrorRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getPolicyRequest
 
Build request for getPolicy
See Also: https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### getPolicy
 
Send getPolicyRrequest
See Also: https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getStatisticsRequest
 
Build request for getStatistics
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### getStatistics
 
Send getStatisticsRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### headRequest
 
Build request for head
See Also: https://docs.qingcloud.com/qingstor/api/bucket/head.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### head
 
Send headRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/head.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### listMultipartUploadsRequest
 
Build request for listMultipartUploads
See Also: https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|delimiter|string|Put all keys that share a common prefix into a list|
|key_marker|string|Limit results returned from the first key after key_marker sorted by alphabetical order|
|limit|integer|Results count limit|
|prefix|string|Limits results to keys that begin with the prefix|
|upload_id_marker|string|Limit results returned from the first uploading segment after upload_id_marker sorted by the time of upload_id|

##### Returns

RequestHandler:handle the request
 
#### listMultipartUploads
 
Send listMultipartUploadsRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|delimiter|string|Put all keys that share a common prefix into a list|
|key_marker|string|Limit results returned from the first key after key_marker sorted by alphabetical order|
|limit|integer|Results count limit|
|prefix|string|Limits results to keys that begin with the prefix|
|upload_id_marker|string|Limit results returned from the first uploading segment after upload_id_marker sorted by the time of upload_id|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### listObjectsRequest
 
Build request for listObjects
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|delimiter|string|Put all keys that share a common prefix into a list|
|limit|integer|Results count limit|
|marker|string|Limit results to keys that start at this marker|
|prefix|string|Limits results to keys that begin with the prefix|

##### Returns

RequestHandler:handle the request
 
#### listObjects
 
Send listObjectsRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/get.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|delimiter|string|Put all keys that share a common prefix into a list|
|limit|integer|Results count limit|
|marker|string|Limit results to keys that start at this marker|
|prefix|string|Limits results to keys that begin with the prefix|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putRequest
 
Build request for put
See Also: https://docs.qingcloud.com/qingstor/api/bucket/put.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### put
 
Send putRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/put.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putACLRequest
 
Build request for putACL
See Also: https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|acl|array|Bucket ACL rules|

##### Returns

RequestHandler:handle the request
 
#### putACL
 
Send putACLRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|acl|array|Bucket ACL rules|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putCORSRequest
 
Build request for putCORS
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|cors_rules|array|Bucket CORS rules|

##### Returns

RequestHandler:handle the request
 
#### putCORS
 
Send putCORSRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|cors_rules|array|Bucket CORS rules|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putExternalMirrorRequest
 
Build request for putExternalMirror
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|source_site|string|Source site url|

##### Returns

RequestHandler:handle the request
 
#### putExternalMirror
 
Send putExternalMirrorRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|source_site|string|Source site url|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putPolicyRequest
 
Build request for putPolicy
See Also: https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|statement|array|Bucket policy statement|

##### Returns

RequestHandler:handle the request
 
#### putPolicy
 
Send putPolicyRrequest
See Also: https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|statement|array|Bucket policy statement|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### abortMultipartUploadRequest
 
Build request for abortMultipartUpload
See Also: https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|upload_id|string|Object multipart upload ID|

##### Returns

RequestHandler:handle the request
 
#### abortMultipartUpload
 
Send abortMultipartUploadRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|upload_id|string|Object multipart upload ID|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### completeMultipartUploadRequest
 
Build request for completeMultipartUpload
See Also: https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|eTag|string|MD5sum of the object part|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|upload_id|string|Object multipart upload ID|
|object_parts|array|Object parts|

##### Returns

RequestHandler:handle the request
 
#### completeMultipartUpload
 
Send completeMultipartUploadRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|eTag|string|MD5sum of the object part|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|upload_id|string|Object multipart upload ID|
|object_parts|array|Object parts|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### deleteObjectRequest
 
Build request for deleteObject
See Also: https://docs.qingcloud.com/qingstor/api/object/delete.html

##### Parameters

None

##### Returns

RequestHandler:handle the request
 
#### deleteObject
 
Send deleteObjectRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/delete.html

##### Parameters
 
None

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### getObjectRequest
 
Build request for getObject
See Also: https://docs.qingcloud.com/qingstor/api/object/get.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|if-Match|string|Check whether the ETag matches|
|if-Modified-Since|timestamp|Check whether the object has been modified|
|if-None-Match|string|Check whether the ETag does not match|
|if-Unmodified-Since|timestamp|Check whether the object has not been modified|
|range|string|Specified range of the object|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|response-cache-control|string|Specified the Cache-Control response header|
|response-content-disposition|string|Specified the Content-Disposition response header|
|response-content-encoding|string|Specified the Content-Encoding response header|
|response-content-language|string|Specified the Content-Language response header|
|response-content-type|string|Specified the Content-Type response header|
|response-expires|string|Specified the Expires response header|

##### Returns

RequestHandler:handle the request
 
#### getObject
 
Send getObjectRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/get.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|if-Match|string|Check whether the ETag matches|
|if-Modified-Since|timestamp|Check whether the object has been modified|
|if-None-Match|string|Check whether the ETag does not match|
|if-Unmodified-Since|timestamp|Check whether the object has not been modified|
|range|string|Specified range of the object|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|response-cache-control|string|Specified the Cache-Control response header|
|response-content-disposition|string|Specified the Content-Disposition response header|
|response-content-encoding|string|Specified the Content-Encoding response header|
|response-content-language|string|Specified the Content-Language response header|
|response-content-type|string|Specified the Content-Type response header|
|response-expires|string|Specified the Expires response header|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### headObjectRequest
 
Build request for headObject
See Also: https://docs.qingcloud.com/qingstor/api/object/head.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|if-Match|string|Check whether the ETag matches|
|if-Modified-Since|timestamp|Check whether the object has been modified|
|if-None-Match|string|Check whether the ETag does not match|
|if-Unmodified-Since|timestamp|Check whether the object has not been modified|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|

##### Returns

RequestHandler:handle the request
 
#### headObject
 
Send headObjectRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/head.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|if-Match|string|Check whether the ETag matches|
|if-Modified-Since|timestamp|Check whether the object has been modified|
|if-None-Match|string|Check whether the ETag does not match|
|if-Unmodified-Since|timestamp|Check whether the object has not been modified|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### initiateMultipartUploadRequest
 
Build request for initiateMultipartUpload
See Also: https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Type|string|Object content type|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|

##### Returns

RequestHandler:handle the request
 
#### initiateMultipartUpload
 
Send initiateMultipartUploadRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Type|string|Object content type|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### listMultipartRequest
 
Build request for listMultipart
See Also: https://docs.qingcloud.com/qingstor/api/object/list_multipart.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|limit|integer|Limit results count|
|part_number_marker|integer|Object multipart upload part number|
|upload_id|string|Object multipart upload ID|

##### Returns

RequestHandler:handle the request
 
#### listMultipart
 
Send listMultipartRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/list_multipart.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|limit|integer|Limit results count|
|part_number_marker|integer|Object multipart upload part number|
|upload_id|string|Object multipart upload ID|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### optionsObjectRequest
 
Build request for optionsObject
See Also: https://docs.qingcloud.com/qingstor/api/object/options.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|access-Control-Request-Headers|string|Request headers|
|access-Control-Request-Method|string|Request method|
|origin|string|Request origin|

##### Returns

RequestHandler:handle the request
 
#### optionsObject
 
Send optionsObjectRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/options.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|access-Control-Request-Headers|string|Request headers|
|access-Control-Request-Method|string|Request method|
|origin|string|Request origin|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### putObjectRequest
 
Build request for putObject
See Also: https://docs.qingcloud.com/qingstor/api/object/put.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Length|long|Object content size|
|content-MD5|string|Object MD5sum|
|content-Type|string|Object content type|
|expect|string|Used to indicate that particular server behaviors are required by the client|
|x-QS-Copy-Source|string|Copy source, format (/<bucket-name>/<object-key>)|
|x-QS-Copy-Source-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Copy-Source-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Copy-Source-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Copy-Source-If-Match|string|Check whether the copy source matches|
|x-QS-Copy-Source-If-Modified-Since|timestamp|Check whether the copy source has been modified|
|x-QS-Copy-Source-If-None-Match|string|Check whether the copy source does not match|
|x-QS-Copy-Source-If-Unmodified-Since|timestamp|Check whether the copy source has not been modified|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Fetch-If-Unmodified-Since|timestamp|Check whether fetch target object has not been modified|
|x-QS-Fetch-Source|string|Fetch source, should be a valid url|
|x-QS-Move-Source|string|Move source, format (/<bucket-name>/<object-key>)|

##### Returns

RequestHandler:handle the request
 
#### putObject
 
Send putObjectRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/put.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Length|long|Object content size|
|content-MD5|string|Object MD5sum|
|content-Type|string|Object content type|
|expect|string|Used to indicate that particular server behaviors are required by the client|
|x-QS-Copy-Source|string|Copy source, format (/<bucket-name>/<object-key>)|
|x-QS-Copy-Source-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Copy-Source-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Copy-Source-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Copy-Source-If-Match|string|Check whether the copy source matches|
|x-QS-Copy-Source-If-Modified-Since|timestamp|Check whether the copy source has been modified|
|x-QS-Copy-Source-If-None-Match|string|Check whether the copy source does not match|
|x-QS-Copy-Source-If-Unmodified-Since|timestamp|Check whether the copy source has not been modified|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Fetch-If-Unmodified-Since|timestamp|Check whether fetch target object has not been modified|
|x-QS-Fetch-Source|string|Fetch source, should be a valid url|
|x-QS-Move-Source|string|Move source, format (/<bucket-name>/<object-key>)|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### uploadMultipartRequest
 
Build request for uploadMultipart
See Also: https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Length|long|Object multipart content length|
|content-MD5|string|Object multipart content MD5sum|
|x-QS-Copy-Range|string|Specify range of the source object|
|x-QS-Copy-Source|string|Copy source, format (/<bucket-name>/<object-key>)|
|x-QS-Copy-Source-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Copy-Source-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Copy-Source-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Copy-Source-If-Match|string|Check whether the Etag of copy source matches the specified value|
|x-QS-Copy-Source-If-Modified-Since|timestamp|Check whether the copy source has been modified since the specified date|
|x-QS-Copy-Source-If-None-Match|string|Check whether the Etag of copy source does not matches the specified value|
|x-QS-Copy-Source-If-Unmodified-Since|timestamp|Check whether the copy source has not been unmodified since the specified date|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|part_number|integer|Object multipart upload part number|
|upload_id|string|Object multipart upload ID|

##### Returns

RequestHandler:handle the request
 
#### uploadMultipart
 
Send uploadMultipartRrequest
See Also: https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|object_key|str|Object key name|
|content-Length|long|Object multipart content length|
|content-MD5|string|Object multipart content MD5sum|
|x-QS-Copy-Range|string|Specify range of the source object|
|x-QS-Copy-Source|string|Copy source, format (/<bucket-name>/<object-key>)|
|x-QS-Copy-Source-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Copy-Source-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Copy-Source-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|x-QS-Copy-Source-If-Match|string|Check whether the Etag of copy source matches the specified value|
|x-QS-Copy-Source-If-Modified-Since|timestamp|Check whether the copy source has been modified since the specified date|
|x-QS-Copy-Source-If-None-Match|string|Check whether the Etag of copy source does not matches the specified value|
|x-QS-Copy-Source-If-Unmodified-Since|timestamp|Check whether the copy source has not been unmodified since the specified date|
|x-QS-Encryption-Customer-Algorithm|string|Encryption algorithm of the object|
|x-QS-Encryption-Customer-Key|string|Encryption key of the object|
|x-QS-Encryption-Customer-Key-MD5|string|MD5sum of encryption key|
|part_number|integer|Object multipart upload part number|
|upload_id|string|Object multipart upload ID|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 




