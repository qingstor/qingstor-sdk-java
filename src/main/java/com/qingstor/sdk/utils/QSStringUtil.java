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
package com.qingstor.sdk.utils;

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** Created by on 11/4/15. */
public class QSStringUtil {

    public static String objectToJson(String key, Object o) throws QSException {
        String buffer = "{ \"" + key + "\":" + objectJSONKeyValue(key, o) + "}";
        return buffer;
    }

    private static String objectJSONKeyValue(String key, Object o) throws QSException {
        return " \"" + key + "\":" + objectJSONValue(o);
    }

    public static Object objectJSONValue(Object o) throws QSException {

        if (o instanceof List) {
            List lst = (List) o;
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < lst.size(); i++) {
                QSJSONUtil.putJsonData(jsonArray, i, objectJSONValue(lst.get(i)));
            }
            return jsonArray;
        } else if (o instanceof Integer
                || o instanceof Double
                || o instanceof Boolean
                || o instanceof Long
                || o instanceof Float
                || o instanceof String) {
            return o;
        } else if (o instanceof Map) {
            return getMapToJson((Map) o);
        } else {
            Map params = new HashMap();
            QSParamInvokeUtil.invokeObject2Map(o.getClass(), o, params);
            return getMapToJson(params);
        }
    }

    public static JSONObject getMapToJson(Map o) throws QSException {
        JSONObject json = new JSONObject();
        try {
            for (Object value : o.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                String key = (String) entry.getKey();
                Object bodyObj = o.get(key);
                json.put(key, objectJSONValue(bodyObj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new QSException("ObjectToJson", e);
        }

        return json;
    }

    public static String getObjectToJson(Object o) {
        JSONObject json = null;
        if (o instanceof Map) {
            json = new JSONObject();
            for (Object value : ((Map) o).entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                String key = (String) entry.getKey();
                Object bodyObj = ((Map) o).get(key);
                json.put(key, bodyObj);
            }
        } else {
            json = new JSONObject(o);
        }

        return json.toString();
    }

    public static String percentEncode(String value, String encoding)
            throws UnsupportedEncodingException {
        return value != null
                ? URLEncoder.encode(value, encoding)
                        .replace("+", "%20")
                        .replace("*", "%2A")
                        .replace("%7E", "~")
                : null;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str);
    }

    public static String getUserAgent() {
        String osName = System.getProperty("os.name"); // 操作系统名称
        String langVersion = System.getProperty("java.version"); // java.version系统版本
        return QSConstant.SDK_NAME
                + "/"
                + QSConstant.SDK_VERSION
                + " ( java v"
                + langVersion
                + ";"
                + osName
                + ")";
    }

    public static String getParameterRequired(String paraName, String value) {
        return String.format("%s is required in %s ", paraName, value);
    }

    public static String getParameterValueNotAllowedError(
            String paraName, String value, String[] values) {

        StringBuilder buf = new StringBuilder();
        for (Object o : values) {
            buf.append(o.toString()).append(",");
        }
        if (buf.length() > 0) {
            buf.setLength(buf.length() - 1);
        }

        return String.format(
                "%s value %s is not allowed, should be one of %s ",
                paraName, value, buf.toString());
    }

    /**
     * Chinese characters transform
     *
     * @param str unEncoded chars
     * @return encoded chars
     * @throws QSException UnsupportedEncodingException
     */
    public static String asciiCharactersEncoding(String str) throws QSException {
        if (QSStringUtil.isEmpty(str)) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(str, QSConstant.ENCODING_UTF8);
            encoded = encoded.replace("%2F", "/");
            encoded = encoded.replace("%3D", "=");
            encoded = encoded.replace("+", "%20");
            encoded = encoded.replace("%3A", ":");
            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new QSException("UnsupportedEncodingException:", e);
        }
    }

    public static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
