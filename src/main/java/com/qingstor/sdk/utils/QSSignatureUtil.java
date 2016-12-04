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

package com.qingstor.sdk.utils;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.RequestInputModel;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class QSSignatureUtil {
    private static Logger logger = QSLoggerUtil.setLoggerHanlder(QSSignatureUtil.class.getName());

    private static final String ENCODING = "UTF-8";
    private static final String ALGORITHM = "HmacSHA256";
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String GMT_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    private static Map keysMap;

    public static String generateQSURL(Map<String, String> parameters, String requestUrl)
            throws QSException {

        parameters = QSParamInvokeUtil.serializeParams(parameters);
        StringBuilder sbStringToSign = new StringBuilder();

        String[] sortedKeys = parameters.keySet().toArray(new String[] {});
        Arrays.sort(sortedKeys);
        String query = "";
        int count = 0;

        try {
            for (String key : sortedKeys) {
                if (count != 0) {
                    sbStringToSign.append("&");
                }
                sbStringToSign
                        .append(QSStringUtil.percentEncode(key, QSConstant.ENCODING_UTF8))
                        .append("=")
                        .append(
                                QSStringUtil.percentEncode(
                                        parameters.get(key), QSConstant.ENCODING_UTF8));
                count++;
            }
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new QSException("generateQSURL error", e);
        }

        if (sbStringToSign.length() > 0) {

            if (requestUrl.indexOf("?") > 0) {
                return String.format("%s&%s", requestUrl, sbStringToSign.toString());
            } else {
                return String.format("%s?%s", requestUrl, sbStringToSign.toString());
            }
        }
        return requestUrl;
    }

    /**
     * Generate signature for request against QingStor.
     *
     * @param accessKey: API access key ID
     * @param secretKey: API secret access key ID
     * @param method: HTTP method
     * @param authPath:
     * @param params: HTTP request parameters
     * @param headers: HTTP request headers
     * @return a string which can be used as value of HTTP request header field "Authorization"
     *     directly.
     *     <p>See https://docs.qingcloud.com/qingstor/api/common/signature.html for more details
     *     about how to do signature of request against QingStor.
     */
    public static String getAuth(
            String accessKey,
            String secretKey,
            String method,
            String authPath,
            Map<String, String> params,
            Map<String, String> headers) {
        String signature = getSignature(accessKey, secretKey, method, authPath, params, headers);
        return String.format("QS %s:%s", accessKey, signature);
    }

    /**
     * Generate signature for request against QingStor.
     *
     * @param accessKey: API access key ID
     * @param secretKey: API secret access key ID
     * @param method: HTTP method
     * @param authPath:
     * @param params: HTTP request parameters
     * @param headers: HTTP request headers
     * @return a string which can be used as value of HTTP request header field "Authorization"
     *     directly.
     *     <p>See https://docs.qingcloud.com/qingstor/api/common/signature.html for more details
     *     about how to do signature of request against QingStor.
     */
    public static String getSignature(
            String accessKey,
            String secretKey,
            String method,
            String authPath,
            Map<String, String> params,
            Map<String, String> headers) {
        final String SEPARATOR = "&";
        String signature = "";
        String strToSign = "";

        strToSign += method.toUpperCase() + "\n";

        String contentMD5 = "";
        String contentType = "";
        if (headers != null) {
            if (headers.containsKey("Content-MD5")) contentMD5 = headers.get("Content-MD5");
            if (headers.containsKey("Content-Type")) contentType = headers.get("Content-Type");
        }
        strToSign += contentMD5 + "\n";
        strToSign += contentType;

        // Append request time as string
        String dateStr = "";
        if (headers != null) {
            if (headers.containsKey(QSConstant.HEADER_PARAM_KEY_DATE)) {
                dateStr = headers.get(QSConstant.HEADER_PARAM_KEY_DATE);
            }
            if (headers.containsKey(QSConstant.HEADER_PARAM_KEY_EXPIRES)) {
                dateStr = headers.get(QSConstant.HEADER_PARAM_KEY_EXPIRES);
            }
        }
        strToSign += "\n" + dateStr;

        // Generate signed headers.
        if (headers != null) {
            String[] sortedHeadersKeys = headers.keySet().toArray(new String[] {});
            Arrays.sort(sortedHeadersKeys);
            for (String key : sortedHeadersKeys) {
                if (!key.startsWith("x-qs-") && !key.startsWith("X-QS-")) continue;
                strToSign += String.format("\n%s:%s", key.toLowerCase(), headers.get(key));
            }
        }

        // Generate canonicalized query string.
        String canonicalized_query = "";
        if (params != null) {
            String[] sortedParamsKeys = params.keySet().toArray(new String[] {});
            Arrays.sort(sortedParamsKeys);
            for (String key : sortedParamsKeys) {

                if (!isSubKey(key)) {
                    continue;
                }

                if (!canonicalized_query.isEmpty()) {
                    canonicalized_query += SEPARATOR;
                }

                try {
                    canonicalized_query += URLEncoder.encode(key, ENCODING);
                    String value = params.get(key);
                    if (!value.isEmpty()) {
                        canonicalized_query += "=" + URLEncoder.encode(value, ENCODING);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Generate canonicalized resource.
        String canonicalized_resource = authPath;
        if (!canonicalized_query.isEmpty()) {
            canonicalized_resource += "?" + canonicalized_query;
        }
        strToSign += String.format("\n%s", canonicalized_resource);

        logger.log(Level.INFO, "== String to sign ==\n" + strToSign + "\n");

        signature = genSignature(secretKey, strToSign);
        return signature;
    }

    public static String genSignature(String secretKey, String strToSign) {
        byte[] signData = null;
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secretKey.getBytes(ENCODING), ALGORITHM));
            signData = mac.doFinal(strToSign.getBytes(ENCODING));
            // return new String(Base64.encodeBase64(signData));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }

        // Base64 encoder = Base64.getEncoder();
        return new String(Base64.encode(signData));
    }

    public static boolean isSubKey(String key) {
        if (keysMap == null) {
            keysMap =
                    new HashMap() {
                        {
                            put("acl", "acl");
                            put("cors", "cors");
                            put("delete", "delete");
                            put("mirror", "mirror");
                            put("part_number", "part_number");
                            put("policy", "policy");
                            put("stats", "stats");
                            put("upload_id", "upload_id");
                            put("uploads", "uploads");
                        }
                    };
        }
        return keysMap.containsKey(key);
    }

    public static String formatIso8601Date(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }

    public static String formatGmtDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(GMT_DATE_FORMAT, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }

    public static String getObjectAuthRequestUrl(
            EvnContext evnContext,
            String zone,
            String bucketName,
            String objectName,
            int expiresSecond)
            throws QSException {
        Map context = new HashMap();
        try {
            objectName = percentEncode(objectName);
            context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, zone);
            context.put(QSConstant.EVN_CONTEXT_KEY, evnContext);
            context.put("OperationName", "GetObject");
            context.put("APIName", "GetObject");
            context.put("ServiceName", "QingStor");
            context.put("RequestMethod", "GET");
            context.put("RequestURI", "/<bucket-name>/<object-key>");
            context.put("bucketNameInput", bucketName);
            context.put("objectNameInput", objectName);
            int expiresTime = (int) (new Date().getTime() / 1000 + expiresSecond);
            String expireAuth = getExpireAuth(context, expiresTime, new RequestInputModel());
            String serviceUrl = evnContext.getRequestUrl();
            String storRequestUrl = serviceUrl.replace("://", "://%s." + zone + ".");
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
        } catch (UnsupportedEncodingException e) {
            throw new QSException("Auth signature error", e);
        }
    }

    public static String percentEncode(String value) throws UnsupportedEncodingException {
        return value != null
                ? URLEncoder.encode(value, QSConstant.ENCODING_UTF8)
                        .replaceAll("%2F", "/")
                        .replaceAll("%2B", "+")
                        .replaceAll("%2A", "*")
                        .replaceAll("%7E", "~")
                : null;
    }

    public static String getExpireAuth(Map context, int expiresSecond, RequestInputModel params)
            throws UnsupportedEncodingException {

        EvnContext evnContext = (EvnContext) context.get(QSConstant.EVN_CONTEXT_KEY);

        Map paramsQuery = QSParamInvokeUtil.getRequestParams(params, QSConstant.PARAM_TYPE_QUERY);
        Map paramsHeaders =
                QSParamInvokeUtil.getRequestParams(params, QSConstant.PARAM_TYPE_HEADER);
        paramsHeaders.remove(QSConstant.HEADER_PARAM_KEY_DATE);
        paramsHeaders.clear();
        paramsHeaders.put(QSConstant.HEADER_PARAM_KEY_EXPIRES, expiresSecond + "");

        String method = (String) context.get(QSConstant.PARAM_KEY_REQUEST_METHOD);
        String bucketName = (String) context.get(QSConstant.PARAM_KEY_BUCKET_NAME);
        String requestPath = (String) context.get(QSConstant.PARAM_KEY_REQUEST_PATH);
        requestPath = requestPath.replace(QSConstant.BUCKET_NAME_REPLACE, bucketName);
        if (context.containsKey(QSConstant.PARAM_KEY_OBJECT_NAME)) {
            requestPath =
                    requestPath.replace(
                            QSConstant.OBJECT_NAME_REPLACE,
                            (String) context.get(QSConstant.PARAM_KEY_OBJECT_NAME));
        }
        String authSign =
                getSignature(
                        evnContext.getAccessKey(),
                        evnContext.getAccessSecret(),
                        method,
                        requestPath,
                        paramsQuery,
                        paramsHeaders);

        return URLEncoder.encode(authSign, QSConstant.ENCODING_UTF8);
    }
}
