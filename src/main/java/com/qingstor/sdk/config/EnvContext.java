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
package com.qingstor.sdk.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.qingstor.sdk.common.auth.Credentials;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.ParamValidate;
import com.qingstor.sdk.utils.QSStringUtil;
import java.io.*;
import java.net.URI;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvContext implements ParamValidate, Credentials {

    private static final ObjectMapper om;

    static {
        om = new ObjectMapper(new YAMLFactory());
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    private static final String DEFAULT_HOST = "qingstor.com";
    private static final String DEFAULT_PROTOCOL = "https";
    private static final HttpConfig DEFAULT_HTTP_CONFIG = new HttpConfig();

    private String accessKeyId;

    private String secretAccessKey;

    private String host = DEFAULT_HOST;
    private boolean cnameSupport = false;
    private String port;
    private String protocol = DEFAULT_PROTOCOL;
    private String additionalUserAgent;

    // default style, like this: https://bucket-name.zone-id.qingstor.com/object-name
    @Deprecated private String requestUrlStyle = "virtual_host_style";

    private boolean virtualHostEnabled = false;

    private HttpConfig httpConfig = DEFAULT_HTTP_CONFIG;

    private boolean safeOkHttp = true;

    private EnvContext() {}

    public EnvContext(String accessKey, String accessSecret) {
        this.setAccessKeyId(accessKey);
        this.setSecretAccessKey(accessSecret);
    }

    public static EnvContext loadFromFile(String filePathName) throws QSException {
        try {
            return om.readValue(new File(filePathName), EnvContext.class);
        } catch (IOException e) {
            throw new QSException(e.getMessage());
        }
    }

    /**
     * if true is returned, virtual-host style will be the default url style, otherwise, path style
     * will be used.
     *
     * @return is virtual-host style is enabled
     * @see #setVirtualHostEnabled(boolean)
     */
    public boolean isVirtualHostEnabled() {
        return virtualHostEnabled;
    }

    /**
     * if true is passed in, url style will like 1, otherwise, 2 will be the url style when send
     * request.
     *
     * <p>URL have two style: <br>
     * 1. VIRTUAL_HOST_STYLE: <br>
     * https://bucket-name.zone-id.qingstor.com/object-name <br>
     * 2. PATH_STYLE: <br>
     * https://zone-id.qingstor.com/bucket-name/object-name <br>
     */
    public void setVirtualHostEnabled(boolean virtualHostEnabled) {
        this.virtualHostEnabled = virtualHostEnabled;
    }

    /**
     * URL have two style: <br>
     * 1. VIRTUAL_HOST_STYLE: <br>
     * https://bucket-name.zone-id.qingstor.com/object-name <br>
     * 2. PATH_STYLE: <br>
     * https://zone-id.qingstor.com/bucket-name/object-name <br>
     *
     * @return request url style
     * @deprecated Use {@link #isVirtualHostEnabled()} instead.
     */
    @Deprecated
    public String getRequestUrlStyle() {
        return requestUrlStyle;
    }

    /**
     * You can use this method to change the url style. <br>
     * URL have two style: <br>
     * 1. VIRTUAL_HOST_STYLE: <br>
     * https://bucket-name.zone-id.qingstor.com/object-name <br>
     * 2. PATH_STYLE: <br>
     * https://zone-id.qingstor.com/bucket-name/object-name <br>
     *
     * @param requestUrlStyle set QSConstant.PATH_STYLE or QSConstant.VIRTUAL_HOST_STYLE
     * @deprecated Use {@link #setVirtualHostEnabled(boolean)} instead.
     */
    @Deprecated
    public void setRequestUrlStyle(String requestUrlStyle) {
        this.requestUrlStyle = requestUrlStyle;
    }

    public boolean isSafeOkHttp() {
        return safeOkHttp;
    }

    /**
     * This method will be deleted in subsequent releases
     *
     * @param safeOkHttp is safe okHttp or not
     */
    @Deprecated
    public void setSafeOkHttp(boolean safeOkHttp) {
        this.safeOkHttp = safeOkHttp;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
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

    public URI getEndpoint() {
        String joinUrl = this.getProtocol() + "://" + this.getHost();
        if (this.getPort() != null) {
            joinUrl += ":" + this.getPort();
        }
        return URI.create(joinUrl);
    }

    /** @deprecated Use {@link #getEndpoint()} instead. */
    public String getRequestUrl() {
        String joinUrl = this.getProtocol() + "://" + this.getHost();
        if (this.getPort() != null) {
            joinUrl += ":" + this.getPort();
        }
        return joinUrl;
    }

    /** @return the additionalUserAgent */
    public String getAdditionalUserAgent() {
        return additionalUserAgent;
    }

    /** @param additionalUserAgent the additionalUserAgent to set */
    public void setAdditionalUserAgent(String additionalUserAgent) {
        this.additionalUserAgent = additionalUserAgent;
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public void setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    @Override
    public String toString() {
        return "EnvContext{"
                + "accessKeyId='"
                + accessKeyId
                + '\''
                + ", secretAccessKey='"
                + secretAccessKey
                + '\''
                + ", host='"
                + host
                + '\''
                + ", port='"
                + port
                + '\''
                + ", protocol='"
                + protocol
                + '\''
                + ", additionalUserAgent='"
                + additionalUserAgent
                + '\''
                + ", requestUrlStyle='"
                + requestUrlStyle
                + '\''
                + ", httpConfig="
                + httpConfig
                + ", safeOkHttp="
                + safeOkHttp
                + '}';
    }

    @Override
    public String validateParam() {
        if (QSStringUtil.isEmpty(getAccessKeyId())) {
            return QSStringUtil.getParameterRequired("AccessKeyId", "EnvContext");
        }

        if (QSStringUtil.isEmpty(getEndpoint().toString())) {
            return QSStringUtil.getParameterRequired("host", "EnvContext");
        }
        if (!QSStringUtil.isEmpty(getAdditionalUserAgent())) {
            for (int i = 0; i < getAdditionalUserAgent().length(); i++) {
                char temp = getAdditionalUserAgent().charAt(i);
                // Allow space(32) to ~(126) in ASCII Table, exclude "(34).
                if ((int) temp < 32 || (int) temp > 126 || (int) temp == 32 || (int) temp == 34) {
                    return "Additional User-Agent contains characters that not allowed :"
                            + getAdditionalUserAgent().charAt(i);
                }
            }
        }
        return null;
    }

    public boolean isCnameSupport() {
        return cnameSupport;
    }

    public void setCnameSupport(boolean cnameSupport) {
        this.cnameSupport = cnameSupport;
    }

    public static class HttpConfig {
        private int readTimeout = 100;
        private int connectionTimeout = 60;

        private int writeTimeout = 100;

        public HttpConfig() {}

        public HttpConfig(int readTimeout, int connectionTimeout, int writeTimeout) {
            this.readTimeout = readTimeout;
            this.connectionTimeout = connectionTimeout;
            this.writeTimeout = writeTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
        }
    }
}
