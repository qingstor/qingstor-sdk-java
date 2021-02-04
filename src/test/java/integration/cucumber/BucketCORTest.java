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
import java.util.ArrayList;
import java.util.List;

public class BucketCORTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket = new Bucket(ctx, zone, bucketName);

    private Bucket.PutBucketCORSOutput putBucketCORSOutput;
    private Bucket.GetBucketCORSOutput getBucketCORSOutput;
    private Bucket.DeleteBucketCORSOutput deleteBucketCORSOutput;

    @When("^put bucket CORS:$")
    public void put_bucket_CORS(String arg1) throws Throwable {
        Bucket.PutBucketCORSInput input = new Bucket.PutBucketCORSInput();
        List cors = new ArrayList();

        input.setBodyInput(arg1);
        putBucketCORSOutput = testBucket.putCORS(input);
    }

    @Then("^put bucket CORS status code is (\\d+)$")
    public void put_bucket_CORS_status_code_is(int arg1) throws Throwable {
        System.out.println(
                "put_bucket_CORS_status_code_msg:" + this.putBucketCORSOutput.getMessage());
        TestUtil.assertEqual(this.putBucketCORSOutput.getStatueCode(), arg1);
    }

    @When("^get bucket CORS$")
    public void get_bucket_CORS() throws Throwable {
        getBucketCORSOutput = testBucket.getCORS();
    }

    @Then("^get bucket CORS status code is (\\d+)$")
    public void get_bucket_CORS_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(getBucketCORSOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket CORS should have allowed origin \"([^\"]*)\"$")
    public void get_bucket_CORS_should_have_allowed_origin(String arg1) throws Throwable {
        System.out.println(
                "get_bucket_CORS_should_have_allowed_origin_msg:"
                        + this.getBucketCORSOutput.getMessage());
    }

    @When("^delete bucket CORS$")
    public void delete_bucket_CORS() throws Throwable {
        deleteBucketCORSOutput = testBucket.deleteCORS();
    }

    @Then("^delete bucket CORS status code is (\\d+)$")
    public void delete_bucket_CORS_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(deleteBucketCORSOutput.getStatueCode(), arg1);
    }
}
