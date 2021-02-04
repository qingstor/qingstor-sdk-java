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
package com.qingstor.sdk.config;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConfiguration {

    /**
     * IPV4_LIKE is used to match host part of an already valid URI, which means if URI's host is a
     * ip, it has to be a valid one. so regex we used is just to check if it's a (4 x 3 digit)
     * format, no need to do a strict ip grammar check.
     */
    private static final Pattern IPV4_LIKE =
            Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    private final URI endpoint;
    private final boolean cnameSupport;
    private final Set<String> cnameExcludeSet;
    private final String userAgent;
    private final boolean virtualHostEnabled;
    @Deprecated private final boolean safeOkHttp;
    private final int readTimeout;
    private final int connTimeout;
    private final int writeTimeout;

    private final boolean rawHost;

    ClientConfiguration(
            URI endpoint,
            boolean cnameSupport,
            Set<String> cnameExcludeSet,
            String userAgent,
            boolean virtualHostEnabled,
            boolean safeOkHttp,
            int readTimeout,
            int connTimeout,
            int writeTimeout) {
        this.endpoint = endpoint;
        this.userAgent = userAgent;
        this.cnameSupport = cnameSupport;
        this.cnameExcludeSet = cnameExcludeSet;
        this.safeOkHttp = safeOkHttp;
        this.readTimeout = readTimeout;
        this.connTimeout = connTimeout;
        this.writeTimeout = writeTimeout;
        // virtualHostEnabled must be false if endpoint is a raw ip.
        this.rawHost = isIpOrLocalhost(this.endpoint);
        this.virtualHostEnabled = !rawHost && virtualHostEnabled;
    }

    /**
     * Checks if the uri's host is an IP or domain. If it's IP or localhost, then path style must be
     * used, zone will not be inserted in this uri. Otherwise, uri will be processed normally.
     *
     * @implNote the ip address validation is done when construct URI class.
     * @param uri a URI to check.
     */
    private static boolean isIpOrLocalhost(URI uri) {
        String host = uri.getHost();
        if (host.equals("localhost")) { // also handle localhost
            return true;
        }
        // if host is a ip address, is must be a valid one.
        // So we just make sure it's not a domain name.
        Matcher matcher = IPV4_LIKE.matcher(host);
        // if it's a v4/v6 address, otherwise it must be a domain name.
        // ref: URI.getHost().
        return matcher.matches() || host.startsWith("[");
    }

    public static ClientConfigurationBuilder builder() {
        return new ClientConfigurationBuilder();
    }

    public static ClientConfiguration from(EnvContext env) {
        ClientConfigurationBuilder builder = ClientConfiguration.builder();
        return builder.userAgent(env.getAdditionalUserAgent())
                .safeOkHttp(env.isSafeOkHttp())
                .virtualHostEnabled(env.isVirtualHostEnabled())
                .supportCname(env.isCnameSupport())
                .endpoint(env.getEndpoint())
                .build();
    }

    public URI endpoint() {
        return endpoint;
    }

    public String userAgent() {
        return userAgent;
    }

    public boolean isVirtualHostEnabled() {
        return virtualHostEnabled;
    }

    public boolean isCnameSupport() {
        return cnameSupport;
    }

    public Set<String> cnameExcludeSet() {
        return Collections.unmodifiableSet(this.cnameExcludeSet);
    }

    /**
     * if endpoint is a raw host(ip address) or domain name. No zone should be inserted if true.
     *
     * @return true if endpoint if a raw ip address.
     */
    public boolean isRawHost() {
        return rawHost;
    }

    @Deprecated
    public boolean isSafeOkHttp() {
        return safeOkHttp;
    }

    public int readTimeout() {
        return readTimeout;
    }

    public int connTimeout() {
        return connTimeout;
    }

    public int writeTimeout() {
        return writeTimeout;
    }

    @Override
    public String toString() {
        return "ClientConfiguration{"
                + "endpoint="
                + endpoint
                + ", supportCname="
                + cnameSupport
                + ", userAgent='"
                + userAgent
                + '\''
                + ", virtualHostEnabled="
                + virtualHostEnabled
                + ", safeOkHttp="
                + safeOkHttp
                + ", readTimeout="
                + readTimeout
                + ", connTimeout="
                + connTimeout
                + ", writeTimeout="
                + writeTimeout
                + '}';
    }

    public static class ClientConfigurationBuilder {
        public static final String DEFAULT_CNAME_EXCLUDE_LIST = "qingstor.com";
        private static final String DEFAULT_HOST = "qingstor.com";
        private static final String DEFAULT_PROTOCOL = "https";
        private static final int READ_TIMEOUT = 100;
        private static final int CONN_TIMEOUT = 60;
        private static final int WRITE_TIMEOUT = 100;
        private URI endpoint = URI.create(DEFAULT_PROTOCOL + "://" + DEFAULT_HOST);
        private String userAgent;
        private boolean virtualHostEnabled;
        private boolean supportCname;
        private Set<String> cnameExcludeSet = new HashSet<>();
        private boolean safeOkHttp = true;
        private int readTimeout = READ_TIMEOUT;
        private int connTimeout = CONN_TIMEOUT;
        private int writeTimeout = WRITE_TIMEOUT;

        ClientConfigurationBuilder() {}

        public ClientConfigurationBuilder endpoint(URI endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public ClientConfigurationBuilder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public ClientConfigurationBuilder connTimeout(int connTimeout) {
            this.connTimeout = connTimeout;
            return this;
        }

        public ClientConfigurationBuilder writeTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public ClientConfigurationBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public ClientConfigurationBuilder virtualHostEnabled(boolean virtualHostEnabled) {
            this.virtualHostEnabled = virtualHostEnabled;
            return this;
        }

        public ClientConfigurationBuilder supportCname(boolean supportCname) {
            this.supportCname = supportCname;
            return this;
        }

        /**
         * Sets the excluded CName list. Any domain ends with an item in this list will not do Cname
         * resolution.
         *
         * @param cnameExcludeSet The excluded CName set, immutable.
         */
        public ClientConfigurationBuilder cnameExcludeSet(Set<String> cnameExcludeSet) {
            if (cnameExcludeSet == null) {
                throw new IllegalArgumentException("cname exclude set should not be null.");
            }

            this.cnameExcludeSet.clear();
            for (String excl : cnameExcludeSet) {
                if (!excl.trim().isEmpty()) {
                    this.cnameExcludeSet.add(excl);
                }
            }
            appendDefaultExcludeSet(this.cnameExcludeSet);
            return this;
        }

        public ClientConfiguration build() {
            appendDefaultExcludeSet(this.cnameExcludeSet);
            return new ClientConfiguration(
                    endpoint,
                    supportCname,
                    cnameExcludeSet,
                    userAgent,
                    virtualHostEnabled,
                    safeOkHttp,
                    readTimeout,
                    connTimeout,
                    writeTimeout);
        }

        private static void appendDefaultExcludeSet(Set<String> excludeSet) {
            String[] excludes = DEFAULT_CNAME_EXCLUDE_LIST.split(",");
            for (String excl : excludes) {
                if (!excl.trim().isEmpty() && !excludeSet.contains(excl)) {
                    excludeSet.add(excl.trim().toLowerCase());
                }
            }
        }

        @Deprecated
        public ClientConfigurationBuilder safeOkHttp(boolean safeOkHttp) {
            this.safeOkHttp = safeOkHttp;
            return this;
        }
    }
}
