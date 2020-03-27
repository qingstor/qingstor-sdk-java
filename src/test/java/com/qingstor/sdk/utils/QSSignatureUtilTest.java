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

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.service.Bucket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class QSSignatureUtilTest {

    @Test
    public void testSignature() throws QSException {
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
                QSSignatureUtil.generateAuthorization(
                        "QYACCESSKEYIDEXAMPLE",
                        "wudajefiLSJDWIFJLSD",
                        "GET",
                        "objectTest",
                        queryParam,
                        null);

        Assert.assertEquals(url.indexOf("QS QYACCESSKEYIDEXAMPLE:") >= 0, true);
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

    @Test
    public void testPutObjectSign() {
        EnvContext context =
                new EnvContext("NPTEPEATTGXAKXFYSFXR", "VdVyOLO6oG7wzzilGbuuqiDrwR1K0NgD0DzFaNKX");
        String zoneKey = "pek3b";
        String bucketName = "test";
        Bucket bucket = new Bucket(context, zoneKey, bucketName);
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setContentType("image/jpeg");
        input.setContentMD5("Content-MD5");
        input.setContentLength(10000l);
        try {
            RequestHandler requestHandler = bucket.putObjectRequest("test/test.jpg", input);
            requestHandler.getBuilder().setHeader("Date", "Thu, 06 Dec 2018 06:47:43 GMT");
            String stringToSign = requestHandler.getStringToSignature();
            Assert.assertEquals(
                    stringToSign,
                    "PUT\nContent-MD5\nimage/jpeg\nThu, 06 Dec 2018 06:47:43 GMT\n/test/test/test.jpg");
            String sign =
                    QSSignatureUtil.generateSignature(
                            "VdVyOLO6oG7wzzilGbuuqiDrwR1K0NgD0DzFaNKX", stringToSign);
            Assert.assertEquals(sign, "PiwbpsStHI4p3OF3Y68bX9TV72OEOFbgqJGkmu7mN8E=");
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
}
