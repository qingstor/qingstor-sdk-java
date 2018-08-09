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

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.impl.ProgressRequestBody;
import com.qingstor.sdk.utils.QSLoggerUtil;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSStringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Request;
import okhttp3.RequestBody;


public class RequestHandler {

    private static Logger logger = QSLoggerUtil.setLoggerHanlder(RequestHandler.class.getName());

    private Map contextParam;

    private RequestInputModel paramBean;

    private Class outputClass;

    private ResponseCallBack asyncCallback;

    private QSBuilder builder;

    private BodyProgressListener progressListener;

    private CancellationHandler cancellationHandler;

    private QSRequestBody qsRequestBody;

    public RequestHandler(Map context, RequestInputModel paramBean, Class outputClass) throws QSException {
        this.contextParam = context;
        this.paramBean = paramBean;
        this.outputClass = outputClass;
        this.builder = new QSBuilder(context, paramBean);
    }

    public RequestHandler(Map context, RequestInputModel paramBean, ResponseCallBack asyncCallback) throws QSException {
        this.contextParam = context;
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
            EnvContext envContext = (EnvContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);
            Request request = this.getRequest();
            QSOkHttpRequestClient.getInstance()
                    .requestActionAsync(request, envContext.isSafeOkHttp(), this.asyncCallback);
        }
    }

    public OutputModel send() throws QSException {

        String validate = this.check();
        if (!QSStringUtil.isEmpty(validate)) {
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
            EnvContext envContext = (EnvContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);

            Request request = this.getRequest();

            return QSOkHttpRequestClient.getInstance()
                    .requestAction(request, envContext.isSafeOkHttp(), outputClass);
        }
    }


    private Request getRequest() throws QSException{
        checkDownloadRequest();
        RequestBody  body = this.builder.getRequestBody(this.getQsRequestBody());
    	if(this.getProgressListener() != null){
    		return this.builder.getRequest(new ProgressRequestBody(body,this.progressListener, getCancellationHandler()));
    	}
    	return this.builder.getRequest(body);
    }

    /**
     *  <p> OkHttp will use "Accept-Encoding: gzip" as default header,
     *  which may not get Content-Length form server when download. </p>
     */
    private void checkDownloadRequest() {
        if (outputClass == null) return;
        boolean isDownloadRequest = false;
        Field[] declaredField = outputClass.getDeclaredFields();
        for (Field field : declaredField) {
            String methodName = "get" + QSParamInvokeUtil.capitalize(field.getName());
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


    public String getStringToSignature() throws QSException {
        return this.builder.getStringToSignature();
    }

    public void setSignature(String accessKey, String signature) throws QSException {
        this.builder.setSignature(accessKey, signature);
    }

    /**
     * Set signature and server time.
     * @param accessKey accessKey
     * @param signature signature
     * @param gmtTime time format with GMT
     * @throws QSException exception
     */
    public void setSignature(String accessKey, String signature, String gmtTime) throws QSException {
        builder.setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
        setSignature(accessKey, signature);
    }

    public String getExpiresRequestUrl() throws QSException {
        return this.builder.getExpiresRequestUrl();
    }

    public String check() {
        String validate = this.paramBean != null ? paramBean.validateParam() : "";
        EnvContext envContext = (EnvContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);
        String envValidate = envContext.validateParam();
        if (!QSStringUtil.isEmpty(validate) || !QSStringUtil.isEmpty(envValidate)) {
            if (QSStringUtil.isEmpty(validate)) {
                validate = envValidate;
            }
            return validate;
        }
        return null;
    }

    public QSBuilder getBuilder() {
        return this.builder;
    }

	/**
	 * @return the progressListener
	 */
	public BodyProgressListener getProgressListener() {
		return progressListener;
	}

	/**
	 * @param progressListener the progressListener to set
	 */
	public void setProgressListener(BodyProgressListener progressListener) {
		this.progressListener = progressListener;
	}

	/**
	 * @return the qsRequestBody
	 */
	public QSRequestBody getQsRequestBody() {
		return qsRequestBody;
	}

	/**
	 * @param qsRequestBody the qsRequestBody to set
	 */
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
