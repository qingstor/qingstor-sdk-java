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

package scenario_impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.qingstor.sdk.config.EvnContext;

public class TestUtil {

	public static void assertNotNull(Object o) throws Exception{
        if(o == null){
            throw new Exception("is null");
        }
    }


    public static void assertEqual(int i,int j) throws Exception{
        if(i != j){
            throw new Exception(i + " is not equal "+j);
        }
    }

    public static EvnContext getEvnContext(){
    	return EvnContext.loadFromFile("config.yaml");
    }

    public static String getZone(){
    	return getFileConfig("zone");
    }

    public static String getBucketName(){
    	return getFileConfig("bucket_name");
    }

    private static String getFileConfig(String key){
    	File f = new File("test_config.yaml");
        if(f.exists()){
            BufferedReader br = null;
            Map<String,String> confParams = new HashMap<String,String>();
            try {
                br = new BufferedReader(new
                        InputStreamReader(new FileInputStream(f)));
                String strConf = null;

                while ((strConf = br.readLine()) != null){
                    String[] str = strConf.split(":");
                    if(str.length > 1){
                        confParams.put(str[0].trim(),str[1].trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(br != null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return confParams.get(key);
        }
        return "";
    }
}

