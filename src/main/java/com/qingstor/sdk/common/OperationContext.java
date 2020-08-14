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
package com.qingstor.sdk.common;

import com.qingstor.sdk.common.auth.Credentials;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import java.util.Map;

public class OperationContext {

    private Credentials credentials;

    private String operationName;
    private String apiName;
    private String serviceName;
    private String reqMethod;

    private boolean virtualHostEnabled;
    private String endpoint;
    private String subSourcePath;

    private String zone;
    private String bucketName;
    private String objKey;

    private String userAgent;

    @Deprecated private boolean safeOKHttp = true;

    /** support temp expires param */
    @Deprecated private String expires;

    OperationContext(
            Credentials credentials,
            String operationName,
            String apiName,
            String serviceName,
            String reqMethod,
            boolean virtualHostEnabled,
            String endpoint,
            String subSourcePath,
            String zone,
            String bucketName,
            String objKey,
            String userAgent,
            boolean safeOKHttp,
            String expires) {
        this.credentials = credentials;
        this.operationName = operationName;
        this.apiName = apiName;
        this.serviceName = serviceName;
        this.reqMethod = reqMethod;
        this.virtualHostEnabled = virtualHostEnabled;
        this.endpoint = endpoint;
        this.subSourcePath = subSourcePath;
        this.zone = zone;
        this.bucketName = bucketName;
        this.objKey = objKey;
        this.userAgent = userAgent;
        this.safeOKHttp = safeOKHttp;
        this.expires = expires;
    }

    public static OperationContext from(Map<String, Object> opCtx) {
        OperationContextBuilder builder = OperationContext.builder();
        EnvContext envCtx = (EnvContext) opCtx.get(QSConstant.ENV_CONTEXT_KEY);
        if (envCtx != null) {
            builder.userAgent(envCtx.getAdditionalUserAgent())
                    .safeOKHttp(envCtx.isSafeOkHttp())
                    .virtualHostEnabled(envCtx.isVirtualHostEnabled())
                    .credentials(envCtx)
                    .endpoint(envCtx.getRequestUrl());
        }
        return builder.operationName((String) opCtx.get("OperationName"))
                .apiName((String) opCtx.get("APIName"))
                .serviceName((String) opCtx.get("ServiceName"))
                .reqMethod((String) opCtx.get("RequestMethod"))
                .subSourcePath((String) opCtx.get("RequestURI"))
                .zone((String) opCtx.get(QSConstant.PARAM_KEY_REQUEST_ZONE))
                .bucketName((String) opCtx.get("bucketNameInput"))
                .objKey((String) opCtx.get("objectNameInput"))
                .expires((String) opCtx.get(QSConstant.PARAM_KEY_EXPIRES))
                .build();
    }

    public static OperationContextBuilder builder() {
        return new OperationContextBuilder();
    }

    public Credentials credentials() {
        return this.credentials;
    }

    public String operationName() {
        return this.operationName;
    }

    public String apiName() {
        return this.apiName;
    }

    public String serviceName() {
        return this.serviceName;
    }

    public String reqMethod() {
        return this.reqMethod;
    }

    public String endpoint() {
        return this.endpoint;
    }

    public String subSourcePath() {
        return this.subSourcePath;
    }

    public String zone() {
        return this.zone;
    }

    public String bucketName() {
        return this.bucketName;
    }

    public String objKey() {
        return this.objKey;
    }

    public String userAgent() {
        return this.userAgent;
    }

    @Deprecated
    public boolean isSafeOKHttp() {
        return this.safeOKHttp;
    }

    public boolean isVirtualHostEnabled() {
        return this.virtualHostEnabled;
    }

    @Deprecated
    public String expires() {
        return this.expires;
    }

    public String toString() {
        return "OperationContext(credentials="
                + this.credentials()
                + ", operationName="
                + this.operationName()
                + ", apiName="
                + this.apiName()
                + ", serviceName="
                + this.serviceName()
                + ", reqMethod="
                + this.reqMethod()
                + ", endpoint="
                + this.endpoint()
                + ", subSourcePath="
                + this.subSourcePath()
                + ", zone="
                + this.zone()
                + ", bucketName="
                + this.bucketName()
                + ", objKey="
                + this.objKey()
                + ", userAgent="
                + this.userAgent()
                + ", safeOKHttp="
                + this.isSafeOKHttp()
                + ", expires="
                + this.expires()
                + ")";
    }

    public static class OperationContextBuilder {
        private Credentials credentials;
        private String operationName;
        private String apiName;
        private String serviceName;
        private String reqMethod;
        private boolean virtualHostEnabled;
        private String endpoint;
        private String subSourcePath;
        private String zone;
        private String bucketName;
        private String objKey;
        private String userAgent;
        private boolean safeOKHttp;
        private String expires;

        OperationContextBuilder() {}

        public OperationContextBuilder credentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public OperationContextBuilder operationName(String operationName) {
            this.operationName = operationName;
            return this;
        }

        public OperationContextBuilder apiName(String apiName) {
            this.apiName = apiName;
            return this;
        }

        public OperationContextBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public OperationContextBuilder reqMethod(String reqMethod) {
            this.reqMethod = reqMethod;
            return this;
        }

        public OperationContextBuilder virtualHostEnabled(boolean enable) {
            this.virtualHostEnabled = enable;
            return this;
        }

        public OperationContextBuilder endpoint(String endpoint) {
            if (endpoint == null) {
                throw new NullPointerException("endpoint must be NonNull");
            }
            this.endpoint = endpoint;
            return this;
        }

        public OperationContextBuilder subSourcePath(String subSourcePath) {
            this.subSourcePath = subSourcePath;
            return this;
        }

        public OperationContextBuilder zone(String zone) {
            this.zone = zone;
            return this;
        }

        public OperationContextBuilder bucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public OperationContextBuilder objKey(String objKey) {
            this.objKey = objKey;
            return this;
        }

        public OperationContextBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        @Deprecated
        public OperationContextBuilder safeOKHttp(boolean safeOKHttp) {
            this.safeOKHttp = safeOKHttp;
            return this;
        }

        @Deprecated
        public OperationContextBuilder expires(String expires) {
            this.expires = expires;
            return this;
        }

        public OperationContext build() {
            return new OperationContext(
                    credentials,
                    operationName,
                    apiName,
                    serviceName,
                    reqMethod,
                    virtualHostEnabled,
                    endpoint,
                    subSourcePath,
                    zone,
                    bucketName,
                    objKey,
                    userAgent,
                    safeOKHttp,
                    expires);
        }

        public String toString() {
            return "OperationContext.OperationContextBuilder(credentials="
                    + this.credentials
                    + ", operationName="
                    + this.operationName
                    + ", apiName="
                    + this.apiName
                    + ", serviceName="
                    + this.serviceName
                    + ", reqMethod="
                    + this.reqMethod
                    + ", virtualHostEnabled="
                    + this.virtualHostEnabled
                    + ", endpoint="
                    + this.endpoint
                    + ", subSourcePath="
                    + this.subSourcePath
                    + ", zone="
                    + this.zone
                    + ", bucketName="
                    + this.bucketName
                    + ", objKey="
                    + this.objKey
                    + ", userAgent="
                    + this.userAgent
                    + ", safeOKHttp="
                    + this.safeOKHttp
                    + ", expires="
                    + this.expires
                    + ")";
        }
    }
}
