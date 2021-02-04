/*
 * Copyright (C) 2021 Yunify, Inc.
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

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.common.OperationContext;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.impl.ProgressRequestBody;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final OperationContext opCtx;

    private final RequestInputModel paramBean;

    private Class<? extends OutputModel> outputClass;

    private ResponseCallBack asyncCallback;

    private QSBuilder builder;

    private BodyProgressListener progressListener;

    private CancellationHandler cancellationHandler;

    @Deprecated private QSRequestBody qsRequestBody;

    @Deprecated
    public RequestHandler(
            Map<String, Object> operationCtx,
            RequestInputModel paramBean,
            Class<? extends OutputModel> outputClass)
            throws QSException {
        this(OperationContext.from(operationCtx), paramBean, outputClass);
    }

    public RequestHandler(
            OperationContext opCtx,
            RequestInputModel paramBean,
            Class<? extends OutputModel> outputClass)
            throws QSException {
        this.opCtx = opCtx;
        this.paramBean = paramBean;
        this.outputClass = outputClass;
        this.builder = new QSBuilder(opCtx, paramBean);
    }

    @Deprecated
    public RequestHandler(
            Map<String, Object> context,
            RequestInputModel paramBean,
            ResponseCallBack<? extends OutputModel> asyncCallback)
            throws QSException {
        this(OperationContext.from(context), paramBean, asyncCallback);
    }

    public RequestHandler(
            OperationContext context,
            RequestInputModel paramBean,
            ResponseCallBack<? extends OutputModel> asyncCallback)
            throws QSException {
        this.opCtx = context;
        this.paramBean = paramBean;
        this.asyncCallback = asyncCallback;
        this.builder = new QSBuilder(context, paramBean);
    }

    public void sendAsync() throws QSException {
        String validate = this.check();
        if (!QSStringUtil.isEmpty(validate)) {
            OutputModel out = QSParamInvokeUtil.getOutputModel(this.asyncCallback);
            QSOkHttpRequestClient.fillResponseCallbackModel(
                    QSConstant.REQUEST_ERROR_CODE, validate, out);
            this.asyncCallback.onAPIResponse(out);
        } else {
            EnvContext envContext = (EnvContext) this.opCtx.credentials();
            Request request = this.getRequest();
            QSOkHttpRequestClient.getInstance(envContext)
                    .requestActionAsync(
                            request, this.opCtx.clientCfg().isSafeOkHttp(), this.asyncCallback);
        }
    }

    public OutputModel send() throws QSException {

        String validate = this.check();
        if (!QSStringUtil.isEmpty(validate)) {
            try {
                OutputModel model = outputClass.getDeclaredConstructor().newInstance();
                QSOkHttpRequestClient.fillResponseCallbackModel(
                        QSConstant.REQUEST_ERROR_CODE, validate, model);
                return model;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new QSException(e.getMessage());
            }
        } else {
            EnvContext envContext = (EnvContext) this.opCtx.credentials();

            Request request = this.getRequest();

            return QSOkHttpRequestClient.getInstance(envContext)
                    .requestAction(request, this.opCtx.clientCfg().isSafeOkHttp(), outputClass);
        }
    }

    private Request getRequest() throws QSException {
        checkDownloadRequest();
        RequestBody body = this.builder.getRequestBody();
        if (this.getProgressListener() != null) {
            return this.builder.getRequest(
                    new ProgressRequestBody(body, this.progressListener, getCancellationHandler()));
        }
        return this.builder.getRequest(body);
    }

    /**
     * OkHttp will use "Accept-Encoding: gzip" as default header, which may not get Content-Length
     * from server when download.
     */
    private void checkDownloadRequest() {
        if (outputClass == null) return;
        boolean isDownloadRequest = false;
        Field[] declaredField = outputClass.getDeclaredFields();
        for (Field field : declaredField) {
            String methodName = "get" + QSStringUtil.capitalize(field.getName());
            Method[] methods = outputClass.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equalsIgnoreCase(methodName)) {
                    ParamAnnotation annotation = m.getAnnotation(ParamAnnotation.class);
                    if (annotation == null) continue;
                    if ("BodyInputStream".equals(annotation.paramName())) {
                        isDownloadRequest = true;
                        break;
                    }
                }
            }
        }
        if (isDownloadRequest) {
            getBuilder().setHeader("Accept-Encoding", "identity");
        }
    }

    public String getStringToSignature() {
        return this.builder.getStringToSignature();
    }

    public void setSignature(String accessKey, String signature) throws QSException {
        this.builder.setSignature(accessKey, signature);
    }

    /**
     * Set signature and server time.
     *
     * @param accessKey accessKey
     * @param signature signature
     * @param gmtTime time format with GMT
     * @throws QSException exception
     */
    public void setSignature(String accessKey, String signature, String gmtTime)
            throws QSException {
        builder.setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
        setSignature(accessKey, signature);
    }

    public String getExpiresRequestUrl() throws QSException {
        return this.builder.getExpiresRequestUrl();
    }

    private String check() {
        return this.paramBean != null ? paramBean.validateParam() : "";
    }

    public QSBuilder getBuilder() {
        return this.builder;
    }

    /** @return the progressListener */
    public BodyProgressListener getProgressListener() {
        return progressListener;
    }

    /** @param progressListener the progressListener to set */
    public void setProgressListener(BodyProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    /** @return the qsRequestBody */
    @Deprecated
    public QSRequestBody getQsRequestBody() {
        return qsRequestBody;
    }

    /** @param qsRequestBody the qsRequestBody to set */
    @Deprecated
    public void setQsRequestBody(QSRequestBody qsRequestBody) {
        this.qsRequestBody = qsRequestBody;
    }

    public CancellationHandler getCancellationHandler() {
        return cancellationHandler;
    }

    public void setCancellationHandler(CancellationHandler cancellationHandler) {
        this.cancellationHandler = cancellationHandler;
    }
}
