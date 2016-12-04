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

    private Integer retCode;

    private Integer statueCode;

    @ParamAnnotation(paramType = "query", paramName = "statue_code")
    public Integer getStatueCode() {
        return statueCode;
    }

    public void setStatueCode(Integer statueCode) {
        this.statueCode = statueCode;
    }

    @ParamAnnotation(paramType = "query", paramName = "ret_code")
    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    @ParamAnnotation(paramType = "query", paramName = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
