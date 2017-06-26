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
import com.qingstor.sdk.utils.QSStringUtil;
import okhttp3.Request;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RequestHandler {

    private static Logger logger = QSLoggerUtil.setLoggerHanlder(RequestHandler.class.getName());

    private Map contextParam;

    private RequestInputModel paramBean;

    private Class outputClass;

    private ResponseCallBack asyncCallback;

    private QSBuilder builder;

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
            EvnContext evnContext = (EvnContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);
            Request request = this.builder.getRequest();
            QSOkHttpRequestClient.getInstance()
                    .requestActionAsync(request, evnContext.isSafeOkHttp(), this.asyncCallback);
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
            EvnContext evnContext = (EvnContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);
            Request request = this.builder.getRequest();
            return QSOkHttpRequestClient.getInstance()
                    .requestAction(request, evnContext.isSafeOkHttp(), outputClass);
        }
    }


    public String getStringToSignature() throws QSException {
        return this.builder.getStringToSignature();
    }

    public void setSignature(String accessKey, String signature) throws QSException {
        this.builder.setSignature(accessKey, signature);
    }

    public String getExpiresRequestUrl() throws QSException {
        return this.builder.getExpiresRequestUrl();
    }

    public String check() {
        String validate = this.paramBean != null ? paramBean.validateParam() : "";
        EvnContext evnContext = (EvnContext) this.contextParam.get(QSConstant.EVN_CONTEXT_KEY);
        String evnValidate = evnContext.validateParam();
        if (!QSStringUtil.isEmpty(validate) || !QSStringUtil.isEmpty(evnValidate)) {
            if (QSStringUtil.isEmpty(validate)) {
                validate = evnValidate;
            }
            return validate;
        }
        return null;
    }

    public QSBuilder getBuilder() {
        return this.builder;
    }


}
