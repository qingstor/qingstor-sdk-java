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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.service.Bucket;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class MultiObjectTemplateUnitTest {


    public static String QC_STOR_CONTENT_TEXT = "text/plain; charset=utf-8";
    public static String QC_STOR_CONTENT_JSON = "application/json";
    public static String QC_STOR_CONTENT_MULTI = "multipart/form-data;";


    @Test
    /*
            *
            *
            *
            */
    public void qcstorHeadBucketObject() throws Exception {

        EvnContext evn = EvnContext.loadFromFile("qingcloud_cli/config_stor.yaml");
        String bucketName = "mobile";
        Bucket ss = new Bucket(evn,bucketName);
        String objectName = "voddd.jpg";

        Bucket.HeadObjectInput bb = new Bucket.HeadObjectInput();
        //bb.setIfMatch("cda0a741aac6541c730ed6be6c6f5bcc");
        OutputModel dd = ss.headObject(objectName,bb);

        System.out.println(dd+"");

    }

    @Test
    /*
            *
            *
            *
            */
    public void qcstorDeleteBucketObject() throws Exception {

        EvnContext evn = EvnContext.loadFromFile("qingcloud_cli/config_stor.yaml");
        String bucketName = "mobile";
        Bucket ss = new Bucket(evn,bucketName);
        String objectName = "ddd/640.jpeg";


        //bb.setIfMatch("cda0a741aac6541c730ed6be6c6f5bcc");
        OutputModel dd = ss.deleteObject(objectName);

        System.out.println(dd+"");

    }

    @Test
    /*
            *
            *
            *
            */
    public void qcstorGetObject() throws Exception {

        EvnContext evn = EvnContext.loadFromFile("qingcloud_cli/config_stor.yaml");
        String bucketName = "mobile";
        Bucket ss = new Bucket(evn,bucketName);
        String objectName = "voddd.jpg";

        Bucket.GetObjectInput bb = new Bucket.GetObjectInput();

        //bb.setIfMatch("cda0a741aac6541c730ed6be6c6f5bcc");
        Bucket.GetObjectOutput dd = ss.getObject(objectName,bb);

        System.out.println(dd+"");

        if(dd != null && dd.getBodyInputStream() != null){
            File ff = new File("Downloads/voddd-yyAAAy.jpg");
            OutputStream os = new FileOutputStream(ff);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = dd.getBodyInputStream().read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            dd.getBodyInputStream().close();
        }

    }
}
