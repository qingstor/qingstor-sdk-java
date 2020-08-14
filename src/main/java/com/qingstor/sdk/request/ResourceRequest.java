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
package com.qingstor.sdk.request;

import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import java.util.Map;

public interface ResourceRequest {

    /**
     * @param context EnvContext
     * @param paramBean param
     * @param callback callback
     * @throws QSException exception
     */
    void sendApiRequestAsync(Map context, RequestInputModel paramBean, ResponseCallBack callback)
            throws QSException;

    /**
     * @param context EnvContext
     * @param paramBean param
     * @param outputClass output class
     * @return OutputModel
     * @throws QSException exception
     */
    OutputModel sendApiRequest(
            Map context, RequestInputModel paramBean, Class<? extends OutputModel> outputClass)
            throws QSException;

    /**
     * @param requestUrl request url
     * @param context EnvContext
     * @param callback callback
     * @throws QSException exception
     */
    void sendApiRequestAsync(String requestUrl, Map context, ResponseCallBack callback)
            throws QSException;

    /**
     * @param requestUrl request url
     * @param context context
     * @param outputClass output class
     * @return output model
     * @throws QSException exception
     */
    OutputModel sendApiRequest(
            String requestUrl, Map context, Class<? extends OutputModel> outputClass)
            throws QSException;

    /**
     * @param context context
     * @param paramBean param
     * @param callback callback
     * @return request handle
     * @throws QSException exception
     */
    RequestHandler getRequestAsync(
            Map context, RequestInputModel paramBean, ResponseCallBack callback) throws QSException;

    /**
     * @param paramBean param
     * @param context envContext
     * @param outputClass output class
     * @return request handle
     * @throws QSException exception
     */
    RequestHandler getRequest(
            Map context, RequestInputModel paramBean, Class<? extends OutputModel> outputClass)
            throws QSException;
}
