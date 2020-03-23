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
package com.qingstor.sdk.utils;

import com.qingstor.sdk.annotation.ParamAnnotation;
import java.util.List;

public class ParamTypeModel {

    private String alarmStatus;

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    @ParamAnnotation(paramType = "query", paramName = "alarm_status")
    public String getAlarmStatus() {
        return this.alarmStatus;
    }

    private String cPUTopology;

    public void setCPUTopology(String cPUTopology) {
        this.cPUTopology = cPUTopology;
    }

    @ParamAnnotation(paramType = "query", paramName = "cpu_topology")
    public String getCPUTopology() {
        return this.cPUTopology;
    }

    private String createTime;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @ParamAnnotation(paramType = "query", paramName = "create_time")
    public String getCreateTime() {
        return this.createTime;
    }

    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    @ParamAnnotation(paramType = "query", paramName = "description")
    public String getDescription() {
        return this.description;
    }

    private String device;

    public void setDevice(String device) {
        this.device = device;
    }

    @ParamAnnotation(paramType = "query", paramName = "device")
    public String getDevice() {
        return this.device;
    }

    private String graphicsPasswd;

    public void setGraphicsPasswd(String graphicsPasswd) {
        this.graphicsPasswd = graphicsPasswd;
    }

    @ParamAnnotation(paramType = "query", paramName = "graphics_passwd")
    public String getGraphicsPasswd() {
        return this.graphicsPasswd;
    }

    private String graphicsProtocol;

    public void setGraphicsProtocol(String graphicsProtocol) {
        this.graphicsProtocol = graphicsProtocol;
    }

    @ParamAnnotation(paramType = "query", paramName = "graphics_protocol")
    public String getGraphicsProtocol() {
        return this.graphicsProtocol;
    }

    private String imageID;

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    @ParamAnnotation(paramType = "query", paramName = "image_id")
    public String getImageID() {
        return this.imageID;
    }

    private Integer instanceClass;

    public void setInstanceClass(Integer instanceClass) {
        this.instanceClass = instanceClass;
    }

    @ParamAnnotation(paramType = "query", paramName = "instance_class")
    public Integer getInstanceClass() {
        return this.instanceClass;
    }

    private String instanceID;

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    @ParamAnnotation(paramType = "query", paramName = "instance_id")
    public String getInstanceID() {
        return this.instanceID;
    }

    private String instanceName;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    @ParamAnnotation(paramType = "query", paramName = "instance_name")
    public String getInstanceName() {
        return this.instanceName;
    }

    private String instanceType;

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    @ParamAnnotation(paramType = "query", paramName = "instance_type")
    public String getInstanceType() {
        return this.instanceType;
    }

    private List<String> keyPairIDs;

    public void setKeyPairIDs(List<String> keyPairIDs) {
        this.keyPairIDs = keyPairIDs;
    }

    @ParamAnnotation(paramType = "query", paramName = "keypair_ids")
    public List<String> getKeyPairIDs() {
        return this.keyPairIDs;
    }

    private Integer memoryCurrent;

    public void setMemoryCurrent(Integer memoryCurrent) {
        this.memoryCurrent = memoryCurrent;
    }

    @ParamAnnotation(paramType = "query", paramName = "memory_current")
    public Integer getMemoryCurrent() {
        return this.memoryCurrent;
    }

    private String privateIP;

    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    @ParamAnnotation(paramType = "query", paramName = "private_ip")
    public String getPrivateIP() {
        return this.privateIP;
    }

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @ParamAnnotation(paramType = "query", paramName = "status")
    public String getStatus() {
        return this.status;
    }

    private String statusTime;

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    @ParamAnnotation(paramType = "query", paramName = "status_time")
    public String getStatusTime() {
        return this.statusTime;
    }

    private Integer subCode;

    public void setSubCode(Integer subCode) {
        this.subCode = subCode;
    }

    @ParamAnnotation(paramType = "query", paramName = "sub_code")
    public Integer getSubCode() {
        return this.subCode;
    }

    private String transitionStatus;

    public void setTransitionStatus(String transitionStatus) {
        this.transitionStatus = transitionStatus;
    }

    @ParamAnnotation(paramType = "query", paramName = "transition_status")
    public String getTransitionStatus() {
        return this.transitionStatus;
    }

    private Integer vCPUsCurrent;

    public void setVCPUsCurrent(Integer vCPUsCurrent) {
        this.vCPUsCurrent = vCPUsCurrent;
    }

    @ParamAnnotation(paramType = "query", paramName = "vcpus_current")
    public Integer getVCPUsCurrent() {
        return this.vCPUsCurrent;
    }

    private List<String> volumeIDs;

    public void setVolumeIDs(List<String> volumeIDs) {
        this.volumeIDs = volumeIDs;
    }

    @ParamAnnotation(paramType = "query", paramName = "volume_ids")
    public List<String> getVolumeIDs() {
        return this.volumeIDs;
    }
}
