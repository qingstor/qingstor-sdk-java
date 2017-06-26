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

package com.qingstor.sdk.config;

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.ParamValidate;
import com.qingstor.sdk.utils.QSStringUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class EvnContext implements ParamValidate {

    public static String qingcloudStorHost = "qingstor.com";
    public static String default_protocal = "https";

    private String accessKey;

    private String accessSecret;

    private String host;
    private String port;
    private String protocol = default_protocal;
    private String uri;
    private String log_level = QSConstant.LOGGER_ERROR;
    private String additionalUserAgent;

    private boolean safeOkHttp = true;

    public boolean isSafeOkHttp() {
        return safeOkHttp;
    }

    
    /**
     * This method will be deleted in subsequent releases
     * 
     * @param safeOkHttp
     */
    @Deprecated
    public void setSafeOkHttp(boolean safeOkHttp) {
        this.safeOkHttp = safeOkHttp;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getHost() {
        return host;
    }

    /**
     * @param host example: qingstor.com
     */
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    /**
     * @param port example: 8080
     */
    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol example: https or http
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUri() {
        return uri;
    }

    public String getRequestUrl() {
        String joinUrl = this.getProtocol() + "://" + this.getHost();
        if (this.getPort() != null) {
            joinUrl += ":" + this.getPort();
        }
        if (this.getUri() != null) {
            joinUrl += this.getUri();
        }
        return joinUrl;
    }

    /**
     * @param uri example: /iaas
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    private EvnContext() {
    }

    public EvnContext(String accessKey, String accessSecret) {
        this.setAccessKey(accessKey);
        this.setAccessSecret(accessSecret);
        this.setHost(qingcloudStorHost);
        QSConstant.LOGGER_LEVEL = this.getLog_level();
    }

    public static EvnContext loadFromFile(String filePathName) throws QSException {
        EvnContext evn = new EvnContext();
        File f = new File(filePathName);
        if (f.exists()) {
            BufferedReader br = null;

            Yaml yaml = new Yaml();
            try {
                Map confParams = (Map) yaml.load(new FileInputStream(f));
                evn.setAccessKey(getYamlConfig("access_key_id", confParams));
                evn.setAccessSecret(getYamlConfig("secret_access_key", confParams));
                evn.setProtocol(getYamlConfig("protocol", confParams));
                evn.setHost(getYamlConfig("host", confParams));
                evn.setUri(getYamlConfig("uri", confParams));
                evn.setPort(getYamlConfig("port", confParams));
                evn.setLog_level(getYamlConfig("log_level", confParams));
                evn.setAdditionalUserAgent(getYamlConfig("additional_user_agent", confParams));

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new QSException("Yaml config error:", e);
            }

        }
        return evn;
    }

    private static String getYamlConfig(String key, Map config) {
        if (config.containsKey(key)) {
            return String.valueOf(config.get(key));
        }
        return null;
    }

    public String getLog_level() {
        return log_level;
    }

    public void setLog_level(String log_level) {
        if (!QSStringUtil.isEmpty(log_level)) {
            QSConstant.LOGGER_LEVEL = log_level;
        }
        this.log_level = log_level;
    }


    /**
     * @return the additionalUserAgent
     */
    public String getAdditionalUserAgent() {
        return additionalUserAgent;
    }

    /**
     * @param additionalUserAgent the additionalUserAgent to set
     */
    public void setAdditionalUserAgent(String additionalUserAgent) {
        this.additionalUserAgent = additionalUserAgent;
    }

    @Override
    public String validateParam() {
        if (QSStringUtil.isEmpty(getAccessKey())) {
            return QSStringUtil.getParameterRequired("AccessKey", "EvnContext");
        }

        if (QSStringUtil.isEmpty(getRequestUrl())) {
            return QSStringUtil.getParameterRequired("host", "EvnContext");
        }
        if (!QSStringUtil.isEmpty(getAdditionalUserAgent())) {
            for (int i = 0; i < getAdditionalUserAgent().length(); i++) {
                char temp = getAdditionalUserAgent().charAt(i);
                int value = (int) temp;
                // Allow space(32) to ~(126) in ASCII Table, exclude "(34).
                if (value < 32 || value > 126 || value == 32 || value == 34) {
                    return "additional User-Agent contains characters that not allowed :"
                            + getAdditionalUserAgent().substring(i, i + 1);
                }
            }
        }
        return null;
    }

}
