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
package com.qingstor.sdk.constants;

public class QSConstant {

    public static String SDK_VERSION = "2.3.0";
    public static String SDK_NAME = "qingstor-sdk-java";

    public static String QC_CODE_FIELD_NAME = "statue_code";
    public static String QC_MESSAGE_FIELD_NAME = "message";

    public static final String PARAM_TYPE_QUERY = "query";
    public static final String PARAM_TYPE_BODY = "body";
    public static final String PARAM_TYPE_HEADER = "header";
    public static final String PARAM_TYPE_FORM_DATA = "formData";

    public static final String PARAM_TYPE_BODYINPUTSTREAM = "BodyInputStream";
    public static final String PARAM_TYPE_BODYINPUTSTRING = "BodyInput";
    public static final String PARAM_TYPE_BODYINPUTFILE = "BodyInputFile";

    public static final String PARAM_KEY_BUCKET_NAME = "bucketNameInput";
    public static final String PARAM_KEY_OBJECT_NAME = "objectNameInput";
    public static final String PARAM_KEY_REQUEST_PATH = "RequestURI";
    public static final String PARAM_KEY_REQUEST_METHOD = "RequestMethod";
    public static final String PARAM_KEY_REQUEST_ZONE = "RequestZone";

    public static final String PARAM_KEY_REQUEST_API_MULTIPART = "UploadMultipart";
    public static final String PARAM_KEY_REQUEST_API_DELETE_MULTIPART = "DeleteMultipleObjects";
    public static final String PARAM_KEY_REQUEST_APINAME = "APIName";

    public static final String PARAM_KEY_CONTENT_LENGTH = "content-length";
    public static final String PARAM_KEY_CONTENT_MD5 = "content-md5";
    public static final String PARAM_KEY_USER_AGENT = "User-Agent";
    public static final String PARAM_KEY_EXPIRES = "expires";
    public static final String PARAM_KEY_PART_NUMBER = "part_number";
    public static final String PARAM_KEY_FILE_OFFSET = "file_offset";
    public static final String PARAM_KEY_METADATA = "x-qs-metadata";

    public static final String BUCKET_PLACEHOLDER = "<bucket-name>";
    public static final String OBJECT_PLACEHOLDER = "<object-key>";

    public static final String CONTENT_TYPE_TEXT = "application/json";

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String ENV_CONTEXT_KEY = "envContext";

    public static final String SDK_TYPE_IAAS = "qingcloud_iaas";

    public static final String SDK_TYPE_STOR = "qingcloud_stor";

    public static final String HEADER_PARAM_KEY_DATE = "Date";

    public static final String HEADER_PARAM_KEY_EXPIRES = "Expires";

    public static final String HEADER_PARAM_KEY_CONTENTTYPE = "content-type";

    public static final String HEADER_PARAM_KEY_AUTHORIZATION = "Authorization";

    public static final String HEADER_PARAM_KEY_SIGNATURE = "Signature";

    public static final int REQUEST_ERROR_CODE = 10000;
    public static final int REQUEST_ERROR_CANCELLED = 20000;

    public static int HTTPCLIENT_CONNECTION_TIME_OUT = 60; // Seconds
    public static int HTTPCLIENT_READ_TIME_OUT = 100; // Seconds
    public static int HTTPCLIENT_WRITE_TIME_OUT = 100; // Seconds

    /** default url style, like this: https://bucket-name.zone-id.qingstor.com/object-name */
    public static final String VIRTUAL_HOST_STYLE = "virtual_host_style";

    /** https://zone-id.qingstor.com/bucket-name/object-name */
    public static final String PATH_STYLE = "path_style";
}
