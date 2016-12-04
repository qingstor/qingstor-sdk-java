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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.cert.CertificateException;
import java.util.Iterator;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;
import okio.BufferedSink;
import org.json.JSONObject;

public class QSOkHttpRequestClient {

    private static Logger logger =
            QSLoggerUtil.setLoggerHanlder(QSOkHttpRequestClient.class.getName());

    private OkHttpClient client = null;
    private OkHttpClient unsafeClient = null;

    private static QSOkHttpRequestClient ins;

    protected QSOkHttpRequestClient() {
        intiOkHttpClient();
    }

    public void intiOkHttpClient() {
        client =
                new OkHttpClient.Builder()
                        .connectTimeout(QSConstant.HTTPCLIENT_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(QSConstant.HTTPCLIENT_READ_TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(QSConstant.HTTPCLIENT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                        .build();
        unsafeClient = getUnsafeOkHttpClient();
    }

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
            return client.newCall(request);
        } else {
            return this.unsafeClient.newCall(request);
        }
    }

    public OutputModel requestAction(Request request, boolean bSafe, Class outputClass)
            throws QSException {
        Call okhttpCall = getRequestCall(bSafe, request);
        okhttp3.Response response = null;
        try {
            OutputModel model = (OutputModel) QSParamInvokeUtil.getOutputModel(outputClass);
            response = okhttpCall.execute();
            fillResponseValue2Object(response, model);
            return model;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new QSException(e.getMessage());
        }
    }

    /**
     * @param singedUrl
     * @param
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
                        onOkhttpFailure(e.getMessage(), callBack);
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
                            onOkhttpFailure(e.getMessage(), callBack);
                        } finally {
                            if (response != null) {
                                Util.closeQuietly(response.body().source());
                            }
                        }
                    }
                });
        return null;
    }

    private void onOkhttpFailure(String message, ResponseCallBack callBack) {
        try {
            if (callBack != null) {
                OutputModel m = QSParamInvokeUtil.getOutputModel(callBack);
                fillResponseCallbackModel(QSConstant.REQUEST_ERROR_CODE, message, m);
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
                }
            }
            Headers responceHeaders = response.headers();
            int iHeads = responceHeaders.size();
            JSONObject headJson = QSJSONUtil.toJSONObject("{}");
            QSJSONUtil.putJsonData(headJson, QSConstant.QC_CODE_FIELD_NAME, code);
            for (int i = 0; i < iHeads; i++) {
                QSJSONUtil.putJsonData(headJson, responceHeaders.name(i), responceHeaders.value(i));
            }
            QSJSONUtil.jsonObjFillValue2Object(headJson, target);
        }
    }

    /**
     * @param method
     * @param bodyContent
     * @param headParams
     * @param singedUrl
     * @throws QSException
     */
    public Request buildStorRequest(
            final String method,
            final Map bodyContent,
            final String singedUrl,
            final Map headParams)
            throws QSException {

        Request.Builder builder = new Request.Builder();
        Request request = null;
        String[] sortedHeadersKeys = (String[]) headParams.keySet().toArray(new String[] {});
        for (String key : sortedHeadersKeys) {
            builder.addHeader(key, headParams.get(key) + "");
        }
        if (!headParams.containsKey(QSConstant.PARAM_KEY_USER_AGENT)) {
            builder.addHeader(QSConstant.PARAM_KEY_USER_AGENT, QSStringUtil.getUserAgent());
        }

        String contentType = headParams.get(QSConstant.HEADER_PARAM_KEY_CONTENTTYPE) + "";
        MediaType mediaType = MediaType.parse(contentType);
        if (bodyContent != null && bodyContent.size() > 0) {

            RequestBody body = null;

            Iterator iterator = bodyContent.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                Object bodyObj = bodyContent.get(key);
                if (bodyObj instanceof String) {
                    body = RequestBody.create(mediaType, bodyObj.toString());
                } else if (bodyObj instanceof File) {
                    body = RequestBody.create(mediaType, (File) bodyObj);
                } else if (bodyObj instanceof InputStream) {
                    int contentLength = 0;
                    if (headParams.containsKey(QSConstant.PARAM_KEY_CONTENT_LENGTH)) {
                        contentLength =
                                Integer.parseInt(
                                        headParams.get(QSConstant.PARAM_KEY_CONTENT_LENGTH) + "");
                    }
                    body =
                            new InputStreamUploadBody(
                                    contentType, (InputStream) bodyObj, contentLength);
                } else {
                    String jsonStr = QSStringUtil.objectToJson(key, bodyObj);
                    body = RequestBody.create(mediaType, jsonStr);
                }
            }
            request = builder.url(singedUrl).method(method, body).build();
            //connection.getOutputStream().write(bodyContent.getBytes());
        } else {
            if (HttpMethod.permitsRequestBody(method)) {
                request =
                        builder.url(singedUrl)
                                .method(method, RequestBody.create(mediaType, ""))
                                .build();
            } else {
                request = builder.url(singedUrl).method(method, null).build();
            }
        }

        return request;
    }

