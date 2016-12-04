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

public class BucketAclSubServiceTest {

	private Bucket Bucket;
    public static String bucketName = TestUtil.getBucketName();

    private Bucket.PutBucketACLOutput putBucketACLOutput;
    private Bucket.GetBucketACLOutput getBucketACLOutput;

    @When("^initialize the bucket ACL$")
    public void initialize_the_bucket_ACL() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext(); 
        		//TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
    }

    @Then("^the bucket ACL is initialized$")
    public void the_bucket_ACL_is_initialized() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertNotNull(Bucket);
    }

    @When("^put bucket ACL:$")
    public void put_bucket_ACL(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        Bucket.PutBucketACLInput input = new Bucket.PutBucketACLInput();
        input.setBodyInput(arg1);
        putBucketACLOutput = Bucket.putACL(input);
    }

    @Then("^put bucket ACL status code is (\\d+)$")
    public void put_bucket_ACL_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("put_bucket_ACL_status_code_msg:"+this.putBucketACLOutput.getMessage());
        TestUtil.assertEqual(this.putBucketACLOutput.getStatueCode(),arg1);
    }

    @When("^get bucket ACL$")
    public void get_bucket_ACL() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        getBucketACLOutput = Bucket.getACL();
    }

    @Then("^get bucket ACL status code is (\\d+)$")
    public void get_bucket_ACL_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_bucket_ACL_status_code_msg:"+this.getBucketACLOutput.getMessage());
        TestUtil.assertEqual(this.getBucketACLOutput.getStatueCode(),arg1);
    }

    @Then("^get bucket ACL should have grantee name \"([^\"]*)\"$")
    public void get_bucket_ACL_shoud_have_grantee_name(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_bucket_ACL_shoud_have_grantee_name:"+this.getBucketACLOutput.getACL());
    }


}

