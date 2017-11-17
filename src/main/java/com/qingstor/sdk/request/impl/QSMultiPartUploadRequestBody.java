/****
 +-------------------------------------------------------------------------
 | Copyright (C) 2017 Yunify, Inc.
 +-------------------------------------------------------------------------
 | Licensed under the Apache License, Version 2.0 (the "License");
 | you may not use this work except in compliance with the License.
 | You may obtain a copy of the License in the LICENSE file, or at:
 |
 | http://www.apache.org/licenses/LICENSE-2.0
 |
 | Unless required by applicable law or agreed to in writing, software
 | distributed under the License is distributed on an "AS IS" BASIS,
 | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 | See the License for the specific language governing permissions and
 | limitations under the License.
 +-------------------------------------------------------------------------
*****/
package com.qingstor.sdk.request.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.QSRequestBody;
import com.qingstor.sdk.utils.QSLoggerUtil;
import com.qingstor.sdk.utils.QSStringUtil;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * @author karooli
 *
 */
public class QSMultiPartUploadRequestBody implements QSRequestBody {

	private static Logger logger =
            QSLoggerUtil.setLoggerHanlder(QSMultiPartUploadRequestBody.class.getName());
 
	@Override
	public RequestBody getRequestBody(String contentType, long contentLength, String method,
			Map<String, Object> bodyParams,Map<String, Object> queryParams) throws QSException {
		logger.info("----QSMultiPartUploadRequestBody---");
		MediaType mediaType = MediaType.parse(contentType);
        if (bodyParams != null && bodyParams.size() > 0) {
            RequestBody requestBody = null;
            Iterator iterator = bodyParams.entrySet().iterator();
            
            int partNumber =
                    Integer.parseInt(queryParams.get(QSConstant.PARAM_KEY_PART_NUMBER) + "");

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                Object bodyObj = bodyParams.get(key);
                if (bodyObj instanceof String) {
                    requestBody = RequestBody.create(mediaType, bodyObj.toString());
                } else if (bodyObj instanceof File) {

                    RandomAccessFile rFile = null;
                    try {
                        rFile = new RandomAccessFile((File) bodyObj, "r");
                        rFile.seek(contentLength * partNumber);
                        long contentLeft =
                                ((File) bodyObj).length() - contentLength * (partNumber + 1);
                        long readContentLength = contentLength;
                        if (contentLeft < 0) {
                            readContentLength += contentLeft;
                            readContentLength = readContentLength > 0 ? readContentLength : 0;
                        }
                        requestBody =
                                new MultiFileuploadRequestBody(contentType, rFile, readContentLength);
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
            return requestBody;
            //connection.getOutputStream().write(bodyContent.getBytes());
        } else {
            if (HttpMethod.permitsRequestBody(method)) {
                return new EmptyRequestBody(contentType);
            }
        }
        return null;
	}

	public Object getBodyContent(Map bodyContent) throws QSException {
        Iterator iterator = bodyContent.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object bodyObj = bodyContent.get(key);
            if (QSConstant.PARAM_TYPE_BODYINPUTFILE.equals(key)
                    || QSConstant.PARAM_TYPE_BODYINPUTSTREAM.equals(key)
                    || QSConstant.PARAM_TYPE_BODYINPUTSTRING.equals(key)) {
                return bodyObj;
            }
        }
        return QSStringUtil.getMapToJson(bodyContent).toString();
    }


}
