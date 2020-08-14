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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import okhttp3.HttpUrl;

public class UrlUtils {
    // borrowed from gcs.
    public static String rfc3986UriEncode(final String segment, final boolean encodeForwardSlash) {
        String encodedSegment;
        try {
            encodedSegment = URLEncoder.encode(segment, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
        // URLEncoder.encode() does mostly what we want,
        // with the exception of a few characters("~/ *") that we fix in a second phase:
        encodedSegment =
                encodedSegment
                        .replace("*", "%2A") // Asterisks should be encoded.
                        .replace(
                                "+",
                                "%20") // Spaces should be encoded as %20 instead of a plus sign.
                        .replace("%7E", "~"); // Tildes should not be encoded.
        // Forward slashes should NOT be encoded in the segment of the URI that represents the
        // object's name, but should be encoded for all other segments.
        if (!encodeForwardSlash) {
            encodedSegment = encodedSegment.replace("%2F", "/");
        }
        return encodedSegment;
    }

    /**
     * Calculate the final endpoint based on pathMode, the possible results are: 1.
     * scheme://bucket.zone.originHost:port (pathMode = true) 2. scheme://zone.originHost:port
     * (pathMode = false)
     *
     * @param endpoint endpoint from your config, format: http(s)://storage.com(:80).
     * @param zone zone of your bucket
     * @param bucket bucket name
     * @param pathMode construct use path mode or virtual-host mode.
     * @return calculated url. if null is returned, means your endpoint is malformed.
     */
    public static HttpUrl calcFinalEndpoint(
            String endpoint, String zone, String bucket, boolean pathMode) {
        HttpUrl uri = HttpUrl.parse(endpoint);
        if (uri == null) {
            return null;
        }
        return uri.newBuilder().host(buildCanonicalHost(uri, zone, bucket, pathMode)).build();
    }

    private static String buildCanonicalHost(
            HttpUrl endpoint, String zone, String bucket, boolean pathMode) {
        String host = endpoint.host();

        StringBuilder canonicalHost = new StringBuilder();
        if (bucket != null && !pathMode) {
            canonicalHost.append(bucket).append(".").append(zone).append(".");
        } else {
            canonicalHost.append(zone).append(".");
        }

        return canonicalHost.append(host).toString();
    }

    /**
     * calculate the path part according to pathMode, the possible results are: 1. bucket/encodedKey
     * (pathMode = true) 2. encodedKey (pathMode = false)
     *
     * @param bucket bucket name
     * @param key object key
     * @param pathMode construct use path mode or virtual-host mode.
     * @return calculated path segment of url.
     */
    public static String calcResourcePath(String bucket, String key, boolean pathMode) {
        return pathMode ? makeResourcePath(bucket, key) : makeResourcePath(key);
    }

    /**
     * Make a resource path from the object key, used when the bucket name appearing in the
     * endpoint.
     */
    private static String makeResourcePath(String key) {
        return key != null ? rfc3986UriEncode(key, false) : "";
    }

    /** Make a resource path from the bucket name and the object key. */
    private static String makeResourcePath(String bucket, String key) {
        if (bucket != null) {
            return bucket + "/" + (key != null ? rfc3986UriEncode(key, false) : "");
        } else {
            return "";
        }
    }
}
