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
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.utils.QSLoggerUtil;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSSignatureUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Request;

public class QSRequest implements ResourceRequest {

    private static Logger logger = QSLoggerUtil.setLoggerHanlder(QSRequest.class.getName());

    @Override
    public void sendApiRequestAsync(
            Map context, RequestInputModel paramBean, ResponseCallBack callback)
            throws QSException {
        String validate = paramBean != null ? paramBean.validateParam() : "";
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        String evnValidate = evnContext.validateParam();
        if (!QSStringUtil.isEmpty(validate) || !QSStringUtil.isEmpty(evnValidate)) {
            if (QSStringUtil.isEmpty(validate)) {
                validate = evnValidate;
            }
            OutputModel out = QSParamInvokeUtil.getOutputModel(callback);
            QSOkHttpRequestClient.fillResponseCallbackModel(
                    QSConstant.REQUEST_ERROR_CODE, validate, out);
            callback.onAPIResponse(out);
        } else {
            Request request = buildRequest(context, paramBean);
            QSOkHttpRequestClient.getInstance()
                    .requestActionAsync(request, evnContext.isSafeOkHttp(), callback);
        }
    }

    @Override
    public void sendApiRequestAsync(String requestUrl, Map context, ResponseCallBack callback)
            throws QSException {
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        Request request = QSOkHttpRequestClient.getInstance().buildUrlRequest(requestUrl);
        QSOkHttpRequestClient.getInstance()
                .requestActionAsync(request, evnContext.isSafeOkHttp(), callback);
    }

    @Override
    public OutputModel sendApiRequest(
            String requestUrl, Map context, Class<? extends OutputModel> outputClass)
            throws QSException {
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        Request request = QSOkHttpRequestClient.getInstance().buildUrlRequest(requestUrl);
        return QSOkHttpRequestClient.getInstance()
                .requestAction(request, evnContext.isSafeOkHttp(), outputClass);
    }

    @Override
    public OutputModel sendApiRequest(Map context, RequestInputModel paramBean, Class outputClass)
            throws QSException {
        String validate = paramBean != null ? paramBean.validateParam() : "";
        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        String evnValidate = evnContext.validateParam();
        if (!QSStringUtil.isEmpty(validate) || !QSStringUtil.isEmpty(evnValidate)) {
            if (QSStringUtil.isEmpty(validate)) {
                validate = evnValidate;
            }
            try {
                OutputModel model = (OutputModel) outputClass.newInstance();
                QSOkHttpRequestClient.fillResponseCallbackModel(
                        QSConstant.REQUEST_ERROR_CODE, validate, model);
                return model;
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
                throw new QSException(e.getMessage());
            }
        } else {
            Request request = buildRequest(context, paramBean);
            return QSOkHttpRequestClient.getInstance()
                    .requestAction(request, evnContext.isSafeOkHttp(), outputClass);
        }
    }

    private Request buildRequest(Map context, RequestInputModel params) throws QSException {

        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);
        String zone = (String) context.get(QSConstant.PARAM_KEY_REQUEST_ZONE);

        Map paramsQuery = QSParamInvokeUtil.getRequestParams(params, QSConstant.PARAM_TYPE_QUERY);
        Map paramsBody = QSParamInvokeUtil.getRequestParams(params, QSConstant.PARAM_TYPE_BODY);
        Map paramsHeaders =
                QSParamInvokeUtil.getRequestParams(params, QSConstant.PARAM_TYPE_HEADER);

        String requestApi = (String) context.get(QSConstant.PARAM_KEY_REQUEST_APINAME);

        String method = (String) context.get(QSConstant.PARAM_KEY_REQUEST_METHOD);
        String bucketName = (String) context.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        String requestPath = (String) context.get(QSConstant.PARAM_KEY_REQUEST_PATH);

        if (context.containsKey(QSConstant.PARAM_KEY_OBJECT_NAME)) {
            requestPath = requestPath.replace(QSConstant.BUCKET_NAME_REPLACE, bucketName);
            String objectName = (String) context.get(QSConstant.PARAM_KEY_OBJECT_NAME);
            try {
                objectName = URLEncoder.encode(objectName, QSConstant.ENCODING_UTF8);
            } catch (UnsupportedEncodingException e) {
                throw new QSException("Auth signature error", e);
            }
            requestPath = requestPath.replace(QSConstant.OBJECT_NAME_REPLACE, objectName);
        } else {
            requestPath = requestPath.replace(QSConstant.BUCKET_NAME_REPLACE, bucketName + "/");
        }

        String authSign =
                QSSignatureUtil.getAuth(
                        evnContext.getAccessKey(),
                        evnContext.getAccessSecret(),
                        method,
                        requestPath,
                        paramsQuery,
                        paramsHeaders);
        String requestSuffixPath = requestPath.replace("/" + bucketName, "");
        paramsHeaders.put("Authorization", authSign);
        logger.log(Level.INFO, authSign);
        String singedUrl =
                getSignedUrl(
                        evnContext.getRequestUrl(),
                        zone,
                        bucketName,
                        paramsQuery,
                        requestSuffixPath);
        logger.log(Level.INFO, singedUrl);
        if (QSConstant.PARAM_KEY_REQUEST_API_MULTIPART.equals(requestApi)) {
            Request request =
                    QSOkHttpRequestClient.getInstance()
                            .buildStorMultiUpload(
                                    method, paramsBody, singedUrl, paramsHeaders, paramsQuery);
            return request;

        } else {
            Request request =
                    QSOkHttpRequestClient.getInstance()
                            .buildStorRequest(method, paramsBody, singedUrl, paramsHeaders);
            return request;
        }
    }

    private static String getSignedUrl(
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
}
