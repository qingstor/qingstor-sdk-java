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
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.ResourceRequestFactory;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.Types.*;
import com.qingstor.sdk.utils.QSSignatureUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bucket {
    private String zone;
    private String bucketName;
    private EvnContext evnContext;

    public Bucket(EvnContext evnContext, String bucketName) {
        this(evnContext, QSConstant.STOR_DEFAULT_ZONE, bucketName);
    }

    public Bucket(EvnContext evnContext, String zone, String bucketName) {
        this.evnContext = evnContext;
        this.zone = zone;
        this.bucketName = bucketName;
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/delete.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketOutput delete() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucket");
        context.put("APIName", "DeleteBucket");
        context.put("ServiceName", "DELETE Bucket");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, DeleteBucketOutput.class);
        if (backModel != null) {
            return (DeleteBucketOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/delete.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteAsync(ResponseCallBack<DeleteBucketOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucket");
        context.put("APIName", "DeleteBucket");
        context.put("ServiceName", "DELETE Bucket");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class DeleteBucketOutput extends OutputModel {}

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketCORSOutput deleteCORS() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketCORS");
        context.put("APIName", "DeleteBucketCORS");
        context.put("ServiceName", "DELETE Bucket CORS");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, DeleteBucketCORSOutput.class);
        if (backModel != null) {
            return (DeleteBucketCORSOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteCORSAsync(ResponseCallBack<DeleteBucketCORSOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketCORS");
        context.put("APIName", "DeleteBucketCORS");
        context.put("ServiceName", "DELETE Bucket CORS");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class DeleteBucketCORSOutput extends OutputModel {}

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketExternalMirrorOutput deleteExternalMirror() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketExternalMirror");
        context.put("APIName", "DeleteBucketExternalMirror");
        context.put("ServiceName", "DELETE Bucket External Mirror");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, DeleteBucketExternalMirrorOutput.class);
        if (backModel != null) {
            return (DeleteBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteExternalMirrorAsync(
            ResponseCallBack<DeleteBucketExternalMirrorOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketExternalMirror");
        context.put("APIName", "DeleteBucketExternalMirror");
        context.put("ServiceName", "DELETE Bucket External Mirror");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class DeleteBucketExternalMirrorOutput extends OutputModel {}

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketPolicyOutput deletePolicy() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketPolicy");
        context.put("APIName", "DeleteBucketPolicy");
        context.put("ServiceName", "DELETE Bucket Policy");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, DeleteBucketPolicyOutput.class);
        if (backModel != null) {
            return (DeleteBucketPolicyOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deletePolicyAsync(ResponseCallBack<DeleteBucketPolicyOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteBucketPolicy");
        context.put("APIName", "DeleteBucketPolicy");
        context.put("ServiceName", "DELETE Bucket Policy");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class DeleteBucketPolicyOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteMultipleObjectsOutput deleteMultipleObjects(DeleteMultipleObjectsInput input)
            throws QSException {

        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteMultipleObjects");
        context.put("APIName", "DeleteMultipleObjects");
        context.put("ServiceName", "Delete Multiple Objects");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>?delete");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, DeleteMultipleObjectsOutput.class);
        if (backModel != null) {
            return (DeleteMultipleObjectsOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteMultipleObjectsAsync(
            DeleteMultipleObjectsInput input,
            ResponseCallBack<DeleteMultipleObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteMultipleObjects");
        context.put("APIName", "DeleteMultipleObjects");
        context.put("ServiceName", "Delete Multiple Objects");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>?delete");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class DeleteMultipleObjectsInput extends RequestInputModel {


        // The request body
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // A list of keys to delete
        // Required

        private List<KeyModel> objects;

        public void setObjects(List<KeyModel> objects) {
            this.objects = objects;
        }

        @ParamAnnotation(paramType = "body", paramName = "objects")
        public List<KeyModel> getObjects() {
            return this.objects;
        } // Whether to return the list of deleted objects

        private Boolean quiet;

        public void setQuiet(Boolean quiet) {
            this.quiet = quiet;
        }

        @ParamAnnotation(paramType = "body", paramName = "quiet")
        public Boolean getQuiet() {
            return this.quiet;
        }

        @Override
        public String validateParam() {

            if (this.getObjects() != null && this.getObjects().size() > 0) {
                for (int i = 0; i < this.getObjects().size(); i++) {
                    String vValidate = this.getObjects().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }

            return null;
        }
    }

    public static class DeleteMultipleObjectsOutput extends OutputModel {

        // List of deleted objects

        private List<KeyModel> deleted;

        public void setDeleted(List<KeyModel> deleted) {
            this.deleted = deleted;
        }

        @ParamAnnotation(paramType = "query", paramName = "deleted")
        public List<KeyModel> getDeleted() {
            return this.deleted;
        } // Error messages

        private List<KeyDeleteErrorModel> errors;

        public void setErrors(List<KeyDeleteErrorModel> errors) {
            this.errors = errors;
        }

        @ParamAnnotation(paramType = "query", paramName = "errors")
        public List<KeyDeleteErrorModel> getErrors() {
            return this.errors;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketACLOutput getACL() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketACL");
        context.put("APIName", "GetBucketACL");
        context.put("ServiceName", "GET Bucket ACL");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?acl");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, GetBucketACLOutput.class);
        if (backModel != null) {
            return (GetBucketACLOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getACLAsync(ResponseCallBack<GetBucketACLOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketACL");
        context.put("APIName", "GetBucketACL");
        context.put("ServiceName", "GET Bucket ACL");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?acl");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class GetBucketACLOutput extends OutputModel {

        // Bucket ACL rules

        private List<ACLModel> aCL;

        public void setACL(List<ACLModel> aCL) {
            this.aCL = aCL;
        }

        @ParamAnnotation(paramType = "query", paramName = "acl")
        public List<ACLModel> getACL() {
            return this.aCL;
        } // Bucket owner

        private OwnerModel owner;

        public void setOwner(OwnerModel owner) {
            this.owner = owner;
        }

        @ParamAnnotation(paramType = "query", paramName = "owner")
        public OwnerModel getOwner() {
            return this.owner;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketCORSOutput getCORS() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketCORS");
        context.put("APIName", "GetBucketCORS");
        context.put("ServiceName", "GET Bucket CORS");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, GetBucketCORSOutput.class);
        if (backModel != null) {
            return (GetBucketCORSOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getCORSAsync(ResponseCallBack<GetBucketCORSOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketCORS");
        context.put("APIName", "GetBucketCORS");
        context.put("ServiceName", "GET Bucket CORS");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class GetBucketCORSOutput extends OutputModel {

        // Bucket CORS rules

        private List<CORSRuleModel> cORSRules;

        public void setCORSRules(List<CORSRuleModel> cORSRules) {
            this.cORSRules = cORSRules;
        }

        @ParamAnnotation(paramType = "query", paramName = "cors_rules")
        public List<CORSRuleModel> getCORSRules() {
            return this.cORSRules;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketExternalMirrorOutput getExternalMirror() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketExternalMirror");
        context.put("APIName", "GetBucketExternalMirror");
        context.put("ServiceName", "GET Bucket External Mirror");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, GetBucketExternalMirrorOutput.class);
        if (backModel != null) {
            return (GetBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getExternalMirrorAsync(ResponseCallBack<GetBucketExternalMirrorOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketExternalMirror");
        context.put("APIName", "GetBucketExternalMirror");
        context.put("ServiceName", "GET Bucket External Mirror");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class GetBucketExternalMirrorOutput extends OutputModel {

        // Source site url

        private String sourceSite;

        public void setSourceSite(String sourceSite) {
            this.sourceSite = sourceSite;
        }

        @ParamAnnotation(paramType = "query", paramName = "source_site")
        public String getSourceSite() {
            return this.sourceSite;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketPolicyOutput getPolicy() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketPolicy");
        context.put("APIName", "GetBucketPolicy");
        context.put("ServiceName", "GET Bucket Policy");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, GetBucketPolicyOutput.class);
        if (backModel != null) {
            return (GetBucketPolicyOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getPolicyAsync(ResponseCallBack<GetBucketPolicyOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketPolicy");
        context.put("APIName", "GetBucketPolicy");
        context.put("ServiceName", "GET Bucket Policy");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class GetBucketPolicyOutput extends OutputModel {

        // Bucket policy statement

        private List<StatementModel> statement;

        public void setStatement(List<StatementModel> statement) {
            this.statement = statement;
        }

        @ParamAnnotation(paramType = "query", paramName = "statement")
        public List<StatementModel> getStatement() {
            return this.statement;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketStatisticsOutput getStatistics() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketStatistics");
        context.put("APIName", "GetBucketStatistics");
        context.put("ServiceName", "GET Bucket Statistics");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?stats");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, GetBucketStatisticsOutput.class);
        if (backModel != null) {
            return (GetBucketStatisticsOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getStatisticsAsync(ResponseCallBack<GetBucketStatisticsOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetBucketStatistics");
        context.put("APIName", "GetBucketStatistics");
        context.put("ServiceName", "GET Bucket Statistics");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?stats");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class GetBucketStatisticsOutput extends OutputModel {

        // Objects count in the bucket

        private Long count;

        public void setCount(Long count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "query", paramName = "count")
        public Long getCount() {
            return this.count;
        } // Bucket created time

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
        } // Bucket storage size

        private Long size;

        public void setSize(Long size) {
            this.size = size;
        }

        @ParamAnnotation(paramType = "query", paramName = "size")
        public Long getSize() {
            return this.size;
        } // Bucket status
        // Status's available values: active, suspended

        private String status;

        public void setStatus(String status) {
            this.status = status;
        }

        @ParamAnnotation(paramType = "query", paramName = "status")
        public String getStatus() {
            return this.status;
        } // URL to access the bucket

        private String uRL;

        public void setURL(String uRL) {
            this.uRL = uRL;
        }

        @ParamAnnotation(paramType = "query", paramName = "url")
        public String getURL() {
            return this.uRL;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/head.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HeadBucketOutput head() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "HeadBucket");
        context.put("APIName", "HeadBucket");
        context.put("ServiceName", "HEAD Bucket");
        context.put("RequestMethod", "HEAD");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, HeadBucketOutput.class);
        if (backModel != null) {
            return (HeadBucketOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/head.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void headAsync(ResponseCallBack<HeadBucketOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "HeadBucket");
        context.put("APIName", "HeadBucket");
        context.put("ServiceName", "HEAD Bucket");
        context.put("RequestMethod", "HEAD");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class HeadBucketOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListMultipartUploadsOutput listMultipartUploads(ListMultipartUploadsInput input)
            throws QSException {

        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListMultipartUploads");
        context.put("APIName", "ListMultipartUploads");
        context.put("ServiceName", "List Multipart Uploads");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?uploads");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, ListMultipartUploadsOutput.class);
        if (backModel != null) {
            return (ListMultipartUploadsOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listMultipartUploadsAsync(
            ListMultipartUploadsInput input, ResponseCallBack<ListMultipartUploadsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListMultipartUploads");
        context.put("APIName", "ListMultipartUploads");
        context.put("ServiceName", "List Multipart Uploads");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>?uploads");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class ListMultipartUploadsInput extends RequestInputModel {
        // Put all keys that share a common prefix into a list

        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        } // Results count limit

        private Long limit;

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Long getLimit() {
            return this.limit;
        } // Limit results to keys that start at this marker

        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "query", paramName = "marker")
        public String getMarker() {
            return this.marker;
        } // Limits results to keys that begin with the prefix

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

            return null;
        }
    }

    public static class ListMultipartUploadsOutput extends OutputModel {

        // Other object keys that share common prefixes

        private List<String> commonPrefixes;

        public void setCommonPrefixes(List<String> commonPrefixes) {
            this.commonPrefixes = commonPrefixes;
        }

        @ParamAnnotation(paramType = "query", paramName = "common_prefixes")
        public List<String> getCommonPrefixes() {
            return this.commonPrefixes;
        } // Delimiter that specified in request parameters

        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        } // Limit that specified in request parameters

        private Long limit;

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Long getLimit() {
            return this.limit;
        } // Marker that specified in request parameters

        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "query", paramName = "marker")
        public String getMarker() {
            return this.marker;
        } // Bucket name

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "query", paramName = "name")
        public String getName() {
            return this.name;
        } // The last key in keys list

        private String nextMarker;

        public void setNextMarker(String nextMarker) {
            this.nextMarker = nextMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "next_marker")
        public String getNextMarker() {
            return this.nextMarker;
        } // Prefix that specified in request parameters

        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "query", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        } // Multipart uploads

        private List<UploadsModel> uploads;

        public void setUploads(List<UploadsModel> uploads) {
            this.uploads = uploads;
        }

        @ParamAnnotation(paramType = "query", paramName = "uploads")
        public List<UploadsModel> getUploads() {
            return this.uploads;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListObjectsOutput listObjects(ListObjectsInput input) throws QSException {

        if (input == null) {
            input = new ListObjectsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListObjects");
        context.put("APIName", "ListObjects");
        context.put("ServiceName", "GET Bucket (List Objects)");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, ListObjectsOutput.class);
        if (backModel != null) {
            return (ListObjectsOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listObjectsAsync(
            ListObjectsInput input, ResponseCallBack<ListObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListObjectsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListObjects");
        context.put("APIName", "ListObjects");
        context.put("ServiceName", "GET Bucket (List Objects)");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class ListObjectsInput extends RequestInputModel {
        // Put all keys that share a common prefix into a list

        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        } // Results count limit

        private Long limit;

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Long getLimit() {
            return this.limit;
        } // Limit results to keys that start at this marker

        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "query", paramName = "marker")
        public String getMarker() {
            return this.marker;
        } // Limits results to keys that begin with the prefix

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

            return null;
        }
    }

    public static class ListObjectsOutput extends OutputModel {

        // Other object keys that share common prefixes

        private List<String> commonPrefixes;

        public void setCommonPrefixes(List<String> commonPrefixes) {
            this.commonPrefixes = commonPrefixes;
        }

        @ParamAnnotation(paramType = "query", paramName = "common_prefixes")
        public List<String> getCommonPrefixes() {
            return this.commonPrefixes;
        } // Delimiter that specified in request parameters

        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        } // Object keys

        private List<KeyModel> keys;

        public void setKeys(List<KeyModel> keys) {
            this.keys = keys;
        }

        @ParamAnnotation(paramType = "query", paramName = "keys")
        public List<KeyModel> getKeys() {
            return this.keys;
        } // Limit that specified in request parameters

        private Long limit;

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Long getLimit() {
            return this.limit;
        } // Marker that specified in request parameters

        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "query", paramName = "marker")
        public String getMarker() {
            return this.marker;
        } // Bucket name

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "query", paramName = "name")
        public String getName() {
            return this.name;
        } // The last key in keys list

        private String nextMarker;

        public void setNextMarker(String nextMarker) {
            this.nextMarker = nextMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "next_marker")
        public String getNextMarker() {
            return this.nextMarker;
        } // Bucket owner

        private OwnerModel owner;

        public void setOwner(OwnerModel owner) {
            this.owner = owner;
        }

        @ParamAnnotation(paramType = "query", paramName = "owner")
        public OwnerModel getOwner() {
            return this.owner;
        } // Prefix that specified in request parameters

        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "query", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        }
    }

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/put.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketOutput put() throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucket");
        context.put("APIName", "PutBucket");
        context.put("ServiceName", "PUT Bucket");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, PutBucketOutput.class);
        if (backModel != null) {
            return (PutBucketOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/put.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putAsync(ResponseCallBack<PutBucketOutput> callback) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucket");
        context.put("APIName", "PutBucket");
        context.put("ServiceName", "PUT Bucket");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class PutBucketOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketACLOutput putACL(PutBucketACLInput input) throws QSException {

        if (input == null) {
            input = new PutBucketACLInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketACL");
        context.put("APIName", "PutBucketACL");
        context.put("ServiceName", "PUT Bucket ACL");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?acl");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, PutBucketACLOutput.class);
        if (backModel != null) {
            return (PutBucketACLOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putACLAsync(PutBucketACLInput input, ResponseCallBack<PutBucketACLOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketACLInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketACL");
        context.put("APIName", "PutBucketACL");
        context.put("ServiceName", "PUT Bucket ACL");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?acl");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class PutBucketACLInput extends RequestInputModel {

        // The request body
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // Bucket ACL rules
        // Required

        private List<ACLModel> aCL;

        public void setACL(List<ACLModel> aCL) {
            this.aCL = aCL;
        }

        @ParamAnnotation(paramType = "body", paramName = "acl")
        public List<ACLModel> getACL() {
            return this.aCL;
        }

        @Override
        public String validateParam() {

            if (this.getACL() != null && this.getACL().size() > 0) {
                for (int i = 0; i < this.getACL().size(); i++) {
                    String vValidate = this.getACL().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }

            return null;
        }
    }

    public static class PutBucketACLOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketCORSOutput putCORS(PutBucketCORSInput input) throws QSException {

        if (input == null) {
            input = new PutBucketCORSInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketCORS");
        context.put("APIName", "PutBucketCORS");
        context.put("ServiceName", "PUT Bucket CORS");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, PutBucketCORSOutput.class);
        if (backModel != null) {
            return (PutBucketCORSOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putCORSAsync(
            PutBucketCORSInput input, ResponseCallBack<PutBucketCORSOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketCORSInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketCORS");
        context.put("APIName", "PutBucketCORS");
        context.put("ServiceName", "PUT Bucket CORS");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?cors");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class PutBucketCORSInput extends RequestInputModel {

        // The request body
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // Bucket CORS rules
        // Required

        private List<CORSRuleModel> cORSRules;

        public void setCORSRules(List<CORSRuleModel> cORSRules) {
            this.cORSRules = cORSRules;
        }

        @ParamAnnotation(paramType = "body", paramName = "cors_rules")
        public List<CORSRuleModel> getCORSRules() {
            return this.cORSRules;
        }

        @Override
        public String validateParam() {

            if (this.getCORSRules() != null && this.getCORSRules().size() > 0) {
                for (int i = 0; i < this.getCORSRules().size(); i++) {
                    String vValidate = this.getCORSRules().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }

            return null;
        }
    }

    public static class PutBucketCORSOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketExternalMirrorOutput putExternalMirror(PutBucketExternalMirrorInput input)
            throws QSException {

        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketExternalMirror");
        context.put("APIName", "PutBucketExternalMirror");
        context.put("ServiceName", "PUT Bucket External Mirror");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, PutBucketExternalMirrorOutput.class);
        if (backModel != null) {
            return (PutBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putExternalMirrorAsync(
            PutBucketExternalMirrorInput input,
            ResponseCallBack<PutBucketExternalMirrorOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketExternalMirror");
        context.put("APIName", "PutBucketExternalMirror");
        context.put("ServiceName", "PUT Bucket External Mirror");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?mirror");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class PutBucketExternalMirrorInput extends RequestInputModel {

        // The request body
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // Source site url
        // Required

        private String sourceSite;

        public void setSourceSite(String sourceSite) {
            this.sourceSite = sourceSite;
        }

        @ParamAnnotation(paramType = "body", paramName = "source_site")
        public String getSourceSite() {
            return this.sourceSite;
        }

        @Override
        public String validateParam() {

            if (QSStringUtil.isEmpty(this.getSourceSite())) {
                return QSStringUtil.getParameterRequired(
                        "SourceSite", "PutBucketExternalMirrorInput");
            }

            return null;
        }
    }

    public static class PutBucketExternalMirrorOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketPolicyOutput putPolicy(PutBucketPolicyInput input) throws QSException {

        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketPolicy");
        context.put("APIName", "PutBucketPolicy");
        context.put("ServiceName", "PUT Bucket Policy");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, PutBucketPolicyOutput.class);
        if (backModel != null) {
            return (PutBucketPolicyOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putPolicyAsync(
            PutBucketPolicyInput input, ResponseCallBack<PutBucketPolicyOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutBucketPolicy");
        context.put("APIName", "PutBucketPolicy");
        context.put("ServiceName", "PUT Bucket Policy");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>?policy");
        context.put("bucketNameInput", this.bucketName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class PutBucketPolicyInput extends RequestInputModel {

        // The request body
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // Bucket policy statement
        // Required

        private List<StatementModel> statement;

        public void setStatement(List<StatementModel> statement) {
            this.statement = statement;
        }

        @ParamAnnotation(paramType = "body", paramName = "statement")
        public List<StatementModel> getStatement() {
            return this.statement;
        }

        @Override
        public String validateParam() {

            if (this.getStatement() != null && this.getStatement().size() > 0) {
                for (int i = 0; i < this.getStatement().size(); i++) {
                    String vValidate = this.getStatement().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }

            return null;
        }
    }

    public static class PutBucketPolicyOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public AbortMultipartUploadOutput abortMultipartUpload(
            String objectName, AbortMultipartUploadInput input) throws QSException {

        if (input == null) {
            input = new AbortMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "AbortMultipartUpload");
        context.put("APIName", "AbortMultipartUpload");
        context.put("ServiceName", "Abort Multipart Upload");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, AbortMultipartUploadOutput.class);
        if (backModel != null) {
            return (AbortMultipartUploadOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void abortMultipartUploadAsync(
            String objectName,
            AbortMultipartUploadInput input,
            ResponseCallBack<AbortMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new AbortMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "AbortMultipartUpload");
        context.put("APIName", "AbortMultipartUpload");
        context.put("ServiceName", "Abort Multipart Upload");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class AbortMultipartUploadInput extends RequestInputModel {
        // Object multipart upload ID
        // Required

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

            if (QSStringUtil.isEmpty(this.getUploadID())) {
                return QSStringUtil.getParameterRequired("UploadID", "AbortMultipartUploadInput");
            }

            return null;
        }
    }

    public static class AbortMultipartUploadOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public CompleteMultipartUploadOutput completeMultipartUpload(
            String objectName, CompleteMultipartUploadInput input) throws QSException {

        if (input == null) {
            input = new CompleteMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "CompleteMultipartUpload");
        context.put("APIName", "CompleteMultipartUpload");
        context.put("ServiceName", "Complete multipart upload");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, CompleteMultipartUploadOutput.class);
        if (backModel != null) {
            return (CompleteMultipartUploadOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void completeMultipartUploadAsync(
            String objectName,
            CompleteMultipartUploadInput input,
            ResponseCallBack<CompleteMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new CompleteMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "CompleteMultipartUpload");
        context.put("APIName", "CompleteMultipartUpload");
        context.put("ServiceName", "Complete multipart upload");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class CompleteMultipartUploadInput extends RequestInputModel {
        // Object multipart upload ID
        // Required

        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        // MD5sum of the object part

        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "ETag")
        public String getETag() {
            return this.eTag;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        //Object json string
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        } // Object parts

        private List<ObjectPartModel> objectParts;

        public void setObjectParts(List<ObjectPartModel> objectParts) {
            this.objectParts = objectParts;
        }

        @ParamAnnotation(paramType = "body", paramName = "object_parts")
        public List<ObjectPartModel> getObjectParts() {
            return this.objectParts;
        }

        @Override
        public String validateParam() {

            if (QSStringUtil.isEmpty(this.getUploadID())) {
                return QSStringUtil.getParameterRequired(
                        "UploadID", "CompleteMultipartUploadInput");
            }

            if (this.getObjectParts() != null && this.getObjectParts().size() > 0) {
                for (int i = 0; i < this.getObjectParts().size(); i++) {
                    String vValidate = this.getObjectParts().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }

            return null;
        }
    }

    public static class CompleteMultipartUploadOutput extends OutputModel {}

    /*
     *
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/delete.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteObjectOutput deleteObject(String objectName) throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteObject");
        context.put("APIName", "DeleteObject");
        context.put("ServiceName", "DELETE Object");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, null, DeleteObjectOutput.class);
        if (backModel != null) {
            return (DeleteObjectOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/delete.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteObjectAsync(String objectName, ResponseCallBack<DeleteObjectOutput> callback)
            throws QSException {

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "DeleteObject");
        context.put("APIName", "DeleteObject");
        context.put("ServiceName", "DELETE Object");
        context.put("RequestMethod", "DELETE");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, null, callback);
    }

    public static class DeleteObjectOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetObjectOutput getObject(String objectName, GetObjectInput input) throws QSException {

        if (input == null) {
            input = new GetObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetObject");
        context.put("APIName", "GetObject");
        context.put("ServiceName", "GET Object");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, GetObjectOutput.class);
        if (backModel != null) {
            return (GetObjectOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getObjectAsync(
            String objectName, GetObjectInput input, ResponseCallBack<GetObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new GetObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetObject");
        context.put("APIName", "GetObject");
        context.put("ServiceName", "GET Object");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class GetObjectInput extends RequestInputModel {
        // Specified the Cache-Control response header

        private String responseCacheControl;

        public void setResponseCacheControl(String responseCacheControl) {
            this.responseCacheControl = responseCacheControl;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-cache-control")
        public String getResponseCacheControl() {
            return this.responseCacheControl;
        } // Specified the Content-Disposition response header

        private String responseContentDisposition;

        public void setResponseContentDisposition(String responseContentDisposition) {
            this.responseContentDisposition = responseContentDisposition;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-disposition")
        public String getResponseContentDisposition() {
            return this.responseContentDisposition;
        } // Specified the Content-Encoding response header

        private String responseContentEncoding;

        public void setResponseContentEncoding(String responseContentEncoding) {
            this.responseContentEncoding = responseContentEncoding;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-encoding")
        public String getResponseContentEncoding() {
            return this.responseContentEncoding;
        } // Specified the Content-Language response header

        private String responseContentLanguage;

        public void setResponseContentLanguage(String responseContentLanguage) {
            this.responseContentLanguage = responseContentLanguage;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-language")
        public String getResponseContentLanguage() {
            return this.responseContentLanguage;
        } // Specified the Content-Type response header

        private String responseContentType;

        public void setResponseContentType(String responseContentType) {
            this.responseContentType = responseContentType;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-type")
        public String getResponseContentType() {
            return this.responseContentType;
        } // Specified the Expires response header

        private String responseExpires;

        public void setResponseExpires(String responseExpires) {
            this.responseExpires = responseExpires;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-expires")
        public String getResponseExpires() {
            return this.responseExpires;
        }

        // Check whether the ETag matches

        private String ifMatch;

        public void setIfMatch(String ifMatch) {
            this.ifMatch = ifMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Match")
        public String getIfMatch() {
            return this.ifMatch;
        } // Check whether the object has been modified

        private String ifModifiedSince;

        public void setIfModifiedSince(String ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Modified-Since")
        public String getIfModifiedSince() {
            return this.ifModifiedSince;
        } // Check whether the ETag does not match

        private String ifNoneMatch;

        public void setIfNoneMatch(String ifNoneMatch) {
            this.ifNoneMatch = ifNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-None-Match")
        public String getIfNoneMatch() {
            return this.ifNoneMatch;
        } // Check whether the object has not been modified

        private String ifUnmodifiedSince;

        public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
            this.ifUnmodifiedSince = ifUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Unmodified-Since")
        public String getIfUnmodifiedSince() {
            return this.ifUnmodifiedSince;
        } // Specified range of the object

        private String range;

        public void setRange(String range) {
            this.range = range;
        }

        @ParamAnnotation(paramType = "header", paramName = "Range")
        public String getRange() {
            return this.range;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class GetObjectOutput extends OutputModel {

        // The response body
        private java.io.InputStream bodyInputStream;

        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public java.io.InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        public void setBodyInputStream(java.io.InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        // Object content length

        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Length")
        public Long getContentLength() {
            return this.contentLength;
        } // Range of response data content

        private String contentRange;

        public void setContentRange(String contentRange) {
            this.contentRange = contentRange;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Range")
        public String getContentRange() {
            return this.contentRange;
        } // MD5sum of the object

        // Specified the Cache-Control response header

        private String responseCacheControl;

        public void setResponseCacheControl(String responseCacheControl) {
            this.responseCacheControl = responseCacheControl;
        }

        @ParamAnnotation(paramType = "header", paramName = "Cache-Control")
        public String getResponseCacheControl() {
            return this.responseCacheControl;
        } // Specified the Content-Disposition response header

        private String responseContentDisposition;

        public void setResponseContentDisposition(String responseContentDisposition) {
            this.responseContentDisposition = responseContentDisposition;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Disposition")
        public String getResponseContentDisposition() {
            return this.responseContentDisposition;
        } // Specified the Content-Encoding response header

        private String responseContentEncoding;

        public void setResponseContentEncoding(String responseContentEncoding) {
            this.responseContentEncoding = responseContentEncoding;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Encoding")
        public String getResponseContentEncoding() {
            return this.responseContentEncoding;
        } // Specified the Content-Language response header

        private String responseContentLanguage;

        public void setResponseContentLanguage(String responseContentLanguage) {
            this.responseContentLanguage = responseContentLanguage;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Language")
        public String getResponseContentLanguage() {
            return this.responseContentLanguage;
        } // Specified the Content-Type response header

        private String responseContentType;

        public void setResponseContentType(String responseContentType) {
            this.responseContentType = responseContentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Type")
        public String getResponseContentType() {
            return this.responseContentType;
        } // Specified the Expires response header

        private String responseExpires;

        public void setResponseExpires(String responseExpires) {
            this.responseExpires = responseExpires;
        }

        @ParamAnnotation(paramType = "header", paramName = "Expires")
        public String getResponseExpires() {
            return this.responseExpires;
        }


        
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "ETag")
        public String getETag() {
            return this.eTag;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/head.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HeadObjectOutput headObject(String objectName, HeadObjectInput input)
            throws QSException {

        if (input == null) {
            input = new HeadObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "HeadObject");
        context.put("APIName", "HeadObject");
        context.put("ServiceName", "HEAD Object");
        context.put("RequestMethod", "HEAD");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, HeadObjectOutput.class);
        if (backModel != null) {
            return (HeadObjectOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/head.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void headObjectAsync(
            String objectName, HeadObjectInput input, ResponseCallBack<HeadObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new HeadObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "HeadObject");
        context.put("APIName", "HeadObject");
        context.put("ServiceName", "HEAD Object");
        context.put("RequestMethod", "HEAD");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class HeadObjectInput extends RequestInputModel {

        // Check whether the ETag matches

        private String ifMatch;

        public void setIfMatch(String ifMatch) {
            this.ifMatch = ifMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Match")
        public String getIfMatch() {
            return this.ifMatch;
        } // Check whether the object has been modified

        private String ifModifiedSince;

        public void setIfModifiedSince(String ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Modified-Since")
        public String getIfModifiedSince() {
            return this.ifModifiedSince;
        } // Check whether the ETag does not match

        private String ifNoneMatch;

        public void setIfNoneMatch(String ifNoneMatch) {
            this.ifNoneMatch = ifNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-None-Match")
        public String getIfNoneMatch() {
            return this.ifNoneMatch;
        } // Check whether the object has not been modified

        private String ifUnmodifiedSince;

        public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
            this.ifUnmodifiedSince = ifUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "If-Unmodified-Since")
        public String getIfUnmodifiedSince() {
            return this.ifUnmodifiedSince;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class HeadObjectOutput extends OutputModel {

        // Object content length

        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Length")
        public Long getContentLength() {
            return this.contentLength;
        } // Object content type

        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Type")
        public String getContentType() {
            return this.contentType;
        } // MD5sum of the object

        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "ETag")
        public String getETag() {
            return this.eTag;
        }

        private String lastModified;

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        @ParamAnnotation(paramType = "header", paramName = "Last-Modified")
        public String getLastModified() {
            return this.lastModified;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public InitiateMultipartUploadOutput initiateMultipartUpload(
            String objectName, InitiateMultipartUploadInput input) throws QSException {

        if (input == null) {
            input = new InitiateMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "InitiateMultipartUpload");
        context.put("APIName", "InitiateMultipartUpload");
        context.put("ServiceName", "Initiate Multipart Upload");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>/<object-key>?uploads");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, InitiateMultipartUploadOutput.class);
        if (backModel != null) {
            return (InitiateMultipartUploadOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void initiateMultipartUploadAsync(
            String objectName,
            InitiateMultipartUploadInput input,
            ResponseCallBack<InitiateMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new InitiateMultipartUploadInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "InitiateMultipartUpload");
        context.put("APIName", "InitiateMultipartUpload");
        context.put("ServiceName", "Initiate Multipart Upload");
        context.put("RequestMethod", "POST");
        context.put("RequestURI", "/<bucket-name>/<object-key>?uploads");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class InitiateMultipartUploadInput extends RequestInputModel {

        // Object content type

        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Type")
        public String getContentType() {
            return this.contentType;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class InitiateMultipartUploadOutput extends OutputModel {

        // Bucket name

        private String bucket;

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        @ParamAnnotation(paramType = "query", paramName = "bucket")
        public String getBucket() {
            return this.bucket;
        } // Object key

        private String key;

        public void setKey(String key) {
            this.key = key;
        }

        @ParamAnnotation(paramType = "query", paramName = "key")
        public String getKey() {
            return this.key;
        } // Object multipart upload ID

        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/list_multipart.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListMultipartOutput listMultipart(String objectName, ListMultipartInput input)
            throws QSException {

        if (input == null) {
            input = new ListMultipartInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListMultipart");
        context.put("APIName", "ListMultipart");
        context.put("ServiceName", "List Multipart");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, ListMultipartOutput.class);
        if (backModel != null) {
            return (ListMultipartOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/list_multipart.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listMultipartAsync(
            String objectName,
            ListMultipartInput input,
            ResponseCallBack<ListMultipartOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListMultipartInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListMultipart");
        context.put("APIName", "ListMultipart");
        context.put("ServiceName", "List Multipart");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class ListMultipartInput extends RequestInputModel {
        // Limit results count

        private Long limit;

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Long getLimit() {
            return this.limit;
        } // Object multipart upload part number

        private Long partNumberMarker;

        public void setPartNumberMarker(Long partNumberMarker) {
            this.partNumberMarker = partNumberMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number_marker")
        public Long getPartNumberMarker() {
            return this.partNumberMarker;
        } // Object multipart upload ID
        // Required

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

            if (QSStringUtil.isEmpty(this.getUploadID())) {
                return QSStringUtil.getParameterRequired("UploadID", "ListMultipartInput");
            }

            return null;
        }
    }

    public static class ListMultipartOutput extends OutputModel {

        // Object multipart count

        private Long count;

        public void setCount(Long count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "query", paramName = "count")
        public Long getCount() {
            return this.count;
        } // Object parts

        private List<ObjectPartModel> objectParts;

        public void setObjectParts(List<ObjectPartModel> objectParts) {
            this.objectParts = objectParts;
        }

        @ParamAnnotation(paramType = "query", paramName = "object_parts")
        public List<ObjectPartModel> getObjectParts() {
            return this.objectParts;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/options.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OptionsObjectOutput optionsObject(String objectName, OptionsObjectInput input)
            throws QSException {

        if (input == null) {
            input = new OptionsObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "OptionsObject");
        context.put("APIName", "OptionsObject");
        context.put("ServiceName", "OPTIONS Object");
        context.put("RequestMethod", "OPTIONS");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, OptionsObjectOutput.class);
        if (backModel != null) {
            return (OptionsObjectOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/options.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void optionsObjectAsync(
            String objectName,
            OptionsObjectInput input,
            ResponseCallBack<OptionsObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new OptionsObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "OptionsObject");
        context.put("APIName", "OptionsObject");
        context.put("ServiceName", "OPTIONS Object");
        context.put("RequestMethod", "OPTIONS");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class OptionsObjectInput extends RequestInputModel {

        // Request headers

        private String accessControlRequestHeaders;

        public void setAccessControlRequestHeaders(String accessControlRequestHeaders) {
            this.accessControlRequestHeaders = accessControlRequestHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Request-Headers")
        public String getAccessControlRequestHeaders() {
            return this.accessControlRequestHeaders;
        } // Request method
        // Required

        private String accessControlRequestMethod;

        public void setAccessControlRequestMethod(String accessControlRequestMethod) {
            this.accessControlRequestMethod = accessControlRequestMethod;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Request-Method")
        public String getAccessControlRequestMethod() {
            return this.accessControlRequestMethod;
        } // Request origin
        // Required

        private String origin;

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        @ParamAnnotation(paramType = "header", paramName = "Origin")
        public String getOrigin() {
            return this.origin;
        }

        @Override
        public String validateParam() {

            if (QSStringUtil.isEmpty(this.getAccessControlRequestMethod())) {
                return QSStringUtil.getParameterRequired(
                        "AccessControlRequestMethod", "OptionsObjectInput");
            }
            if (QSStringUtil.isEmpty(this.getOrigin())) {
                return QSStringUtil.getParameterRequired("Origin", "OptionsObjectInput");
            }

            return null;
        }
    }

    public static class OptionsObjectOutput extends OutputModel {

        // Allowed headers

        private String accessControlAllowHeaders;

        public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
            this.accessControlAllowHeaders = accessControlAllowHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Allow-Headers")
        public String getAccessControlAllowHeaders() {
            return this.accessControlAllowHeaders;
        } // Allowed methods

        private String accessControlAllowMethods;

        public void setAccessControlAllowMethods(String accessControlAllowMethods) {
            this.accessControlAllowMethods = accessControlAllowMethods;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Allow-Methods")
        public String getAccessControlAllowMethods() {
            return this.accessControlAllowMethods;
        } // Allowed origin

        private String accessControlAllowOrigin;

        public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Allow-Origin")
        public String getAccessControlAllowOrigin() {
            return this.accessControlAllowOrigin;
        } // Expose headers

        private String accessControlExposeHeaders;

        public void setAccessControlExposeHeaders(String accessControlExposeHeaders) {
            this.accessControlExposeHeaders = accessControlExposeHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Expose-Headers")
        public String getAccessControlExposeHeaders() {
            return this.accessControlExposeHeaders;
        } // Max age

        private String accessControlMaxAge;

        public void setAccessControlMaxAge(String accessControlMaxAge) {
            this.accessControlMaxAge = accessControlMaxAge;
        }

        @ParamAnnotation(paramType = "header", paramName = "Access-Control-Max-Age")
        public String getAccessControlMaxAge() {
            return this.accessControlMaxAge;
        }
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/put.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutObjectOutput putObject(String objectName, PutObjectInput input) throws QSException {

        if (input == null) {
            input = new PutObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutObject");
        context.put("APIName", "PutObject");
        context.put("ServiceName", "PUT Object");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, PutObjectOutput.class);
        if (backModel != null) {
            return (PutObjectOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/put.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putObjectAsync(
            String objectName, PutObjectInput input, ResponseCallBack<PutObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutObjectInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "PutObject");
        context.put("APIName", "PutObject");
        context.put("ServiceName", "PUT Object");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class PutObjectInput extends RequestInputModel {

        // Object content size
        // Required

        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Length")
        public Long getContentLength() {
            return this.contentLength;
        } // Object MD5sum

        private String contentMD5;

        public void setContentMD5(String contentMD5) {
            this.contentMD5 = contentMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-MD5")
        public String getContentMD5() {
            return this.contentMD5;
        } // Object content type

        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Type")
        public String getContentType() {
            return this.contentType;
        } // Used to indicate that particular server behaviors are required by the client

        private String expect;

        public void setExpect(String expect) {
            this.expect = expect;
        }

        @ParamAnnotation(paramType = "header", paramName = "Expect")
        public String getExpect() {
            return this.expect;
        } // Copy source, format (/<bucket-name>/<object-key>)

        private String xQSCopySource;

        public void setXQSCopySource(String xQSCopySource) {
            this.xQSCopySource = xQSCopySource;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source")
        public String getXQSCopySource() {
            return this.xQSCopySource;
        } // Encryption algorithm of the object

        private String xQSCopySourceEncryptionCustomerAlgorithm;

        public void setXQSCopySourceEncryptionCustomerAlgorithm(
                String xQSCopySourceEncryptionCustomerAlgorithm) {
            this.xQSCopySourceEncryptionCustomerAlgorithm =
                    xQSCopySourceEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Algorithm"
        )
        public String getXQSCopySourceEncryptionCustomerAlgorithm() {
            return this.xQSCopySourceEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSCopySourceEncryptionCustomerKey;

        public void setXQSCopySourceEncryptionCustomerKey(
                String xQSCopySourceEncryptionCustomerKey) {
            this.xQSCopySourceEncryptionCustomerKey = xQSCopySourceEncryptionCustomerKey;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Key"
        )
        public String getXQSCopySourceEncryptionCustomerKey() {
            return this.xQSCopySourceEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSCopySourceEncryptionCustomerKeyMD5;

        public void setXQSCopySourceEncryptionCustomerKeyMD5(
                String xQSCopySourceEncryptionCustomerKeyMD5) {
            this.xQSCopySourceEncryptionCustomerKeyMD5 = xQSCopySourceEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Key-MD5"
        )
        public String getXQSCopySourceEncryptionCustomerKeyMD5() {
            return this.xQSCopySourceEncryptionCustomerKeyMD5;
        } // Check whether the copy source matches

        private String xQSCopySourceIfMatch;

        public void setXQSCopySourceIfMatch(String xQSCopySourceIfMatch) {
            this.xQSCopySourceIfMatch = xQSCopySourceIfMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Match")
        public String getXQSCopySourceIfMatch() {
            return this.xQSCopySourceIfMatch;
        } // Check whether the copy source has been modified

        private String xQSCopySourceIfModifiedSince;

        public void setXQSCopySourceIfModifiedSince(String xQSCopySourceIfModifiedSince) {
            this.xQSCopySourceIfModifiedSince = xQSCopySourceIfModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Modified-Since")
        public String getXQSCopySourceIfModifiedSince() {
            return this.xQSCopySourceIfModifiedSince;
        } // Check whether the copy source does not match

        private String xQSCopySourceIfNoneMatch;

        public void setXQSCopySourceIfNoneMatch(String xQSCopySourceIfNoneMatch) {
            this.xQSCopySourceIfNoneMatch = xQSCopySourceIfNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-None-Match")
        public String getXQSCopySourceIfNoneMatch() {
            return this.xQSCopySourceIfNoneMatch;
        } // Check whether the copy source has not been modified

        private String xQSCopySourceIfUnmodifiedSince;

        public void setXQSCopySourceIfUnmodifiedSince(String xQSCopySourceIfUnmodifiedSince) {
            this.xQSCopySourceIfUnmodifiedSince = xQSCopySourceIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Unmodified-Since")
        public String getXQSCopySourceIfUnmodifiedSince() {
            return this.xQSCopySourceIfUnmodifiedSince;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        } // Check whether fetch target object has not been modified

        private String xQSFetchIfUnmodifiedSince;

        public void setXQSFetchIfUnmodifiedSince(String xQSFetchIfUnmodifiedSince) {
            this.xQSFetchIfUnmodifiedSince = xQSFetchIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Fetch-If-Unmodified-Since")
        public String getXQSFetchIfUnmodifiedSince() {
            return this.xQSFetchIfUnmodifiedSince;
        } // Fetch source, should be a valid url

        private String xQSFetchSource;

        public void setXQSFetchSource(String xQSFetchSource) {
            this.xQSFetchSource = xQSFetchSource;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Fetch-Source")
        public String getXQSFetchSource() {
            return this.xQSFetchSource;
        } // Move source, format (/<bucket-name>/<object-key>)

        private String xQSMoveSource;

        public void setXQSMoveSource(String xQSMoveSource) {
            this.xQSMoveSource = xQSMoveSource;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Move-Source")
        public String getXQSMoveSource() {
            return this.xQSMoveSource;
        }

        // The request body
        private File bodyInputFile;

        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFile() {
            return bodyInputFile;
        }
        //
        public void setBodyInputFile(File bodyInputFile) {
            this.bodyInputFile = bodyInputFile;
        }

        private java.io.InputStream bodyInputStream;

        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public java.io.InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        public void setBodyInputStream(java.io.InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class PutObjectOutput extends OutputModel {}

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public UploadMultipartOutput uploadMultipart(String objectName, UploadMultipartInput input)
            throws QSException {

        if (input == null) {
            input = new UploadMultipartInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "UploadMultipart");
        context.put("APIName", "UploadMultipart");
        context.put("ServiceName", "Upload Multipart");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        OutputModel backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(context, input, UploadMultipartOutput.class);
        if (backModel != null) {
            return (UploadMultipartOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void uploadMultipartAsync(
            String objectName,
            UploadMultipartInput input,
            ResponseCallBack<UploadMultipartOutput> callback)
            throws QSException {
        if (input == null) {
            input = new UploadMultipartInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "UploadMultipart");
        context.put("APIName", "UploadMultipart");
        context.put("ServiceName", "Upload Multipart");
        context.put("RequestMethod", "PUT");
        context.put("RequestURI", "/<bucket-name>/<object-key>");
        context.put("bucketNameInput", this.bucketName);
        context.put("objectNameInput", objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context, input, callback);
    }

    public static class UploadMultipartInput extends RequestInputModel {
        // Object multipart upload part number
        // Required

        private Long partNumber;

        public void setPartNumber(Long partNumber) {
            this.partNumber = partNumber;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number")
        public Long getPartNumber() {
            return this.partNumber;
        } // Object multipart upload ID
        // Required

        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        // Object multipart content length

        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-Length")
        public Long getContentLength() {
            return this.contentLength;
        } // Object multipart content MD5sum

        private String contentMD5;

        public void setContentMD5(String contentMD5) {
            this.contentMD5 = contentMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "Content-MD5")
        public String getContentMD5() {
            return this.contentMD5;
        } // Specify range of the source object

        private String xQSCopyRange;

        public void setXQSCopyRange(String xQSCopyRange) {
            this.xQSCopyRange = xQSCopyRange;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Range")
        public String getXQSCopyRange() {
            return this.xQSCopyRange;
        } // Copy source, format (/<bucket-name>/<object-key>)

        private String xQSCopySource;

        public void setXQSCopySource(String xQSCopySource) {
            this.xQSCopySource = xQSCopySource;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source")
        public String getXQSCopySource() {
            return this.xQSCopySource;
        } // Encryption algorithm of the object

        private String xQSCopySourceEncryptionCustomerAlgorithm;

        public void setXQSCopySourceEncryptionCustomerAlgorithm(
                String xQSCopySourceEncryptionCustomerAlgorithm) {
            this.xQSCopySourceEncryptionCustomerAlgorithm =
                    xQSCopySourceEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Algorithm"
        )
        public String getXQSCopySourceEncryptionCustomerAlgorithm() {
            return this.xQSCopySourceEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSCopySourceEncryptionCustomerKey;

        public void setXQSCopySourceEncryptionCustomerKey(
                String xQSCopySourceEncryptionCustomerKey) {
            this.xQSCopySourceEncryptionCustomerKey = xQSCopySourceEncryptionCustomerKey;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Key"
        )
        public String getXQSCopySourceEncryptionCustomerKey() {
            return this.xQSCopySourceEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSCopySourceEncryptionCustomerKeyMD5;

        public void setXQSCopySourceEncryptionCustomerKeyMD5(
                String xQSCopySourceEncryptionCustomerKeyMD5) {
            this.xQSCopySourceEncryptionCustomerKeyMD5 = xQSCopySourceEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(
            paramType = "header",
            paramName = "X-QS-Copy-Source-Encryption-Customer-Key-MD5"
        )
        public String getXQSCopySourceEncryptionCustomerKeyMD5() {
            return this.xQSCopySourceEncryptionCustomerKeyMD5;
        } // Check whether the Etag of copy source matches the specified value

        private String xQSCopySourceIfMatch;

        public void setXQSCopySourceIfMatch(String xQSCopySourceIfMatch) {
            this.xQSCopySourceIfMatch = xQSCopySourceIfMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Match")
        public String getXQSCopySourceIfMatch() {
            return this.xQSCopySourceIfMatch;
        } // Check whether the copy source has been modified since the specified date

        private String xQSCopySourceIfModifiedSince;

        public void setXQSCopySourceIfModifiedSince(String xQSCopySourceIfModifiedSince) {
            this.xQSCopySourceIfModifiedSince = xQSCopySourceIfModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Modified-Since")
        public String getXQSCopySourceIfModifiedSince() {
            return this.xQSCopySourceIfModifiedSince;
        } // Check whether the Etag of copy source does not matches the specified value

        private String xQSCopySourceIfNoneMatch;

        public void setXQSCopySourceIfNoneMatch(String xQSCopySourceIfNoneMatch) {
            this.xQSCopySourceIfNoneMatch = xQSCopySourceIfNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-None-Match")
        public String getXQSCopySourceIfNoneMatch() {
            return this.xQSCopySourceIfNoneMatch;
        } // Check whether the copy source has not been unmodified since the specified date

        private String xQSCopySourceIfUnmodifiedSince;

        public void setXQSCopySourceIfUnmodifiedSince(String xQSCopySourceIfUnmodifiedSince) {
            this.xQSCopySourceIfUnmodifiedSince = xQSCopySourceIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Copy-Source-If-Unmodified-Since")
        public String getXQSCopySourceIfUnmodifiedSince() {
            return this.xQSCopySourceIfUnmodifiedSince;
        } // Encryption algorithm of the object

        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        } // Encryption key of the object

        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        } // MD5sum of encryption key

        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "X-QS-Encryption-Customer-Key-MD5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        // The request body
        private File bodyInputFile;

        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFile() {
            return bodyInputFile;
        }
        //
        public void setBodyInputFile(File bodyInputFile) {
            this.bodyInputFile = bodyInputFile;
        }

        private java.io.InputStream bodyInputStream;

        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public java.io.InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        public void setBodyInputStream(java.io.InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        @Override
        public String validateParam() {

            if (this.getPartNumber() < 0) {
                return QSStringUtil.getParameterRequired("PartNumber", "UploadMultipartInput");
            }
            if (QSStringUtil.isEmpty(this.getUploadID())) {
                return QSStringUtil.getParameterRequired("UploadID", "UploadMultipartInput");
            }

            return null;
        }
    }

    public static class UploadMultipartOutput extends OutputModel {}

    /**
     * @param objectName
     * @param expiresSecond Relative current time，the second when this quert sign expires
     * @return
     * @throws QSException
     */
    public String GetObjectSignatureUrl(String objectName, int expiresSecond) throws QSException {
        return QSSignatureUtil.getObjectAuthRequestUrl(
                this.evnContext, this.zone, bucketName, objectName, expiresSecond);
    }

    /**
     * @param signaturedRequest
     * @return
     * @throws QSException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetObjectOutput GetObjectBySignatureUrl(String signaturedRequest) throws QSException {
        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetObject");
        context.put("APIName", "GetObject");
        context.put("ServiceName", "QingStor");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");

        Object backModel =
                ResourceRequestFactory.getResourceRequest()
                        .sendApiRequest(signaturedRequest, context, GetObjectOutput.class);
        if (backModel != null) {
            return (GetObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param signaturedRequest
     * @param callback
     * @throws QSException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void GetObjectBySignatureUrlAsync(
            String signaturedRequest, ResponseCallBack<GetObjectOutput> callback)
            throws QSException {
        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "GetObject");
        context.put("APIName", "GetObject");
        context.put("ServiceName", "QingStor");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/<bucket-name>/<object-key>");

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest()
                .sendApiRequestAsync(signaturedRequest, context, callback);
    }
}
