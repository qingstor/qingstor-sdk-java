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
package integration.cucumber;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.service.Bucket;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;

public class ImageTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket = new Bucket(ctx, zone, bucketName);
    private static Bucket.ImageProcessOutput imageProcessOutput;

    @When("^image process with key \"([^\"]*)\" and query \"([^\"]*)\"$")
    public void image_process_with_key_and_query(String key, String query) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.ImageProcessInput input = new Bucket.ImageProcessInput();
        input.setAction(query);
        Bucket.PutObjectInput upload = new Bucket.PutObjectInput();
        File f = new File("./tests/features/fixtures/test.jpg");
        upload.setBodyInputFile(f);
        upload.setContentType("image/jpg; charset=utf8");
        upload.setContentLength(f.length());
        testBucket.putObject(key, upload);

        imageProcessOutput = testBucket.imageProcess(key, input);
    }

    @Then("^image process status code is (\\d+)$")
    public void image_process_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("image_process_message:" + imageProcessOutput.getMessage());
        TestUtil.assertEqual(imageProcessOutput.getStatueCode(), statusCode);
    }
}
