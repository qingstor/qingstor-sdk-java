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

import java.util.ArrayList;
import java.util.List;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BucketCorSubServiceTest {

	public  String bucketName = TestUtil.getBucketName();
    private Bucket Bucket;

    private Bucket.PutBucketCORSOutput putBucketCORSOutput;
    private Bucket.GetBucketCORSOutput getBucketCORSOutput;
    private Bucket.DeleteBucketCORSOutput deleteBucketCORSOutput;

    @When("^initialize the bucket CORS$")
    public void initialize_the_bucket_CORS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
    }

    @Then("^the bucket CORS is initialized$")
    public void the_bucket_CORS_is_initialized() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertNotNull(this.Bucket);
    }

    @When("^put bucket CORS:$")
    public void put_bucket_CORS(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        Bucket.PutBucketCORSInput input = new Bucket.PutBucketCORSInput();
        List cors = new ArrayList();

        input.setBodyInput(arg1);
        putBucketCORSOutput = Bucket.putCORS(input);
    }

    @Then("^put bucket CORS status code is (\\d+)$")
    public void put_bucket_CORS_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("put_bucket_CORS_status_code_msg:"+this.putBucketCORSOutput.getMessage());
        TestUtil.assertEqual(this.putBucketCORSOutput.getStatueCode(),arg1);
    }

    @When("^get bucket CORS$")
    public void get_bucket_CORS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        getBucketCORSOutput = Bucket.getCORS();
    }

    @Then("^get bucket CORS status code is (\\d+)$")
    public void get_bucket_CORS_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(getBucketCORSOutput.getStatueCode(),arg1);
    }

    @Then("^get bucket CORS should have allowed origin \"([^\"]*)\"$")
    public void get_bucket_CORS_should_have_allowed_origin(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_bucket_CORS_should_have_allowed_origin_msg:"+this.getBucketCORSOutput.getMessage());
    }

    @When("^delete bucket CORS$")
    public void delete_bucket_CORS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        deleteBucketCORSOutput = Bucket.deleteCORS();
    }


    @Then("^delete bucket CORS status code is (\\d+)$")
    public void delete_bucket_CORS_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(deleteBucketCORSOutput.getStatueCode(),arg1);
    }


}

