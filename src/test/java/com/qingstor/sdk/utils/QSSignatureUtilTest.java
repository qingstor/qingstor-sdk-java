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

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class QSSignatureUtilTest {

    @Test
    public void testSignature() {
        ParamTestModel instancesInput = new ParamTestModel();
        instancesInput.setAction("TestAction");
        List<String> imgs =
                new ArrayList<String>() {
                    {
                        add("test-0001");
                        add("test-0002");
                    }
                };
        instancesInput.setImageID(imgs);

        Map queryParam =
                QSParamInvokeUtil.getRequestParams(instancesInput, QSConstant.PARAM_TYPE_QUERY);

        /*https://api.qc.dev/iaas?access_key_id=QYACCESSKEYIDEXAMPLE&image_id.0=test-0001&image_id.1=test-0002&
        // search_word=serch_word_test&signature_method=HmacSHA256&signature_version=1&
        // time_stamp=2016-12-02T13%3A07%3A16Z&version=1&signature=r%2FR9TmmnZQWhkEi1gQy5qV9wEPjoJYi9QHSKzliq2eg%3D
        */
        String d = QSSignatureUtil.formatGmtDate(new Date());
        String url =
                QSSignatureUtil.getAuth(
                        "QYACCESSKEYIDEXAMPLE",
                        "wudajefiLSJDWIFJLSD",
                        "GET",
                        "objectTest",
                        queryParam,
                        null);

        Assert.assertEquals(url.indexOf("QS QYACCESSKEYIDEXAMPLE:") >= 0, true);
    }

    @Test
    public void testEncodeString() {
        String req1 = null;
        String req2 = null;
        try {
            req1 = QSSignatureUtil.percentEncode("test/obj+.txt");
            req2 = QSSignatureUtil.percentEncode("test/在obj.txt");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(req1, "test/obj+.txt");

        Assert.assertNotEquals(req2, "test/在obj.txt");
    }

    @Test
    public void testExpireString() {
        EvnContext evnContext = new EvnContext("testkey", "test_asss");

        String req1 = null;
        String req2 = null;
        try {
            req1 =
                    QSSignatureUtil.getObjectAuthRequestUrl(
                            evnContext, "testzone", "bucketName", "objectName/dd.txt", 1000);
        } catch (QSException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(
                req1.indexOf("https://bucketName.testzone.qingstor.com/objectName/dd.txt?") == 0,
                true);
    }
}
