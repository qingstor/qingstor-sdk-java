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

import com.qingstor.sdk.request.ResponseCallBack;

/**
 * Callback of the upload manager. <br>
 * Created by chengww on 2018/1/23.
 */
public interface UploadManagerCallback extends ResponseCallBack {
    /**
     * When upload manager needs sign a string with the server will call this method. <br>
     * If you do not want to sign with server, just let it return null. <br>
     *
     * @param strToSign string to sign
     * @return signed string.
     */
    String onSignature(String strToSign);

    /**
     * When upload manager needs the access key will call this method. <br>
     * If you do not want to sign with server, just let it return null. <br>
     *
     * @return access key
     */
    String onAccessKey();
}
