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

/**
 * A interface defined to make records when multi uploads. <br>
 * Created by chengww on 2018/1/23.
 */
public interface Recorder {

    /**
     * Set a new or updated progress of multi uploads. <br>
     *
     * @param key  key recorded in the upload.
     * @param data data recorded in the upload.
     */
    void set(String key, byte[] data);

    /**
     * Get the info of progress of multi uploads. <br>
     *
     * @param key key recorded in the upload.
     * @return info of progress.
     */
    byte[] get(String key);

    /**
     * Delete the info of progress of multi uploads. <br>
     *
     * @param key key recorded in the upload.
     */
    void del(String key);
}
