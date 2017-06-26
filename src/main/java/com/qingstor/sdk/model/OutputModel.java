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

package com.qingstor.sdk.model;

import com.qingstor.sdk.annotation.ParamAnnotation;

public class OutputModel {

    private String message;

    private Integer statueCode;

    private String code;

    private String requestId;

    private String url;

    @ParamAnnotation(paramType = "query", paramName = "statue_code")
    public Integer getStatueCode() {
        return statueCode;
    }

    public void setStatueCode(Integer statueCode) {
        this.statueCode = statueCode;
    }

    /**
     * @return the error code
     */
    @ParamAnnotation(paramType = "query", paramName = "code")
    public String getCode() {
        return code;
    }

    /**
     * @param response code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the requestId
     */
    @ParamAnnotation(paramType = "query", paramName = "request_id")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the help url
     */
    @ParamAnnotation(paramType = "query", paramName = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ParamAnnotation(paramType = "query", paramName = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
