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
package com.qingstor.sdk.upload;

import com.qingstor.sdk.model.OutputModel;

/**
 * Callback of the upload manager. <br>
 * Created by chengww on 2018/1/23.
 */
public abstract class UploadManagerCallback<O extends OutputModel> {
    /**
     * When upload manager needs sign a string with the server will call this method. <br>
     * If you want to sign with server, override it and return the signed string. <br>
     *
     * @param strToSign string to sign
     * @return signed string.
     */
    String onSignature(String strToSign) {
        return null;
    }

    /**
     * When upload manager needs the access key will call this method. <br>
     * If you want to sign with server, override it and return your access key. <br>
     *
     * @return access key
     */
    String onAccessKey() {
        return null;
    }

    /**
     * If the local time of user's clients are not synchronized with the network time.<br>
     * You should get the network time when the server signed and return it.<br>
     * This is an example of <strong>the server</strong> about how to return the right time to clients.
     * <br>
     * <code>
     *     // Get the server time when sign
     *     Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Asia/Beijing"));
     *     String gmtTime = QSSignatureUtil.formatGmtDate(instance.getTime());
     *     return gmtTime;
     * </code>
     * <br>
     * If you needn't correct time, return null and ignore it.
     * @return Gmt time string with time zone "Asia/Beijing"("GMT+8:00")
     */
    String onCorrectTime(String strToSign) {
        return null;
    }

    public abstract void onAPIResponse(String objectKey, O outputModel);
}
