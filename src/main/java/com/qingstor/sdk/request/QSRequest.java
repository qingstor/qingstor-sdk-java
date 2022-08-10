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
package com.qingstor.sdk.request;

import com.qingstor.sdk.common.OperationContext;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import okhttp3.Request;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QSRequest {

    public static <T extends OutputModel> void sendApiRequestAsync(
            String requestUrl,
            OperationContext opCtx,
            ResponseCallBack<T> callback,
            Class<T> outputClass) {
        EnvContext envContext = (EnvContext) opCtx.credentials();
        Request request = new Request.Builder().url(requestUrl).build();
        QSOkHttpRequestClient.getInstance(envContext)
                .requestActionAsync(request, envContext.isSafeOkHttp(), callback, outputClass);
    }

    public static OutputModel sendApiRequest(
            String requestUrl, OperationContext opCtx, Class<? extends OutputModel> outputClass)
            throws QSException {
        EnvContext envContext = (EnvContext) opCtx.credentials();
        Request request = new Request.Builder().url(requestUrl).build();
        return QSOkHttpRequestClient.getInstance(envContext)
                .requestAction(request, envContext.isSafeOkHttp(), outputClass);
    }

    public static <T extends OutputModel> RequestHandler<T> getRequestAsync(
            OperationContext opCtx,
            RequestInputModel paramBean,
            ResponseCallBack<? extends OutputModel> callback,
            Class<T> outputClass)
            throws QSException {
        return new RequestHandler(opCtx, paramBean, callback, outputClass);
    }

    public static <T extends OutputModel> RequestHandler<T> getRequest(
            OperationContext opCtx, RequestInputModel paramBean, Class<T> outputClass)
            throws QSException {
        return new RequestHandler(opCtx, paramBean, outputClass);
    }
}
