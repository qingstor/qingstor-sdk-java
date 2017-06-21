// +-------------------------------------------------------------------------
// | Copyright (C) 2016 Yunify, Inc.
// +-------------------------------------------------------------------------
// | Licensed under the Apache License, Version 2.0 (the "License");
// | you may not use this work except in compliance with the License.
// | You may obtain a copy of the License in the LICENSE file, or at:
// |
// | http://www.apache.org/licenses/LICENSE-2.0
// |
// | Unless required by applicable law or agreed to in writing, software
// | distributed under the License is distributed on an "AS IS" BASIS,
// | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// | See the License for the specific language governing permissions and
// | limitations under the License.
// +-------------------------------------------------------------------------

package com.qingstor.sdk.service;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.utils.QSStringUtil;
import java.util.List;

public class Types {

    public static class ACLModel extends RequestInputModel {

        // Required

        private GranteeModel grantee;

        public void setGrantee(GranteeModel grantee) {
            this.grantee = grantee;
        }

        @ParamAnnotation(paramType = "query", paramName = "grantee")
        public GranteeModel getGrantee() {
            return this.grantee;
        } // Permission for this grantee
        // Permission's available values: READ, WRITE, FULL_CONTROL
        // Required

        private String permission;

        public void setPermission(String permission) {
            this.permission = permission;
        }

        @ParamAnnotation(paramType = "query", paramName = "permission")
        public String getPermission() {
            return this.permission;
        }

