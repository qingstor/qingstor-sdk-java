## QingStor Service Class

QingStor Service Class provides QingStor Service API (API Version 2016-01-06)

### Parameters

|Name|Type|Description|
|-|-|-|
|zone|String|zone's name|
|evnContent|EvnContent|Config that initializes a QingStor service|
|bucketName|String|bucket'name|

### Attribute

|Name|Type|Description|
|-|-|-|
|zone|String|zone's name|
|evnContent|EveContent|Config that initializes a QingStor service|

### Functions
#### listBucketsRequest
 
Build request for listBuckets
See Also: https://docs.qingcloud.com/qingstor/api/service/get.html

##### Parameters
|Name|Type|Description|
|-|-|-|
|location|string|Limits results to buckets that in the location|

##### Returns

RequestHandler:handle the request
 
#### listBuckets
 
Send listBucketsRequest
See Also: https://docs.qingcloud.com/qingstor/api/service/get.html

##### Parameters
 
|Name|Type|Description|
|-|-|-|
|location|string|Limits results to buckets that in the location|

##### Returns

DeleteBucketCORSOutput: Remove the cross-source resource sharing (cors) policy for storage space
 
#### Bucket

Create a Bucket instance with QingStor service

##### Parameters

|Name|Type|Description|
|-|-|-|
|bucket_name|str|Bucket's name|
|zone|str|Zone's name|

##### Returns
Bucket: Create a Bucket instanc




