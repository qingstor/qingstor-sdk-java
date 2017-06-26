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

import com.qingstor.sdk.annotation.ParamAnnotation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class QSJSONUtil {

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Iterator<String> keysItr = object.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                Object value = object.get(key);

                if (value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }

                if (value != null) {
                    map.put(key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        try {
            for (int i = 0, cnt = array.length(); i < cnt; i++) {
                Object value = array.get(i);
                if (value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                list.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String toString(JSONObject object, String key) {
        String rst = "";
        if (object == null || object.isNull(key)) return rst;
        try {
            rst = object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static Double toDouble(JSONArray labelDatas, int i) {
        if (labelDatas == null || labelDatas.length() <= i) return 0.0;
        double rst = 0;
        try {
            rst = labelDatas.getDouble(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static Object toObject(JSONArray labelDatas, int i) {
        if (labelDatas == null || labelDatas.length() <= i) return null;
        Object rst = null;
        try {
            rst = labelDatas.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static Object toObject(JSONObject labelDatas, String key) {
        if (labelDatas == null || labelDatas.isNull(key)) return null;
        Object rst = null;
        try {
            rst = labelDatas.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static boolean toBoolean(JSONObject object, String key) {
        if (object == null || object.isNull(key)) return false;
        boolean rst = false;
        try {
            rst = object.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static int toInt(JSONObject object, String key) {
        int i = -1;
        try {
            i = object.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static double toDouble(JSONObject object, String key) {
        double i = -1;
        try {
            i = object.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static List toList(JSONObject obj, String key) {
        if (obj == null || obj.isNull(key)) return null;
        ArrayList llist = new ArrayList();
        try {
            JSONArray array = obj.getJSONArray(key);
            for (int i = 0, c = array.length(); i < c; i++) {
                llist.add(array.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return llist;
    }

    public static JSONArray toJSONArray(JSONObject obj, String key) {
        JSONArray res = null;
        if (obj == null || obj.isNull(key)) return null;
        try {
            res = obj.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Object toJSONObject(JSONObject obj, int index) {
        JSONArray res = null;
        if (obj == null || obj.length() < index) return null;
        Iterator<String> it = obj.keys();
        int i = 0;
        while (it.hasNext()) {
            if (index == i) {
                String key = it.next();
                return QSJSONUtil.toObject(obj, key);
            }
            i++;
        }
        return res;
    }

    public static String toJSONkey(JSONObject obj, int index) {
        String key = null;
        if (obj == null || obj.length() < index) return null;
        Iterator<String> it = obj.keys();
        int i = 0;
        while (it.hasNext()) {
            if (index == i) {
                key = it.next();
            } else {
                it.next();
            }
            i++;
        }
        return key;
    }

    public static JSONArray toJSONArray(JSONArray obj, int i) {
        JSONArray res = null;
        if (obj.length() > i) {
            try {
                res = obj.getJSONArray(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static JSONObject toJSONObject(String str) {
        JSONObject obj = null;
        try {
            if (str != null) obj = new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject toJSONObject(JSONObject jsonArray, String key) {
        JSONObject res = null;
        if (jsonArray == null || jsonArray.isNull(key)) {
            return null;
        }
        try {
            res = jsonArray.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int toJSONInt(JSONArray jsonArray, int index) {
        int obj = 0;
        if (jsonArray == null || jsonArray.length() <= index) {
            return obj;
        }
        try {
            obj = jsonArray.getInt(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void putJsonData(JSONObject jsonObject, String key, Object value) {
        if (jsonObject != null) {
            try {
                jsonObject.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void putJsonData(JSONArray jsonObject, int index, Object value) {
        if (jsonObject != null) {
            try {
                jsonObject.put(index, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String toJSONString(JSONArray jsonArray, int index) {
        String obj = "";
        if (jsonArray == null || jsonArray.length() <= index) {
            return obj;
        }
        try {
            obj = jsonArray.getString(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject toJSONObject(JSONArray jsonArray, int index) {
        JSONObject obj = null;
        if (jsonArray == null) return null;
        try {
            obj = jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static List sortJSONArray(JSONArray array) {
        LinkedList llist = new LinkedList();
        try {
            for (int i = 0, c = array.length(); i < c; i++) {
                llist.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(
                (List<String>) llist,
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s2.compareToIgnoreCase(s1);
                    }
                });
        return llist;
    }

    public static void sortJSONArray(List array, final String key) {

        Collections.sort(
                (List<JSONObject>) array,
                new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        String v1 = QSJSONUtil.toString(o1, key);
                        String v2 = QSJSONUtil.toString(o2, key);
                        if (v2 == null) {
                            v2 = "";
                        }
                        if (v1 == null) {
                            v1 = "";
                        }
                        return v2.compareToIgnoreCase(v1);
                    }
                });
    }

    public static void sortJSONArrayAscending(List array, final String key) {

        Collections.sort(
                (List<JSONObject>) array,
                new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        String v1 = QSJSONUtil.toString(o1, key);
                        String v2 = QSJSONUtil.toString(o2, key);
                        return v1.compareToIgnoreCase(v2);
                    }
                });
    }

    public static JSONObject convertJSONObject(String string) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void responceFillValue2Object(okhttp3.Response response, Object targetObj) {
    }

    public static boolean jsonFillValue2Object(String jsonStr, Object targetObj) {
        JSONObject o = toJSONObject(jsonStr);
        return jsonObjFillValue2Object(o, targetObj);
    }

    public static boolean jsonObjFillValue2Object(JSONObject o, Object targetObj) {
        boolean hasParam = false;
        if (targetObj != null) {
            try {
                Class tmpClass = targetObj.getClass();
                while (tmpClass != Object.class) {
                    Field[] fields = tmpClass.getDeclaredFields();
                    if (initParameter(o, fields, tmpClass, targetObj)) {
                        hasParam = true;
                    }
                    tmpClass = tmpClass.getSuperclass();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hasParam;
    }

    private static boolean initParameter(
            JSONObject o, Field[] declaredField, Class objClass, Object targetObj)
            throws NoSuchMethodException {
        boolean hasParam = false;
        for (Field field : declaredField) {
            String methodField = QSParamInvokeUtil.capitalize(field.getName());
            String getMethodName =
                    field.getType() == Boolean.class ? "is" + methodField : "get" + methodField;
            String setMethodName = "set" + methodField;
            Method[] methods = objClass.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(getMethodName)) {
                    ParamAnnotation annotation = m.getAnnotation(ParamAnnotation.class);
                    if (annotation == null) {
                        continue;
                    }
                    String dataKey = annotation.paramName();

                    if (o.has(dataKey)) {
                        hasParam = true;
                        Object data = toObject(o, dataKey);
                        Method setM = objClass.getDeclaredMethod(setMethodName, field.getType());
                        setParameterToMap(setM, targetObj, field, data);
                    }
                }
            }
        }
        return hasParam;
    }

    private static void setParameterToMap(Method m, Object source, Field f, Object data) {
        if (data != null) {
            try {
                if (data instanceof JSONArray || data instanceof JSONObject) {
                    Class fClass = f.getType();
                    if (fClass.equals(List.class)) {
                        List invokeData = new ArrayList();
                        ParameterizedType stringListType = (ParameterizedType) f.getGenericType();
                        Class<?> cls = (Class<?>) stringListType.getActualTypeArguments()[0];

                        if (cls.equals(String.class)
                                || cls.equals(Integer.class)
                                || cls.equals(Double.class)
                                || cls.equals(Long.class)
                                || cls.equals(Float.class)) {
                            if (data instanceof JSONArray) {
                                JSONArray jsonData = (JSONArray) data;
                                for (int i = 0; i < jsonData.length(); i++) {

                                    Object o = toObject(jsonData, i);
                                    invokeData.add(o);
                                }
                            }
                        } else {
                            if (data instanceof JSONArray) {
                                JSONArray jsonData = (JSONArray) data;
                                for (int i = 0; i < jsonData.length(); i++) {
                                    Object fObject = cls.newInstance();
                                    JSONObject o = toJSONObject(jsonData, i);
                                    Class tmpClass = fObject.getClass();
                                    while (tmpClass != Object.class) {
                                        Field[] fields = tmpClass.getDeclaredFields();
                                        initParameter(o, fields, tmpClass, fObject);
                                        tmpClass = tmpClass.getSuperclass();
                                    }
                                    invokeData.add(fObject);
                                }
                            }
                        }

                        m.invoke(source, invokeData);

                    } else if (fClass.equals(Map.class)) {
                        Map invokeData = new HashMap();
                        if (data instanceof JSONObject) {
                            JSONObject jsonData = (JSONObject) data;
                            for (int i = 0; i < jsonData.length(); i++) {
                                String key = toJSONObject(jsonData, i) + "";
                                Object value = toJSONObject(jsonData, key);
                                invokeData.put(key, value);
                            }
                        }
                        m.invoke(source, invokeData);
                    } else {

                        Object invokeData = f.getType().newInstance();
                        Class tmpClass = invokeData.getClass();
                        while (tmpClass != Object.class) {
                            Field[] fields = tmpClass.getDeclaredFields();
                            initParameter((JSONObject) data, fields, tmpClass, invokeData);
                            tmpClass = tmpClass.getSuperclass();
                        }
                        m.invoke(source, invokeData);
                    }

                } else {
                    if (f.getType().equals(data.getClass())) {
                        m.invoke(source, data);
                    } else {
                        m.invoke(source, getParseValue(f.getType(), data));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Object getParseValue(Class type, Object value) {
        if (String.class.equals(type)) {
            return value.toString();
        } else if (Integer.class.equals(type) || "int".equals(type.toString())) {
            return Integer.parseInt(value.toString());
        } else if (Double.class.equals(type) || "double".equals(type.toString())) {
            return Double.parseDouble(value.toString());
        } else if (Long.class.equals(type) || "long".equals(type.toString())) {
            return Long.parseLong(value.toString());
        } else if (Float.class.equals(type) || "float".equals(type.toString())) {
            return Float.parseFloat(value.toString());
        }
        return value;
    }
}