    /**
     * @param method
     * @param bodyContent
     * @param headParams
     * @param singedUrl
     * @throws QSException
     */
    public Request buildStorMultiUpload(
            final String method,
            final Map bodyContent,
            final String singedUrl,
            final Map headParams,
            final Map queryParams)
            throws QSException {

        Request.Builder builder = new Request.Builder();
        String[] sortedHeadersKeys = (String[]) headParams.keySet().toArray(new String[] {});
        for (String key : sortedHeadersKeys) {
            builder.addHeader(key, headParams.get(key) + "");
        }
        if (!headParams.containsKey(QSConstant.PARAM_KEY_USER_AGENT)) {
            builder.addHeader(QSConstant.PARAM_KEY_USER_AGENT, QSStringUtil.getUserAgent());
        }
        if (bodyContent != null && bodyContent.size() > 0) {

            String contentType = headParams.get(QSConstant.HEADER_PARAM_KEY_CONTENTTYPE) + "";

            MediaType mediaType = MediaType.parse(contentType);
            RequestBody requestBody = null;

            Iterator iterator = bodyContent.entrySet().iterator();
            int contentLength =
                    Integer.parseInt(headParams.get(QSConstant.PARAM_KEY_CONTENT_LENGTH) + "");
            int partNumber =
                    Integer.parseInt(queryParams.get(QSConstant.PARAM_KEY_PART_NUMBER) + "");

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                Object bodyObj = bodyContent.get(key);
                if (bodyObj instanceof String) {
                    requestBody = RequestBody.create(mediaType, bodyObj.toString());
                } else if (bodyObj instanceof File) {

                    RandomAccessFile rFile = null;
                    try {
                        rFile = new RandomAccessFile((File) bodyObj, "r");
                        rFile.seek(contentLength * partNumber);
                        requestBody = new MulitFileuploadBody(contentType, rFile, contentLength);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new QSException(e.getMessage());
                    }

                } else if (bodyObj instanceof InputStream) {

                    requestBody =
                            new InputStreamUploadBody(
                                    contentType, (InputStream) bodyObj, contentLength);

                } else {
                    String jsonStr = QSStringUtil.objectToJson(key, bodyObj);
                    requestBody = RequestBody.create(mediaType, jsonStr);
                }
            }
            //connection.getOutputStream().write(bodyContent.getBytes());
            if (requestBody != null) {
                builder.method(method, requestBody);
            }
        }

        Request request = builder.url(singedUrl).build();
        return request;
    }

    private static class MulitFileuploadBody extends RequestBody {

        private String contentType;

        private int contentLength;

        private RandomAccessFile file;

        public MulitFileuploadBody(String contentType, RandomAccessFile rFile, int contentLength) {
            this.contentLength = contentLength;
            this.contentType = contentType;
            this.file = rFile;
        }

        @Override
        public long contentLength() throws IOException {
            return this.contentLength;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(this.contentType);
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {

            int readSize = 1024;
            int bytes = 0;
            byte[] bufferOut = new byte[readSize];
            int count = contentLength / readSize;
            int leftCount = contentLength % readSize;
            while (count > 0 && (bytes = file.read(bufferOut)) != -1) {
                sink.write(bufferOut, 0, bytes);
                count--;
            }
            if (count == 0 && leftCount > 0) {
                bufferOut = new byte[leftCount];
                if ((bytes = file.read(bufferOut)) != -1) {
                    sink.write(bufferOut, 0, bytes);
                }
            }

            Util.closeQuietly(file);
        }
    }

    private static class InputStreamUploadBody extends RequestBody {

        private String contentType;

        private int contentLength;

        private InputStream file;

        public InputStreamUploadBody(String contentType, InputStream rFile, int contentLength) {
            this.contentLength = contentLength;
            this.contentType = contentType;
            this.file = rFile;
        }

        @Override
        public long contentLength() throws IOException {
            return this.contentLength;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(this.contentType);
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {

            int readSize = 1024;
            int bytes = 0;
            byte[] bufferOut = new byte[readSize];
            int count = contentLength / readSize;
            int leftCount = contentLength % readSize;
            while (count > 0 && (bytes = file.read(bufferOut)) != -1) {
                sink.write(bufferOut, 0, bytes);
                count--;
            }
            if (count == 0 && leftCount > 0) {
                bufferOut = new byte[leftCount];
                if ((bytes = file.read(bufferOut)) != -1) {
                    sink.write(bufferOut, 0, bytes);
                }
            }

            Util.closeQuietly(file);
        }
    }

    public static void fillResponseCallbackModel(int code, Object content, OutputModel model) {
        String errorJson =
                String.format(
                        "{'%s':%s,'%s':'%s'}",
                        QSConstant.QC_CODE_FIELD_NAME,
                        code + "",
                        QSConstant.QC_MESSAGE_FIELD_NAME,
                        content);
        QSJSONUtil.jsonFillValue2Object(errorJson, model);
    }
}
