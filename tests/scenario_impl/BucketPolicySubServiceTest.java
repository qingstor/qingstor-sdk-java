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

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BucketPolicySubServiceTest {

	private String bucketName = TestUtil.getBucketName();

    private Bucket Bucket;

    private Bucket.PutBucketPolicyOutput putBucketPolicyOutput;
    private Bucket.GetBucketPolicyOutput getBucketPolicyOutput;
    private Bucket.DeleteBucketPolicyOutput deleteBucketPolicyOutput;

    @When("^initialize the bucket policy$")
    public void initialize_the_bucket_policy() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
    }

    @Then("^the bucket policy is initialized$")
    public void the_bucket_policy_is_initialized() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertNotNull(this.Bucket);
    }

    @When("^put bucket policy:$")
    public void put_bucket_policy(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        Bucket.PutBucketPolicyInput input = new Bucket.PutBucketPolicyInput();
        input.setBodyInput(arg1);
        putBucketPolicyOutput = Bucket.putPolicy(input);
    }

    @Then("^put bucket policy status code is (\\d+)$")
    public void put_bucket_policy_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	System.out.println("put_bucket_policy_status_code_is:"+this.putBucketPolicyOutput.getMessage());
        TestUtil.assertEqual(this.putBucketPolicyOutput.getStatueCode(),arg1);
    }

    @When("^get bucket policy$")
    public void get_bucket_policy() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        getBucketPolicyOutput = Bucket.getPolicy();
    }

    @Then("^get bucket policy status code is (\\d+)$")
    public void get_bucket_policy_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(this.getBucketPolicyOutput.getStatueCode(),arg1);
    }

    @Then("^get bucket policy should have Referer \"([^\"]*)\"$")
    public void get_bucket_policy_should_have_Referer(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_bucket_policy_should_have_Referer:\n"+this.getBucketPolicyOutput.getStatement());
    }

    @When("^delete bucket policy$")
    public void delete_bucket_policy() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        deleteBucketPolicyOutput = Bucket.deletePolicy();
    }

    @Then("^delete bucket policy status code is (\\d+)$")
    public void delete_bucket_policy_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(this.deleteBucketPolicyOutput.getStatueCode(),arg1);
    }


}

