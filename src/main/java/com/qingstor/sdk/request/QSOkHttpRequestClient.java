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

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.utils.QSJSONUtil;
import com.qingstor.sdk.utils.QSLoggerUtil;
import com.qingstor.sdk.utils.QSParamInvokeUtil;
import com.qingstor.sdk.utils.QSStringUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSOkHttpRequestClient {

    private static Logger logger =
            QSLoggerUtil.setLoggerHanlder(QSOkHttpRequestClient.class.getName());

    private OkHttpClient client = null;
    private OkHttpClient unsafeClient = null;

    private static volatile QSOkHttpRequestClient ins;

    protected QSOkHttpRequestClient() {}

    public OkHttpClient getSafetyClient() {
        return new OkHttpClient.Builder()
                        .connectTimeout(QSConstant.HTTPCLIENT_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(QSConstant.HTTPCLIENT_READ_TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(QSConstant.HTTPCLIENT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                        .build();
    }

    @Deprecated
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts =
                    new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] chain, String authType)
                                    throws CertificateException {}

                            @Override
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] chain, String authType)
                                    throws CertificateException {}

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[] {};
                            }
                        }
                    };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder =
                    new OkHttpClient.Builder()
                            .connectTimeout(
                                    QSConstant.HTTPCLIENT_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(QSConstant.HTTPCLIENT_READ_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(QSConstant.HTTPCLIENT_WRITE_TIME_OUT, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(
                    new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static QSOkHttpRequestClient getInstance() {
        if (ins == null) {
            synchronized (QSOkHttpRequestClient.class) {
                if (ins == null) ins = new QSOkHttpRequestClient();
            }
        }
        return ins;
    }

    private Call getRequestCall(boolean bSafe, Request request) {
        if (bSafe) {
            if (client == null) {
                synchronized (QSOkHttpRequestClient.class) {
                    if (client == null) {
                        client = getSafetyClient();
                    }
                }
            }
            return client.newCall(request);
        } else {
            if (unsafeClient == null) {
                synchronized (QSOkHttpRequestClient.class) {
                    if (unsafeClient == null) {
                        unsafeClient = getUnsafeOkHttpClient();
                    }
                }
            }
            return this.unsafeClient.newCall(request);
        }
    }

    public OutputModel requestAction(Request request, boolean bSafe, Class outputClass)
            throws QSException {
        Call okhttpCall = getRequestCall(bSafe, request);
        okhttp3.Response response = null;
            OutputModel model = (OutputModel) QSParamInvokeUtil.getOutputModel(outputClass);
        try {
            response = okhttpCall.execute();
            fillResponseValue2Object(response, model);
            return model;
        } catch (Exception e) {
            if (e instanceof CancellationHandler.CancellationException) {
                fillResponseCallbackModel(QSConstant.REQUEST_ERROR_CANCELLED, e.getMessage(), model);
                return model;
            } else {
                e.printStackTrace();
                logger.log(Level.SEVERE, e.getMessage());
                throw new QSException(e.getMessage());
            }
        }
    }

    /**
     * @param singedUrl with singed parameter url
     * @return a build request
     */
    public Request buildUrlRequest(final String singedUrl) {

        Request request = new Request.Builder().url(singedUrl).build();

        // Execute the request and retrieve the response.
        return request;
    }

    public OutputModel requestActionAsync(
            Request request, boolean bSafe, final ResponseCallBack callBack) throws QSException {
        Call okhttpCall = getRequestCall(bSafe, request);
        okhttpCall.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onOkhttpFailure(e, callBack);
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response)
                            throws IOException {
                        try {
                            if (callBack != null) {
                                OutputModel m = QSParamInvokeUtil.getOutputModel(callBack);
                                fillResponseValue2Object(response, m);
                                callBack.onAPIResponse(m);
                            }
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, e.getMessage());
                            onOkhttpFailure(e, callBack);
                        } finally {
                            if (response != null) {
                                Util.closeQuietly(response.body().source());
                            }
                        }
                    }
                });
        return null;
    }

    private void onOkhttpFailure(Exception e, ResponseCallBack callBack) {
        try {
            if (callBack != null) {
                //Check error code here.
                int errorCode = QSConstant.REQUEST_ERROR_CODE;
                if (e instanceof CancellationHandler.CancellationException)
                    errorCode = QSConstant.REQUEST_ERROR_CANCELLED; // Cancelled by users.
                OutputModel m = QSParamInvokeUtil.getOutputModel(callBack);
                fillResponseCallbackModel(errorCode, e.getMessage(), m);
                callBack.onAPIResponse(m);
            }
        } catch (Exception ee) {
            logger.log(Level.SEVERE, ee.getMessage());
        }
    }

    private void fillResponseValue2Object(okhttp3.Response response, OutputModel target)
            throws IOException {
        int code = response.code();
        ResponseBody body = response.body();
        JSONObject o = QSJSONUtil.toJSONObject("{}");
        QSJSONUtil.putJsonData(
                o, QSConstant.PARAM_TYPE_BODYINPUTSTREAM, body.source().inputStream());
        if (target != null) {
            if (!QSJSONUtil.jsonObjFillValue2Object(o, target)) {
                try {
                    String responseInfo = body.string();
                    // Deserialize HTTP response to concrete type.
                    if (!QSStringUtil.isEmpty(responseInfo)) {
                        QSJSONUtil.jsonFillValue2Object(responseInfo, target);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Headers responceHeaders = response.headers();
            int iHeads = responceHeaders.size();
            JSONObject headJson = QSJSONUtil.toJSONObject("{}");
            QSJSONUtil.putJsonData(headJson, QSConstant.QC_CODE_FIELD_NAME, code);
            for (int i = 0; i < iHeads; i++) {
                String key = responceHeaders.name(i);
                if (key != null) key = key.toLowerCase();
                QSJSONUtil.putJsonData(headJson, key, responceHeaders.value(i));
            }
            QSJSONUtil.jsonObjFillValue2Object(headJson, target);
        }
    }

    /**
     * @param method     request method name
     * @param signedUrl  with signed param url
     * @param headParams http head params
     * @param requestBody request body params
     * @return a build request
     */
    public Request buildRequest(
            final String method, final String signedUrl, RequestBody requestBody, final Map headParams) {

        Request.Builder builder = new Request.Builder();
        String[] sortedHeadersKeys = (String[]) headParams.keySet().toArray(new String[]{});
        for (String key : sortedHeadersKeys) {
            builder.addHeader(key, String.valueOf(headParams.get(key)));
        }
        if (!headParams.containsKey(QSConstant.PARAM_KEY_USER_AGENT)) {
            builder.addHeader(QSConstant.PARAM_KEY_USER_AGENT, QSStringUtil.getUserAgent());
        }
        return builder.url(signedUrl).method(method, requestBody).build();
    }
    
    
    public static void fillResponseCallbackModel(int code, Object content, OutputModel model) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(QSConstant.QC_CODE_FIELD_NAME, code);
        errorMap.put(QSConstant.QC_MESSAGE_FIELD_NAME, content);

        QSJSONUtil.jsonFillValue2Object(QSStringUtil.getObjectToJson(errorMap), model);
    }
}
