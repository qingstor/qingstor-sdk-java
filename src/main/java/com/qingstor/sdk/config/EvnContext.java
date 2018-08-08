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

import com.qingstor.sdk.exception.QSException;

import java.io.File;

/**
 * Deprecated, use {@link com.qingstor.sdk.config.EnvContext} instead. <br>
 * 已过时，请使用 {@link com.qingstor.sdk.config.EnvContext}
 */
@Deprecated
public class EvnContext extends EnvContext {

    public EvnContext(String accessKey, String accessSecret) {
        super(accessKey, accessSecret);
    }

    public static EvnContext loadFromFile(String filePathName) throws QSException {
        EvnContext evn = new EvnContext("", "");
        File f = new File(filePathName);
        loadYaml(evn, f);
        return evn;
    }
}
