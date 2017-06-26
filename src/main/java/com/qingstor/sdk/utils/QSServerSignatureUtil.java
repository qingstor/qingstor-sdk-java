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

public class QSServerSignatureUtil {


    /**
     * Generate signature for request against QingStor.
     *
     * @param accessKey: API access key ID
     * @param secretKey: API secret access key ID
     * @param strToSign: strToSign
     * @return a string which can be used as value of HTTP request header field "Authorization"
     * directly.
     * <p>See https://docs.qingcloud.com/qingstor/api/common/signature.html for more details
     * about how to do signature of request against QingStor.
     */
    public static String generateAuthorization(
            String accessKey,
            String secretKey,
            String strToSign) {
        return QSSignatureUtil.generateAuthorization(accessKey, secretKey, strToSign);
    }

    /**
     * Generate signature for request against QingStor.
     *
     * @param secretKey
     * @param strToSign
     * @return signature
     * <p>
     * <p>See https://docs.qingcloud.com/qingstor/api/common/signature.html for more details
     */
    public static String generateSignature(String secretKey, String strToSign) {
        return QSSignatureUtil.generateSignature(secretKey, strToSign);
    }
}
