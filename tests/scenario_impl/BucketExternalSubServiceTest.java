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

public class BucketExternalSubServiceTest {

	private Bucket Bucket;
    public  String bucketName = TestUtil.getBucketName();

    private Bucket.PutBucketExternalMirrorOutput putBucketExternalMirrorOutput;
    private Bucket.GetBucketExternalMirrorOutput getBucketExternalMirrorOutput;
    private Bucket.DeleteBucketExternalMirrorOutput deleteBucketExternalMirrorOutput;


    @When("^initialize the bucket external mirror$")
    public void initialize_the_bucket_external_mirror() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);

    }

    @Then("^the bucket external mirror is initialized$")
    public void the_bucket_external_mirror_is_initialized() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertNotNull(Bucket);
    }


    @When("^put bucket external mirror:$")
    public void put_bucket_external_mirror(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        Bucket.PutBucketExternalMirrorInput input = new Bucket.PutBucketExternalMirrorInput();
        input.setSourceSite(arg1);
        putBucketExternalMirrorOutput  = Bucket.putExternalMirror(input);
    }

    @Then("^put bucket external mirror status code is (\\d+)$")
    public void put_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(putBucketExternalMirrorOutput.getStatueCode(),arg1);
    }

    @When("^get bucket external mirror$")
    public void get_bucket_external_mirror() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);
        getBucketExternalMirrorOutput = Bucket.getExternalMirror();
    }

    @Then("^get bucket external mirror status code is (\\d+)$")
    public void get_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(getBucketExternalMirrorOutput.getStatueCode(),arg1);
    }

    @Then("^get bucket external mirror should have source_site \"([^\"]*)\"$")
    public void get_bucket_external_mirror_should_have_source_site(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_bucket_external_mirror_should_have_source_site:"+getBucketExternalMirrorOutput.getSourceSite());
    }

    @When("^delete bucket external mirror$")
    public void delete_bucket_external_mirror() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext,bucketName);

        deleteBucketExternalMirrorOutput = Bucket.deleteExternalMirror();

    }

    @Then("^delete bucket external mirror status code is (\\d+)$")
    public void delete_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(deleteBucketExternalMirrorOutput.getStatueCode(),arg1);
    }


}

