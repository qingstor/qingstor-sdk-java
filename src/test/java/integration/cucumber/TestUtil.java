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
package integration.cucumber;

import com.qingstor.sdk.config.EnvContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class TestUtil {

    public static void assertNotNull(Object o) throws Exception {
        if (o == null) {
            throw new Exception("is null");
        }
    }

    public static void assertEqual(int i, int j) throws Exception {
        if (i != j) {
            throw new Exception(i + " is not equal " + j);
        }
    }

    public static void assertEqual(String i, String j) throws Exception {
        if (!i.equals(j)) {
            throw new Exception(i + " is not equal " + j);
        }
    }

    public static EnvContext getEnvContext() {
        try {
            return EnvContext.loadFromFile("config.yaml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getZone() {
        return getFileConfig("zone");
    }

    public static String getBucketName() {
        return getFileConfig("bucket_name");
    }

    private static String getFileConfig(String key) {
        File f = new File("test_config.yaml");
        if (f.exists()) {
            BufferedReader br = null;
            Yaml yaml = new Yaml();
            try {
                Map confParams = (Map) yaml.load(new FileInputStream(f));
                if (confParams.containsKey(key)) {
                    return String.valueOf(confParams.get(key));
                }
                return "";
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
}
