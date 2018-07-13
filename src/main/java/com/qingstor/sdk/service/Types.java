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

    public static class AbortIncompleteMultipartUploadModel extends RequestInputModel {

        // days after initiation
        // Required

        private Integer daysAfterInitiation;

        public void setDaysAfterInitiation(Integer daysAfterInitiation) {
            this.daysAfterInitiation = daysAfterInitiation;
        }

        @ParamAnnotation(paramType = "query", paramName = "days_after_initiation")
        public Integer getDaysAfterInitiation() {
            return this.daysAfterInitiation;
        }

        @Override
        public String validateParam() {

            if (this.getDaysAfterInitiation() < 0) {
                return QSStringUtil.getParameterRequired(
                        "DaysAfterInitiation", "AbortIncompleteMultipartUpload");
            }
            return null;
        }
    }

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
            String value = this.getPermission();
            if (null == value || "".equals(value)) {
                permissionIsValid = true;
            } else {
                for (String v : permissionValidValues) {
                    if (v.equals(value)) {
                        permissionIsValid = true;
                    }
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

    public static class CloudfuncArgsModel extends RequestInputModel {

        // Required

        private String action;

        public void setAction(String action) {
            this.action = action;
        }

        @ParamAnnotation(paramType = "query", paramName = "action")
        public String getAction() {
            return this.action;
        }

        private String keyPrefix;

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        @ParamAnnotation(paramType = "query", paramName = "key_prefix")
        public String getKeyPrefix() {
            return this.keyPrefix;
        }

        private String keySeprate;

        public void setKeySeprate(String keySeprate) {
            this.keySeprate = keySeprate;
        }

        @ParamAnnotation(paramType = "query", paramName = "key_seprate")
        public String getKeySeprate() {
            return this.keySeprate;
        }

        private String saveBucket;

        public void setSaveBucket(String saveBucket) {
            this.saveBucket = saveBucket;
        }

        @ParamAnnotation(paramType = "query", paramName = "save_bucket")
        public String getSaveBucket() {
            return this.saveBucket;
        }

        @Override
        public String validateParam() {
            if (QSStringUtil.isEmpty(this.getAction())) {
                return QSStringUtil.getParameterRequired("Action", "CloudfuncArgs");
            }
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

        private Integer maxAgeSeconds;

        public void setMaxAgeSeconds(Integer maxAgeSeconds) {
            this.maxAgeSeconds = maxAgeSeconds;
        }

        @ParamAnnotation(paramType = "query", paramName = "max_age_seconds")
        public Integer getMaxAgeSeconds() {
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

    public static class ExpirationModel extends RequestInputModel {

        // days

        private Integer days;

        public void setDays(Integer days) {
            this.days = days;
        }

        @ParamAnnotation(paramType = "query", paramName = "days")
        public Integer getDays() {
            return this.days;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class FilterModel extends RequestInputModel {

        // Prefix matching
        // Required

        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "query", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        }

        @Override
        public String validateParam() {
            if (QSStringUtil.isEmpty(this.getPrefix())) {
                return QSStringUtil.getParameterRequired("Prefix", "Filter");
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
            String value = this.getType();
            if (null == value || "".equals(value)) {
                typeIsValid = true;
            } else {
                for (String v : typeValidValues) {
                    if (v.equals(value)) {
                        typeIsValid = true;
                    }
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

        private Integer modified;

        public void setModified(Integer modified) {
            this.modified = modified;
        }

        @ParamAnnotation(paramType = "query", paramName = "modified")
        public Integer getModified() {
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

    public static class NotificationModel extends RequestInputModel {

        // Event processing service
        // Cloudfunc's available values: tupu-porn, notifier, image
        // Required

        private String cloudfunc;

        public void setCloudfunc(String cloudfunc) {
            this.cloudfunc = cloudfunc;
        }

        @ParamAnnotation(paramType = "query", paramName = "cloudfunc")
        public String getCloudfunc() {
            return this.cloudfunc;
        }

        private CloudfuncArgsModel cloudfuncArgs;

        public void setCloudfuncArgs(CloudfuncArgsModel cloudfuncArgs) {
            this.cloudfuncArgs = cloudfuncArgs;
        }

        @ParamAnnotation(paramType = "query", paramName = "cloudfunc_args")
        public CloudfuncArgsModel getCloudfuncArgs() {
            return this.cloudfuncArgs;
        } // event types
        // Required

        private List<String> eventTypes;

        public void setEventTypes(List<String> eventTypes) {
            this.eventTypes = eventTypes;
        }

        @ParamAnnotation(paramType = "query", paramName = "event_types")
        public List<String> getEventTypes() {
            return this.eventTypes;
        } // notification id
        // Required

        private String iD;

        public void setID(String iD) {
            this.iD = iD;
        }

        @ParamAnnotation(paramType = "query", paramName = "id")
        public String getID() {
            return this.iD;
        } // notify url

        private String notifyURL;

        public void setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
        }

        @ParamAnnotation(paramType = "query", paramName = "notify_url")
        public String getNotifyURL() {
            return this.notifyURL;
        } // Object name matching rule

        private List<String> objectFilters;

        public void setObjectFilters(List<String> objectFilters) {
            this.objectFilters = objectFilters;
        }

        @ParamAnnotation(paramType = "query", paramName = "object_filters")
        public List<String> getObjectFilters() {
            return this.objectFilters;
        }

        @Override
        public String validateParam() {
            if (QSStringUtil.isEmpty(this.getCloudfunc())) {
                return QSStringUtil.getParameterRequired("Cloudfunc", "Notification");
            }
            String[] cloudfuncValidValues = {"tupu-porn", "notifier", "image"};

            boolean cloudfuncIsValid = false;
            String value = this.getCloudfunc();
            if (null == value || "".equals(value)) {
                cloudfuncIsValid = true;
            } else {
                for (String v : cloudfuncValidValues) {
                    if (v.equals(value)) {
                        cloudfuncIsValid = true;
                    }
                }
            }

            if (!cloudfuncIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "Cloudfunc", this.getCloudfunc() + "", cloudfuncValidValues);
            }
            if (this.getCloudfuncArgs() != null) {
                String vValidate = this.getCloudfuncArgs().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }
            if (QSStringUtil.isEmpty(this.getID())) {
                return QSStringUtil.getParameterRequired("ID", "Notification");
            }
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

        private Integer partNumber;

        public void setPartNumber(Integer partNumber) {
            this.partNumber = partNumber;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number")
        public Integer getPartNumber() {
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

    public static class RuleModel extends RequestInputModel {

        private AbortIncompleteMultipartUploadModel abortIncompleteMultipartUpload;

        public void setAbortIncompleteMultipartUpload(
                AbortIncompleteMultipartUploadModel abortIncompleteMultipartUpload) {
            this.abortIncompleteMultipartUpload = abortIncompleteMultipartUpload;
        }

        @ParamAnnotation(paramType = "query", paramName = "abort_incomplete_multipart_upload")
        public AbortIncompleteMultipartUploadModel getAbortIncompleteMultipartUpload() {
            return this.abortIncompleteMultipartUpload;
        }

        private ExpirationModel expiration;

        public void setExpiration(ExpirationModel expiration) {
            this.expiration = expiration;
        }

        @ParamAnnotation(paramType = "query", paramName = "expiration")
        public ExpirationModel getExpiration() {
            return this.expiration;
        } // Required

        private FilterModel filter;

        public void setFilter(FilterModel filter) {
            this.filter = filter;
        }

        @ParamAnnotation(paramType = "query", paramName = "filter")
        public FilterModel getFilter() {
            return this.filter;
        } // rule id
        // Required

        private String iD;

        public void setID(String iD) {
            this.iD = iD;
        }

        @ParamAnnotation(paramType = "query", paramName = "id")
        public String getID() {
            return this.iD;
        } // rule status
        // Status's available values: enabled, disabled
        // Required

        private String status;

        public void setStatus(String status) {
            this.status = status;
        }

        @ParamAnnotation(paramType = "query", paramName = "status")
        public String getStatus() {
            return this.status;
        }

        private TransitionModel transition;

        public void setTransition(TransitionModel transition) {
            this.transition = transition;
        }

        @ParamAnnotation(paramType = "query", paramName = "transition")
        public TransitionModel getTransition() {
            return this.transition;
        }

        @Override
        public String validateParam() {

            if (this.getAbortIncompleteMultipartUpload() != null) {
                String vValidate = this.getAbortIncompleteMultipartUpload().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getExpiration() != null) {
                String vValidate = this.getExpiration().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            if (this.getFilter() != null) {
                String vValidate = this.getFilter().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }
            if (this.getFilter() == null) {
                return QSStringUtil.getParameterRequired("Filter", "Rule");
            }
            if (QSStringUtil.isEmpty(this.getID())) {
                return QSStringUtil.getParameterRequired("ID", "Rule");
            }
            if (QSStringUtil.isEmpty(this.getStatus())) {
                return QSStringUtil.getParameterRequired("Status", "Rule");
            }
            String[] statusValidValues = {"enabled", "disabled"};

            boolean statusIsValid = false;
            String value = this.getStatus();
            if (null == value || "".equals(value)) {
                statusIsValid = true;
            } else {
                for (String v : statusValidValues) {
                    if (v.equals(value)) {
                        statusIsValid = true;
                    }
                }
            }

            if (!statusIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "Status", this.getStatus() + "", statusValidValues);
            }
            if (this.getTransition() != null) {
                String vValidate = this.getTransition().validateParam();
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

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
            String value = this.getEffect();
            if (null == value || "".equals(value)) {
                effectIsValid = true;
            } else {
                for (String v : effectValidValues) {
                    if (v.equals(value)) {
                        effectIsValid = true;
                    }
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

    public static class TransitionModel extends RequestInputModel {

        // days

        private Integer days;

        public void setDays(Integer days) {
            this.days = days;
        }

        @ParamAnnotation(paramType = "query", paramName = "days")
        public Integer getDays() {
            return this.days;
        } // storage class
        // Required

        private Integer storageClass;

        public void setStorageClass(Integer storageClass) {
            this.storageClass = storageClass;
        }

        @ParamAnnotation(paramType = "query", paramName = "storage_class")
        public Integer getStorageClass() {
            return this.storageClass;
        }

        @Override
        public String validateParam() {

            if (this.getStorageClass() < 0) {
                return QSStringUtil.getParameterRequired("StorageClass", "Transition");
            }
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
