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
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

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
public class QSFormRequestBody implements QSRequestBody {
	
	private static Logger logger =
            QSLoggerUtil.setLoggerHanlder(QSFormRequestBody.class.getName());
 
	@Override
	public RequestBody getRequestBody(String contentType, long contentLength, String method,
			Map<String, Object> bodyParams, Map<String, Object> queryParams) throws QSException {
		logger.info("----QSFormRequestBody----");
		MediaType mediaType = MediaType.parse(contentType);

		if (bodyParams != null && bodyParams.size() > 0) {
			Iterator iterator = bodyParams.entrySet().iterator();
			if (checkHasFileBody(bodyParams)) {
				MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
				String fileKey = "";
				File file = null;
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					String key = (String) entry.getKey();
					Object bodyObj = bodyParams.get(key);
					if (bodyObj instanceof File) {
						fileKey = key;
						file = (File) bodyObj;
					} else if (bodyObj instanceof InputStream) {
						requestBody.addFormDataPart("form-data", key,
								new InputStreamUploadBody(contentType, (InputStream) bodyObj, contentLength));
					} else if (bodyObj instanceof Map) {
						requestBody.addFormDataPart(key, QSStringUtil.getMapToJson((Map) bodyObj).toString());
					} else {
						Object json = QSStringUtil.objectJSONValue(bodyObj);
						requestBody.addFormDataPart(key, String.valueOf(json));
					}
				}
				if (file != null) {
					requestBody.addFormDataPart(fileKey, file.getName(), RequestBody.create(mediaType, file));
				}
				return requestBody.build();
			} else {
				FormBody.Builder requestFormBody = new FormBody.Builder();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					String key = (String) entry.getKey();
					Object bodyObj = bodyParams.get(key);
					Object json = QSStringUtil.objectJSONValue(bodyObj);

					requestFormBody.add(key, String.valueOf(json));
				}
				return requestFormBody.build();
			}
		} else {
			if (HttpMethod.permitsRequestBody(method)) {
				return new EmptyRequestBody(contentType);
			} else {
				return null;
			}
		}
	}

	private boolean checkHasFileBody(Map formParams) {
		Iterator iterator = formParams.entrySet().iterator();
		boolean hasFile = false;
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Object bodyObj = formParams.get(key);
			if (bodyObj instanceof File) {
				hasFile = true;
			} else if (bodyObj instanceof InputStream) {
				hasFile = true;
			}
		}

		return hasFile;
	}

}
