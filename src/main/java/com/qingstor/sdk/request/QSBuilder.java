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
package com.qingstor.sdk.request;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.impl.QSFormRequestBody;
import com.qingstor.sdk.request.impl.QSMultiPartUploadRequestBody;
import com.qingstor.sdk.request.impl.QSNormalRequestBody;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSSignatureUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import com.qingstor.sdk.utils.UrlUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSBuilder {

    private static final Logger log = LoggerFactory.getLogger(QSBuilder.class);

    private Map<String, Object> opCtx;

    private RequestInputModel paramsModel;

    private String requestMethod = "GET";

    private Map paramsQuery;

    private Map paramsBody;

    private Map paramsHeaders;

    private Map paramsFormData;

    private HttpUrl urlWithoutQueries;

    private boolean pathMode;

    public QSBuilder(Map opCtx, RequestInputModel params) throws QSException {
        this.opCtx = opCtx;
        this.paramsModel = params;
        this.initHeadersAndBody();
        this.initUrlWithQueries();
    }

    private void initHeadersAndBody() throws QSException {
        this.requestMethod = (String) opCtx.get(QSConstant.PARAM_KEY_REQUEST_METHOD);
        this.paramsBody =
                QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_BODY);
        this.paramsHeaders =
                QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_HEADER);

        if (this.paramsHeaders.containsKey(QSConstant.PARAM_KEY_METADATA)) {
            Object o = this.paramsHeaders.get(QSConstant.PARAM_KEY_METADATA);
            if (o != null) {
                Map<String, String> map = (Map<String, String>) o;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.paramsHeaders.put(entry.getKey(), entry.getValue());
                }
            }
            this.paramsHeaders.remove(QSConstant.PARAM_KEY_METADATA);
        }

        EnvContext ctx = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
        String additionalUserAgent = ctx.getAdditionalUserAgent();
        additionalUserAgent =
                (additionalUserAgent == null ? QSStringUtil.getUserAgent() : additionalUserAgent);
        paramsHeaders.put(QSConstant.PARAM_KEY_USER_AGENT, additionalUserAgent);

        if (this.checkExpiresParam()) {
            paramsHeaders.clear();
            paramsHeaders.put(
                    QSConstant.HEADER_PARAM_KEY_EXPIRES, opCtx.get(QSConstant.PARAM_KEY_EXPIRES));
        }

        String requestApi = (String) opCtx.get(QSConstant.PARAM_KEY_REQUEST_APINAME);
        this.initHeadContentMd5(requestApi, paramsBody, paramsHeaders);

        paramsHeaders = this.headParamEncoding(paramsHeaders);

        this.paramsFormData =
                QSParamInvokeUtil.getRequestParams(
                        this.paramsModel, QSConstant.PARAM_TYPE_FORM_DATA);
    }

    private void initUrlWithQueries() throws QSException {
        String zone = (String) opCtx.get(QSConstant.PARAM_KEY_REQUEST_ZONE);
        String bucketName = (String) this.opCtx.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        String objectName = (String) this.opCtx.get(QSConstant.PARAM_KEY_OBJECT_NAME);

        EnvContext envContext = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
        String urlStyle = envContext.getRequestUrlStyle();
        boolean pathMode = urlStyle.equals(QSConstant.PATH_STYLE);
        this.pathMode = pathMode;

        HttpUrl endpoint =
                UrlUtils.calcFinalEndpoint(envContext.getRequestUrl(), zone, bucketName, pathMode);
        if (endpoint == null) {
            throw new QSException("url parsing failed");
        }
        String resourcePath = UrlUtils.calcResourcePath(bucketName, objectName, pathMode);

        this.urlWithoutQueries = endpoint.newBuilder().addEncodedPathSegments(resourcePath).build();
        HttpUrl.Builder urlBuilder = this.urlWithoutQueries.newBuilder();

        String requestPath = (String) opCtx.get(QSConstant.PARAM_KEY_REQUEST_PATH);
        int i = requestPath.indexOf('?');
        if (i != -1) {
            String extraQueries = requestPath.substring(i + 1);
            urlBuilder.query(extraQueries);
        }

        this.paramsQuery =
                QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_QUERY);
        this.paramsQuery.forEach(
                (k, v) ->
                        urlBuilder.addQueryParameter(
                                k.toString(), v == null ? null : v.toString()));
        HttpUrl url = urlBuilder.build();
        Map<String, String> queries = new HashMap<>();
        for (String k : url.queryParameterNames()) {
            List<String> values = url.queryParameterValues(k);
            queries.put(k, values.get(0)); // it should always size > 0.
        }
        this.paramsQuery = queries;

        log.debug("== resource Url ==\n" + this.urlWithoutQueries + "\n");
    }

    private void doSignature() throws QSException {

        String authSign = this.getParamSignature();
        log.debug("== authSign ==\n" + authSign + "\n");

        paramsHeaders.put(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION, authSign);
    }

    private String getParamSignature() throws QSException {

        String authSign = "";
        EnvContext envContext = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
        try {

            if (this.checkExpiresParam()) {
                authSign =
                        QSSignatureUtil.generateSignature(
                                envContext.getSecretAccessKey(), this.getStringToSignature());
            } else {
                authSign =
                        QSSignatureUtil.generateAuthorization(
                                envContext.getAccessKeyId(),
                                envContext.getSecretAccessKey(),
                                this.getStringToSignature());
            }
        } catch (Exception e) {
            throw new QSException("Auth signature error", e);
        }

        return authSign;
    }

    private Map headParamEncoding(Map headParams) throws QSException {
        final int maxAscii = 127;
        Map<String, String> head = new HashMap<>();
        for (Object o : headParams.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            String value = entry.getValue().toString();

            for (int charIndex = 0, nChars = value.length(), codePoint;
                    charIndex < nChars;
                    charIndex += Character.charCount(codePoint)) {
                codePoint = value.codePointAt(charIndex);
                if (codePoint > maxAscii) {
                    value = QSStringUtil.asciiCharactersEncoding(value);
                    break;
                }
            }
            head.put(key, value);
        }

        return head;
    }

    private void initHeadContentMd5(String requestApi, Map paramsBody, Map headerParams)
            throws QSException {
        if (QSConstant.PARAM_KEY_REQUEST_API_DELETE_MULTIPART.equals(requestApi)) {
            if (paramsBody.size() > 0) {
                Object bodyContent = QSNormalRequestBody.getBodyContent(paramsBody);
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    String contentMD5 =
                            // Base64.encode(instance.digest(bodyContent.toString().getBytes()));
                            Base64.getEncoder()
                                    .encodeToString(md5.digest(bodyContent.toString().getBytes()));
                    headerParams.put(QSConstant.PARAM_KEY_CONTENT_MD5, contentMD5);
                } catch (NoSuchAlgorithmException e) {
                    throw new QSException(e.getMessage(), e);
                }
            }
        }
    }

    public void setHeader(String name, String value) {
        this.paramsHeaders.put(name, value);
    }

    public void setSignature(String accessKey, String signature) throws QSException {
        try {
            EnvContext envContext = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
            envContext.setAccessKeyId(accessKey);
            if (this.checkExpiresParam()) {
                signature = URLEncoder.encode(signature, QSConstant.ENCODING_UTF8);
            } else {
                signature = String.format("QS %s:%s", accessKey, signature);
            }
            this.paramsHeaders.put(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION, signature);
        } catch (UnsupportedEncodingException e) {
            throw new QSException("Auth signature error", e);
        }
    }

    public String getStringToSignature() {
        return QSSignatureUtil.getStringToSignature(
                this.requestMethod, resourcePathForSign(), this.paramsQuery, this.paramsHeaders);
    }

    public RequestBody getRequestBody(QSRequestBody qsBody) throws QSException {
        this.getSignature();
        RequestBody requestBody = null;
        String contentType =
                String.valueOf(paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_CONTENTTYPE));
        long contentLength = 0;
        if (paramsHeaders.containsKey(QSConstant.PARAM_KEY_CONTENT_LENGTH)) {
            contentLength =
                    Long.parseLong(paramsHeaders.get(QSConstant.PARAM_KEY_CONTENT_LENGTH) + "");
        }
        if (qsBody != null) {
            requestBody =
                    qsBody.getRequestBody(
                            contentType,
                            contentLength,
                            this.requestMethod,
                            this.paramsBody,
                            this.paramsQuery);
        } else {
            requestBody = getRequestBody();
        }

        return requestBody;
    }

    public RequestBody getRequestBody() throws QSException {
        this.getSignature();
        RequestBody requestBody = null;
        String contentType =
                String.valueOf(paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_CONTENTTYPE));
        long contentLength = 0;
        if (paramsHeaders.containsKey(QSConstant.PARAM_KEY_CONTENT_LENGTH)) {
            contentLength =
                    Long.parseLong(paramsHeaders.get(QSConstant.PARAM_KEY_CONTENT_LENGTH) + "");
        }

        if (this.paramsFormData != null && this.paramsFormData.size() > 0) {
            requestBody =
                    new QSFormRequestBody()
                            .getRequestBody(
                                    contentType,
                                    contentLength,
                                    this.requestMethod,
                                    this.paramsFormData,
                                    this.paramsQuery);
        } else {
            String requestApi = (String) this.opCtx.get(QSConstant.PARAM_KEY_REQUEST_APINAME);
            if (QSConstant.PARAM_KEY_REQUEST_API_MULTIPART.equals(requestApi)) {
                requestBody =
                        new QSMultiPartUploadRequestBody()
                                .getRequestBody(
                                        contentType,
                                        contentLength,
                                        this.requestMethod,
                                        this.paramsBody,
                                        this.paramsQuery);
            } else {
                requestBody =
                        new QSNormalRequestBody()
                                .getRequestBody(
                                        contentType,
                                        contentLength,
                                        this.requestMethod,
                                        this.paramsBody,
                                        this.paramsQuery);
            }
        }
        return requestBody;
    }

    public Request getRequest(RequestBody requestBody) throws QSException {
        if (this.checkExpiresParam()) {
            throw new QSException("You need to 'getExpiresRequestUrl' do request!");
        }

        Request.Builder builder = new Request.Builder();
        String[] sortedHeadersKeys =
                (String[]) this.paramsHeaders.keySet().toArray(new String[] {});
        for (String key : sortedHeadersKeys) {
            builder.addHeader(key, String.valueOf(this.paramsHeaders.get(key)));
        }
        return builder.url(mergeFullUrl()).method(this.requestMethod, requestBody).build();
    }

    private boolean checkExpiresParam() {
        Object expiresTime = this.opCtx.get(QSConstant.PARAM_KEY_EXPIRES);
        return expiresTime != null;
    }

    private String mergeFullUrl() {
        HttpUrl.Builder builder = this.urlWithoutQueries.newBuilder();
        this.paramsQuery.forEach(
                (k, v) -> builder.addQueryParameter(k.toString(), v == null ? null : v.toString()));
        return builder.build().toString();
    }

    public String getExpiresRequestUrl() throws QSException {
        HttpUrl.Builder builder = this.urlWithoutQueries.newBuilder();
        this.paramsQuery.forEach(
                (k, v) -> builder.addQueryParameter(k.toString(), v == null ? null : v.toString()));
        Object expiresTime = this.opCtx.get(QSConstant.PARAM_KEY_EXPIRES);
        if (expiresTime != null) {
            EnvContext envContext = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
            String expireAuth = this.getSignature();
            String ak = envContext.getAccessKeyId();
            builder.addQueryParameter("access_key_id", ak)
                    .addQueryParameter("expires", expiresTime.toString())
                    .addQueryParameter("signature", expireAuth);
        }
        return builder.build().toString();
    }

    private String getSignature() throws QSException {
        String signature =
                String.valueOf(this.paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION));
        if (QSStringUtil.isEmpty(signature)) {
            this.doSignature();
            return String.valueOf(
                    this.paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION));
        }
        return signature;
    }

    private String resourcePathForSign() {
        String encodedPath = this.urlWithoutQueries.encodedPath();
        if (this.pathMode) {
            return encodedPath;
        }
        String bucketName = (String) this.opCtx.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        return "/" + (bucketName != null ? bucketName + encodedPath : "");
    }
}
