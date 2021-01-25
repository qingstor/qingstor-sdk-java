/*
 * Copyright (C) 2020 Yunify, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingstor.sdk.service;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.common.OperationContext;
import com.qingstor.sdk.common.auth.Credentials;
import com.qingstor.sdk.config.ClientConfiguration;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.QSRequest;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.Types.*;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Most of operations in qingstor can be found in this class.<br>
 * Usage:
 *
 * <pre>
 * EnvContext env = new EnvContext("ACCESS_KEY_ID_EXAMPLE", "SECRET_ACCESS_KEY_EXAMPLE");
 * String zoneName = "pek3a";
 * String bucketName = "testBucketName";
 * Bucket bucket = new Bucket(env, zoneKey, bucketName);
 * </pre>
 *
 * Now you can use the object bucket to do the operations.
 *
 * <p>Note: If your endpoint is configured as a raw ip or localhost, the zone parameter in
 * constructor can be ignored.
 */
public class Bucket {
    private String zone;
    private String bucketName;
    private Credentials cred;
    private ClientConfiguration clientCfg;

    public Bucket(EnvContext envContext, String zone, String bucketName) {
        this.cred = envContext;
        this.clientCfg = ClientConfiguration.from(envContext);
        this.zone = zone;
        this.bucketName = bucketName;
    }

    // Provided for {@code QingStor#getBucket()} only currently.
    Bucket(Credentials cred, ClientConfiguration clientCfg, String zone, String bucketName) {
        this.cred = cred;
        this.clientCfg = clientCfg;
        this.zone = zone;
        this.bucketName = bucketName;
    }

    /**
     * @throws QSException exception
     * @return DeleteBucketOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/delete.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketOutput delete() throws QSException {
        RequestHandler requestHandler = this.deleteRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete.html">https://docs.qingcloud.com/qingstor/api/bucket/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucket")
                .apiName("DeleteBucket")
                .serviceName("DELETE Bucket")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete.html">https://docs.qingcloud.com/qingstor/api/bucket/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteAsync(ResponseCallBack<DeleteBucketOutput> callback) throws QSException {

        RequestHandler requestHandler = this.deleteAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete.html">https://docs.qingcloud.com/qingstor/api/bucket/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteAsyncRequest(ResponseCallBack<DeleteBucketOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucket")
                .apiName("DeleteBucket")
                .serviceName("DELETE Bucket")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return DeleteBucketCNAMEOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketCNAMEOutput deleteCNAME(DeleteBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new DeleteBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.deleteCNAMERequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketCNAMEOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteCNAMERequest(DeleteBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new DeleteBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketCNAME")
                .apiName("DeleteBucketCNAME")
                .serviceName("DELETE Bucket CNAME")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, DeleteBucketCNAMEOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteCNAMEAsync(
            DeleteBucketCNAMEInput input, ResponseCallBack<DeleteBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new DeleteBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.deleteCNAMEAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/delete_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteCNAMEAsyncRequest(
            DeleteBucketCNAMEInput input, ResponseCallBack<DeleteBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new DeleteBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketCNAME")
                .apiName("DeleteBucketCNAME")
                .serviceName("DELETE Bucket CNAME")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * DeleteBucketCNAMEInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Domain domain name <br>
     */
    public static class DeleteBucketCNAMEInput extends RequestInputModel {

        /** domain name Required */
        private String domain;

        public void setDomain(String domain) {
            this.domain = domain;
        }

        @ParamAnnotation(paramType = "element", paramName = "domain")
        public String getDomain() {
            return this.domain;
        }

