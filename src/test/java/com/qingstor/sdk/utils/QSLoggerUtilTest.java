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

import static org.junit.Assert.*;

import com.qingstor.sdk.constants.QSConstant;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

public class QSLoggerUtilTest {

    @Test
    public void testLoggerUtil() {

        Logger log = QSLoggerUtil.setLoggerHanlder(QSLoggerUtilTest.class.getName());
        Assert.assertEquals(log.getLevel(), Level.SEVERE);
        QSConstant.LOGGER_LEVEL = QSConstant.LOGGER_INFO;
        Logger log2 = QSLoggerUtil.setLoggerHanlder(QSLoggerUtilTest.class.getName());
        Assert.assertEquals(log2.getLevel(), Level.INFO);
    }
}
