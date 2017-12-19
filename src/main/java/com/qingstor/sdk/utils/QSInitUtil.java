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

/**
 * util for init url style. <br/>
 * to change the url style use this class get single instance <br>
 * and set style like the code below.<br/>
 * QSInitUtil.getInstance().setRequestUrlStyle(QSConstant.BUCKET_NAME_BEHIND_DOMAIN_NAME); <br/>
 * now available style: <br>
 * one is the default, when requestUrlStyle != {@link
 * com.qingstor.sdk.constants.QSConstant#BUCKET_NAME_BEHIND_DOMAIN_NAME} <br>
 * you may see the url like this: <br>
 * https://bucket-name.zone-id.qingstor.com/object-name <br>
 * otherwise you may see the url like this: <br>
 * https://zone-id.qingstor.com/bucket-name/object-name <br>
 * Created by chengww on 2017/12/19.
 */
public class QSInitUtil {

    private static QSInitUtil mQSInitUtil;

    //default style like this: https://<bucket-name>.<zone-id>.qingstor.com/<object-name>
    private int requestUrlStyle;

    private QSInitUtil() {}

    public static QSInitUtil getInstance() {
        if (mQSInitUtil == null) {
            synchronized (QSInitUtil.class) {
                if (mQSInitUtil == null) {
                    mQSInitUtil = new QSInitUtil();
                }
            }
        }
        return mQSInitUtil;
    }

    public void setRequestUrlStyle(int requestUrlStyle) {
        this.requestUrlStyle = requestUrlStyle;
    }

    public int getRequestUrlStyle() {
        return requestUrlStyle;
    }
}