        /** the domain will deleted from cname records. */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (QSStringUtil.isEmpty(this.getDomain())) {
                return QSStringUtil.getParameterRequired("Domain", "DeleteBucketCNAMEInput");
            }
            return null;
        }
    }

    /**
     * DeleteBucketCNAMEOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Domain domain name <br>
     */
    public static class DeleteBucketCNAMEOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketCORSOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketCORSOutput deleteCORS() throws QSException {
        RequestHandler requestHandler = this.deleteCORSRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketCORSOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteCORSRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketCORS")
                .apiName("DeleteBucketCORS")
                .serviceName("DELETE Bucket CORS")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketCORSOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteCORSAsync(ResponseCallBack<DeleteBucketCORSOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteCORSAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/delete_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteCORSAsyncRequest(ResponseCallBack<DeleteBucketCORSOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketCORS")
                .apiName("DeleteBucketCORS")
                .serviceName("DELETE Bucket CORS")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketCORSOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketCORSOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketExternalMirrorOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html
     *     </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketExternalMirrorOutput deleteExternalMirror() throws QSException {
        RequestHandler requestHandler = this.deleteExternalMirrorRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteExternalMirrorRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketExternalMirror")
                .apiName("DeleteBucketExternalMirror")
                .serviceName("DELETE Bucket External Mirror")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketExternalMirrorOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteExternalMirrorAsync(
            ResponseCallBack<DeleteBucketExternalMirrorOutput> callback) throws QSException {

        RequestHandler requestHandler = this.deleteExternalMirrorAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/delete_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteExternalMirrorAsyncRequest(
            ResponseCallBack<DeleteBucketExternalMirrorOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketExternalMirror")
                .apiName("DeleteBucketExternalMirror")
                .serviceName("DELETE Bucket External Mirror")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketExternalMirrorOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketExternalMirrorOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketLifecycleOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketLifecycleOutput deleteLifecycle() throws QSException {
        RequestHandler requestHandler = this.deleteLifecycleRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketLifecycleOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteLifecycleRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketLifecycle")
                .apiName("DeleteBucketLifecycle")
                .serviceName("DELETE Bucket Lifecycle")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketLifecycleOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteLifecycleAsync(ResponseCallBack<DeleteBucketLifecycleOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteLifecycleAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/delete_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteLifecycleAsyncRequest(
            ResponseCallBack<DeleteBucketLifecycleOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketLifecycle")
                .apiName("DeleteBucketLifecycle")
                .serviceName("DELETE Bucket Lifecycle")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketLifecycleOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketLifecycleOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketLoggingOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketLoggingOutput deleteLogging() throws QSException {
        RequestHandler requestHandler = this.deleteLoggingRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketLoggingOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteLoggingRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketLogging")
                .apiName("DeleteBucketLogging")
                .serviceName("DELETE Bucket Logging")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketLoggingOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteLoggingAsync(ResponseCallBack<DeleteBucketLoggingOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteLoggingAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/delete_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteLoggingAsyncRequest(
            ResponseCallBack<DeleteBucketLoggingOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketLogging")
                .apiName("DeleteBucketLogging")
                .serviceName("DELETE Bucket Logging")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketLoggingOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketLoggingOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketNotificationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketNotificationOutput deleteNotification() throws QSException {
        RequestHandler requestHandler = this.deleteNotificationRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketNotificationOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteNotificationRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketNotification")
                .apiName("DeleteBucketNotification")
                .serviceName("DELETE Bucket Notification")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketNotificationOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteNotificationAsync(ResponseCallBack<DeleteBucketNotificationOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteNotificationAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/delete_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteNotificationAsyncRequest(
            ResponseCallBack<DeleteBucketNotificationOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketNotification")
                .apiName("DeleteBucketNotification")
                .serviceName("DELETE Bucket Notification")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketNotificationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketNotificationOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketPolicyOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketPolicyOutput deletePolicy() throws QSException {
        RequestHandler requestHandler = this.deletePolicyRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketPolicyOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deletePolicyRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketPolicy")
                .apiName("DeleteBucketPolicy")
                .serviceName("DELETE Bucket Policy")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketPolicyOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deletePolicyAsync(ResponseCallBack<DeleteBucketPolicyOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deletePolicyAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/delete_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deletePolicyAsyncRequest(
            ResponseCallBack<DeleteBucketPolicyOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketPolicy")
                .apiName("DeleteBucketPolicy")
                .serviceName("DELETE Bucket Policy")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketPolicyOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketPolicyOutput extends OutputModel {}

    /**
     * @throws QSException exception
     * @return DeleteBucketReplicationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteBucketReplicationOutput deleteReplication() throws QSException {
        RequestHandler requestHandler = this.deleteReplicationRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteBucketReplicationOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteReplicationRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketReplication")
                .apiName("DeleteBucketReplication")
                .serviceName("DELETE Bucket Replication")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteBucketReplicationOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteReplicationAsync(ResponseCallBack<DeleteBucketReplicationOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteReplicationAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/delete_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteReplicationAsyncRequest(
            ResponseCallBack<DeleteBucketReplicationOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteBucketReplication")
                .apiName("DeleteBucketReplication")
                .serviceName("DELETE Bucket Replication")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteBucketReplicationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteBucketReplicationOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return DeleteMultipleObjectsOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteMultipleObjectsOutput deleteMultipleObjects(DeleteMultipleObjectsInput input)
            throws QSException {
        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        RequestHandler requestHandler = this.deleteMultipleObjectsRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteMultipleObjectsOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html">https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteMultipleObjectsRequest(DeleteMultipleObjectsInput input)
            throws QSException {
        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteMultipleObjects")
                .apiName("DeleteMultipleObjects")
                .serviceName("Delete Multiple Objects")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>?delete");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, DeleteMultipleObjectsOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html">https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteMultipleObjectsAsync(
            DeleteMultipleObjectsInput input,
            ResponseCallBack<DeleteMultipleObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        RequestHandler requestHandler = this.deleteMultipleObjectsAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html">https://docs.qingcloud.com/qingstor/api/bucket/delete_multiple.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteMultipleObjectsAsyncRequest(
            DeleteMultipleObjectsInput input,
            ResponseCallBack<DeleteMultipleObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new DeleteMultipleObjectsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteMultipleObjects")
                .apiName("DeleteMultipleObjects")
                .serviceName("Delete Multiple Objects")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>?delete");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * DeleteMultipleObjectsInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentMD5 Object MD5sum <br>
     * field Objects A list of keys to delete <br>
     * field Quiet Whether to return the list of deleted objects <br>
     */
    public static class DeleteMultipleObjectsInput extends RequestInputModel {

        /** A list of keys to delete Required */
        private List<KeyModel> objects;

        public void setObjects(List<KeyModel> objects) {
            this.objects = objects;
        }

        @ParamAnnotation(paramType = "element", paramName = "objects")
        public List<KeyModel> getObjects() {
            return this.objects;
        }
        /** Whether to return the list of deleted objects */
        private Boolean quiet;

        public void setQuiet(Boolean quiet) {
            this.quiet = quiet;
        }

        @ParamAnnotation(paramType = "element", paramName = "quiet")
        public Boolean getQuiet() {
            return this.quiet;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

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

    /**
     * DeleteMultipleObjectsOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentMD5 Object MD5sum <br>
     * field Objects A list of keys to delete <br>
     * field Quiet Whether to return the list of deleted objects <br>
     */
    public static class DeleteMultipleObjectsOutput extends OutputModel {

        /** List of deleted objects */
        private List<KeyModel> deleted;

        public void setDeleted(List<KeyModel> deleted) {
            this.deleted = deleted;
        }

        @ParamAnnotation(paramType = "element", paramName = "deleted")
        public List<KeyModel> getDeleted() {
            return this.deleted;
        }
        /** Error messages */
        private List<KeyDeleteErrorModel> errors;

        public void setErrors(List<KeyDeleteErrorModel> errors) {
            this.errors = errors;
        }

        @ParamAnnotation(paramType = "element", paramName = "errors")
        public List<KeyDeleteErrorModel> getErrors() {
            return this.errors;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketACLOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketACLOutput getACL() throws QSException {
        RequestHandler requestHandler = this.getACLRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketACLOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getACLRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketACL")
                .apiName("GetBucketACL")
                .serviceName("GET Bucket ACL")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?acl");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketACLOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getACLAsync(ResponseCallBack<GetBucketACLOutput> callback) throws QSException {

        RequestHandler requestHandler = this.getACLAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/get_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getACLAsyncRequest(ResponseCallBack<GetBucketACLOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketACL")
                .apiName("GetBucketACL")
                .serviceName("GET Bucket ACL")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?acl");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketACLOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketACLOutput extends OutputModel {

        /** Bucket ACL rules */
        private List<ACLModel> aCL;

        public void setACL(List<ACLModel> aCL) {
            this.aCL = aCL;
        }

        @ParamAnnotation(paramType = "element", paramName = "acl")
        public List<ACLModel> getACL() {
            return this.aCL;
        }
        /** Bucket owner */
        private OwnerModel owner;

        public void setOwner(OwnerModel owner) {
            this.owner = owner;
        }

        @ParamAnnotation(paramType = "element", paramName = "owner")
        public OwnerModel getOwner() {
            return this.owner;
        }
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return GetBucketCNAMEOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketCNAMEOutput getCNAME(GetBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new GetBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.getCNAMERequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketCNAMEOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getCNAMERequest(GetBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new GetBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketCNAME")
                .apiName("GetBucketCNAME")
                .serviceName("GET Bucket CNAME")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, GetBucketCNAMEOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getCNAMEAsync(
            GetBucketCNAMEInput input, ResponseCallBack<GetBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new GetBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.getCNAMEAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/get_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getCNAMEAsyncRequest(
            GetBucketCNAMEInput input, ResponseCallBack<GetBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new GetBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketCNAME")
                .apiName("GetBucketCNAME")
                .serviceName("GET Bucket CNAME")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * GetBucketCNAMEInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Type Limit the type used for query, normal will be recognized if empty. <br>
     */
    public static class GetBucketCNAMEInput extends RequestInputModel {

        /**
         * Limit the type used for query, normal will be recognized if empty. Type's available
         * values: website, normal
         */
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

            String[] typeValidValues = {"website", "normal"};

            boolean typeIsValid = false;
            String type = this.getType();
            if (null == type || "".equals(type)) {
                typeIsValid = true;
            } else {
                for (String v : typeValidValues) {
                    if (v.equals(type)) {
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

    /**
     * GetBucketCNAMEOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Type Limit the type used for query, normal will be recognized if empty. <br>
     */
    public static class GetBucketCNAMEOutput extends OutputModel {

        /** the details of all eligible CNAME records. */
        private List<CnameRecordModel> cnameRecords;

        public void setCnameRecords(List<CnameRecordModel> cnameRecords) {
            this.cnameRecords = cnameRecords;
        }

        @ParamAnnotation(paramType = "element", paramName = "cname_records")
        public List<CnameRecordModel> getCnameRecords() {
            return this.cnameRecords;
        }
        /** the count of all eligible CNAME records. */
        private Integer count;

        public void setCount(Integer count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "element", paramName = "count")
        public Integer getCount() {
            return this.count;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketCORSOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketCORSOutput getCORS() throws QSException {
        RequestHandler requestHandler = this.getCORSRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketCORSOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getCORSRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketCORS")
                .apiName("GetBucketCORS")
                .serviceName("GET Bucket CORS")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketCORSOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getCORSAsync(ResponseCallBack<GetBucketCORSOutput> callback) throws QSException {

        RequestHandler requestHandler = this.getCORSAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/get_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getCORSAsyncRequest(ResponseCallBack<GetBucketCORSOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketCORS")
                .apiName("GetBucketCORS")
                .serviceName("GET Bucket CORS")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketCORSOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketCORSOutput extends OutputModel {

        /** Bucket CORS rules */
        private List<CORSRuleModel> cORSRules;

        public void setCORSRules(List<CORSRuleModel> cORSRules) {
            this.cORSRules = cORSRules;
        }

        @ParamAnnotation(paramType = "element", paramName = "cors_rules")
        public List<CORSRuleModel> getCORSRules() {
            return this.cORSRules;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketExternalMirrorOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html
     *     </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketExternalMirrorOutput getExternalMirror() throws QSException {
        RequestHandler requestHandler = this.getExternalMirrorRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getExternalMirrorRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketExternalMirror")
                .apiName("GetBucketExternalMirror")
                .serviceName("GET Bucket External Mirror")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketExternalMirrorOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getExternalMirrorAsync(ResponseCallBack<GetBucketExternalMirrorOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getExternalMirrorAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/get_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getExternalMirrorAsyncRequest(
            ResponseCallBack<GetBucketExternalMirrorOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketExternalMirror")
                .apiName("GetBucketExternalMirror")
                .serviceName("GET Bucket External Mirror")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketExternalMirrorOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketExternalMirrorOutput extends OutputModel {

        /** Source site url */
        private String sourceSite;

        public void setSourceSite(String sourceSite) {
            this.sourceSite = sourceSite;
        }

        @ParamAnnotation(paramType = "element", paramName = "source_site")
        public String getSourceSite() {
            return this.sourceSite;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketLifecycleOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketLifecycleOutput getLifecycle() throws QSException {
        RequestHandler requestHandler = this.getLifecycleRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketLifecycleOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getLifecycleRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketLifecycle")
                .apiName("GetBucketLifecycle")
                .serviceName("GET Bucket Lifecycle")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketLifecycleOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getLifecycleAsync(ResponseCallBack<GetBucketLifecycleOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getLifecycleAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/get_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getLifecycleAsyncRequest(
            ResponseCallBack<GetBucketLifecycleOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketLifecycle")
                .apiName("GetBucketLifecycle")
                .serviceName("GET Bucket Lifecycle")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketLifecycleOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketLifecycleOutput extends OutputModel {

        /** Bucket Lifecycle rule */
        private List<RuleModel> rule;

        public void setRule(List<RuleModel> rule) {
            this.rule = rule;
        }

        @ParamAnnotation(paramType = "element", paramName = "rule")
        public List<RuleModel> getRule() {
            return this.rule;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketLoggingOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketLoggingOutput getLogging() throws QSException {
        RequestHandler requestHandler = this.getLoggingRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketLoggingOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getLoggingRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketLogging")
                .apiName("GetBucketLogging")
                .serviceName("GET Bucket Logging")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketLoggingOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getLoggingAsync(ResponseCallBack<GetBucketLoggingOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getLoggingAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/get_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getLoggingAsyncRequest(ResponseCallBack<GetBucketLoggingOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketLogging")
                .apiName("GetBucketLogging")
                .serviceName("GET Bucket Logging")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketLoggingOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketLoggingOutput extends OutputModel {

        /** The name of the bucket used to store logs. The user must be the owner of the bucket. */
        private String targetBucket;

        public void setTargetBucket(String targetBucket) {
            this.targetBucket = targetBucket;
        }

        @ParamAnnotation(paramType = "element", paramName = "target_bucket")
        public String getTargetBucket() {
            return this.targetBucket;
        }
        /** generated log files' common prefix */
        private String targetPrefix;

        public void setTargetPrefix(String targetPrefix) {
            this.targetPrefix = targetPrefix;
        }

        @ParamAnnotation(paramType = "element", paramName = "target_prefix")
        public String getTargetPrefix() {
            return this.targetPrefix;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketNotificationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketNotificationOutput getNotification() throws QSException {
        RequestHandler requestHandler = this.getNotificationRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketNotificationOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getNotificationRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketNotification")
                .apiName("GetBucketNotification")
                .serviceName("GET Bucket Notification")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketNotificationOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getNotificationAsync(ResponseCallBack<GetBucketNotificationOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getNotificationAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/get_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getNotificationAsyncRequest(
            ResponseCallBack<GetBucketNotificationOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketNotification")
                .apiName("GetBucketNotification")
                .serviceName("GET Bucket Notification")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketNotificationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketNotificationOutput extends OutputModel {

        /** Bucket Notification */
        private List<NotificationModel> notifications;

        public void setNotifications(List<NotificationModel> notifications) {
            this.notifications = notifications;
        }

        @ParamAnnotation(paramType = "element", paramName = "notifications")
        public List<NotificationModel> getNotifications() {
            return this.notifications;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketPolicyOutput output stream Documentation URL: <a
     *     href="https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html">
     *     https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketPolicyOutput getPolicy() throws QSException {
        RequestHandler requestHandler = this.getPolicyRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketPolicyOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html">https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getPolicyRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketPolicy")
                .apiName("GetBucketPolicy")
                .serviceName("GET Bucket Policy")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketPolicyOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html">https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getPolicyAsync(ResponseCallBack<GetBucketPolicyOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getPolicyAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html">https://https://docs.qingcloud.com/qingstor/api/bucket/policy/get_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getPolicyAsyncRequest(ResponseCallBack<GetBucketPolicyOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketPolicy")
                .apiName("GetBucketPolicy")
                .serviceName("GET Bucket Policy")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketPolicyOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketPolicyOutput extends OutputModel {

        /** Bucket policy statement */
        private List<StatementModel> statement;

        public void setStatement(List<StatementModel> statement) {
            this.statement = statement;
        }

        @ParamAnnotation(paramType = "element", paramName = "statement")
        public List<StatementModel> getStatement() {
            return this.statement;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketReplicationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketReplicationOutput getReplication() throws QSException {
        RequestHandler requestHandler = this.getReplicationRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketReplicationOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getReplicationRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketReplication")
                .apiName("GetBucketReplication")
                .serviceName("GET Bucket Replication")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketReplicationOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getReplicationAsync(ResponseCallBack<GetBucketReplicationOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getReplicationAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/get_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getReplicationAsyncRequest(
            ResponseCallBack<GetBucketReplicationOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketReplication")
                .apiName("GetBucketReplication")
                .serviceName("GET Bucket Replication")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketReplicationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketReplicationOutput extends OutputModel {

        /** Bucket Replication rule */
        private List<RulesModel> rules;

        public void setRules(List<RulesModel> rules) {
            this.rules = rules;
        }

        @ParamAnnotation(paramType = "element", paramName = "rules")
        public List<RulesModel> getRules() {
            return this.rules;
        }
    }

    /**
     * @throws QSException exception
     * @return GetBucketStatisticsOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetBucketStatisticsOutput getStatistics() throws QSException {
        RequestHandler requestHandler = this.getStatisticsRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetBucketStatisticsOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html">https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getStatisticsRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketStatistics")
                .apiName("GetBucketStatistics")
                .serviceName("GET Bucket Statistics")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?stats");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, GetBucketStatisticsOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html">https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getStatisticsAsync(ResponseCallBack<GetBucketStatisticsOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.getStatisticsAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html">https://docs.qingcloud.com/qingstor/api/bucket/get_stats.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getStatisticsAsyncRequest(
            ResponseCallBack<GetBucketStatisticsOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetBucketStatistics")
                .apiName("GetBucketStatistics")
                .serviceName("GET Bucket Statistics")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?stats");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * GetBucketStatisticsOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class GetBucketStatisticsOutput extends OutputModel {

        /** Objects count in the bucket */
        private Long count;

        public void setCount(Long count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "element", paramName = "count")
        public Long getCount() {
            return this.count;
        }
        /** Bucket created time */
        private String created;

        public void setCreated(String created) {
            this.created = created;
        }

        @ParamAnnotation(paramType = "element", paramName = "created")
        public String getCreated() {
            return this.created;
        }
        /** QingCloud Zone ID */
        private String location;

        public void setLocation(String location) {
            this.location = location;
        }

        @ParamAnnotation(paramType = "element", paramName = "location")
        public String getLocation() {
            return this.location;
        }
        /** Bucket name */
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "element", paramName = "name")
        public String getName() {
            return this.name;
        }
        /** Bucket storage size */
        private Long size;

        public void setSize(Long size) {
            this.size = size;
        }

        @ParamAnnotation(paramType = "element", paramName = "size")
        public Long getSize() {
            return this.size;
        }
        /** Bucket status Status's available values: active, suspended */
        private String status;

        public void setStatus(String status) {
            this.status = status;
        }

        @ParamAnnotation(paramType = "element", paramName = "status")
        public String getStatus() {
            return this.status;
        }
        /** URL to access the bucket */
        private String uRL;

        public void setURL(String uRL) {
            this.uRL = uRL;
        }

        @ParamAnnotation(paramType = "element", paramName = "url")
        public String getURL() {
            return this.uRL;
        }
    }

    /**
     * @throws QSException exception
     * @return HeadBucketOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/head.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/head.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HeadBucketOutput head() throws QSException {
        RequestHandler requestHandler = this.headRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (HeadBucketOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/head.html">https://docs.qingcloud.com/qingstor/api/bucket/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler headRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("HeadBucket")
                .apiName("HeadBucket")
                .serviceName("HEAD Bucket")
                .reqMethod("HEAD")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, HeadBucketOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/head.html">https://docs.qingcloud.com/qingstor/api/bucket/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void headAsync(ResponseCallBack<HeadBucketOutput> callback) throws QSException {

        RequestHandler requestHandler = this.headAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/head.html">https://docs.qingcloud.com/qingstor/api/bucket/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler headAsyncRequest(ResponseCallBack<HeadBucketOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("HeadBucket")
                .apiName("HeadBucket")
                .serviceName("HEAD Bucket")
                .reqMethod("HEAD")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * HeadBucketOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class HeadBucketOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return ListMultipartUploadsOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListMultipartUploadsOutput listMultipartUploads(ListMultipartUploadsInput input)
            throws QSException {
        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        RequestHandler requestHandler = this.listMultipartUploadsRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (ListMultipartUploadsOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html">https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listMultipartUploadsRequest(ListMultipartUploadsInput input)
            throws QSException {
        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListMultipartUploads")
                .apiName("ListMultipartUploads")
                .serviceName("List Multipart Uploads")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?uploads");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, ListMultipartUploadsOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html">https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listMultipartUploadsAsync(
            ListMultipartUploadsInput input, ResponseCallBack<ListMultipartUploadsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        RequestHandler requestHandler = this.listMultipartUploadsAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html">https://docs.qingcloud.com/qingstor/api/bucket/list_multipart_uploads.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listMultipartUploadsAsyncRequest(
            ListMultipartUploadsInput input, ResponseCallBack<ListMultipartUploadsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListMultipartUploadsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListMultipartUploads")
                .apiName("ListMultipartUploads")
                .serviceName("List Multipart Uploads")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>?uploads");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * ListMultipartUploadsInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Delimiter Put all keys that share a common prefix into a list <br>
     * field KeyMarker Limit results returned from the first key after key_marker sorted by
     * alphabetical order <br>
     * field Limit Results count limit <br>
     * field Prefix Limits results to keys that begin with the prefix <br>
     * field UploadIDMarker Limit results returned from the first uploading segment after
     * upload_id_marker sorted by the time of upload_id <br>
     */
    public static class ListMultipartUploadsInput extends RequestInputModel {

        /** Put all keys that share a common prefix into a list */
        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        }
        /**
         * Limit results returned from the first key after key_marker sorted by alphabetical order
         */
        private String keyMarker;

        public void setKeyMarker(String keyMarker) {
            this.keyMarker = keyMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "key_marker")
        public String getKeyMarker() {
            return this.keyMarker;
        }
        /** Results count limit */
        private Integer limit;

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Integer getLimit() {
            return this.limit;
        }
        /** Limits results to keys that begin with the prefix */
        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "query", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        }
        /**
         * Limit results returned from the first uploading segment after upload_id_marker sorted by
         * the time of upload_id
         */
        private String uploadIDMarker;

        public void setUploadIDMarker(String uploadIDMarker) {
            this.uploadIDMarker = uploadIDMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id_marker")
        public String getUploadIDMarker() {
            return this.uploadIDMarker;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    /**
     * ListMultipartUploadsOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Delimiter Put all keys that share a common prefix into a list <br>
     * field KeyMarker Limit results returned from the first key after key_marker sorted by
     * alphabetical order <br>
     * field Limit Results count limit <br>
     * field Prefix Limits results to keys that begin with the prefix <br>
     * field UploadIDMarker Limit results returned from the first uploading segment after
     * upload_id_marker sorted by the time of upload_id <br>
     */
    public static class ListMultipartUploadsOutput extends OutputModel {

        /** Other object keys that share common prefixes */
        private List<String> commonPrefixes;

        public void setCommonPrefixes(List<String> commonPrefixes) {
            this.commonPrefixes = commonPrefixes;
        }

        @ParamAnnotation(paramType = "element", paramName = "common_prefixes")
        public List<String> getCommonPrefixes() {
            return this.commonPrefixes;
        }
        /** Delimiter that specified in request parameters */
        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "element", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        }
        /** Indicate if these are more results in the next page */
        private Boolean hasMore;

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        @ParamAnnotation(paramType = "element", paramName = "has_more")
        public Boolean getHasMore() {
            return this.hasMore;
        }
        /** Limit that specified in request parameters */
        private Integer limit;

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "element", paramName = "limit")
        public Integer getLimit() {
            return this.limit;
        }
        /** Marker that specified in request parameters */
        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "element", paramName = "marker")
        public String getMarker() {
            return this.marker;
        }
        /** Bucket name */
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "element", paramName = "name")
        public String getName() {
            return this.name;
        }
        /** The last key in uploads list */
        private String nextKeyMarker;

        public void setNextKeyMarker(String nextKeyMarker) {
            this.nextKeyMarker = nextKeyMarker;
        }

        @ParamAnnotation(paramType = "element", paramName = "next_key_marker")
        public String getNextKeyMarker() {
            return this.nextKeyMarker;
        }
        /** The last upload_id in uploads list */
        private String nextUploadIDMarker;

        public void setNextUploadIDMarker(String nextUploadIDMarker) {
            this.nextUploadIDMarker = nextUploadIDMarker;
        }

        @ParamAnnotation(paramType = "element", paramName = "next_upload_id_marker")
        public String getNextUploadIDMarker() {
            return this.nextUploadIDMarker;
        }
        /** Prefix that specified in request parameters */
        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "element", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        }
        /** Multipart uploads */
        private List<UploadsModel> uploads;

        public void setUploads(List<UploadsModel> uploads) {
            this.uploads = uploads;
        }

        @ParamAnnotation(paramType = "element", paramName = "uploads")
        public List<UploadsModel> getUploads() {
            return this.uploads;
        }
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return ListObjectsOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/get.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListObjectsOutput listObjects(ListObjectsInput input) throws QSException {
        if (input == null) {
            input = new ListObjectsInput();
        }

        RequestHandler requestHandler = this.listObjectsRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (ListObjectsOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get.html">https://docs.qingcloud.com/qingstor/api/bucket/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listObjectsRequest(ListObjectsInput input) throws QSException {
        if (input == null) {
            input = new ListObjectsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListObjects")
                .apiName("ListObjects")
                .serviceName("GET Bucket (List Objects)")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, ListObjectsOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get.html">https://docs.qingcloud.com/qingstor/api/bucket/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listObjectsAsync(
            ListObjectsInput input, ResponseCallBack<ListObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListObjectsInput();
        }

        RequestHandler requestHandler = this.listObjectsAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/get.html">https://docs.qingcloud.com/qingstor/api/bucket/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listObjectsAsyncRequest(
            ListObjectsInput input, ResponseCallBack<ListObjectsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListObjectsInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListObjects")
                .apiName("ListObjects")
                .serviceName("GET Bucket (List Objects)")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * ListObjectsInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Delimiter Put all keys that share a common prefix into a list <br>
     * field Limit Results count limit <br>
     * field Marker Limit results to keys that start at this marker <br>
     * field Prefix Limits results to keys that begin with the prefix <br>
     */
    public static class ListObjectsInput extends RequestInputModel {

        /** Put all keys that share a common prefix into a list */
        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "query", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        }
        /** Results count limit */
        private Integer limit;

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Integer getLimit() {
            return this.limit;
        }
        /** Limit results to keys that start at this marker */
        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "query", paramName = "marker")
        public String getMarker() {
            return this.marker;
        }
        /** Limits results to keys that begin with the prefix */
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

    /**
     * ListObjectsOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Delimiter Put all keys that share a common prefix into a list <br>
     * field Limit Results count limit <br>
     * field Marker Limit results to keys that start at this marker <br>
     * field Prefix Limits results to keys that begin with the prefix <br>
     */
    public static class ListObjectsOutput extends OutputModel {

        /** Other object keys that share common prefixes */
        private List<String> commonPrefixes;

        public void setCommonPrefixes(List<String> commonPrefixes) {
            this.commonPrefixes = commonPrefixes;
        }

        @ParamAnnotation(paramType = "element", paramName = "common_prefixes")
        public List<String> getCommonPrefixes() {
            return this.commonPrefixes;
        }
        /** Delimiter that specified in request parameters */
        private String delimiter;

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @ParamAnnotation(paramType = "element", paramName = "delimiter")
        public String getDelimiter() {
            return this.delimiter;
        }
        /** Indicate if these are more results in the next page */
        private Boolean hasMore;

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        @ParamAnnotation(paramType = "element", paramName = "has_more")
        public Boolean getHasMore() {
            return this.hasMore;
        }
        /** Object keys */
        private List<KeyModel> keys;

        public void setKeys(List<KeyModel> keys) {
            this.keys = keys;
        }

        @ParamAnnotation(paramType = "element", paramName = "keys")
        public List<KeyModel> getKeys() {
            return this.keys;
        }
        /** Limit that specified in request parameters */
        private Integer limit;

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "element", paramName = "limit")
        public Integer getLimit() {
            return this.limit;
        }
        /** Marker that specified in request parameters */
        private String marker;

        public void setMarker(String marker) {
            this.marker = marker;
        }

        @ParamAnnotation(paramType = "element", paramName = "marker")
        public String getMarker() {
            return this.marker;
        }
        /** Bucket name */
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @ParamAnnotation(paramType = "element", paramName = "name")
        public String getName() {
            return this.name;
        }
        /** The last key in keys list */
        private String nextMarker;

        public void setNextMarker(String nextMarker) {
            this.nextMarker = nextMarker;
        }

        @ParamAnnotation(paramType = "element", paramName = "next_marker")
        public String getNextMarker() {
            return this.nextMarker;
        }
        /** Bucket owner */
        private OwnerModel owner;

        public void setOwner(OwnerModel owner) {
            this.owner = owner;
        }

        @ParamAnnotation(paramType = "element", paramName = "owner")
        public OwnerModel getOwner() {
            return this.owner;
        }
        /** Prefix that specified in request parameters */
        private String prefix;

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @ParamAnnotation(paramType = "element", paramName = "prefix")
        public String getPrefix() {
            return this.prefix;
        }
    }

    /**
     * @throws QSException exception
     * @return PutBucketOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/put.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketOutput put() throws QSException {
        RequestHandler requestHandler = this.putRequest();

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketOutput) backModel;
        }
        return null;
    }

    /**
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put.html">https://docs.qingcloud.com/qingstor/api/bucket/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putRequest() throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucket")
                .apiName("PutBucket")
                .serviceName("PUT Bucket")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, PutBucketOutput.class);

        return requestHandler;
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put.html">https://docs.qingcloud.com/qingstor/api/bucket/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putAsync(ResponseCallBack<PutBucketOutput> callback) throws QSException {

        RequestHandler requestHandler = this.putAsyncRequest(callback);

        requestHandler.sendAsync();
    }

    /**
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put.html">https://docs.qingcloud.com/qingstor/api/bucket/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putAsyncRequest(ResponseCallBack<PutBucketOutput> callback)
            throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucket")
                .apiName("PutBucket")
                .serviceName("PUT Bucket")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * PutBucketOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class PutBucketOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketACLOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketACLOutput putACL(PutBucketACLInput input) throws QSException {
        if (input == null) {
            input = new PutBucketACLInput();
        }

        RequestHandler requestHandler = this.putACLRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketACLOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putACLRequest(PutBucketACLInput input) throws QSException {
        if (input == null) {
            input = new PutBucketACLInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketACL")
                .apiName("PutBucketACL")
                .serviceName("PUT Bucket ACL")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?acl");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketACLOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putACLAsync(PutBucketACLInput input, ResponseCallBack<PutBucketACLOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketACLInput();
        }

        RequestHandler requestHandler = this.putACLAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html">https://docs.qingcloud.com/qingstor/api/bucket/put_acl.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putACLAsyncRequest(
            PutBucketACLInput input, ResponseCallBack<PutBucketACLOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketACLInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketACL")
                .apiName("PutBucketACL")
                .serviceName("PUT Bucket ACL")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?acl");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketACLInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ACL Bucket ACL rules <br>
     */
    public static class PutBucketACLInput extends RequestInputModel {

        /** Bucket ACL rules Required */
        private List<ACLModel> aCL;

        public void setACL(List<ACLModel> aCL) {
            this.aCL = aCL;
        }

        @ParamAnnotation(paramType = "element", paramName = "acl")
        public List<ACLModel> getACL() {
            return this.aCL;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

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

    /**
     * PutBucketACLOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ACL Bucket ACL rules <br>
     */
    public static class PutBucketACLOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketCNAMEOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketCNAMEOutput putCNAME(PutBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new PutBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.putCNAMERequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketCNAMEOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putCNAMERequest(PutBucketCNAMEInput input) throws QSException {
        if (input == null) {
            input = new PutBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketCNAME")
                .apiName("PutBucketCNAME")
                .serviceName("PUT Bucket CNAME")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketCNAMEOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putCNAMEAsync(
            PutBucketCNAMEInput input, ResponseCallBack<PutBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketCNAMEInput();
        }

        RequestHandler requestHandler = this.putCNAMEAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html">https://docs.qingcloud.com/qingstor/api/bucket/cname/put_cname.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putCNAMEAsyncRequest(
            PutBucketCNAMEInput input, ResponseCallBack<PutBucketCNAMEOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketCNAMEInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketCNAME")
                .apiName("PutBucketCNAME")
                .serviceName("PUT Bucket CNAME")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?cname");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketCNAMEInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Domain The domain name to be bound to the bucket. The domain name must have been
     * registered and not bound to another bucket. <br>
     * field Type The purpose of the domain name to be bound. Currently supports two types, normal
     * and website. <br>
     */
    public static class PutBucketCNAMEInput extends RequestInputModel {

        /**
         * The domain name to be bound to the bucket. The domain name must have been registered and
         * not bound to another bucket. Required
         */
        private String domain;

        public void setDomain(String domain) {
            this.domain = domain;
        }

        @ParamAnnotation(paramType = "element", paramName = "domain")
        public String getDomain() {
            return this.domain;
        }
        /**
         * The purpose of the domain name to be bound. Currently supports two types, normal and
         * website. Type's available values: normal, website
         */
        private String type;

        public void setType(String type) {
            this.type = type;
        }

        @ParamAnnotation(paramType = "element", paramName = "type")
        public String getType() {
            return this.type;
        }

        /** cname record will bound to a specific bucket. */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (QSStringUtil.isEmpty(this.getDomain())) {
                return QSStringUtil.getParameterRequired("Domain", "PutBucketCNAMEInput");
            }
            String[] typeValidValues = {"normal", "website"};

            boolean typeIsValid = false;
            String type = this.getType();
            if (null == type || "".equals(type)) {
                typeIsValid = true;
            } else {
                for (String v : typeValidValues) {
                    if (v.equals(type)) {
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

    /**
     * PutBucketCNAMEOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Domain The domain name to be bound to the bucket. The domain name must have been
     * registered and not bound to another bucket. <br>
     * field Type The purpose of the domain name to be bound. Currently supports two types, normal
     * and website. <br>
     */
    public static class PutBucketCNAMEOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketCORSOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketCORSOutput putCORS(PutBucketCORSInput input) throws QSException {
        if (input == null) {
            input = new PutBucketCORSInput();
        }

        RequestHandler requestHandler = this.putCORSRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketCORSOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putCORSRequest(PutBucketCORSInput input) throws QSException {
        if (input == null) {
            input = new PutBucketCORSInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketCORS")
                .apiName("PutBucketCORS")
                .serviceName("PUT Bucket CORS")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketCORSOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putCORSAsync(
            PutBucketCORSInput input, ResponseCallBack<PutBucketCORSOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketCORSInput();
        }

        RequestHandler requestHandler = this.putCORSAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html">https://docs.qingcloud.com/qingstor/api/bucket/cors/put_cors.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putCORSAsyncRequest(
            PutBucketCORSInput input, ResponseCallBack<PutBucketCORSOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketCORSInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketCORS")
                .apiName("PutBucketCORS")
                .serviceName("PUT Bucket CORS")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?cors");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketCORSInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field CORSRules Bucket CORS rules <br>
     */
    public static class PutBucketCORSInput extends RequestInputModel {

        /** Bucket CORS rules Required */
        private List<CORSRuleModel> cORSRules;

        public void setCORSRules(List<CORSRuleModel> cORSRules) {
            this.cORSRules = cORSRules;
        }

        @ParamAnnotation(paramType = "element", paramName = "cors_rules")
        public List<CORSRuleModel> getCORSRules() {
            return this.cORSRules;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

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

    /**
     * PutBucketCORSOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field CORSRules Bucket CORS rules <br>
     */
    public static class PutBucketCORSOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketExternalMirrorOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html
     *     </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketExternalMirrorOutput putExternalMirror(PutBucketExternalMirrorInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        RequestHandler requestHandler = this.putExternalMirrorRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketExternalMirrorOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putExternalMirrorRequest(PutBucketExternalMirrorInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketExternalMirror")
                .apiName("PutBucketExternalMirror")
                .serviceName("PUT Bucket External Mirror")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketExternalMirrorOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putExternalMirrorAsync(
            PutBucketExternalMirrorInput input,
            ResponseCallBack<PutBucketExternalMirrorOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        RequestHandler requestHandler = this.putExternalMirrorAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html">https://docs.qingcloud.com/qingstor/api/bucket/external_mirror/put_external_mirror.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putExternalMirrorAsyncRequest(
            PutBucketExternalMirrorInput input,
            ResponseCallBack<PutBucketExternalMirrorOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketExternalMirrorInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketExternalMirror")
                .apiName("PutBucketExternalMirror")
                .serviceName("PUT Bucket External Mirror")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?mirror");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketExternalMirrorInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field SourceSite Source site url <br>
     */
    public static class PutBucketExternalMirrorInput extends RequestInputModel {

        /** Source site url Required */
        private String sourceSite;

        public void setSourceSite(String sourceSite) {
            this.sourceSite = sourceSite;
        }

        @ParamAnnotation(paramType = "element", paramName = "source_site")
        public String getSourceSite() {
            return this.sourceSite;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (QSStringUtil.isEmpty(this.getSourceSite())) {
                return QSStringUtil.getParameterRequired(
                        "SourceSite", "PutBucketExternalMirrorInput");
            }
            return null;
        }
    }

    /**
     * PutBucketExternalMirrorOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field SourceSite Source site url <br>
     */
    public static class PutBucketExternalMirrorOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketLifecycleOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketLifecycleOutput putLifecycle(PutBucketLifecycleInput input) throws QSException {
        if (input == null) {
            input = new PutBucketLifecycleInput();
        }

        RequestHandler requestHandler = this.putLifecycleRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketLifecycleOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putLifecycleRequest(PutBucketLifecycleInput input) throws QSException {
        if (input == null) {
            input = new PutBucketLifecycleInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketLifecycle")
                .apiName("PutBucketLifecycle")
                .serviceName("PUT Bucket Lifecycle")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketLifecycleOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putLifecycleAsync(
            PutBucketLifecycleInput input, ResponseCallBack<PutBucketLifecycleOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketLifecycleInput();
        }

        RequestHandler requestHandler = this.putLifecycleAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html">https://docs.qingcloud.com/qingstor/api/bucket/lifecycle/put_lifecycle.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putLifecycleAsyncRequest(
            PutBucketLifecycleInput input, ResponseCallBack<PutBucketLifecycleOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketLifecycleInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketLifecycle")
                .apiName("PutBucketLifecycle")
                .serviceName("PUT Bucket Lifecycle")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?lifecycle");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketLifecycleInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Rule Bucket Lifecycle rule <br>
     */
    public static class PutBucketLifecycleInput extends RequestInputModel {

        /** Bucket Lifecycle rule Required */
        private List<RuleModel> rule;

        public void setRule(List<RuleModel> rule) {
            this.rule = rule;
        }

        @ParamAnnotation(paramType = "element", paramName = "rule")
        public List<RuleModel> getRule() {
            return this.rule;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (this.getRule() != null && this.getRule().size() > 0) {
                for (int i = 0; i < this.getRule().size(); i++) {
                    String vValidate = this.getRule().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }
            return null;
        }
    }

    /**
     * PutBucketLifecycleOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Rule Bucket Lifecycle rule <br>
     */
    public static class PutBucketLifecycleOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketLoggingOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketLoggingOutput putLogging(PutBucketLoggingInput input) throws QSException {
        if (input == null) {
            input = new PutBucketLoggingInput();
        }

        RequestHandler requestHandler = this.putLoggingRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketLoggingOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putLoggingRequest(PutBucketLoggingInput input) throws QSException {
        if (input == null) {
            input = new PutBucketLoggingInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketLogging")
                .apiName("PutBucketLogging")
                .serviceName("PUT Bucket Logging")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketLoggingOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putLoggingAsync(
            PutBucketLoggingInput input, ResponseCallBack<PutBucketLoggingOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketLoggingInput();
        }

        RequestHandler requestHandler = this.putLoggingAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html">https://docs.qingcloud.com/qingstor/api/bucket/logging/put_logging.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putLoggingAsyncRequest(
            PutBucketLoggingInput input, ResponseCallBack<PutBucketLoggingOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketLoggingInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketLogging")
                .apiName("PutBucketLogging")
                .serviceName("PUT Bucket Logging")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?logging");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketLoggingInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field TargetBucket The name of the bucket used to store logs. The user must be the owner of
     * the bucket. <br>
     * field TargetPrefix generated log files' common prefix <br>
     */
    public static class PutBucketLoggingInput extends RequestInputModel {

        /**
         * The name of the bucket used to store logs. The user must be the owner of the bucket.
         * Required
         */
        private String targetBucket;

        public void setTargetBucket(String targetBucket) {
            this.targetBucket = targetBucket;
        }

        @ParamAnnotation(paramType = "element", paramName = "target_bucket")
        public String getTargetBucket() {
            return this.targetBucket;
        }
        /** generated log files' common prefix Required */
        private String targetPrefix;

        public void setTargetPrefix(String targetPrefix) {
            this.targetPrefix = targetPrefix;
        }

        @ParamAnnotation(paramType = "element", paramName = "target_prefix")
        public String getTargetPrefix() {
            return this.targetPrefix;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (QSStringUtil.isEmpty(this.getTargetBucket())) {
                return QSStringUtil.getParameterRequired("TargetBucket", "PutBucketLoggingInput");
            }
            if (QSStringUtil.isEmpty(this.getTargetPrefix())) {
                return QSStringUtil.getParameterRequired("TargetPrefix", "PutBucketLoggingInput");
            }
            return null;
        }
    }

    /**
     * PutBucketLoggingOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field TargetBucket The name of the bucket used to store logs. The user must be the owner of
     * the bucket. <br>
     * field TargetPrefix generated log files' common prefix <br>
     */
    public static class PutBucketLoggingOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketNotificationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketNotificationOutput putNotification(PutBucketNotificationInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketNotificationInput();
        }

        RequestHandler requestHandler = this.putNotificationRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketNotificationOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putNotificationRequest(PutBucketNotificationInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketNotificationInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketNotification")
                .apiName("PutBucketNotification")
                .serviceName("PUT Bucket Notification")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketNotificationOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putNotificationAsync(
            PutBucketNotificationInput input,
            ResponseCallBack<PutBucketNotificationOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketNotificationInput();
        }

        RequestHandler requestHandler = this.putNotificationAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html">https://docs.qingcloud.com/qingstor/api/bucket/notification/put_notification.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putNotificationAsyncRequest(
            PutBucketNotificationInput input,
            ResponseCallBack<PutBucketNotificationOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketNotificationInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketNotification")
                .apiName("PutBucketNotification")
                .serviceName("PUT Bucket Notification")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?notification");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketNotificationInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Notifications Bucket Notification <br>
     */
    public static class PutBucketNotificationInput extends RequestInputModel {

        /** Bucket Notification Required */
        private List<NotificationModel> notifications;

        public void setNotifications(List<NotificationModel> notifications) {
            this.notifications = notifications;
        }

        @ParamAnnotation(paramType = "element", paramName = "notifications")
        public List<NotificationModel> getNotifications() {
            return this.notifications;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (this.getNotifications() != null && this.getNotifications().size() > 0) {
                for (int i = 0; i < this.getNotifications().size(); i++) {
                    String vValidate = this.getNotifications().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }
            return null;
        }
    }

    /**
     * PutBucketNotificationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Notifications Bucket Notification <br>
     */
    public static class PutBucketNotificationOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketPolicyOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketPolicyOutput putPolicy(PutBucketPolicyInput input) throws QSException {
        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        RequestHandler requestHandler = this.putPolicyRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketPolicyOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putPolicyRequest(PutBucketPolicyInput input) throws QSException {
        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketPolicy")
                .apiName("PutBucketPolicy")
                .serviceName("PUT Bucket Policy")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketPolicyOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putPolicyAsync(
            PutBucketPolicyInput input, ResponseCallBack<PutBucketPolicyOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        RequestHandler requestHandler = this.putPolicyAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html">https://docs.qingcloud.com/qingstor/api/bucket/policy/put_policy.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putPolicyAsyncRequest(
            PutBucketPolicyInput input, ResponseCallBack<PutBucketPolicyOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketPolicyInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketPolicy")
                .apiName("PutBucketPolicy")
                .serviceName("PUT Bucket Policy")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?policy");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketPolicyInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Statement Bucket policy statement <br>
     */
    public static class PutBucketPolicyInput extends RequestInputModel {

        /** Bucket policy statement Required */
        private List<StatementModel> statement;

        public void setStatement(List<StatementModel> statement) {
            this.statement = statement;
        }

        @ParamAnnotation(paramType = "element", paramName = "statement")
        public List<StatementModel> getStatement() {
            return this.statement;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

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

    /**
     * PutBucketPolicyOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Statement Bucket policy statement <br>
     */
    public static class PutBucketPolicyOutput extends OutputModel {}

    /**
     * @param input input
     * @throws QSException exception
     * @return PutBucketReplicationOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html">
     *     https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutBucketReplicationOutput putReplication(PutBucketReplicationInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketReplicationInput();
        }

        RequestHandler requestHandler = this.putReplicationRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutBucketReplicationOutput) backModel;
        }
        return null;
    }

    /**
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putReplicationRequest(PutBucketReplicationInput input)
            throws QSException {
        if (input == null) {
            input = new PutBucketReplicationInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketReplication")
                .apiName("PutBucketReplication")
                .serviceName("PUT Bucket Replication")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutBucketReplicationOutput.class);

        return requestHandler;
    }

    /**
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putReplicationAsync(
            PutBucketReplicationInput input, ResponseCallBack<PutBucketReplicationOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketReplicationInput();
        }

        RequestHandler requestHandler = this.putReplicationAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html">https://docs.qingcloud.com/qingstor/api/bucket/replication/put_replication.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putReplicationAsyncRequest(
            PutBucketReplicationInput input, ResponseCallBack<PutBucketReplicationOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutBucketReplicationInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutBucketReplication")
                .apiName("PutBucketReplication")
                .serviceName("PUT Bucket Replication")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>?replication");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutBucketReplicationInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Rules Bucket Replication rules <br>
     */
    public static class PutBucketReplicationInput extends RequestInputModel {

        /** Bucket Replication rules Required */
        private List<RulesModel> rules;

        public void setRules(List<RulesModel> rules) {
            this.rules = rules;
        }

        @ParamAnnotation(paramType = "element", paramName = "rules")
        public List<RulesModel> getRules() {
            return this.rules;
        }

        /** The request body */
        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        @Override
        public String validateParam() {

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
            }

            if (this.getRules() != null && this.getRules().size() > 0) {
                for (int i = 0; i < this.getRules().size(); i++) {
                    String vValidate = this.getRules().get(i).validateParam();
                    if (!QSStringUtil.isEmpty(vValidate)) {
                        return vValidate;
                    }
                }
            }
            return null;
        }
    }

    /**
     * PutBucketReplicationOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Rules Bucket Replication rules <br>
     */
    public static class PutBucketReplicationOutput extends OutputModel {}

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return AbortMultipartUploadOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html">
     *     https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public AbortMultipartUploadOutput abortMultipartUpload(
            String objectName, AbortMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new AbortMultipartUploadInput();
        }

        RequestHandler requestHandler = this.abortMultipartUploadRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (AbortMultipartUploadOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler abortMultipartUploadRequest(
            String objectName, AbortMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new AbortMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("AbortMultipartUpload")
                .apiName("AbortMultipartUpload")
                .serviceName("Abort Multipart Upload")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, AbortMultipartUploadOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html</a>
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

        RequestHandler requestHandler =
                this.abortMultipartUploadAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/abort_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler abortMultipartUploadAsyncRequest(
            String objectName,
            AbortMultipartUploadInput input,
            ResponseCallBack<AbortMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new AbortMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("AbortMultipartUpload")
                .apiName("AbortMultipartUpload")
                .serviceName("Abort Multipart Upload")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * AbortMultipartUploadInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class AbortMultipartUploadInput extends RequestInputModel {

        /** Object multipart upload ID Required */
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

    /**
     * AbortMultipartUploadOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class AbortMultipartUploadOutput extends OutputModel {}

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return AppendObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/append.html">
     *     https://docs.qingcloud.com/qingstor/api/object/append.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public AppendObjectOutput appendObject(String objectName, AppendObjectInput input)
            throws QSException {
        if (input == null) {
            input = new AppendObjectInput();
        }

        RequestHandler requestHandler = this.appendObjectRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (AppendObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/append.html">https://docs.qingcloud.com/qingstor/api/object/append.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler appendObjectRequest(String objectName, AppendObjectInput input)
            throws QSException {
        if (input == null) {
            input = new AppendObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("AppendObject")
                .apiName("AppendObject")
                .serviceName("Append Object")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>?append");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, AppendObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/append.html">https://docs.qingcloud.com/qingstor/api/object/append.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void appendObjectAsync(
            String objectName,
            AppendObjectInput input,
            ResponseCallBack<AppendObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new AppendObjectInput();
        }

        RequestHandler requestHandler = this.appendObjectAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/append.html">https://docs.qingcloud.com/qingstor/api/object/append.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler appendObjectAsyncRequest(
            String objectName,
            AppendObjectInput input,
            ResponseCallBack<AppendObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new AppendObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("AppendObject")
                .apiName("AppendObject")
                .serviceName("Append Object")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>?append");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * AppendObjectInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentLength Object content size <br>
     * field ContentMD5 Object MD5sum <br>
     * field ContentType Object content type <br>
     * field XQSStorageClass Specify the storage class for object <br>
     * field Position Object append position <br>
     */
    public static class AppendObjectInput extends RequestInputModel {

        /** Object append position Required */
        private Long position;

        public void setPosition(Long position) {
            this.position = position;
        }

        @ParamAnnotation(paramType = "query", paramName = "position")
        public Long getPosition() {
            return this.position;
        }

        /** Object content size Required */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
        /** Object MD5sum */
        private String contentMD5;

        public void setContentMD5(String contentMD5) {
            this.contentMD5 = contentMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-md5")
        public String getContentMD5() {
            return this.contentMD5;
        }
        /** Object content type */
        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-type")
        public String getContentType() {
            return this.contentType;
        }
        /**
         * Specify the storage class for object XQSStorageClass's available values: STANDARD,
         * STANDARD_IA
         */
        private String xQSStorageClass;

        public void setXQSStorageClass(String xQSStorageClass) {
            this.xQSStorageClass = xQSStorageClass;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-storage-class")
        public String getXQSStorageClass() {
            return this.xQSStorageClass;
        }

        /** The request body */
        private File bodyInputFile;

        /**
         * Get the File will be updated.
         *
         * @return the File object will be updated
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFile() {
            return bodyInputFile;
        }

        /**
         * Set the File to update. <br>
         *
         * @param bodyInputFile File to update
         */
        public void setBodyInputFile(File bodyInputFile) {
            this.bodyInputFile = bodyInputFile;
        }

        private InputStream bodyInputStream;

        /**
         * Get the body input stream.
         *
         * @return input stream
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        /**
         * Set the body input stream.
         *
         * @param bodyInputStream input stream to update
         */
        public void setBodyInputStream(InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        @Override
        public String validateParam() {

            String[] xQSStorageClassValidValues = {"STANDARD", "STANDARD_IA"};

            boolean xQSStorageClassIsValid = false;
            String xQSStorageClass = this.getXQSStorageClass();
            if (null == xQSStorageClass || "".equals(xQSStorageClass)) {
                xQSStorageClassIsValid = true;
            } else {
                for (String v : xQSStorageClassValidValues) {
                    if (v.equals(xQSStorageClass)) {
                        xQSStorageClassIsValid = true;
                    }
                }
            }

            if (!xQSStorageClassIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "XQSStorageClass",
                        this.getXQSStorageClass() + "",
                        xQSStorageClassValidValues);
            }

            return null;
        }
    }

    /**
     * AppendObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentLength Object content size <br>
     * field ContentMD5 Object MD5sum <br>
     * field ContentType Object content type <br>
     * field XQSStorageClass Specify the storage class for object <br>
     * field Position Object append position <br>
     */
    public static class AppendObjectOutput extends OutputModel {

        /** next position when append data to this object */
        private Long xQSNextAppendPosition;

        public void setXQSNextAppendPosition(Long xQSNextAppendPosition) {
            this.xQSNextAppendPosition = xQSNextAppendPosition;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-next-append-position")
        public Long getXQSNextAppendPosition() {
            return this.xQSNextAppendPosition;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return CompleteMultipartUploadOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html">
     *     https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public CompleteMultipartUploadOutput completeMultipartUpload(
            String objectName, CompleteMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new CompleteMultipartUploadInput();
        }

        RequestHandler requestHandler = this.completeMultipartUploadRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (CompleteMultipartUploadOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler completeMultipartUploadRequest(
            String objectName, CompleteMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new CompleteMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("CompleteMultipartUpload")
                .apiName("CompleteMultipartUpload")
                .serviceName("Complete multipart upload")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, CompleteMultipartUploadOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html</a>
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

        RequestHandler requestHandler =
                this.completeMultipartUploadAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/complete_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler completeMultipartUploadAsyncRequest(
            String objectName,
            CompleteMultipartUploadInput input,
            ResponseCallBack<CompleteMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new CompleteMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("CompleteMultipartUpload")
                .apiName("CompleteMultipartUpload")
                .serviceName("Complete multipart upload")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * CompleteMultipartUploadInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ETag MD5sum of the object part <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field UploadID Object multipart upload ID <br>
     * field ObjectParts Object parts <br>
     */
    public static class CompleteMultipartUploadInput extends RequestInputModel {

        /** Object multipart upload ID Required */
        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        /** MD5sum of the object part */
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "etag")
        public String getETag() {
            return this.eTag;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        /** Object parts Required */
        private List<ObjectPartModel> objectParts;

        public void setObjectParts(List<ObjectPartModel> objectParts) {
            this.objectParts = objectParts;
        }

        @ParamAnnotation(paramType = "element", paramName = "object_parts")
        public List<ObjectPartModel> getObjectParts() {
            return this.objectParts;
        }

        private String bodyInput;

        @ParamAnnotation(paramType = "body", paramName = "BodyInput")
        public String getBodyInput() {
            return bodyInput;
        }
        /**
         * Set body with raw json string, After setting this field, SDK will give priority to using
         * this field as the payload, at this time you can ignore the settings of other fields.
         *
         * @param bodyInput body payload
         */
        public void setBodyInput(String bodyInput) {
            this.bodyInput = bodyInput;
        }

        public CompleteMultipartUploadInput() {}

        /**
         * The constructor will auto set values of upload id and body input.
         *
         * @param multipart_upload_id upload id
         * @param partsCount count of multi parts
         * @param startIndex start of part number's index
         */
        @Deprecated
        public CompleteMultipartUploadInput(
                String multipart_upload_id, int partsCount, int startIndex) {
            this.setUploadID(multipart_upload_id);
            this.setBodyInput(getCompleteMultipartUploadContent(partsCount, startIndex));
        }

        /**
         * You can get the json content to complete multipart uploading. <br>
         *
         * @param partsCount count of all the uploaded parts
         * @param startIndex start of part number's index
         * @return content to complete multipart uploading
         */
        @Deprecated
        public String getCompleteMultipartUploadContent(int partsCount, int startIndex) {
            if (partsCount < 1 || startIndex < 0) return null;

            StringBuilder uploadJson = new StringBuilder("{\"object_parts\":[");
            for (int i = 0; i < partsCount; i++) {
                uploadJson.append("{\"part_number\":").append(startIndex++).append("}");
                if (i < partsCount - 1) uploadJson.append(",");
                else uploadJson.append("]}");
            }

            return uploadJson.toString();
        }

        @Override
        public String validateParam() {
            if (QSStringUtil.isEmpty(this.getUploadID())) {
                return QSStringUtil.getParameterRequired(
                        "UploadID", "CompleteMultipartUploadInput");
            }

            if (!QSStringUtil.isEmpty(this.getBodyInput())) {
                return null;
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

    /**
     * CompleteMultipartUploadOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ETag MD5sum of the object part <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field UploadID Object multipart upload ID <br>
     * field ObjectParts Object parts <br>
     */
    public static class CompleteMultipartUploadOutput extends OutputModel {

        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /**
     * @param objectName name of the object
     * @throws QSException exception
     * @return DeleteObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/delete.html">
     *     https://docs.qingcloud.com/qingstor/api/object/delete.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DeleteObjectOutput deleteObject(String objectName) throws QSException {
        RequestHandler requestHandler = this.deleteObjectRequest(objectName);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (DeleteObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/delete.html">https://docs.qingcloud.com/qingstor/api/object/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteObjectRequest(String objectName) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteObject")
                .apiName("DeleteObject")
                .serviceName("DELETE Object")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), null, DeleteObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/delete.html">https://docs.qingcloud.com/qingstor/api/object/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void deleteObjectAsync(String objectName, ResponseCallBack<DeleteObjectOutput> callback)
            throws QSException {

        RequestHandler requestHandler = this.deleteObjectAsyncRequest(objectName, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/delete.html">https://docs.qingcloud.com/qingstor/api/object/delete.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler deleteObjectAsyncRequest(
            String objectName, ResponseCallBack<DeleteObjectOutput> callback) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("DeleteObject")
                .apiName("DeleteObject")
                .serviceName("DELETE Object")
                .reqMethod("DELETE")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), null, callback);
        return requestHandler;
    }

    /**
     * DeleteObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     */
    public static class DeleteObjectOutput extends OutputModel {}

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return GetObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/get.html">
     *     https://docs.qingcloud.com/qingstor/api/object/get.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetObjectOutput getObject(String objectName, GetObjectInput input) throws QSException {
        if (input == null) {
            input = new GetObjectInput();
        }

        RequestHandler requestHandler = this.getObjectRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (GetObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/get.html">https://docs.qingcloud.com/qingstor/api/object/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getObjectRequest(String objectName, GetObjectInput input)
            throws QSException {
        if (input == null) {
            input = new GetObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetObject")
                .apiName("GetObject")
                .serviceName("GET Object")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, GetObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/get.html">https://docs.qingcloud.com/qingstor/api/object/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getObjectAsync(
            String objectName, GetObjectInput input, ResponseCallBack<GetObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new GetObjectInput();
        }

        RequestHandler requestHandler = this.getObjectAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/get.html">https://docs.qingcloud.com/qingstor/api/object/get.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler getObjectAsyncRequest(
            String objectName, GetObjectInput input, ResponseCallBack<GetObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new GetObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetObject")
                .apiName("GetObject")
                .serviceName("GET Object")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * GetObjectInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfMatch Check whether the ETag matches <br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field IfNoneMatch Check whether the ETag does not match <br>
     * field IfUnmodifiedSince Check whether the object has not been modified <br>
     * field Range Specified range of the object <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field ResponseCacheControl Specified the Cache-Control response header <br>
     * field ResponseContentDisposition Specified the Content-Disposition response header <br>
     * field ResponseContentEncoding Specified the Content-Encoding response header <br>
     * field ResponseContentLanguage Specified the Content-Language response header <br>
     * field ResponseContentType Specified the Content-Type response header <br>
     * field ResponseExpires Specified the Expires response header <br>
     */
    public static class GetObjectInput extends RequestInputModel {

        /** Specified the Cache-Control response header */
        private String responseCacheControl;

        public void setResponseCacheControl(String responseCacheControl) {
            this.responseCacheControl = responseCacheControl;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-cache-control")
        public String getResponseCacheControl() {
            return this.responseCacheControl;
        }
        /** Specified the Content-Disposition response header */
        private String responseContentDisposition;

        public void setResponseContentDisposition(String responseContentDisposition) {
            this.responseContentDisposition = responseContentDisposition;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-disposition")
        public String getResponseContentDisposition() {
            return this.responseContentDisposition;
        }
        /** Specified the Content-Encoding response header */
        private String responseContentEncoding;

        public void setResponseContentEncoding(String responseContentEncoding) {
            this.responseContentEncoding = responseContentEncoding;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-encoding")
        public String getResponseContentEncoding() {
            return this.responseContentEncoding;
        }
        /** Specified the Content-Language response header */
        private String responseContentLanguage;

        public void setResponseContentLanguage(String responseContentLanguage) {
            this.responseContentLanguage = responseContentLanguage;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-language")
        public String getResponseContentLanguage() {
            return this.responseContentLanguage;
        }
        /** Specified the Content-Type response header */
        private String responseContentType;

        public void setResponseContentType(String responseContentType) {
            this.responseContentType = responseContentType;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-type")
        public String getResponseContentType() {
            return this.responseContentType;
        }
        /** Specified the Expires response header */
        private String responseExpires;

        public void setResponseExpires(String responseExpires) {
            this.responseExpires = responseExpires;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-expires")
        public String getResponseExpires() {
            return this.responseExpires;
        }

        /** Check whether the ETag matches */
        private String ifMatch;

        public void setIfMatch(String ifMatch) {
            this.ifMatch = ifMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-match")
        public String getIfMatch() {
            return this.ifMatch;
        }
        /** Check whether the object has been modified */
        private String ifModifiedSince;

        public void setIfModifiedSince(String ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-modified-since")
        public String getIfModifiedSince() {
            return this.ifModifiedSince;
        }
        /** Check whether the ETag does not match */
        private String ifNoneMatch;

        public void setIfNoneMatch(String ifNoneMatch) {
            this.ifNoneMatch = ifNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-none-match")
        public String getIfNoneMatch() {
            return this.ifNoneMatch;
        }
        /** Check whether the object has not been modified */
        private String ifUnmodifiedSince;

        public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
            this.ifUnmodifiedSince = ifUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-unmodified-since")
        public String getIfUnmodifiedSince() {
            return this.ifUnmodifiedSince;
        }
        /** Specified range of the object */
        private String range;

        public void setRange(String range) {
            this.range = range;
        }

        @ParamAnnotation(paramType = "header", paramName = "range")
        public String getRange() {
            return this.range;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    /**
     * GetObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfMatch Check whether the ETag matches <br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field IfNoneMatch Check whether the ETag does not match <br>
     * field IfUnmodifiedSince Check whether the object has not been modified <br>
     * field Range Specified range of the object <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field ResponseCacheControl Specified the Cache-Control response header <br>
     * field ResponseContentDisposition Specified the Content-Disposition response header <br>
     * field ResponseContentEncoding Specified the Content-Encoding response header <br>
     * field ResponseContentLanguage Specified the Content-Language response header <br>
     * field ResponseContentType Specified the Content-Type response header <br>
     * field ResponseExpires Specified the Expires response header <br>
     */
    public static class GetObjectOutput extends OutputModel {

        /** The response body */

        /**
         * deprecated, please use setCacheControl(String cacheControl)
         *
         * @param responseCacheControl cacheControl
         */
        @Deprecated
        public void setResponseCacheControl(String responseCacheControl) {
            cacheControl = responseCacheControl;
        }

        /**
         * deprecated, please use getCacheControl()
         *
         * @return cacheControl
         */
        @Deprecated
        public String getResponseCacheControl() {
            return cacheControl;
        } // Specified the Content-Disposition response header

        /**
         * deprecated, please use setContentDisposition(String contentDisposition)
         *
         * @param responseContentDisposition contentDisposition
         */
        @Deprecated
        public void setResponseContentDisposition(String responseContentDisposition) {
            contentDisposition = responseContentDisposition;
        }

        /**
         * deprecated, please use getContentDisposition()
         *
         * @return contentDisposition
         */
        @Deprecated
        public String getResponseContentDisposition() {
            return contentDisposition;
        } // Specified the Content-Disposition header

        /**
         * deprecated, please use setContentEncoding(String contentEncoding)
         *
         * @param responseContentEncoding contentEncoding
         */
        @Deprecated
        public void setResponseContentEncoding(String responseContentEncoding) {
            contentEncoding = responseContentEncoding;
        }

        /**
         * deprecated, please use getContentEncoding()
         *
         * @return contentEncoding
         */
        @Deprecated
        public String getResponseContentEncoding() {
            return contentEncoding;
        } // Specified the Content-Language response header

        /**
         * deprecated, please use setContentLanguage(String contentLanguage)
         *
         * @param responseContentLanguage contentLanguage
         */
        @Deprecated
        public void setResponseContentLanguage(String responseContentLanguage) {
            contentLanguage = responseContentLanguage;
        }

        /**
         * deprecated, please use getContentLanguage()
         *
         * @return contentLanguage
         */
        @Deprecated
        public String getResponseContentLanguage() {
            return contentLanguage;
        } // Specified the Content-Type response header

        /**
         * deprecated, please use setContentType(String contentType)
         *
         * @param responseContentType contentType
         */
        @Deprecated
        public void setResponseContentType(String responseContentType) {
            contentType = responseContentType;
        }

        /**
         * deprecated, please use getContentType()
         *
         * @return contentType
         */
        @Deprecated
        public String getResponseContentType() {
            return contentType;
        } // Specified the Expires response header

        /**
         * deprecated, please use setExpires(String expires)
         *
         * @param responseExpires expires
         */
        @Deprecated
        public void setResponseExpires(String responseExpires) {
            expires = responseExpires;
        }

        /**
         * deprecated, please use getExpires()
         *
         * @return expires
         */
        @Deprecated
        public String getResponseExpires() {
            return expires;
        }

        private InputStream bodyInputStream;

        /**
         * Get the stream will be downloaded.
         *
         * @return the stream will be downloaded
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        /**
         * Set the stream to download.
         *
         * @param bodyInputStream stream to download
         */
        public void setBodyInputStream(InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        /**
         * The Cache-Control general-header field is used to specify directives for caching
         * mechanisms in both requests and responses.
         */
        private String cacheControl;

        public void setCacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
        }

        @ParamAnnotation(paramType = "header", paramName = "cache-control")
        public String getCacheControl() {
            return this.cacheControl;
        }
        /**
         * In a multipart/form-data body, the HTTP Content-Disposition general header is a header
         * that can be used on the subpart of a multipart body to give information about the field
         * it applies to.
         */
        private String contentDisposition;

        public void setContentDisposition(String contentDisposition) {
            this.contentDisposition = contentDisposition;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-disposition")
        public String getContentDisposition() {
            return this.contentDisposition;
        }
        /** The Content-Encoding entity header is used to compress the media-type. */
        private String contentEncoding;

        public void setContentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-encoding")
        public String getContentEncoding() {
            return this.contentEncoding;
        }
        /**
         * The Content-Language entity header is used to describe the language(s) intended for the
         * audience.
         */
        private String contentLanguage;

        public void setContentLanguage(String contentLanguage) {
            this.contentLanguage = contentLanguage;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-language")
        public String getContentLanguage() {
            return this.contentLanguage;
        }
        /** Object content length */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
        /** Range of response data content */
        private String contentRange;

        public void setContentRange(String contentRange) {
            this.contentRange = contentRange;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-range")
        public String getContentRange() {
            return this.contentRange;
        }
        /** The Content-Type entity header is used to indicate the media type of the resource. */
        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-type")
        public String getContentType() {
            return this.contentType;
        }
        /** MD5sum of the object */
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "etag")
        public String getETag() {
            return this.eTag;
        }
        /**
         * The Expires header contains the date/time after which the response is considered stale.
         */
        private String expires;

        public void setExpires(String expires) {
            this.expires = expires;
        }

        @ParamAnnotation(paramType = "header", paramName = "expires")
        public String getExpires() {
            return this.expires;
        }
        /** */
        private String lastModified;

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        @ParamAnnotation(paramType = "header", paramName = "last-modified")
        public String getLastModified() {
            return this.lastModified;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** User-defined metadata */
        private Map<String, String> xQSMetaData;

        public void setXQSMetaData(Map<String, String> xQSMetaData) {
            this.xQSMetaData = xQSMetaData;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata")
        public Map<String, String> getXQSMetaData() {
            return this.xQSMetaData;
        }
        /** Storage class of the object */
        private String xQSStorageClass;

        public void setXQSStorageClass(String xQSStorageClass) {
            this.xQSStorageClass = xQSStorageClass;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-storage-class")
        public String getXQSStorageClass() {
            return this.xQSStorageClass;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return HeadObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/head.html">
     *     https://docs.qingcloud.com/qingstor/api/object/head.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HeadObjectOutput headObject(String objectName, HeadObjectInput input)
            throws QSException {
        if (input == null) {
            input = new HeadObjectInput();
        }

        RequestHandler requestHandler = this.headObjectRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (HeadObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/head.html">https://docs.qingcloud.com/qingstor/api/object/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler headObjectRequest(String objectName, HeadObjectInput input)
            throws QSException {
        if (input == null) {
            input = new HeadObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("HeadObject")
                .apiName("HeadObject")
                .serviceName("HEAD Object")
                .reqMethod("HEAD")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, HeadObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/head.html">https://docs.qingcloud.com/qingstor/api/object/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void headObjectAsync(
            String objectName, HeadObjectInput input, ResponseCallBack<HeadObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new HeadObjectInput();
        }

        RequestHandler requestHandler = this.headObjectAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/head.html">https://docs.qingcloud.com/qingstor/api/object/head.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler headObjectAsyncRequest(
            String objectName, HeadObjectInput input, ResponseCallBack<HeadObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new HeadObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("HeadObject")
                .apiName("HeadObject")
                .serviceName("HEAD Object")
                .reqMethod("HEAD")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * HeadObjectInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfMatch Check whether the ETag matches <br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field IfNoneMatch Check whether the ETag does not match <br>
     * field IfUnmodifiedSince Check whether the object has not been modified <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     */
    public static class HeadObjectInput extends RequestInputModel {

        /** Check whether the ETag matches */
        private String ifMatch;

        public void setIfMatch(String ifMatch) {
            this.ifMatch = ifMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-match")
        public String getIfMatch() {
            return this.ifMatch;
        }
        /** Check whether the object has been modified */
        private String ifModifiedSince;

        public void setIfModifiedSince(String ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-modified-since")
        public String getIfModifiedSince() {
            return this.ifModifiedSince;
        }
        /** Check whether the ETag does not match */
        private String ifNoneMatch;

        public void setIfNoneMatch(String ifNoneMatch) {
            this.ifNoneMatch = ifNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-none-match")
        public String getIfNoneMatch() {
            return this.ifNoneMatch;
        }
        /** Check whether the object has not been modified */
        private String ifUnmodifiedSince;

        public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
            this.ifUnmodifiedSince = ifUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-unmodified-since")
        public String getIfUnmodifiedSince() {
            return this.ifUnmodifiedSince;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    /**
     * HeadObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfMatch Check whether the ETag matches <br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field IfNoneMatch Check whether the ETag does not match <br>
     * field IfUnmodifiedSince Check whether the object has not been modified <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     */
    public static class HeadObjectOutput extends OutputModel {

        /** Object content length */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
        /** Object content type */
        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-type")
        public String getContentType() {
            return this.contentType;
        }
        /** MD5sum of the object */
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "etag")
        public String getETag() {
            return this.eTag;
        }
        /** */
        private String lastModified;

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        @ParamAnnotation(paramType = "header", paramName = "last-modified")
        public String getLastModified() {
            return this.lastModified;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** User-defined metadata */
        private Map<String, String> xQSMetaData;

        public void setXQSMetaData(Map<String, String> xQSMetaData) {
            this.xQSMetaData = xQSMetaData;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata")
        public Map<String, String> getXQSMetaData() {
            return this.xQSMetaData;
        }
        /**
         * Next position when append data to this object, only returns when object type is
         * appendable
         */
        private Long xQSNextAppendPosition;

        public void setXQSNextAppendPosition(Long xQSNextAppendPosition) {
            this.xQSNextAppendPosition = xQSNextAppendPosition;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-next-append-position")
        public Long getXQSNextAppendPosition() {
            return this.xQSNextAppendPosition;
        }
        /** Object type of this object, only returns when object type is appendable */
        private String xQSObjectType;

        public void setXQSObjectType(String xQSObjectType) {
            this.xQSObjectType = xQSObjectType;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-object-type")
        public String getXQSObjectType() {
            return this.xQSObjectType;
        }
        /** Storage class of the object */
        private String xQSStorageClass;

        public void setXQSStorageClass(String xQSStorageClass) {
            this.xQSStorageClass = xQSStorageClass;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-storage-class")
        public String getXQSStorageClass() {
            return this.xQSStorageClass;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return ImageProcessOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/data_process/image_process/index.html">
     *     https://docs.qingcloud.com/qingstor/data_process/image_process/index.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ImageProcessOutput imageProcess(String objectName, ImageProcessInput input)
            throws QSException {
        if (input == null) {
            input = new ImageProcessInput();
        }

        RequestHandler requestHandler = this.imageProcessRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (ImageProcessOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/data_process/image_process/index.html">https://docs.qingcloud.com/qingstor/data_process/image_process/index.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler imageProcessRequest(String objectName, ImageProcessInput input)
            throws QSException {
        if (input == null) {
            input = new ImageProcessInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ImageProcess")
                .apiName("ImageProcess")
                .serviceName("Image Process")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>?image");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, ImageProcessOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/data_process/image_process/index.html">https://docs.qingcloud.com/qingstor/data_process/image_process/index.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void imageProcessAsync(
            String objectName,
            ImageProcessInput input,
            ResponseCallBack<ImageProcessOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ImageProcessInput();
        }

        RequestHandler requestHandler = this.imageProcessAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/data_process/image_process/index.html">https://docs.qingcloud.com/qingstor/data_process/image_process/index.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler imageProcessAsyncRequest(
            String objectName,
            ImageProcessInput input,
            ResponseCallBack<ImageProcessOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ImageProcessInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ImageProcess")
                .apiName("ImageProcess")
                .serviceName("Image Process")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>?image");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * ImageProcessInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field Action Image process action <br>
     * field ResponseCacheControl Specified the Cache-Control response header <br>
     * field ResponseContentDisposition Specified the Content-Disposition response header <br>
     * field ResponseContentEncoding Specified the Content-Encoding response header <br>
     * field ResponseContentLanguage Specified the Content-Language response header <br>
     * field ResponseContentType Specified the Content-Type response header <br>
     * field ResponseExpires Specified the Expires response header <br>
     */
    public static class ImageProcessInput extends RequestInputModel {

        /** Image process action Required */
        private String action;

        public void setAction(String action) {
            this.action = action;
        }

        @ParamAnnotation(paramType = "query", paramName = "action")
        public String getAction() {
            return this.action;
        }
        /** Specified the Cache-Control response header */
        private String responseCacheControl;

        public void setResponseCacheControl(String responseCacheControl) {
            this.responseCacheControl = responseCacheControl;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-cache-control")
        public String getResponseCacheControl() {
            return this.responseCacheControl;
        }
        /** Specified the Content-Disposition response header */
        private String responseContentDisposition;

        public void setResponseContentDisposition(String responseContentDisposition) {
            this.responseContentDisposition = responseContentDisposition;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-disposition")
        public String getResponseContentDisposition() {
            return this.responseContentDisposition;
        }
        /** Specified the Content-Encoding response header */
        private String responseContentEncoding;

        public void setResponseContentEncoding(String responseContentEncoding) {
            this.responseContentEncoding = responseContentEncoding;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-encoding")
        public String getResponseContentEncoding() {
            return this.responseContentEncoding;
        }
        /** Specified the Content-Language response header */
        private String responseContentLanguage;

        public void setResponseContentLanguage(String responseContentLanguage) {
            this.responseContentLanguage = responseContentLanguage;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-language")
        public String getResponseContentLanguage() {
            return this.responseContentLanguage;
        }
        /** Specified the Content-Type response header */
        private String responseContentType;

        public void setResponseContentType(String responseContentType) {
            this.responseContentType = responseContentType;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-content-type")
        public String getResponseContentType() {
            return this.responseContentType;
        }
        /** Specified the Expires response header */
        private String responseExpires;

        public void setResponseExpires(String responseExpires) {
            this.responseExpires = responseExpires;
        }

        @ParamAnnotation(paramType = "query", paramName = "response-expires")
        public String getResponseExpires() {
            return this.responseExpires;
        }

        /** Check whether the object has been modified */
        private String ifModifiedSince;

        public void setIfModifiedSince(String ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "if-modified-since")
        public String getIfModifiedSince() {
            return this.ifModifiedSince;
        }

        @Override
        public String validateParam() {
            if (QSStringUtil.isEmpty(this.getAction())) {
                return QSStringUtil.getParameterRequired("Action", "ImageProcessInput");
            }

            return null;
        }
    }

    /**
     * ImageProcessOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field IfModifiedSince Check whether the object has been modified <br>
     * field Action Image process action <br>
     * field ResponseCacheControl Specified the Cache-Control response header <br>
     * field ResponseContentDisposition Specified the Content-Disposition response header <br>
     * field ResponseContentEncoding Specified the Content-Encoding response header <br>
     * field ResponseContentLanguage Specified the Content-Language response header <br>
     * field ResponseContentType Specified the Content-Type response header <br>
     * field ResponseExpires Specified the Expires response header <br>
     */
    public static class ImageProcessOutput extends OutputModel {

        /** The response body */
        private InputStream bodyInputStream;

        /**
         * Get the stream will be downloaded.
         *
         * @return the stream will be downloaded
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        /**
         * Set the stream to download.
         *
         * @param bodyInputStream stream to download
         */
        public void setBodyInputStream(InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        /** Object content length */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return InitiateMultipartUploadOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html">
     *     https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public InitiateMultipartUploadOutput initiateMultipartUpload(
            String objectName, InitiateMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new InitiateMultipartUploadInput();
        }

        RequestHandler requestHandler = this.initiateMultipartUploadRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (InitiateMultipartUploadOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler initiateMultipartUploadRequest(
            String objectName, InitiateMultipartUploadInput input) throws QSException {
        if (input == null) {
            input = new InitiateMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("InitiateMultipartUpload")
                .apiName("InitiateMultipartUpload")
                .serviceName("Initiate Multipart Upload")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>?uploads");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, InitiateMultipartUploadOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html</a>
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

        RequestHandler requestHandler =
                this.initiateMultipartUploadAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html">https://docs.qingcloud.com/qingstor/api/object/initiate_multipart_upload.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler initiateMultipartUploadAsyncRequest(
            String objectName,
            InitiateMultipartUploadInput input,
            ResponseCallBack<InitiateMultipartUploadOutput> callback)
            throws QSException {
        if (input == null) {
            input = new InitiateMultipartUploadInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("InitiateMultipartUpload")
                .apiName("InitiateMultipartUpload")
                .serviceName("Initiate Multipart Upload")
                .reqMethod("POST")
                .subSourcePath("/<bucket-name>/<object-key>?uploads");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * InitiateMultipartUploadInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentType Object content type <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSMetaData User-defined metadata <br>
     * field XQSStorageClass Specify the storage class for object <br>
     */
    public static class InitiateMultipartUploadInput extends RequestInputModel {

        /** Object content type */
        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-type")
        public String getContentType() {
            return this.contentType;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }
        /** User-defined metadata */
        private Map<String, String> xQSMetaData;

        public void setXQSMetaData(Map<String, String> xQSMetaData) {
            this.xQSMetaData = xQSMetaData;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata")
        public Map<String, String> getXQSMetaData() {
            return this.xQSMetaData;
        }
        /**
         * Specify the storage class for object XQSStorageClass's available values: STANDARD,
         * STANDARD_IA
         */
        private String xQSStorageClass;

        public void setXQSStorageClass(String xQSStorageClass) {
            this.xQSStorageClass = xQSStorageClass;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-storage-class")
        public String getXQSStorageClass() {
            return this.xQSStorageClass;
        }

        @Override
        public String validateParam() {

            Map<String, String> metadata = this.getXQSMetaData();
            if (metadata != null) {
                String vValidate = QSParamInvokeUtil.metadataIsValid(metadata);
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            String[] xQSStorageClassValidValues = {"STANDARD", "STANDARD_IA"};

            boolean xQSStorageClassIsValid = false;
            String xQSStorageClass = this.getXQSStorageClass();
            if (null == xQSStorageClass || "".equals(xQSStorageClass)) {
                xQSStorageClassIsValid = true;
            } else {
                for (String v : xQSStorageClassValidValues) {
                    if (v.equals(xQSStorageClass)) {
                        xQSStorageClassIsValid = true;
                    }
                }
            }

            if (!xQSStorageClassIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "XQSStorageClass",
                        this.getXQSStorageClass() + "",
                        xQSStorageClassValidValues);
            }

            return null;
        }
    }

    /**
     * InitiateMultipartUploadOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentType Object content type <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSMetaData User-defined metadata <br>
     * field XQSStorageClass Specify the storage class for object <br>
     */
    public static class InitiateMultipartUploadOutput extends OutputModel {

        /** Bucket name */
        private String bucket;

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        @ParamAnnotation(paramType = "element", paramName = "bucket")
        public String getBucket() {
            return this.bucket;
        }
        /** Object key */
        private String key;

        public void setKey(String key) {
            this.key = key;
        }

        @ParamAnnotation(paramType = "element", paramName = "key")
        public String getKey() {
            return this.key;
        }
        /** Object multipart upload ID */
        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "element", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return ListMultipartOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/list_multipart.html">
     *     https://docs.qingcloud.com/qingstor/api/object/list_multipart.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListMultipartOutput listMultipart(String objectName, ListMultipartInput input)
            throws QSException {
        if (input == null) {
            input = new ListMultipartInput();
        }

        RequestHandler requestHandler = this.listMultipartRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (ListMultipartOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/list_multipart.html">https://docs.qingcloud.com/qingstor/api/object/list_multipart.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listMultipartRequest(String objectName, ListMultipartInput input)
            throws QSException {
        if (input == null) {
            input = new ListMultipartInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListMultipart")
                .apiName("ListMultipart")
                .serviceName("List Multipart")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, ListMultipartOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/list_multipart.html">https://docs.qingcloud.com/qingstor/api/object/list_multipart.html</a>
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

        RequestHandler requestHandler = this.listMultipartAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/list_multipart.html">https://docs.qingcloud.com/qingstor/api/object/list_multipart.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listMultipartAsyncRequest(
            String objectName,
            ListMultipartInput input,
            ResponseCallBack<ListMultipartOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListMultipartInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ListMultipart")
                .apiName("ListMultipart")
                .serviceName("List Multipart")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * ListMultipartInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Limit Limit results count <br>
     * field PartNumberMarker Object multipart upload part number <br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class ListMultipartInput extends RequestInputModel {

        /** Limit results count */
        private Integer limit;

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        @ParamAnnotation(paramType = "query", paramName = "limit")
        public Integer getLimit() {
            return this.limit;
        }
        /** Object multipart upload part number */
        private Integer partNumberMarker;

        public void setPartNumberMarker(Integer partNumberMarker) {
            this.partNumberMarker = partNumberMarker;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number_marker")
        public Integer getPartNumberMarker() {
            return this.partNumberMarker;
        }
        /** Object multipart upload ID Required */
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

    /**
     * ListMultipartOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field Limit Limit results count <br>
     * field PartNumberMarker Object multipart upload part number <br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class ListMultipartOutput extends OutputModel {

        /** Object multipart count */
        private Integer count;

        public void setCount(Integer count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "element", paramName = "count")
        public Integer getCount() {
            return this.count;
        }
        /** Object parts */
        private List<ObjectPartModel> objectParts;

        public void setObjectParts(List<ObjectPartModel> objectParts) {
            this.objectParts = objectParts;
        }

        @ParamAnnotation(paramType = "element", paramName = "object_parts")
        public List<ObjectPartModel> getObjectParts() {
            return this.objectParts;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return OptionsObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/options.html">
     *     https://docs.qingcloud.com/qingstor/api/object/options.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OptionsObjectOutput optionsObject(String objectName, OptionsObjectInput input)
            throws QSException {
        if (input == null) {
            input = new OptionsObjectInput();
        }

        RequestHandler requestHandler = this.optionsObjectRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (OptionsObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/options.html">https://docs.qingcloud.com/qingstor/api/object/options.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler optionsObjectRequest(String objectName, OptionsObjectInput input)
            throws QSException {
        if (input == null) {
            input = new OptionsObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("OptionsObject")
                .apiName("OptionsObject")
                .serviceName("OPTIONS Object")
                .reqMethod("OPTIONS")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, OptionsObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/options.html">https://docs.qingcloud.com/qingstor/api/object/options.html</a>
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

        RequestHandler requestHandler = this.optionsObjectAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/options.html">https://docs.qingcloud.com/qingstor/api/object/options.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler optionsObjectAsyncRequest(
            String objectName,
            OptionsObjectInput input,
            ResponseCallBack<OptionsObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new OptionsObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("OptionsObject")
                .apiName("OptionsObject")
                .serviceName("OPTIONS Object")
                .reqMethod("OPTIONS")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * OptionsObjectInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field AccessControlRequestHeaders Request headers <br>
     * field AccessControlRequestMethod Request method <br>
     * field Origin Request origin <br>
     */
    public static class OptionsObjectInput extends RequestInputModel {

        /** Request headers */
        private String accessControlRequestHeaders;

        public void setAccessControlRequestHeaders(String accessControlRequestHeaders) {
            this.accessControlRequestHeaders = accessControlRequestHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-request-headers")
        public String getAccessControlRequestHeaders() {
            return this.accessControlRequestHeaders;
        }
        /** Request method Required */
        private String accessControlRequestMethod;

        public void setAccessControlRequestMethod(String accessControlRequestMethod) {
            this.accessControlRequestMethod = accessControlRequestMethod;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-request-method")
        public String getAccessControlRequestMethod() {
            return this.accessControlRequestMethod;
        }
        /** Request origin Required */
        private String origin;

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        @ParamAnnotation(paramType = "header", paramName = "origin")
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

    /**
     * OptionsObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field AccessControlRequestHeaders Request headers <br>
     * field AccessControlRequestMethod Request method <br>
     * field Origin Request origin <br>
     */
    public static class OptionsObjectOutput extends OutputModel {

        /** Allowed headers */
        private String accessControlAllowHeaders;

        public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
            this.accessControlAllowHeaders = accessControlAllowHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-allow-headers")
        public String getAccessControlAllowHeaders() {
            return this.accessControlAllowHeaders;
        }
        /** Allowed methods */
        private String accessControlAllowMethods;

        public void setAccessControlAllowMethods(String accessControlAllowMethods) {
            this.accessControlAllowMethods = accessControlAllowMethods;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-allow-methods")
        public String getAccessControlAllowMethods() {
            return this.accessControlAllowMethods;
        }
        /** Allowed origin */
        private String accessControlAllowOrigin;

        public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-allow-origin")
        public String getAccessControlAllowOrigin() {
            return this.accessControlAllowOrigin;
        }
        /** Expose headers */
        private String accessControlExposeHeaders;

        public void setAccessControlExposeHeaders(String accessControlExposeHeaders) {
            this.accessControlExposeHeaders = accessControlExposeHeaders;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-expose-headers")
        public String getAccessControlExposeHeaders() {
            return this.accessControlExposeHeaders;
        }
        /** Max age */
        private String accessControlMaxAge;

        public void setAccessControlMaxAge(String accessControlMaxAge) {
            this.accessControlMaxAge = accessControlMaxAge;
        }

        @ParamAnnotation(paramType = "header", paramName = "access-control-max-age")
        public String getAccessControlMaxAge() {
            return this.accessControlMaxAge;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return PutObjectOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/put.html">
     *     https://docs.qingcloud.com/qingstor/api/object/put.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PutObjectOutput putObject(String objectName, PutObjectInput input) throws QSException {
        if (input == null) {
            input = new PutObjectInput();
        }

        RequestHandler requestHandler = this.putObjectRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (PutObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/put.html">https://docs.qingcloud.com/qingstor/api/object/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putObjectRequest(String objectName, PutObjectInput input)
            throws QSException {
        if (input == null) {
            input = new PutObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutObject")
                .apiName("PutObject")
                .serviceName("PUT Object")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, PutObjectOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/put.html">https://docs.qingcloud.com/qingstor/api/object/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putObjectAsync(
            String objectName, PutObjectInput input, ResponseCallBack<PutObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutObjectInput();
        }

        RequestHandler requestHandler = this.putObjectAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/put.html">https://docs.qingcloud.com/qingstor/api/object/put.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler putObjectAsyncRequest(
            String objectName, PutObjectInput input, ResponseCallBack<PutObjectOutput> callback)
            throws QSException {
        if (input == null) {
            input = new PutObjectInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("PutObject")
                .apiName("PutObject")
                .serviceName("PUT Object")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * PutObjectInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field CacheControl Object cache control <br>
     * field ContentEncoding Object content encoding <br>
     * field ContentLength Object content size <br>
     * field ContentMD5 Object MD5sum <br>
     * field ContentType Object content type <br>
     * field Expect Used to indicate that particular server behaviors are required by the client
     * <br>
     * field XQSCopySource Copy source, format (/'bucket-name'/'object-key') <br>
     * field XQSCopySourceEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSCopySourceEncryptionCustomerKey Encryption key of the object <br>
     * field XQSCopySourceEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSCopySourceIfMatch Check whether the copy source matches <br>
     * field XQSCopySourceIfModifiedSince Check whether the copy source has been modified <br>
     * field XQSCopySourceIfNoneMatch Check whether the copy source does not match <br>
     * field XQSCopySourceIfUnmodifiedSince Check whether the copy source has not been modified <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSFetchIfUnmodifiedSince Check whether fetch target object has not been modified <br>
     * field XQSFetchSource Fetch source, should be a valid url <br>
     * field XQSMetaData User-defined metadata <br>
     * field XQSMetadataDirective Use for modified metadata, valid (COPY/REPLACE) <br>
     * field XQSMoveSource Move source, format (/'bucket-name'/'object-key') <br>
     * field XQSStorageClass Specify the storage class for object <br>
     */
    public static class PutObjectInput extends RequestInputModel {

        /** Object cache control */
        private String cacheControl;

        public void setCacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
        }

        @ParamAnnotation(paramType = "header", paramName = "cache-control")
        public String getCacheControl() {
            return this.cacheControl;
        }
        /** Object content encoding */
        private String contentEncoding;

        public void setContentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-encoding")
        public String getContentEncoding() {
            return this.contentEncoding;
        }
        /** Object content size Required */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
        /** Object MD5sum */
        private String contentMD5;

        public void setContentMD5(String contentMD5) {
            this.contentMD5 = contentMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-md5")
        public String getContentMD5() {
            return this.contentMD5;
        }
        /** Object content type */
        private String contentType;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-type")
        public String getContentType() {
            return this.contentType;
        }
        /** Used to indicate that particular server behaviors are required by the client */
        private String expect;

        public void setExpect(String expect) {
            this.expect = expect;
        }

        @ParamAnnotation(paramType = "header", paramName = "expect")
        public String getExpect() {
            return this.expect;
        }
        /** Copy source, format (/<bucket-name>/<object-key>) */
        private String xQSCopySource;

        public void setXQSCopySource(String xQSCopySource) {
            this.xQSCopySource = xQSCopySource;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source")
        public String getXQSCopySource() {
            return this.xQSCopySource;
        }
        /** Encryption algorithm of the object */
        private String xQSCopySourceEncryptionCustomerAlgorithm;

        public void setXQSCopySourceEncryptionCustomerAlgorithm(
                String xQSCopySourceEncryptionCustomerAlgorithm) {
            this.xQSCopySourceEncryptionCustomerAlgorithm =
                    xQSCopySourceEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-algorithm")
        public String getXQSCopySourceEncryptionCustomerAlgorithm() {
            return this.xQSCopySourceEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSCopySourceEncryptionCustomerKey;

        public void setXQSCopySourceEncryptionCustomerKey(
                String xQSCopySourceEncryptionCustomerKey) {
            this.xQSCopySourceEncryptionCustomerKey = xQSCopySourceEncryptionCustomerKey;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-key")
        public String getXQSCopySourceEncryptionCustomerKey() {
            return this.xQSCopySourceEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSCopySourceEncryptionCustomerKeyMD5;

        public void setXQSCopySourceEncryptionCustomerKeyMD5(
                String xQSCopySourceEncryptionCustomerKeyMD5) {
            this.xQSCopySourceEncryptionCustomerKeyMD5 = xQSCopySourceEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-key-md5")
        public String getXQSCopySourceEncryptionCustomerKeyMD5() {
            return this.xQSCopySourceEncryptionCustomerKeyMD5;
        }
        /** Check whether the copy source matches */
        private String xQSCopySourceIfMatch;

        public void setXQSCopySourceIfMatch(String xQSCopySourceIfMatch) {
            this.xQSCopySourceIfMatch = xQSCopySourceIfMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-match")
        public String getXQSCopySourceIfMatch() {
            return this.xQSCopySourceIfMatch;
        }
        /** Check whether the copy source has been modified */
        private String xQSCopySourceIfModifiedSince;

        public void setXQSCopySourceIfModifiedSince(String xQSCopySourceIfModifiedSince) {
            this.xQSCopySourceIfModifiedSince = xQSCopySourceIfModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-modified-since")
        public String getXQSCopySourceIfModifiedSince() {
            return this.xQSCopySourceIfModifiedSince;
        }
        /** Check whether the copy source does not match */
        private String xQSCopySourceIfNoneMatch;

        public void setXQSCopySourceIfNoneMatch(String xQSCopySourceIfNoneMatch) {
            this.xQSCopySourceIfNoneMatch = xQSCopySourceIfNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-none-match")
        public String getXQSCopySourceIfNoneMatch() {
            return this.xQSCopySourceIfNoneMatch;
        }
        /** Check whether the copy source has not been modified */
        private String xQSCopySourceIfUnmodifiedSince;

        public void setXQSCopySourceIfUnmodifiedSince(String xQSCopySourceIfUnmodifiedSince) {
            this.xQSCopySourceIfUnmodifiedSince = xQSCopySourceIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-unmodified-since")
        public String getXQSCopySourceIfUnmodifiedSince() {
            return this.xQSCopySourceIfUnmodifiedSince;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }
        /** Check whether fetch target object has not been modified */
        private String xQSFetchIfUnmodifiedSince;

        public void setXQSFetchIfUnmodifiedSince(String xQSFetchIfUnmodifiedSince) {
            this.xQSFetchIfUnmodifiedSince = xQSFetchIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-fetch-if-unmodified-since")
        public String getXQSFetchIfUnmodifiedSince() {
            return this.xQSFetchIfUnmodifiedSince;
        }
        /** Fetch source, should be a valid url */
        private String xQSFetchSource;

        public void setXQSFetchSource(String xQSFetchSource) {
            this.xQSFetchSource = xQSFetchSource;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-fetch-source")
        public String getXQSFetchSource() {
            return this.xQSFetchSource;
        }
        /** User-defined metadata */
        private Map<String, String> xQSMetaData;

        public void setXQSMetaData(Map<String, String> xQSMetaData) {
            this.xQSMetaData = xQSMetaData;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata")
        public Map<String, String> getXQSMetaData() {
            return this.xQSMetaData;
        }
        /**
         * Use for modified metadata, valid (COPY/REPLACE) XQSMetadataDirective's available values:
         * COPY, REPLACE
         */
        private String xQSMetadataDirective;

        public void setXQSMetadataDirective(String xQSMetadataDirective) {
            this.xQSMetadataDirective = xQSMetadataDirective;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-metadata-directive")
        public String getXQSMetadataDirective() {
            return this.xQSMetadataDirective;
        }
        /** Move source, format (/<bucket-name>/<object-key>) */
        private String xQSMoveSource;

        public void setXQSMoveSource(String xQSMoveSource) {
            this.xQSMoveSource = xQSMoveSource;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-move-source")
        public String getXQSMoveSource() {
            return this.xQSMoveSource;
        }
        /**
         * Specify the storage class for object XQSStorageClass's available values: STANDARD,
         * STANDARD_IA
         */
        private String xQSStorageClass;

        public void setXQSStorageClass(String xQSStorageClass) {
            this.xQSStorageClass = xQSStorageClass;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-storage-class")
        public String getXQSStorageClass() {
            return this.xQSStorageClass;
        }

        /** The request body */
        private File bodyInputFile;

        /**
         * Get the File will be updated.
         *
         * @return the File object will be updated
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFile() {
            return bodyInputFile;
        }

        /**
         * Set the File to update. <br>
         *
         * @param bodyInputFile File to update
         */
        public void setBodyInputFile(File bodyInputFile) {
            this.bodyInputFile = bodyInputFile;
        }

        private InputStream bodyInputStream;

        /**
         * Get the body input stream.
         *
         * @return input stream
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        /**
         * Set the body input stream.
         *
         * @param bodyInputStream input stream to update
         */
        public void setBodyInputStream(InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        @Override
        public String validateParam() {

            Map<String, String> metadata = this.getXQSMetaData();
            if (metadata != null) {
                String vValidate = QSParamInvokeUtil.metadataIsValid(metadata);
                if (!QSStringUtil.isEmpty(vValidate)) {
                    return vValidate;
                }
            }

            String[] xQSMetadataDirectiveValidValues = {"COPY", "REPLACE"};

            boolean xQSMetadataDirectiveIsValid = false;
            String xQSMetadataDirective = this.getXQSMetadataDirective();
            if (null == xQSMetadataDirective || "".equals(xQSMetadataDirective)) {
                xQSMetadataDirectiveIsValid = true;
            } else {
                for (String v : xQSMetadataDirectiveValidValues) {
                    if (v.equals(xQSMetadataDirective)) {
                        xQSMetadataDirectiveIsValid = true;
                    }
                }
            }

            if (!xQSMetadataDirectiveIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "XQSMetadataDirective",
                        this.getXQSMetadataDirective() + "",
                        xQSMetadataDirectiveValidValues);
            }
            String[] xQSStorageClassValidValues = {"STANDARD", "STANDARD_IA"};

            boolean xQSStorageClassIsValid = false;
            String xQSStorageClass = this.getXQSStorageClass();
            if (null == xQSStorageClass || "".equals(xQSStorageClass)) {
                xQSStorageClassIsValid = true;
            } else {
                for (String v : xQSStorageClassValidValues) {
                    if (v.equals(xQSStorageClass)) {
                        xQSStorageClassIsValid = true;
                    }
                }
            }

            if (!xQSStorageClassIsValid) {
                return QSStringUtil.getParameterValueNotAllowedError(
                        "XQSStorageClass",
                        this.getXQSStorageClass() + "",
                        xQSStorageClassValidValues);
            }

            return null;
        }
    }

    /**
     * PutObjectOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field CacheControl Object cache control <br>
     * field ContentEncoding Object content encoding <br>
     * field ContentLength Object content size <br>
     * field ContentMD5 Object MD5sum <br>
     * field ContentType Object content type <br>
     * field Expect Used to indicate that particular server behaviors are required by the client
     * <br>
     * field XQSCopySource Copy source, format (/'bucket-name'/'object-key') <br>
     * field XQSCopySourceEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSCopySourceEncryptionCustomerKey Encryption key of the object <br>
     * field XQSCopySourceEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSCopySourceIfMatch Check whether the copy source matches <br>
     * field XQSCopySourceIfModifiedSince Check whether the copy source has been modified <br>
     * field XQSCopySourceIfNoneMatch Check whether the copy source does not match <br>
     * field XQSCopySourceIfUnmodifiedSince Check whether the copy source has not been modified <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSFetchIfUnmodifiedSince Check whether fetch target object has not been modified <br>
     * field XQSFetchSource Fetch source, should be a valid url <br>
     * field XQSMetaData User-defined metadata <br>
     * field XQSMetadataDirective Use for modified metadata, valid (COPY/REPLACE) <br>
     * field XQSMoveSource Move source, format (/'bucket-name'/'object-key') <br>
     * field XQSStorageClass Specify the storage class for object <br>
     */
    public static class PutObjectOutput extends OutputModel {

        /** MD5sum of the object */
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "etag")
        public String getETag() {
            return this.eTag;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return UploadMultipartOutput output stream Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html">
     *     https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html </a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public UploadMultipartOutput uploadMultipart(String objectName, UploadMultipartInput input)
            throws QSException {
        if (input == null) {
            input = new UploadMultipartInput();
        }

        RequestHandler requestHandler = this.uploadMultipartRequest(objectName, input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (UploadMultipartOutput) backModel;
        }
        return null;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html">https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler uploadMultipartRequest(String objectName, UploadMultipartInput input)
            throws QSException {
        if (input == null) {
            input = new UploadMultipartInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("UploadMultipart")
                .apiName("UploadMultipart")
                .serviceName("Upload Multipart")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, UploadMultipartOutput.class);

        return requestHandler;
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param callback response callback
     * @throws QSException exception
     *     <p>Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html">https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html</a>
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

        RequestHandler requestHandler =
                this.uploadMultipartAsyncRequest(objectName, input, callback);

        requestHandler.sendAsync();
    }

    /**
     * @param objectName name of the object
     * @param input the input
     * @param callback response callback
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html">https://docs.qingcloud.com/qingstor/api/object/multipart/upload_multipart.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler uploadMultipartAsyncRequest(
            String objectName,
            UploadMultipartInput input,
            ResponseCallBack<UploadMultipartOutput> callback)
            throws QSException {
        if (input == null) {
            input = new UploadMultipartInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("UploadMultipart")
                .apiName("UploadMultipart")
                .serviceName("Upload Multipart")
                .reqMethod("PUT")
                .subSourcePath("/<bucket-name>/<object-key>");

        builder.bucketName(this.bucketName);
        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        builder.objKey(objectName);
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler = QSRequest.getRequestAsync(builder.build(), input, callback);
        return requestHandler;
    }
    /**
     * UploadMultipartInput: an input stream of the bucket.<br>
     * The following is the description of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentLength Object multipart content length <br>
     * field ContentMD5 Object multipart content MD5sum <br>
     * field XQSCopyRange Specify range of the source object <br>
     * field XQSCopySource Copy source, format (/'bucket-name'/'object-key') <br>
     * field XQSCopySourceEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSCopySourceEncryptionCustomerKey Encryption key of the object <br>
     * field XQSCopySourceEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSCopySourceIfMatch Check whether the Etag of copy source matches the specified value
     * <br>
     * field XQSCopySourceIfModifiedSince Check whether the copy source has been modified since the
     * specified date <br>
     * field XQSCopySourceIfNoneMatch Check whether the Etag of copy source does not matches the
     * specified value <br>
     * field XQSCopySourceIfUnmodifiedSince Check whether the copy source has not been unmodified
     * since the specified date <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field PartNumber Object multipart upload part number <br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class UploadMultipartInput extends RequestInputModel {

        /** Object multipart upload part number Required */
        private Integer partNumber;

        public void setPartNumber(Integer partNumber) {
            this.partNumber = partNumber;
        }

        @ParamAnnotation(paramType = "query", paramName = "part_number")
        public Integer getPartNumber() {
            return this.partNumber;
        }
        /** Object multipart upload ID Required */
        private String uploadID;

        public void setUploadID(String uploadID) {
            this.uploadID = uploadID;
        }

        @ParamAnnotation(paramType = "query", paramName = "upload_id")
        public String getUploadID() {
            return this.uploadID;
        }

        /** Object multipart content length */
        private Long contentLength;

        public void setContentLength(Long contentLength) {
            this.contentLength = contentLength;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-length")
        public Long getContentLength() {
            return this.contentLength;
        }
        /** Object multipart content MD5sum */
        private String contentMD5;

        public void setContentMD5(String contentMD5) {
            this.contentMD5 = contentMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "content-md5")
        public String getContentMD5() {
            return this.contentMD5;
        }
        /** Specify range of the source object */
        private String xQSCopyRange;

        public void setXQSCopyRange(String xQSCopyRange) {
            this.xQSCopyRange = xQSCopyRange;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-range")
        public String getXQSCopyRange() {
            return this.xQSCopyRange;
        }
        /** Copy source, format (/<bucket-name>/<object-key>) */
        private String xQSCopySource;

        public void setXQSCopySource(String xQSCopySource) {
            this.xQSCopySource = xQSCopySource;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source")
        public String getXQSCopySource() {
            return this.xQSCopySource;
        }
        /** Encryption algorithm of the object */
        private String xQSCopySourceEncryptionCustomerAlgorithm;

        public void setXQSCopySourceEncryptionCustomerAlgorithm(
                String xQSCopySourceEncryptionCustomerAlgorithm) {
            this.xQSCopySourceEncryptionCustomerAlgorithm =
                    xQSCopySourceEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-algorithm")
        public String getXQSCopySourceEncryptionCustomerAlgorithm() {
            return this.xQSCopySourceEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSCopySourceEncryptionCustomerKey;

        public void setXQSCopySourceEncryptionCustomerKey(
                String xQSCopySourceEncryptionCustomerKey) {
            this.xQSCopySourceEncryptionCustomerKey = xQSCopySourceEncryptionCustomerKey;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-key")
        public String getXQSCopySourceEncryptionCustomerKey() {
            return this.xQSCopySourceEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSCopySourceEncryptionCustomerKeyMD5;

        public void setXQSCopySourceEncryptionCustomerKeyMD5(
                String xQSCopySourceEncryptionCustomerKeyMD5) {
            this.xQSCopySourceEncryptionCustomerKeyMD5 = xQSCopySourceEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(
                paramType = "header",
                paramName = "x-qs-copy-source-encryption-customer-key-md5")
        public String getXQSCopySourceEncryptionCustomerKeyMD5() {
            return this.xQSCopySourceEncryptionCustomerKeyMD5;
        }
        /** Check whether the Etag of copy source matches the specified value */
        private String xQSCopySourceIfMatch;

        public void setXQSCopySourceIfMatch(String xQSCopySourceIfMatch) {
            this.xQSCopySourceIfMatch = xQSCopySourceIfMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-match")
        public String getXQSCopySourceIfMatch() {
            return this.xQSCopySourceIfMatch;
        }
        /** Check whether the copy source has been modified since the specified date */
        private String xQSCopySourceIfModifiedSince;

        public void setXQSCopySourceIfModifiedSince(String xQSCopySourceIfModifiedSince) {
            this.xQSCopySourceIfModifiedSince = xQSCopySourceIfModifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-modified-since")
        public String getXQSCopySourceIfModifiedSince() {
            return this.xQSCopySourceIfModifiedSince;
        }
        /** Check whether the Etag of copy source does not matches the specified value */
        private String xQSCopySourceIfNoneMatch;

        public void setXQSCopySourceIfNoneMatch(String xQSCopySourceIfNoneMatch) {
            this.xQSCopySourceIfNoneMatch = xQSCopySourceIfNoneMatch;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-none-match")
        public String getXQSCopySourceIfNoneMatch() {
            return this.xQSCopySourceIfNoneMatch;
        }
        /** Check whether the copy source has not been unmodified since the specified date */
        private String xQSCopySourceIfUnmodifiedSince;

        public void setXQSCopySourceIfUnmodifiedSince(String xQSCopySourceIfUnmodifiedSince) {
            this.xQSCopySourceIfUnmodifiedSince = xQSCopySourceIfUnmodifiedSince;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-copy-source-if-unmodified-since")
        public String getXQSCopySourceIfUnmodifiedSince() {
            return this.xQSCopySourceIfUnmodifiedSince;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
        /** Encryption key of the object */
        private String xQSEncryptionCustomerKey;

        public void setXQSEncryptionCustomerKey(String xQSEncryptionCustomerKey) {
            this.xQSEncryptionCustomerKey = xQSEncryptionCustomerKey;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key")
        public String getXQSEncryptionCustomerKey() {
            return this.xQSEncryptionCustomerKey;
        }
        /** MD5sum of encryption key */
        private String xQSEncryptionCustomerKeyMD5;

        public void setXQSEncryptionCustomerKeyMD5(String xQSEncryptionCustomerKeyMD5) {
            this.xQSEncryptionCustomerKeyMD5 = xQSEncryptionCustomerKeyMD5;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-key-md5")
        public String getXQSEncryptionCustomerKeyMD5() {
            return this.xQSEncryptionCustomerKeyMD5;
        }

        /** The request body */
        private File bodyInputFile;

        /**
         * Get the File will be updated.
         *
         * @return the File object will be updated
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFile() {
            return bodyInputFile;
        }

        /**
         * Set the File to update. <br>
         * Deprecated, please use setBodyInputFilePart() to upload multi part. <br>
         * Then setFileOffset() and setContentLength() to get a part of a file or stream.
         *
         * @param bodyInputFile File to update
         */
        @Deprecated
        public void setBodyInputFile(File bodyInputFile) {
            this.bodyInputFile = bodyInputFile;
        }

        private InputStream bodyInputStream;

        /**
         * Get the body input stream.
         *
         * @return input stream
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputStream")
        public InputStream getBodyInputStream() {
            return bodyInputStream;
        }

        /**
         * Set the body input stream.
         *
         * @param bodyInputStream input stream to update
         */
        public void setBodyInputStream(InputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        private Long fileOffset = -1L;

        /**
         * You can set the offset of a file here. <br>
         * Then use setContentLength() to get a part of a file.
         *
         * @param fileOffset fileOffset
         */
        public void setFileOffset(Long fileOffset) {
            this.fileOffset = fileOffset;
        }

        /**
         * Get the offset of the File or stream(default = -1).
         *
         * @return the offset of the File or stream
         */
        @ParamAnnotation(paramType = "query", paramName = "file_offset")
        public Long getFileOffset() {
            return fileOffset;
        }

        /**
         * Set the File parts to update.
         *
         * @param bodyInputFilePart File part to update
         */
        public void setBodyInputFilePart(File bodyInputFilePart) {
            this.bodyInputFile = bodyInputFilePart;
            fileOffset = 0L;
        }

        /**
         * Get the File will be updated.
         *
         * @return the File part will be updated
         */
        @ParamAnnotation(paramType = "body", paramName = "BodyInputFile")
        public File getBodyInputFilePart() {
            return bodyInputFile;
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

    /**
     * UploadMultipartOutput: an output stream of the bucket.<br>
     * The following is the desc of fields.<br>
     * These fields are headers or bodies of the http request.<br>
     * field ContentLength Object multipart content length <br>
     * field ContentMD5 Object multipart content MD5sum <br>
     * field XQSCopyRange Specify range of the source object <br>
     * field XQSCopySource Copy source, format (/'bucket-name'/'object-key') <br>
     * field XQSCopySourceEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSCopySourceEncryptionCustomerKey Encryption key of the object <br>
     * field XQSCopySourceEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field XQSCopySourceIfMatch Check whether the Etag of copy source matches the specified value
     * <br>
     * field XQSCopySourceIfModifiedSince Check whether the copy source has been modified since the
     * specified date <br>
     * field XQSCopySourceIfNoneMatch Check whether the Etag of copy source does not matches the
     * specified value <br>
     * field XQSCopySourceIfUnmodifiedSince Check whether the copy source has not been unmodified
     * since the specified date <br>
     * field XQSEncryptionCustomerAlgorithm Encryption algorithm of the object <br>
     * field XQSEncryptionCustomerKey Encryption key of the object <br>
     * field XQSEncryptionCustomerKeyMD5 MD5sum of encryption key <br>
     * field PartNumber Object multipart upload part number <br>
     * field UploadID Object multipart upload ID <br>
     */
    public static class UploadMultipartOutput extends OutputModel {

        /** MD5sum of the object */
        private String eTag;

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        @ParamAnnotation(paramType = "header", paramName = "etag")
        public String getETag() {
            return this.eTag;
        }
        /** Range of response data content */
        private String xQSContentCopyRange;

        public void setXQSContentCopyRange(String xQSContentCopyRange) {
            this.xQSContentCopyRange = xQSContentCopyRange;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-content-copy-range")
        public String getXQSContentCopyRange() {
            return this.xQSContentCopyRange;
        }
        /** Encryption algorithm of the object */
        private String xQSEncryptionCustomerAlgorithm;

        public void setXQSEncryptionCustomerAlgorithm(String xQSEncryptionCustomerAlgorithm) {
            this.xQSEncryptionCustomerAlgorithm = xQSEncryptionCustomerAlgorithm;
        }

        @ParamAnnotation(paramType = "header", paramName = "x-qs-encryption-customer-algorithm")
        public String getXQSEncryptionCustomerAlgorithm() {
            return this.xQSEncryptionCustomerAlgorithm;
        }
    }

    /**
     * @param objectName name of the object
     * @param expires time to expire
     * @return signature url
     * @throws QSException exception Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/common/signature.html">https://docs.qingcloud.com/qingstor/api/common/signature.html</a>
     */
    public String GetObjectSignatureUrl(String objectName, long expires) throws QSException {
        RequestHandler requestHandler =
                this.GetObjectBySignatureUrlRequest(objectName, null, expires);
        return requestHandler.getExpiresRequestUrl();
    }

    /**
     * @param objectName name of the object
     * @param input get object input
     * @param expires Relative current time，the second when this quert sign expires
     * @return request handle
     * @throws QSException exception Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/api/common/signature.html">https://docs.qingcloud.com/qingstor/api/common/signature.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler GetObjectBySignatureUrlRequest(
            String objectName, GetObjectInput input, long expires) throws QSException {

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .expires(String.valueOf(expires))
                .operationName("GetObject")
                .apiName("GetObject")
                .serviceName("Get Object")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>")
                .bucketName(this.bucketName)
                .objKey(objectName);

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        if (input == null) {
            input = new GetObjectInput();
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, OutputModel.class);

        return requestHandler;
    }

    /**
     * @param signaturedRequest Signature Url
     * @return GetObjectOutput
     * @throws QSException exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public GetObjectOutput GetObjectBySignatureUrl(String signaturedRequest) throws QSException {
        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetObject")
                .apiName("GetObject")
                .serviceName("QingStor")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        Object backModel =
                QSRequest.sendApiRequest(signaturedRequest, builder.build(), GetObjectOutput.class);
        if (backModel != null) {
            return (GetObjectOutput) backModel;
        }
        return null;
    }

    /**
     * @param signaturedRequest Signature Url
     * @param callback callback
     * @throws QSException exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void GetObjectBySignatureUrlAsync(
            String signaturedRequest, ResponseCallBack<GetObjectOutput> callback)
            throws QSException {
        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("GetObject")
                .apiName("GetObject")
                .serviceName("QingStor")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>");

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        QSRequest.sendApiRequestAsync(signaturedRequest, builder.build(), callback);
    }

    /**
     * @param objectName name of the object
     * @param input input
     * @param expires expires
     * @throws QSException exception
     * @return RequestHandler http request handler Documentation URL: <a
     *     href="https://docs.qingcloud.com/qingstor/data_process/image_process/index.html">https://docs.qingcloud.com/qingstor/data_process/image_process/index.html</a>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler imageProcessExpiredUrlRequest(
            String objectName, ImageProcessInput input, long expires) throws QSException {

        if (input == null) {
            input = new ImageProcessInput();
        }

        OperationContext.OperationContextBuilder builder = OperationContext.builder();
        builder.clientCfg(this.clientCfg)
                .zone(this.zone)
                .credentials(this.cred)
                .operationName("ImageProcess")
                .apiName("ImageProcess")
                .serviceName("Image Process")
                .reqMethod("GET")
                .subSourcePath("/<bucket-name>/<object-key>?image")
                .bucketName(this.bucketName)
                .objKey(objectName);

        if (expires > System.currentTimeMillis() / 1000) builder.expires(String.valueOf(expires));

        if (QSStringUtil.isEmpty(bucketName)) {
            throw new QSException("bucketName can't be empty!");
        }
        if (QSStringUtil.isEmpty(objectName)) {
            throw new QSException("objectName can't be empty!");
        }

        RequestHandler requestHandler =
                QSRequest.getRequest(builder.build(), input, ImageProcessOutput.class);

        return requestHandler;
    }
}
