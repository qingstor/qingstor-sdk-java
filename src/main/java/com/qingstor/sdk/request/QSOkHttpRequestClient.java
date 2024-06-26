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

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.utils.QSJSONUtil;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSOkHttpRequestClient {

    private static final Logger log = LoggerFactory.getLogger(QSOkHttpRequestClient.class);

    private final OkHttpClient client;

    private static volatile QSOkHttpRequestClient ins;

    protected QSOkHttpRequestClient(EnvContext ctx) {
        int readTimeout = ctx.getHttpConfig().getReadTimeout();
        int writeTimeout = ctx.getHttpConfig().getWriteTimeout();
        int connectionTimeout = ctx.getHttpConfig().getConnectionTimeout();
        client =
                new OkHttpClient.Builder()
                        .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                        .readTimeout(readTimeout, TimeUnit.SECONDS)
                        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                        .build();
    }

    public static QSOkHttpRequestClient getInstance(EnvContext ctx) {
        if (ins == null) {
            synchronized (QSOkHttpRequestClient.class) {
                if (ins == null) ins = new QSOkHttpRequestClient(ctx);
            }
        }
        return ins;
    }

    public <T extends OutputModel> T requestAction(
            Request request, boolean bSafe, Class<T> outputClass) throws QSException {
        Call okhttpCall = client.newCall(request);
        T model = QSParamInvokeUtil.getOutputModel(outputClass);
        try {
            Response response = okhttpCall.execute();
            fillResponseValue2Object(response, model);
            return model;
        } catch (Exception e) {
            if (e instanceof CancellationHandler.CancellationException) {
                fillResponseCallbackModel(
                        QSConstant.REQUEST_ERROR_CANCELLED, e.getMessage(), model);
                return model;
            } else {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new QSException(e.getMessage());
            }
        }
    }

    public <T extends OutputModel> void requestActionAsync(
            Request request,
            boolean bSafe,
            final ResponseCallBack<T> callBack,
            Class<T> outputClass) {
        Call okhttpCall = client.newCall(request);
        okhttpCall.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onOkhttpFailure(e, callBack, outputClass);
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            ResponseBody body = response.body();
                            if (callBack != null) {
                                T m = QSParamInvokeUtil.getOutputModel(outputClass);
                                fillResponseValue2Object(response, m);
                                callBack.onAPIResponse(m);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            onOkhttpFailure(e, callBack, outputClass);
                        } finally {
                            response.close();
                        }
                    }
                });
    }

    private <T extends OutputModel> void onOkhttpFailure(
            Exception e, ResponseCallBack<T> callBack, Class<T> outputClass) {
        log.debug("enter onOkhttpFailure");
        try {
            if (callBack != null) {
                // Check error code here.
                int errorCode = QSConstant.REQUEST_ERROR_CODE;
                if (e instanceof CancellationHandler.CancellationException)
                    errorCode = QSConstant.REQUEST_ERROR_CANCELLED; // Cancelled by users.
                T m = QSParamInvokeUtil.getOutputModel(outputClass);
                fillResponseCallbackModel(errorCode, e.getMessage(), m);
                callBack.onAPIResponse(m);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public static void fillResponseCallbackModel(int code, Object content, OutputModel model) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(QSConstant.QC_CODE_FIELD_NAME, code);
        errorMap.put(QSConstant.QC_MESSAGE_FIELD_NAME, content);

        QSJSONUtil.jsonFillValue2Object(QSStringUtil.getObjectToJson(errorMap), model);
    }

    private void fillResponseValue2Object(okhttp3.Response response, OutputModel target)
            throws IOException {
        int code = response.code();
        ResponseBody body = response.body();
        JSONObject o = QSJSONUtil.toJSONObject("{}");
        QSJSONUtil.putJsonData(
                o, QSConstant.PARAM_TYPE_BODYINPUTSTREAM, body.source().inputStream());
        if (target != null) {
            boolean binaryBody = false;
            if (response.isSuccessful()) {
                binaryBody = QSJSONUtil.jsonObjFillValue2Object(o, target);
            }
            if (!binaryBody) {
                String responseInfo = body.string();
                // Deserialize HTTP response to concrete type.
                if (!QSStringUtil.isEmpty(responseInfo)) {
                    QSJSONUtil.jsonFillValue2Object(responseInfo, target);
                }
            }
            Headers headers = response.headers();
            JSONObject headJson = QSJSONUtil.toJSONObject("{}");
            QSJSONUtil.putJsonData(headJson, QSConstant.QC_CODE_FIELD_NAME, code);
            for (int i = 0; i < headers.size(); i++) {
                String key = headers.name(i).toLowerCase();
                QSJSONUtil.putJsonData(headJson, key, headers.value(i));
            }
            QSJSONUtil.jsonObjFillValue2Object(headJson, target);
        }
    }

    /**
     * @param method request method name
     * @param url with signed param url
     * @param headers http head params
     * @param body request body params
     * @return a build request
     */
    @Deprecated
    public static Request buildRequest(
            final String method, final String url, RequestBody body, final Map headers) {

        Request.Builder builder = new Request.Builder();
        String[] sortedHeadersKeys = (String[]) headers.keySet().toArray(new String[] {});
        for (String key : sortedHeadersKeys) {
            builder.addHeader(key, String.valueOf(headers.get(key)));
        }
        return builder.url(url).method(method, body).build();
    }

    /**
     * @param signedUrl with singed parameter url
     * @return a build request
     */
    @Deprecated
    public static Request buildUrlRequest(final String signedUrl) {
        // Execute the request and retrieve the response.
        return new Request.Builder().url(signedUrl).build();
    }

    @Deprecated
    public OkHttpClient getSafetyClient() {
        return client;
    }
}
