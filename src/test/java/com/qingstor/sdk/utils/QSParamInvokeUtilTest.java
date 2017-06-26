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
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class QSParamInvokeUtilTest {

    @Test
    public void testParamInvokeCapitalize() {
        String capitalize = QSParamInvokeUtil.capitalize("tttTtest");
        Assert.assertEquals(capitalize, "TttTtest");
    }

    @Test
    public void testClassToModel() {
        Object outmodel = null;
        try {
            outmodel = QSParamInvokeUtil.getOutputModel(Bucket.PutBucketACLOutput.class);
        } catch (QSException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(
                outmodel.getClass().getName(), Bucket.PutBucketACLOutput.class.getName());
    }

    @Test
    public void testOutputModel() {
        Bucket.PutBucketACLOutput outmodel = null;
        try {
            outmodel =
                    (Bucket.PutBucketACLOutput)
                            QSParamInvokeUtil.getOutputModel(Bucket.PutBucketACLOutput.class);
        } catch (QSException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(
                outmodel.getClass().getName(), Bucket.PutBucketACLOutput.class.getName());
    }

    @Test
    public void testParam() {
        ParamTestModel instancesInput = new ParamTestModel();
        List<String> imgs =
                new ArrayList<String>() {
                    {
                        add("test-0001");
                        add("test-0002");
                    }
                };
        instancesInput.setImageID(imgs);
        instancesInput.setAction("serch_word_test");
        Map queryParam =
                QSParamInvokeUtil.getRequestParams(instancesInput, QSConstant.PARAM_TYPE_QUERY);
        Map bodyParam =
                QSParamInvokeUtil.getRequestParams(
                        instancesInput, QSConstant.PARAM_TYPE_BODYINPUTSTREAM);

        Assert.assertEquals(queryParam.get("action"), "serch_word_test");
        Assert.assertEquals(((List) queryParam.get("image_id")).get(1), "test-0002");
        Assert.assertEquals(queryParam.size(), 2);
        Assert.assertEquals(bodyParam.size(), 0);
    }
}
