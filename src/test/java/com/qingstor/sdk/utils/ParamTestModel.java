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
import com.qingstor.sdk.model.OutputModel;

import java.util.List;

public class ParamTestModel extends OutputModel {

    private String action;

    private List<String> instances;

    public void setInstances(List<String> instances) {
        this.instances = instances;
    }

    @ParamAnnotation(paramType = "query", paramName = "instances")
    public List<String> getInstances() {
        return this.instances;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private List<String> imageID;

    public void setImageID(List<String> imageID) {
        this.imageID = imageID;
    }

    @ParamAnnotation(paramType = "query", paramName = "image_id")
    public List<String> getImageID() {
        return this.imageID;
    } // InstanceClass's available values: 0, 1

    @ParamAnnotation(paramType = "query", paramName = "action")
    public String getAction() {
        return this.action;
    }

    private List<ParamTypeModel> instanceSet;

    public void setInstanceSet(List<ParamTypeModel> instanceSet) {
        this.instanceSet = instanceSet;
    }

    @ParamAnnotation(paramType = "query", paramName = "instance_set")
    public List<ParamTypeModel> getInstanceSet() {
        return this.instanceSet;
    }

    private Integer retCode;

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    private Integer totalCount;

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
