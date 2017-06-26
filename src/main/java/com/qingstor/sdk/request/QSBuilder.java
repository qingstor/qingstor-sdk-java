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

package com.qingstor.sdk.request;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.utils.*;
import okhttp3.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSBuilder {

    private static final String REQUEST_PREFIX = "/";

    private static Logger logger = QSLoggerUtil.setLoggerHanlder(QSBuilder.class.getName());

    private Map context;

    private RequestInputModel paramsModel;

    private String requestMethod = "GET";

    private Map paramsQuery;

    private Map paramsBody;

    private Map paramsHeaders;

    private String requestUrl;

    public QSBuilder(Map context, RequestInputModel params) throws QSException {
        this.context = context;
        this.paramsModel = params;
        this.initParams();
        this.initRequestUrl();
    }

    private void initParams() throws QSException {

        this.paramsQuery = QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_QUERY);
        this.paramsBody = QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_BODY);
        this.paramsHeaders =
                QSParamInvokeUtil.getRequestParams(this.paramsModel, QSConstant.PARAM_TYPE_HEADER);
        paramsHeaders = this.headParamEncoding(paramsHeaders);

        if (context.get(QSConstant.PARAM_KEY_USER_AGENT) != null) {
            paramsHeaders.put(QSConstant.PARAM_KEY_USER_AGENT, context.get(QSConstant.PARAM_KEY_USER_AGENT));
        }

        if (this.checkExpiresParam()) {
            paramsHeaders.clear();
            paramsHeaders.put(QSConstant.HEADER_PARAM_KEY_EXPIRES, context.get(QSConstant.PARAM_KEY_EXPIRES));
        }

        String requestApi = (String) context.get(QSConstant.PARAM_KEY_REQUEST_APINAME);
        this.initHeadContentMd5(requestApi, paramsBody, paramsHeaders);

        this.requestMethod = (String) context.get(QSConstant.PARAM_KEY_REQUEST_METHOD);


    }

    private void doSignature() throws QSException {

        String authSign = this.getParamSignature();
        logger.log(Level.INFO, "== authSign ==\n" + authSign + "\n");

        paramsHeaders.put(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION, authSign);
    }

    private String getParamSignature() throws QSException {

        String authSign = "";
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        try {

            if (this.checkExpiresParam()) {
                authSign =
                        QSSignatureUtil.generateSignature(evnContext.getAccessSecret(), this.getStringToSignature());
                authSign = URLEncoder.encode(authSign, QSConstant.ENCODING_UTF8);
            } else {
                authSign =
                        QSSignatureUtil.generateAuthorization(
                                evnContext.getAccessKey(), evnContext.getAccessSecret(), this.getStringToSignature());

            }
        } catch (Exception e) {
            throw new QSException("Auth signature error", e);
        }

        return authSign;
    }

    private void initRequestUrl() throws QSException {
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        String bucketName = (String) this.context.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        String zone = (String) context.get(QSConstant.PARAM_KEY_REQUEST_ZONE);
        String objectName = (String) this.context.get(QSConstant.PARAM_KEY_OBJECT_NAME);
        String requestSuffixPath = getRequestSuffixPath((String) context.get(QSConstant.PARAM_KEY_REQUEST_PATH), bucketName, objectName);
        this.requestUrl = this.getSignedUrl(
                evnContext.getRequestUrl(),
                zone,
                bucketName,
                paramsQuery,
                requestSuffixPath);
        logger.log(Level.INFO, "== requestUrl ==\n" + this.requestUrl + "\n");
    }

    private Map headParamEncoding(Map headParams) throws QSException {
        Map head = new HashMap();
        Iterator iterator = headParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            if (!key.startsWith("x-qs-") && !key.startsWith("X-QS-")) {
                head.put(key, headParams.get(key));
            } else {
                String keyValue = QSStringUtil.asciiCharactersEncoding(headParams.get(key) + "");
                head.put(key, keyValue);
            }
        }

        return head;
    }


    private void initHeadContentMd5(String requestApi, Map paramsBody, Map paramsHead) throws QSException {
        if (QSConstant.PARAM_KEY_REQUEST_API_DELETE_MULTIPART.equals(requestApi)) {
            if (paramsBody.size() > 0) {
                Object bodyContent = QSOkHttpRequestClient.getInstance().getBodyContent(paramsBody);
                MessageDigest instance = null;
                try {
                    instance = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new QSException("MessageDigest MD5 error", e);
                }
                String contentMD5 = new String(Base64.encode(instance.digest(bodyContent.toString().getBytes())));
                paramsHead.put(QSConstant.PARAM_KEY_CONTENT_MD5, contentMD5);
            }
        }
    }

    private String getRequestSuffixPath(String requestPath, String bucketName, String objectName) throws QSException {
        if (QSStringUtil.isEmpty(bucketName)) {
            return REQUEST_PREFIX;
        }
        String suffixPath = requestPath.replace(REQUEST_PREFIX + QSConstant.BUCKET_NAME_REPLACE, "").replace(REQUEST_PREFIX + QSConstant.OBJECT_NAME_REPLACE, "");
        if (QSStringUtil.isEmpty(objectName)) {
            objectName = "";
        } else {
            objectName = QSStringUtil.asciiCharactersEncoding(objectName);
        }

        return String.format("%s%s%s", REQUEST_PREFIX, objectName, suffixPath);
    }

    private String getSignedUrl(
            String serviceUrl,
            String zone,
            String bucketName,
            Map paramsQuery,
            String requestSuffixPath)
            throws QSException {
        if ("".equals(bucketName) || bucketName == null) {
            return QSSignatureUtil.generateQSURL(paramsQuery, serviceUrl + requestSuffixPath);
        } else {
            String storRequestUrl = serviceUrl.replace("://", "://%s." + zone + ".");
            return QSSignatureUtil.generateQSURL(
                    paramsQuery, String.format(storRequestUrl, bucketName) + requestSuffixPath);
        }
    }

    public void setHeader(String key, String authorization) {
        this.paramsHeaders.put(key, authorization);
    }

    public void setSignature(String accessKey, String signature) throws QSException {

        try {
            EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
            evnContext.setAccessKey(accessKey);
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

    public String getStringToSignature() throws QSException {

        String bucketName = (String) this.context.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        String requestPath = (String) this.context.get(QSConstant.PARAM_KEY_REQUEST_PATH);
        String objectName = (String) this.context.get(QSConstant.PARAM_KEY_OBJECT_NAME);
        if (this.context.containsKey(QSConstant.PARAM_KEY_OBJECT_NAME)) {
            requestPath = requestPath.replace(QSConstant.BUCKET_NAME_REPLACE, bucketName);
            requestPath = requestPath.replace(QSConstant.OBJECT_NAME_REPLACE, QSStringUtil.asciiCharactersEncoding(objectName));
        } else {
            requestPath = requestPath.replace(QSConstant.BUCKET_NAME_REPLACE, bucketName + "/");
        }
        return QSSignatureUtil.getStringToSignature(this.requestMethod, requestPath, this.paramsQuery, this.paramsHeaders);
    }

    public Request getRequest() throws QSException {
        if (this.checkExpiresParam()) {
            throw new QSException("You need to 'getExpiresRequestUrl' do request!");
        }

        this.getSignature();

        String requestApi = (String) this.context.get(QSConstant.PARAM_KEY_REQUEST_APINAME);
        if (QSConstant.PARAM_KEY_REQUEST_API_MULTIPART.equals(requestApi)) {
            Request request =
                    QSOkHttpRequestClient.getInstance()
                            .buildStorMultiUpload(
                                    this.requestMethod, paramsBody, this.requestUrl, paramsHeaders, paramsQuery);
            return request;

        } else {
            Request request =
                    QSOkHttpRequestClient.getInstance()
                            .buildStorRequest(this.requestMethod, paramsBody, this.requestUrl, paramsHeaders);
            return request;
        }
    }

    private boolean checkExpiresParam() {
        Object expiresTime = this.context.get(QSConstant.PARAM_KEY_EXPIRES);
        if (expiresTime != null) {
            return true;
        }
        return false;
    }

    public String getExpiresRequestUrl() throws QSException {
        Object expiresTime = this.context.get(QSConstant.PARAM_KEY_EXPIRES);
        if (expiresTime != null) {
            EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
            String expireAuth = this.getSignature();
            String serviceUrl = evnContext.getRequestUrl();
            String objectName = (String) this.context.get(QSConstant.PARAM_KEY_OBJECT_NAME);
            String bucketName = (String) this.context.get(QSConstant.PARAM_KEY_BUCKET_NAME);
            String zone = (String) context.get(QSConstant.PARAM_KEY_REQUEST_ZONE);
            String storRequestUrl = serviceUrl.replace("://", "://%s." + zone + ".");
            objectName = QSStringUtil.asciiCharactersEncoding(objectName);
            if (objectName != null && objectName.indexOf("?") > 0) {
                return String.format(
                        storRequestUrl + "/%s&access_key_id=%s&expires=%s&signature=%s",
                        bucketName,
                        objectName,
                        evnContext.getAccessKey(),
                        expiresTime + "",
                        expireAuth);
            } else {
                return String.format(
                        storRequestUrl + "/%s?access_key_id=%s&expires=%s&signature=%s",
                        bucketName,
                        objectName,
                        evnContext.getAccessKey(),
                        expiresTime + "",
                        expireAuth);
            }
        } else {
            throw new QSException("ExpiresRequestUrl error:There is no expirs params");
        }

    }

    private String getSignature() throws QSException {
        Object signature = this.paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION);
        if (signature == null) {
            this.doSignature();
            return String.valueOf(this.paramsHeaders.get(QSConstant.HEADER_PARAM_KEY_AUTHORIZATION));
        }
        return String.valueOf(signature);
    }
}