        @Override
        public String validateParam() {

            if (this.getGrantee() != null) {
                String vValidate = this.getGrantee().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }
            if (this.getGrantee() == null) {
                return QSStringUtil.getParameterRequired("Grantee", "ACL");
            }
            if (QSStringUtil.isEmpty(this.getPermission())) {
                return QSStringUtil.getParameterRequired("Permission", "ACL");
            }
            String[] permissionValidValues = {"READ", "WRITE", "FULL_CONTROL"};

            boolean permissionIsValid = false;
            for (String v : permissionValidValues) {
                if (v.equals(this.getPermission())) {
                    permissionIsValid = true;
                }
            }

            if (!permissionIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "Permission", this.getPermission() + "", permissionValidValues);
            }
            return null;
        }
    }

    public static class BucketModel extends RequestInputModel {

        // Created time of the bucket

        private String created;

        public void setCreated(String created) {
            this.created = created;
        }

        @ParamAnnotation(paramType = "query", paramName = "created")
        public String getCreated() {
            return this.created;
        } // QingCloud Zone ID

        private String location;

        public void setLocation(String location) {
            this.location = location;
        }

        @ParamAnnotation(paramType = "query", paramName = "location")
        public String getLocation() {
            return this.location;
        } // Bucket name

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "query", paramName = "name")
        public String getName() {
            return this.name;
        } // URL to access the bucket

        private String uRL;

        public void setURL(String uRL) {
            this.uRL = uRL;
        }

        @ParamAnnotation(paramType = "query", paramName = "url")
        public String getURL() {
            return this.uRL;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class ConditionModel extends RequestInputModel {

        private IPAddressModel iPAddress;

        public void setIPAddress(IPAddressModel iPAddress) {
            this.iPAddress = iPAddress;
        }

        @ParamAnnotation(paramType = "query", paramName = "ip_address")
        public IPAddressModel getIPAddress() {
            return this.iPAddress;
        }

        private IsNullModel isNull;

        public void setIsNull(IsNullModel isNull) {
            this.isNull = isNull;
        }

        @ParamAnnotation(paramType = "query", paramName = "is_null")
        public IsNullModel getIsNull() {
            return this.isNull;
        }

        private NotIPAddressModel notIPAddress;

        public void setNotIPAddress(NotIPAddressModel notIPAddress) {
            this.notIPAddress = notIPAddress;
        }

        @ParamAnnotation(paramType = "query", paramName = "not_ip_address")
        public NotIPAddressModel getNotIPAddress() {
            return this.notIPAddress;
        }

        private StringLikeModel stringLike;

        public void setStringLike(StringLikeModel stringLike) {
            this.stringLike = stringLike;
        }

        @ParamAnnotation(paramType = "query", paramName = "string_like")
        public StringLikeModel getStringLike() {
            return this.stringLike;
        }

        private StringNotLikeModel stringNotLike;

        public void setStringNotLike(StringNotLikeModel stringNotLike) {
            this.stringNotLike = stringNotLike;
        }

        @ParamAnnotation(paramType = "query", paramName = "string_not_like")
        public StringNotLikeModel getStringNotLike() {
            return this.stringNotLike;
        }

        @Override
        public String validateParam() {

            if (this.getIPAddress() != null) {
                String vValidate = this.getIPAddress().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getIsNull() != null) {
                String vValidate = this.getIsNull().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getNotIPAddress() != null) {
                String vValidate = this.getNotIPAddress().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getStringLike() != null) {
                String vValidate = this.getStringLike().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getStringNotLike() != null) {
                String vValidate = this.getStringNotLike().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            return null;
        }
    }

    public static class CORSRuleModel extends RequestInputModel {

        // Allowed headers

        private List<String> allowedHeaders;

        public void setAllowedHeaders(List<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        @ParamAnnotation(paramType = "query", paramName = "allowed_headers")
        public List<String> getAllowedHeaders() {
            return this.allowedHeaders;
        } // Allowed methods
        // Required

        private List<String> allowedMethods;

        public void setAllowedMethods(List<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
        }

        @ParamAnnotation(paramType = "query", paramName = "allowed_methods")
        public List<String> getAllowedMethods() {
            return this.allowedMethods;
        } // Allowed origin
        // Required

        private String allowedOrigin;

        public void setAllowedOrigin(String allowedOrigin) {
            this.allowedOrigin = allowedOrigin;
        }

        @ParamAnnotation(paramType = "query", paramName = "allowed_origin")
        public String getAllowedOrigin() {
            return this.allowedOrigin;
        } // Expose headers

        private List<String> exposeHeaders;

        public void setExposeHeaders(List<String> exposeHeaders) {
            this.exposeHeaders = exposeHeaders;
        }

        @ParamAnnotation(paramType = "query", paramName = "expose_headers")
        public List<String> getExposeHeaders() {
            return this.exposeHeaders;
        } // Max age seconds

        private Long maxAgeSeconds;

        public void setMaxAgeSeconds(Long maxAgeSeconds) {
            this.maxAgeSeconds = maxAgeSeconds;
        }

        @ParamAnnotation(paramType = "query", paramName = "max_age_seconds")
        public Long getMaxAgeSeconds() {
            return this.maxAgeSeconds;
        }

        @Override
        public String validateParam() {

            if (QSStringUtil.isEmpty(this.getAllowedOrigin())) {
                return QSStringUtil.getParameterRequired("AllowedOrigin", "CORSRule");
            }
            return null;
        }
    }

    public static class GranteeModel extends RequestInputModel {

        // Grantee user ID

        private String iD;

        public void setID(String iD) {
            this.iD = iD;
        }

        @ParamAnnotation(paramType = "query", paramName = "id")
        public String getID() {
            return this.iD;
        } // Grantee group name

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "query", paramName = "name")
        public String getName() {
            return this.name;
        } // Grantee type
        // Type's available values: user, group
        // Required

        private String type;

        public void setType(String type) {
            this.type = type;
        }

        @ParamAnnotation(paramType = "query", paramName = "type")
        public String getType() {
            return this.type;
        }

        @Override
        public String validateParam() {

            if (QSStringUtil.isEmpty(this.getType())) {
                return QSStringUtil.getParameterRequired("Type", "Grantee");
            }
            String[] typeValidValues = {"user", "group"};

            boolean typeIsValid = false;
            for (String v : typeValidValues) {
                if (v.equals(this.getType())) {
                    typeIsValid = true;
                }
            }

            if (!typeIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "Type", this.getType() + "", typeValidValues);
            }
            return null;
        }
    }

    public static class IPAddressModel extends RequestInputModel {

        // Source IP

        private List<String> sourceIP;

        public void setSourceIP(List<String> sourceIP) {
            this.sourceIP = sourceIP;
        }

        @ParamAnnotation(paramType = "query", paramName = "source_ip")
        public List<String> getSourceIP() {
            return this.sourceIP;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class IsNullModel extends RequestInputModel {

        // Refer url

        private Boolean referer;

        public void setReferer(Boolean referer) {
            this.referer = referer;
        }

        @ParamAnnotation(paramType = "query", paramName = "Referer")
        public Boolean getReferer() {
            return this.referer;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class KeyModel extends RequestInputModel {

        // Object created time

        private String created;

        public void setCreated(String created) {
            this.created = created;
        }

        @ParamAnnotation(paramType = "query", paramName = "created")
        public String getCreated() {
            return this.created;
        } // Whether this key is encrypted

        private Boolean encrypted;

        public void setEncrypted(Boolean encrypted) {
            this.encrypted = encrypted;
        }

        @ParamAnnotation(paramType = "query", paramName = "encrypted")
        public Boolean getEncrypted() {
            return this.encrypted;
        } // MD5sum of the object

        private String etag;

        public void setEtag(String etag) {
            this.etag = etag;
        }

        @ParamAnnotation(paramType = "query", paramName = "etag")
        public String getEtag() {
            return this.etag;
        } // Object key

        private String key;

        public void setKey(String key) {
            this.key = key;
        }

        @ParamAnnotation(paramType = "query", paramName = "key")
        public String getKey() {
            return this.key;
        } // MIME type of the object

        private String mimeType;

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        @ParamAnnotation(paramType = "query", paramName = "mime_type")
        public String getMimeType() {
            return this.mimeType;
        } // Last modified time in unix time format

        private Long modified;

        public void setModified(Long modified) {
            this.modified = modified;
        }

        @ParamAnnotation(paramType = "query", paramName = "modified")
        public Long getModified() {
            return this.modified;
        } // Object content size

        private Long size;

        public void setSize(Long size) {
            this.size = size;
        }

        @ParamAnnotation(paramType = "query", paramName = "size")
        public Long getSize() {
            return this.size;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class KeyDeleteErrorModel extends RequestInputModel {

        // Error code

        private String code;

        public void setCode(String code) {
            this.code = code;
        }

        @ParamAnnotation(paramType = "query", paramName = "code")
        public String getCode() {
            return this.code;
        } // Object key

        private String key;

        public void setKey(String key) {
            this.key = key;
        }

        @ParamAnnotation(paramType = "query", paramName = "key")
        public String getKey() {
            return this.key;
        } // Error message

        private String message;

        public void setMessage(String message) {
            this.message = message;
        }

        @ParamAnnotation(paramType = "query", paramName = "message")
        public String getMessage() {
            return this.message;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class NotIPAddressModel extends RequestInputModel {

        // Source IP

        private List<String> sourceIP;

        public void setSourceIP(List<String> sourceIP) {
            this.sourceIP = sourceIP;
        }

        @ParamAnnotation(paramType = "query", paramName = "source_ip")
        public List<String> getSourceIP() {
            return this.sourceIP;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class ObjectPartModel extends RequestInputModel {

        // Object part created time

        private String created;

        public void setCreated(String created) {
            this.created = created;
        }

        @ParamAnnotation(paramType = "query", paramName = "created")
        public String getCreated() {
            return this.created;
        } // MD5sum of the object part

        private String etag;

        public void setEtag(String etag) {
            this.etag = etag;
        }

        @ParamAnnotation(paramType = "query", paramName = "etag")
        public String getEtag() {
            return this.etag;
        } // Object part number
        // Required

        private Long partNumber;

        public void setPartNumber(Long partNumber) {
            this.partNumber = partNumber;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number")
        public Long getPartNumber() {
            return this.partNumber;
        } // Object part size

        private Long size;

        public void setSize(Long size) {
            this.size = size;
        }

        @ParamAnnotation(paramType = "query", paramName = "size")
        public Long getSize() {
            return this.size;
        }

        @Override
        public String validateParam() {

            if (this.getPartNumber() < 0) {
                return QSStringUtil.getParameterRequired("PartNumber", "ObjectPart");
            }
            return null;
        }
    }

    public static class OwnerModel extends RequestInputModel {

        // User ID

        private String iD;

        public void setID(String iD) {
            this.iD = iD;
        }

        @ParamAnnotation(paramType = "query", paramName = "id")
        public String getID() {
            return this.iD;
        } // Username

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "query", paramName = "name")
        public String getName() {
            return this.name;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class StatementModel extends RequestInputModel {

        // QingStor API methods
        // Required

        private List<String> action;

        public void setAction(List<String> action) {
            this.action = action;
        }

        @ParamAnnotation(paramType = "query", paramName = "action")
        public List<String> getAction() {
            return this.action;
        }

        private ConditionModel condition;

        public void setCondition(ConditionModel condition) {
            this.condition = condition;
        }

        @ParamAnnotation(paramType = "query", paramName = "condition")
        public ConditionModel getCondition() {
            return this.condition;
        } // Statement effect
        // Effect's available values: allow, deny
        // Required

        private String effect;

        public void setEffect(String effect) {
            this.effect = effect;
        }

        @ParamAnnotation(paramType = "query", paramName = "effect")
        public String getEffect() {
            return this.effect;
        } // Bucket policy id, must be unique
        // Required

        private String iD;

        public void setID(String iD) {
            this.iD = iD;
        }

        @ParamAnnotation(paramType = "query", paramName = "id")
        public String getID() {
            return this.iD;
        } // The resources to apply bucket policy

        private List<String> resource;

        public void setResource(List<String> resource) {
            this.resource = resource;
        }

        @ParamAnnotation(paramType = "query", paramName = "resource")
        public List<String> getResource() {
            return this.resource;
        } // The user to apply bucket policy
        // Required

        private List<String> user;

        public void setUser(List<String> user) {
            this.user = user;
        }

        @ParamAnnotation(paramType = "query", paramName = "user")
        public List<String> getUser() {
            return this.user;
        }

        @Override
        public String validateParam() {

            if (this.getCondition() != null) {
                String vValidate = this.getCondition().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (QSStringUtil.isEmpty(this.getEffect())) {
                return QSStringUtil.getParameterRequired("Effect", "Statement");
            }
            String[] effectValidValues = {"allow", "deny"};

            boolean effectIsValid = false;
            for (String v : effectValidValues) {
                if (v.equals(this.getEffect())) {
                    effectIsValid = true;
                }
            }

            if (!effectIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "Effect", this.getEffect() + "", effectValidValues);
            }
            if (QSStringUtil.isEmpty(this.getID())) {
                return QSStringUtil.getParameterRequired("ID", "Statement");
            }
            return null;
        }
    }

    public static class StringLikeModel extends RequestInputModel {

        // Refer url

        private List<String> referer;

        public void setReferer(List<String> referer) {
            this.referer = referer;
        }

        @ParamAnnotation(paramType = "query", paramName = "Referer")
        public List<String> getReferer() {
            return this.referer;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class StringNotLikeModel extends RequestInputModel {

        // Refer url

        private List<String> referer;

        public void setReferer(List<String> referer) {
            this.referer = referer;
        }

        @ParamAnnotation(paramType = "query", paramName = "Referer")
        public List<String> getReferer() {
            return this.referer;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class UploadsModel extends RequestInputModel {

        // Object part created time

        private String created;

        public void setCreated(String created) {
            this.created = created;
        }

        @ParamAnnotation(paramType = "query", paramName = "created")
        public String getCreated() {
            return this.created;
        } // Object key

        private String key;

        public void setKey(String key) {
            this.key = key;
        }

        @ParamAnnotation(paramType = "query", paramName = "key")
        public String getKey() {
            return this.key;
        } // Object upload id

        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }
}
