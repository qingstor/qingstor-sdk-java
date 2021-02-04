/*
 * Copyright (C) 2021 Yunify, Inc.
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
package com.qingstor.sdk.config;

import com.qingstor.sdk.exception.QSException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

public class EnvContextTest {

    @Test
    public void testDefault() {
        EnvContext ctx = new EnvContext("testkey", "test_asss");
        ctx.setAdditionalUserAgent("\"");
        Assert.assertEquals(ctx.getAccessKeyId(), "testkey");
        Assert.assertEquals(ctx.getSecretAccessKey(), "test_asss");
        Assert.assertEquals(ctx.getEndpoint().toString(), "https://qingstor.com");
        String validate = ctx.validateParam();
        Assert.assertNotNull(validate);
    }

    @Test
    public void testConfig() throws QSException {
        String config =
                "access_key_id: 'testkey'\n"
                        + "secret_access_key: 'test_asss'\n"
                        + "additional_user_agent: 'test/integration'\n"
                        + "port: 443\n"
                        + "protocol: https\n";
        File f = new File("/tmp/config.yaml");
        boolean bConf = false;
        try {
            OutputStream output = new FileOutputStream(f);
            output.write(config.getBytes());
            output.close();
            bConf = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bConf) {
            EnvContext ctx = EnvContext.loadFromFile("/tmp/config.yaml");
            Assert.assertEquals(ctx.getAccessKeyId(), "testkey");
            Assert.assertEquals(ctx.getSecretAccessKey(), "test_asss");
            Assert.assertEquals(ctx.getEndpoint().toString(), "https://qingstor.com");
            Assert.assertEquals(ctx.getAdditionalUserAgent(), "test/integration");
        }
        String hostPart = "host: qingcloud.com\n";
        config = config + hostPart;
        bConf = false;
        try {
            OutputStream output = new FileOutputStream(f);
            output.write(config.getBytes());
            output.close();
            bConf = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bConf) {
            EnvContext ctx = EnvContext.loadFromFile("/tmp/config.yaml");
            Assert.assertEquals(ctx.getAccessKeyId(), "testkey");
            Assert.assertEquals(ctx.getSecretAccessKey(), "test_asss");
            Assert.assertEquals(ctx.getEndpoint().toString(), "https://qingcloud.com:443");
        }
    }
}
