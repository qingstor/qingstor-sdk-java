```java
package com.qingstor.sdk.test_classes;

import com.google.gson.Gson;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.request.BodyProgressListener;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Types;
import com.qingstor.sdk.upload.UploadManager;
import com.qingstor.sdk.upload.UploadManagerCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengww on 2018/7/5.
 */
public class Examples {
    private static final String ACCESS_KEY = "your_access_key";
    private static final String ACCESS_SECRET = "your_access_secret";

    public static final String PROTOCOL = "https";
    public static final String HOST = "qingstor.com";

    public static void main(String... args) {
        Examples examples = new Examples();
        EnvContext context = new EnvContext(ACCESS_KEY, ACCESS_SECRET);
        context.setProtocol(PROTOCOL);
        context.setHost(HOST);
        context.setRequestUrlStyle(QSConstant.PATH_STYLE);
        String zoneKey = "pek3b";
        String bucketName = "test-in-2018-08-08-by-chengww";
        Bucket bucket = new Bucket(context, zoneKey, bucketName);
        examples.testAll(bucket, bucketName);
    }

    public void testAll(Bucket bucket, String bucketName) {

        long start = System.currentTimeMillis();

        try {
            // Create a new Bucket
            Bucket.PutBucketOutput output = bucket.put();
            if (output.getStatueCode() == 201) {
                System.out.println("Put Bucket OK");
                headBucket(bucket);

                putBucketACL(bucket);
                getBucketACL(bucket);

                putBucketPolicy(bucket, bucketName);
                getBucketPolicy(bucket);
                deleteBucketPolicy(bucket);

                putBucketCORS(bucket);
                getBucketCORS(bucket);
                deleteBucketCORS(bucket);

                putBucketExternalMirror(bucket);
                getBucketExternalMirror(bucket);
                deleteBucketExternalMirror(bucket);

                putBucketNotification(bucket);
                getBucketNotification(bucket);
                deleteBucketNotification(bucket);

                putBucketLifecycle(bucket);
                getBucketLifecycle(bucket);
                deleteBucketLifecycle(bucket);

                String uploadFileName = "pek1.zip";
                try {
                    putObject(bucket, "folder/" + uploadFileName, "/Users/chengww/Downloads/vpn/" + uploadFileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String multiPartUploadFileName = "pub-qingstor-sdk-java.zip";
                String filePath = "/Users/chengww/Downloads/backup/pub-qingstor-sdk-java.zip";
                putObjectAuto(bucket, filePath);

                getObject(bucket, "folder/" + uploadFileName, "/Users/chengww/Desktop/test.zip");

                putObjectCopy(bucket, "/" + bucketName + "/folder/" + uploadFileName, "folder-copied/" + uploadFileName);
                putObjectMove(bucket, "/" + bucketName + "/folder/" + uploadFileName, "folder-moved/" + uploadFileName);
                putObjectFetch(bucket, "https://www.qingcloud.com/static/assets/images/icons/common/footer_logo.svg", "folder-fetched/qingcloud_footer_logo.svg");

                headObject(bucket, "folder-copied/" + uploadFileName);
                optionsObject(bucket, "folder-copied/" + uploadFileName);

                listObjects(bucket, null, null);
                listMultipartUploads(bucket);
                getBucketStatistics(bucket);

                deleteObject(bucket, "folder-copied/" + uploadFileName);

                List<String> objectKeys = new ArrayList<>();
                objectKeys.add(multiPartUploadFileName);
                objectKeys.add("folder-moved/" + uploadFileName);
                objectKeys.add("folder-fetched/qingcloud_footer_logo.svg");
                deleteMultpieObjects(bucket, objectKeys);
                deleteBucket(bucket);
            } else {
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }

        System.out.println("End: Time = " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * Delete Multpie Objects once
     *
     * @param bucket     bucket
     * @param objectKeys a string list of the objectKey
     *                   objectKey looks like this: "folder/fileName".<br/>
     *                   If objectKey = "fileName", means the object is in the bucket's root folder.
     * @throws QSException exception will be thrown if (objectKeys == null || objectKeys.size() < 1)
     */
    public void deleteMultpieObjects(Bucket bucket, List<String> objectKeys) throws QSException {
        if (objectKeys == null || objectKeys.size() < 1) {
            throw new QSException("ObjectKeys list must contains at least one key.");
        }

        Bucket.DeleteMultipleObjectsInput deleteInput = new Bucket.DeleteMultipleObjectsInput();
        List<Types.KeyModel> keyList = new ArrayList<>();
        for (String key : objectKeys) {
            Types.KeyModel keyModel = new Types.KeyModel();
            keyModel.setKey(key);
            keyList.add(keyModel);
        }
        deleteInput.setObjects(keyList);
        Bucket.DeleteMultipleObjectsOutput output = bucket.deleteMultipleObjects(deleteInput);
        if (output.getStatueCode() == 200 || output.getStatueCode() == 204) {
            // Deleted
            System.out.println("Delete Multpie Objects: Deleted.");
            System.out.println("Delete Multpie Objects: Keys = \n" + new Gson().toJson(keyList));
        } else {
            // Failed
            System.out.println("Failed to delete multiple objects.");
            handleError(output);
        }
    }

    /**
     * Delete Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
     */
    public void deleteObject(Bucket bucket, String objectKey) {
        try {
            Bucket.DeleteObjectOutput output = bucket.deleteObject(objectKey);
            if (output.getStatueCode() == 204) {
                // Deleted
                System.out.println("Delete Object: Deleted. ");
                System.out.println("Delete Object: Object key = " + objectKey);
            } else {
                // Failed
                System.out.println("Failed to delete " + objectKey);
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

    /**
     * Head Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
     */
    public void headObject(Bucket bucket, String objectKey) {
        try {
            Bucket.HeadObjectInput input = new Bucket.HeadObjectInput();
            Bucket.HeadObjectOutput output = bucket.headObject(objectKey, input);
            if (output.getStatueCode() == 200) {
                System.out.println("Head Object success.");
                System.out.println("ObjectKey = " + objectKey);
                System.out.println("ContentLength = " + output.getContentLength());
                System.out.println("ContentType = " + output.getContentType());
                System.out.println("ETag = " + output.getETag());
                System.out.println("LastModified = " + output.getLastModified());
                System.out.println("XQSEncryptionCustomerAlgorithm = " + output.getXQSEncryptionCustomerAlgorithm());
                System.out.println("XQSStorageClass = " + output.getXQSStorageClass());
            } else {
                // Failed
                System.out.println("Head Object failed.");
                System.out.println("ObjectKey = " + objectKey);
                handleError(output);
            }

        } catch (QSException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get(Download) an object from the bucket.
     *
     * @param bucket        bucket
     * @param objectKey     looks like this: "folder/fileName".<br/>
     *                      If objectKey = "fileName", means the object is in the bucket's root folder.
     * @param localKeptPath the object will be kept in this path.
     */
    public void getObject(Bucket bucket, String objectKey, String localKeptPath) {
        try {
            Bucket.GetObjectInput input = new Bucket.GetObjectInput();
            Bucket.GetObjectOutput output = bucket.getObject(objectKey, input);
            InputStream inputStream = output.getBodyInputStream();
            if (output.getStatueCode() == 200) {
                if (inputStream != null) {
                    FileOutputStream fos = new FileOutputStream(localKeptPath);
                    int len;
                    byte[] bytes = new byte[4096];
                    while ((len = inputStream.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    System.out.println("Get object success.");
                    System.out.println("ObjectKey = " + objectKey);
                    System.out.println("LocalKeptPath = " + localKeptPath);
                } else {
                    System.out.println("Get object status code == 200, but inputStream is null, skipped.");
                }
            } else {
                // Failed
                System.out.println("Failed to get object.");
                handleError(output);
            }
            if (inputStream != null) inputStream.close();
        } catch (QSException | IOException e) {
            e.printStackTrace();
        }
    }

    public void putObjectMove(Bucket bucket, String moveSource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSMoveSource(moveSource); // MoveSource looks like this: "/bucketName/folder/fileName"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-moved/fileName"
            if (output.getStatueCode() == 201) {
                // Created
                System.out.println("Put Object Move: Moved.");
                System.out.println("From " + moveSource + " to " + newObjectKey);
            } else {
                // Failed
                System.out.println("Put Object Move: Failed to move.");
                System.out.println("From " + moveSource + " to " + newObjectKey);
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putObjectCopy(Bucket bucket, String copySource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSCopySource(copySource); // CopySource looks like this: "/bucketName/folder/fileName"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-copied/fileName"
            if (output.getStatueCode() == 201) {
                // Created
                System.out.println("Put Object Copy: Copied.");
                System.out.println("From " + copySource + " to " + newObjectKey);
            } else {
                // Failed
                System.out.println("Put Object Copy: Failed to copy.");
                System.out.println("From " + copySource + " to " + newObjectKey);
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }

    }

    public void putObjectFetch(Bucket bucket, String fetchSource, String newObjectKey) {
        try {
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();
            input.setXQSFetchSource(fetchSource); // Fetch source looks like this: "protocol://host[:port]/[path]"
            Bucket.PutObjectOutput output = bucket.putObject(newObjectKey, input); // NewObjectKey looks like this: "folder-fetched/fileName"
            if (output.getStatueCode() == 201) {
                // Success
                System.out.println("Put Object - Fetch success.");
                System.out.println("From " + fetchSource + " to " + newObjectKey);
            } else {
                // Failed
                System.out.println("Put Object - Fetch failed.");
                System.out.println("From " + fetchSource + " to " + newObjectKey);
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }


    /**
     * Put a file to the bucket.
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", we will put the object into the bucket's root.
     * @param filePath  local file path
     * @throws FileNotFoundException if file does not exist, the exception will occurred.
     */
    public void putObject(Bucket bucket, String objectKey, String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException("File does not exist or it is a directory.");

        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        // Using the JDK1.7 self-bringing method to get content type(Requires JDK 1.7+, linux requires GLib.)
        try {
            String contentType = Files.probeContentType(Paths.get(filePath));
            input.setContentType(contentType);
            System.out.println("Put Object: ContentType = " + contentType);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Put Object: Get file's content type error.");
        }
        input.setContentLength(file.length());
        input.setBodyInputFile(file);
        try {
            Bucket.PutObjectOutput output = bucket.putObject(objectKey, input);
            if (output.getStatueCode() == 201) {
                System.out.println("PUT Object OK.");
                System.out.println("key = " + objectKey);
                System.out.println("path = " + filePath);
            } else {
                // Failed
                System.out.println("Failed to PUT object.");
                System.out.println("key = " + objectKey);
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }

    }

    public void putObjectAuto(Bucket bucket, String filePath) {
            UploadManager uploadManager = new UploadManager(bucket, null, new UploadProgressListener() {
                @Override
                public void onProgress(String objectKey, long currentSize, long totalSize) {
                    float progress = (float) currentSize * 100 / totalSize;
                    System.out.println(objectKey + ": uploading = " + String.format("%.2f", progress) + " %");
                }
            }, null, new UploadManagerCallback() {
                @Override
                public void onAPIResponse(String objectKey, OutputModel output) {
                    if (output.getStatueCode() == 200 || output.getStatueCode() == 201) {
                        System.out.println("Upload success.");
                    } else if (output.getStatueCode() == QSConstant.REQUEST_ERROR_CANCELLED) {
                        System.out.println("Stopped.");
                    } else {
                        handleError(output);
                    }
                }
            });

            try {
                uploadManager.put(new File(filePath));
            } catch (QSException e) {
                e.printStackTrace();
            }
        }


    public void putBucketACL(Bucket bucket) {
        try {
            Bucket.PutBucketACLInput input = new Bucket.PutBucketACLInput();
            Types.ACLModel acl = new Types.ACLModel();
            acl.setPermission("FULL_CONTROL");
            Types.GranteeModel gm = new Types.GranteeModel();
            gm.setName("QS_ALL_USERS");
            gm.setType("group");
            acl.setGrantee(gm);
            List<Types.ACLModel> lstACL = new ArrayList<>();
            lstACL.add(acl);
            input.setACL(lstACL);
            Bucket.PutBucketACLOutput output = bucket.putACL(input);
            if (output.getStatueCode() == 200) {
                System.out.println("Put bucket ACL OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket ACL.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    /**
     * List objects start with prefix(folderName), after a object named marker. Limit up to 100 data.
     * 列取所有前缀为 prefix(folderName) ，在 marker 之后的对象。每次最多 100 条数据。
     * @param bucket bucket
     * @param prefix prefix(folderName)
     * @param marker View the data on the next page. The marker is the value of next_marker returned by the last Response
     *               查看下一页的数据。marker 为上一次 Response 返回的 next_marker 的值
     * @return next_marker
     */
    public String listObjects(Bucket bucket, String prefix, String marker) {
        Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
        if (null != prefix && !"".equals(prefix)) {
            // Only show objects name start with 'prefix'
            input.setPrefix(prefix);
        }

        if (null != marker && !"".equals(marker)) {
            // Sort by name after a object named marker
            input.setMarker(marker);
        }

        input.setDelimiter("/");
        input.setLimit(100); // Default 200, max 1000
        try {
            Bucket.ListObjectsOutput output = bucket.listObjects(input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("List Objects success.");

                System.out.println("=======List Objects:Folders======");
                List<String> commonPrefixes = output.getCommonPrefixes();
                for (String folderName : commonPrefixes) {
                    System.out.println("folderName = " + folderName);
                }
                System.out.println("=======List Objects:Files======");
                List<Types.KeyModel> keys = output.getKeys();
                if (keys != null && keys.size() > 0) {
                    System.out.println("keys = " + new Gson().toJson(keys));
                }
                System.out.println("=============");

                return output.getNextMarker();
            } else {
                // Failed
                System.out.println("List Objects failed.");
                handleError(output);
            }
        } catch (QSException e) {
            // NetWork exception
            e.printStackTrace();
        }

        return null;
    }


    public void getBucketACL(Bucket bucket) {
        try {
            Bucket.GetBucketACLOutput output = bucket.getACL();
            if (output.getStatueCode() == 200) {
                // Success
                Types.OwnerModel owner = output.getOwner();
                if (owner != null) {
                    System.out.println("owner = {'name': " + owner.getName() + ", 'ID': " + owner.getID() + "}");
                } else {
                    System.out.println("owner = null");
                }
                System.out.println("=======Get Bucket ACL======");
                List<Types.ACLModel> acls = output.getACL();
                if (acls != null && acls.size() > 0) {
                    System.out.println("ACLs = " + new Gson().toJson(acls));
                } else {
                    System.out.println("ACLs is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to Get Bucket ACL.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void listMultipartUploads(Bucket bucket) {
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
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketStatistics(Bucket bucket) {
        try {
            Bucket.GetBucketStatisticsOutput output = bucket.getStatistics();
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("Get Bucket Statistics success.");
                System.out.println("Name = " + output.getName());
                System.out.println("Created = " + output.getCreated());
                System.out.println("Location = " + output.getLocation());
                System.out.println("Status = " + output.getStatus());
                System.out.println("URL = " + output.getURL()); // The method is different of output.getUrl().
                System.out.println("Count = " + output.getCount());
                System.out.println("Size = " + output.getSize());
            } else {
                // Failed
                System.out.println("Failed to Get Bucket Statistics.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void headBucket(Bucket bucket) {
        try {
            Bucket.HeadBucketOutput output = bucket.head();
            if (output.getStatueCode() == 200) {
                System.out.println("Head Bucket success.");
                // You can print http headers/parameters you defined here.
                // See [Get/Set HTTP Headers/Parameters Of A Request](./get_set_http_headers.md)
                System.out.println("You can access the Bucket.");
                System.out.println("You can print http headers/parameters you defined here.");
            } else {
                // Failed
                System.out.println("Head Bucket: You cannot access the Bucket or it does not exist.");
                handleError(output);
            }

        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucket(Bucket bucket) {
        try {
            Bucket.DeleteBucketOutput output = bucket.delete();
            if (output.getStatueCode() == 204) {
                // Deleted
                System.out.println("Delete Bucket: Deleted.");
            } else {
                // Failed
                System.out.println("Failed to Delete Bucket.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucket(Bucket bucket) {
        try {
            Bucket.PutBucketOutput output = bucket.put();
            if (output.getStatueCode() == 201) {
                // Created
                System.out.println("Put Bucket: Created.");
            } else {
                // Failed
                System.out.println("Failed to Put Bucket.");
                handleError(output);
            }

        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucketPolicy(Bucket bucket) {
        try {
            Bucket.DeleteBucketPolicyOutput output = bucket.deletePolicy();
            if (output.getStatueCode() == 204) {
                System.out.println("The policy of bucket deleted.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket policy.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketPolicy(Bucket bucket) {
        try {
            Bucket.GetBucketPolicyOutput output = bucket.getPolicy();
            if (output.getStatueCode() == 200) {
                System.out.println("====Get Bucket Policy====");
                List<Types.StatementModel> statements = output.getStatement();
                if (statements != null && statements.size() > 0) {
                    System.out.println("Statements = " + new Gson().toJson(statements));
                } else {
                    System.out.println("Statement list is empty, Policy is empty.");
                }
                System.out.println("========");
            } else {
                // Failed
                System.out.println("Failed to Get Bucket Policy.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucketPolicy(Bucket bucket, String bucketName) {
        try {
            Bucket.PutBucketPolicyInput input = new Bucket.PutBucketPolicyInput();
            List<Types.StatementModel> statement = new ArrayList<>();
            Types.StatementModel statementModel = new Types.StatementModel();
            // ID
            statementModel.setID("allow certain site to get objects");
            // User
            List<String> user = new ArrayList<>();
            user.add("*");
            statementModel.setUser(user);
            // Action
            List<String> action = new ArrayList<>();
            action.add("get_object");
            statementModel.setAction(action);
            // Effect
            statementModel.setEffect("allow");
            // Resource
            List<String> resource = new ArrayList<>();
            resource.add(bucketName + "/*");
            statementModel.setResource(resource);
            // Condition
            Types.ConditionModel conditionModel = new Types.ConditionModel();
            Types.StringLikeModel stringLikeModel = new Types.StringLikeModel();
            List<String> referer = new ArrayList<>();
            referer.add("*.example1.com");
            referer.add("*.example2.com");
            stringLikeModel.setReferer(referer);
            conditionModel.setStringLike(stringLikeModel);
            statementModel.setCondition(conditionModel);

            statement.add(statementModel);
            input.setStatement(statement);
            Bucket.PutBucketPolicyOutput output = bucket.putPolicy(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket Policy OK.");
            } else {
                // Failed
                System.out.println("PUT Bucket Policy failed.");
                handleError(output);
            }

        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucketCORS(Bucket bucket) {
        try {
            Bucket.DeleteBucketCORSOutput output = bucket.deleteCORS();
            if (output.getStatueCode() == 204) {
                System.out.println("Delete bucket CORS OK.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket CORS.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketCORS(Bucket bucket) {
        try {
            Bucket.GetBucketCORSOutput output = bucket.getCORS();
            if (output.getStatueCode() == 200) {
                System.out.println("====Get Bucket CORS====");
                List<Types.CORSRuleModel> corsRules = output.getCORSRules();
                if (corsRules != null && corsRules.size() > 0) {
                    System.out.println("CorsRules = " + new Gson().toJson(corsRules));
                } else {
                    System.out.println("CorsRules list is empty, CORS is empty.");
                }
                System.out.println("========");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket CORS.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucketCORS(Bucket bucket) {
        Bucket.PutBucketCORSInput input = new Bucket.PutBucketCORSInput();
        List<Types.CORSRuleModel> corsRuleModels = new ArrayList<>();
        Types.CORSRuleModel corsRuleModel = new Types.CORSRuleModel();
        // Allowed_origin
        corsRuleModel.setAllowedOrigin("http://*.qingcloud.com");
        // Allowed_methods
        List<String> allowed_methods = new ArrayList<>();
        allowed_methods.add("PUT");
        allowed_methods.add("GET");
        allowed_methods.add("DELETE");
        allowed_methods.add("POST");
        corsRuleModel.setAllowedMethods(allowed_methods);
        // Allowed_headers
        List<String> allowed_headers = new ArrayList<>();
        allowed_headers.add("x-qs-date");
        allowed_headers.add("Content-Type");
        allowed_headers.add("Content-MD5");
        allowed_headers.add("Authorization");
        corsRuleModel.setAllowedHeaders(allowed_headers);
        // Max_age_seconds
        corsRuleModel.setMaxAgeSeconds(200);
        // Expose_headers
        List<String> expose_headers = new ArrayList<>();
        expose_headers.add("x-qs-date");
        corsRuleModel.setExposeHeaders(expose_headers);

        corsRuleModels.add(corsRuleModel);
        input.setCORSRules(corsRuleModels);
        try {
            Bucket.PutBucketCORSOutput output = bucket.putCORS(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket CORS OK.");
            } else {
                // Failed
                System.out.println("PUT Bucket CORS failed.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucketLifecycle(Bucket bucket) {
        try {
            Bucket.DeleteBucketLifecycleOutput output = bucket.deleteLifecycle();
            if (output.getStatueCode() == 204) {
                System.out.println("Delete bucket lifecycle OK.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket lifecycle.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketLifecycle(Bucket bucket) {
        try {
            Bucket.GetBucketLifecycleOutput output = bucket.getLifecycle();
            if (output.getStatueCode() == 200) {
                System.out.println("======GET Bucket Lifecycle=======");
                List<Types.RuleModel> rules = output.getRule();
                if (rules != null && rules.size() > 0) {
                    System.out.println("Rules = " + new Gson().toJson(rules));
                } else {
                    System.out.println("GET Bucket Lifecycle: Rules is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket Lifecycle.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucketLifecycle(Bucket bucket) {
        Bucket.PutBucketLifecycleInput input = new Bucket.PutBucketLifecycleInput();
        List<Types.RuleModel> rules = new ArrayList<>();
        Types.RuleModel rule = new Types.RuleModel();
        // ID
        rule.setID("delete-logs");
        // Status
        rule.setStatus("enabled");
        // Filter
        Types.FilterModel filter = new Types.FilterModel();
        filter.setPrefix("logs/");
        rule.setFilter(filter);
        // Expiration
        Types.ExpirationModel expiration = new Types.ExpirationModel();
        expiration.setDays(180);
        rule.setExpiration(expiration);
        rules.add(rule);
        input.setRule(rules);
        try {
            Bucket.PutBucketLifecycleOutput output = bucket.putLifecycle(input);
            if (output.getStatueCode() == 200) {
                System.out.println("Put bucket lifecycle OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket lifecycle.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucketNotification(Bucket bucket) {
        try {
            Bucket.DeleteBucketNotificationOutput output = bucket.deleteNotification();
            if (output.getStatueCode() == 204) {
                System.out.println("The notification of bucket deleted.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket notification.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketNotification(Bucket bucket) {
        try {
            Bucket.GetBucketNotificationOutput output = bucket.getNotification();
            if (output.getStatueCode() == 200) {
                System.out.println("=======GET Bucket Notification======");
                List<Types.NotificationModel> notifications = output.getNotifications();
                if (notifications != null && notifications.size() > 0) {
                    System.out.println("Notifications = " + new Gson().toJson(notifications));
                } else {
                    System.out.println("GET Bucket Notification: Notifications is empty.");
                }
                System.out.println("=============");
            } else {
                // Failed
                System.out.println("Failed to GET Bucket Notification.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucketNotification(Bucket bucket) {
        Bucket.PutBucketNotificationInput input = new Bucket.PutBucketNotificationInput();
        List<Types.NotificationModel> notifications = new ArrayList<>();
        Types.NotificationModel notification = new Types.NotificationModel();
        // Cloudfunc
        notification.setCloudfunc("tupu-porn");
        // EventTypes
        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("create_object");
        notification.setEventTypes(eventTypes);
        // ID
        notification.setID("notification-1");
        // ObjectFilters: List or String
        List<String> objectFilters = new ArrayList<>();
        objectFilters.add("*");
        objectFilters.add("test_classes");
        notification.setObjectFilters(objectFilters);
        // NotifyURL
        notification.setNotifyURL("http://user_notify_url");
        notifications.add(notification);
        input.setNotifications(notifications);
        try {
            Bucket.PutBucketNotificationOutput output = bucket.putNotification(input);
            if (output.getStatueCode() == 200 || output.getStatueCode() == 201) {
                System.out.println("Put bucket notification OK.");
            } else {
                // Failed
                System.out.println("Failed to put bucket notification.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void deleteBucketExternalMirror(Bucket bucket) {
        try {
            Bucket.DeleteBucketExternalMirrorOutput output = bucket.deleteExternalMirror();
            if (output.getStatueCode() == 204) {
                System.out.println("Delete bucket external mirror OK.");
            } else {
                // Failed
                System.out.println("Failed to delete bucket external mirror.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void getBucketExternalMirror(Bucket bucket) {
        try {
            Bucket.GetBucketExternalMirrorOutput output = bucket.getExternalMirror();
            if (output.getStatueCode() == 200) {
                String sourceSite = output.getSourceSite();
                System.out.println("GET Bucket External Mirror: SourceSite = " + sourceSite);
            } else {
                // Failed
                System.out.println("Failed to GET Bucket External Mirror.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    public void putBucketExternalMirror(Bucket bucket) {
        Bucket.PutBucketExternalMirrorInput input = new Bucket.PutBucketExternalMirrorInput();
        input.setSourceSite("http://example.com:80/image/");
        try {
            Bucket.PutBucketExternalMirrorOutput output = bucket.putExternalMirror(input);
            if (output.getStatueCode() == 200) {
                System.out.println("PUT Bucket External Mirror OK.");
            } else {
                // Failed
                System.out.println("Failed to PUT Bucket External Mirror.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Options Object
     *
     * @param bucket    bucket
     * @param objectKey looks like this: "folder/fileName".<br/>
     *                  If objectKey = "fileName", means the object is in the bucket's root folder.
     */
    public void optionsObject(Bucket bucket, String objectKey) {
        try {
            Bucket.OptionsObjectInput input = new Bucket.OptionsObjectInput();
            input.setOrigin("Origin");
            input.setAccessControlRequestMethod("<http-method>");
            input.setAccessControlRequestHeaders("<request-header>");
            Bucket.OptionsObjectOutput output = bucket.optionsObject(objectKey, input);
            if (output.getStatueCode() == 200) {
                // Success
                System.out.println("Options Object success.");
                System.out.println("AccessControlAllowOrigin = " + output.getAccessControlAllowOrigin());
                System.out.println("AccessControlMaxAge = " + output.getAccessControlMaxAge());
                System.out.println("AccessControlAllowMethods = " + output.getAccessControlAllowMethods());
                System.out.println("AccessControlAllowHeaders = " + output.getAccessControlAllowHeaders());
                System.out.println("AccessControlExposeHeaders = " + output.getAccessControlExposeHeaders());
            } else {
                // Failed
                System.out.println("Failed to Options Object.");
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }


    public void handleError(OutputModel output) {
        if (output == null) return;
        System.out.println("StatusCode = " + output.getStatueCode());
        System.out.println("Message = " + output.getMessage());
        System.out.println("RequestId = " + output.getRequestId());
        System.out.println("Code = " + output.getCode());
        System.out.println("Url = " + output.getUrl());
    }

    private void listBuckets(EnvContext context) {
        QingStor stor = new QingStor(context);
        try {
            QingStor.ListBucketsOutput output = stor.listBuckets(null);
            if (output.getStatueCode() == 200) {
                System.out.println("Count = " + output.getCount());

                List<Types.BucketModel> buckets = output.getBuckets();
                System.out.println("buckets = " + new Gson().toJson(buckets));

            } else {
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
}

```