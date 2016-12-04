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
import com.qingstor.sdk.request.ParamValidate;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class EvnContext implements ParamValidate {

    public static String qingcloudIaasHost = "api.qingcloud.com";

    public static String qingcloudStorHost = "qingstor.com";
    public static String default_protocal = "https";
    public static String default_iaas_uri = "/iaas";

    private String accessKey;

    private String accessSecret;

    private String host;
    private String port;
    private String protocol = default_protocal;
    private String uri;
    private String log_level = QSConstant.LOGGER_ERROR;

    private boolean safeOkHttp = true;

    public boolean isSafeOkHttp() {
        return safeOkHttp;
    }

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

    /** @param host example: qingstor.com */
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    /** @param port example: 8080 */
    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    /** @param protocol example: https or http */
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

    /** @param uri example: /iaas */
    public void setUri(String uri) {
        this.uri = uri;
    }

    private EvnContext() {}

    public EvnContext(String accessKey, String accessSecret) {
        this.setAccessKey(accessKey);
        this.setAccessSecret(accessSecret);
        this.setHost(qingcloudStorHost);
        QSConstant.LOGGER_LEVEL = this.getLog_level();
    }

    public static EvnContext loadFromFile(String filePathName) {
        EvnContext evn = new EvnContext();
        File f = new File(filePathName);
        if (f.exists()) {
            BufferedReader br = null;
            Map<String, String> confParams = new HashMap<String, String>();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String strConf = null;

                while ((strConf = br.readLine()) != null) {
                    String[] str = strConf.split(":");
                    if (str.length > 1) {
                        confParams.put(str[0].trim(), str[1].replaceAll("'","").trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            evn.setAccessKey(confParams.get("access_key"));
            evn.setAccessSecret(confParams.get("access_secret"));
            evn.setProtocol(confParams.get("protocol"));
            evn.setHost(confParams.get("host"));
            evn.setUri(confParams.get("uri"));
            evn.setPort(confParams.get("port"));
            evn.setLog_level(confParams.get("log_level"));
            
        }
        return evn;
    }
    

    public String getLog_level() {
		return log_level;
	}

	public void setLog_level(String log_level) {
		if(!QSStringUtil.isEmpty(log_level)){
        	QSConstant.LOGGER_LEVEL = log_level;
        }
		this.log_level = log_level;
	}

	@Override
    public String validateParam() {
        if (QSStringUtil.isEmpty(getAccessKey())) {
            return QSStringUtil.getParameterRequired("AccessKey", "EvnContext");
        }
        if (QSStringUtil.isEmpty(getAccessSecret())) {
            return QSStringUtil.getParameterRequired("AccessSecret", "EvnContext");
        }
        if (QSStringUtil.isEmpty(getRequestUrl())) {
            return QSStringUtil.getParameterRequired("host", "EvnContext");
        }
        return null;
    }
}
