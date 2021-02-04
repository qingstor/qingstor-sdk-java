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
package com.qingstor.sdk.request.impl;

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.QSRequestBody;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author karooli */
public class QSNormalRequestBody implements QSRequestBody {
    private static final Logger log = LoggerFactory.getLogger(QSNormalRequestBody.class);

    @Override
    public RequestBody getRequestBody(
            String contentType,
            long contentLength,
            String method,
            Map<String, Object> bodyParams,
            Map<String, Object> queryParams)
            throws QSException {
        log.debug("----QSNormalRequestBody----");
        MediaType mediaType = MediaType.parse(contentType);
        if (bodyParams != null && bodyParams.size() > 0) {

            RequestBody body = null;
            Object bodyObj = getBodyContent(bodyParams);
            if (bodyObj instanceof String) {
                // use bytes to avoid okhttp auto add "; charset=utf-8" after MIME type, which could
                // cause signature mismatch.
                body =
                        RequestBody.create(
                                mediaType, ((String) bodyObj).getBytes(StandardCharsets.UTF_8));
            } else if (bodyObj instanceof File) {
                body = RequestBody.create(mediaType, (File) bodyObj);
            } else if (bodyObj instanceof InputStream) {
                body = new InputStreamUploadBody(contentType, (InputStream) bodyObj, contentLength);
            }
            return body;
            // connection.getOutputStream().write(bodyContent.getBytes());
        } else {
            if (HttpMethod.permitsRequestBody(method)) {
                return new EmptyRequestBody(contentType);
            }
        }
        return null;
    }

    public static Object getBodyContent(Map<String, Object> bodyContent) throws QSException {
        Optional<String> matched =
                bodyContent.keySet().stream()
                        .filter(
                                k ->
                                        (k.equals(QSConstant.PARAM_TYPE_BODYINPUTFILE)
                                                || k.equals(QSConstant.PARAM_TYPE_BODYINPUTSTREAM)
                                                || k.equals(QSConstant.PARAM_TYPE_BODYINPUTSTRING)))
                        .findFirst();
        if (matched.isPresent()) {
            return bodyContent.get(matched.get());
        }
        return QSStringUtil.getMapToJson(bodyContent).toString();
    }
}
