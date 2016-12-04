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

import com.qingstor.sdk.constants.QSConstant;
import org.junit.Assert;
import org.junit.Test;

public class QSStringUtilTest {

    @Test
    public void testReqString() {
        String req = QSStringUtil.getParameterRequired("key", "colume");
        Assert.assertEquals(req, "key is required in colume ");
    }

    @Test
    public void testNotAllowedString() {
        String[] values = {"2", "3"};
        String req = QSStringUtil.getParameterValueNotAllowedError("colume", "key", values);
        Assert.assertEquals(req, "colume value key is not allowed, should be one of 2,3 ");
    }

    @Test
    public void testUserAgentString() {
        String req = QSStringUtil.getUserAgent();
        Assert.assertEquals(req.indexOf(QSConstant.SDK_VERSION) > 0, true);
        Assert.assertEquals(req.indexOf(QSConstant.SDK_NAME) == 0, true);
    }
}
