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

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.utils.QSJSONUtil;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;

public class BucketExternalTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EvnContext evnContext = TestUtil.getEvnContext();
    private static Bucket testBucket = new Bucket(evnContext, zone, bucketName);

    private Bucket.PutBucketExternalMirrorOutput putBucketExternalMirrorOutput;
    private Bucket.GetBucketExternalMirrorOutput getBucketExternalMirrorOutput;
    private Bucket.DeleteBucketExternalMirrorOutput deleteBucketExternalMirrorOutput;

    @When("^put bucket external mirror:$")
    public void put_bucket_external_mirror(String arg1) throws Throwable {
        Bucket.PutBucketExternalMirrorInput input = new Bucket.PutBucketExternalMirrorInput();
        JSONObject obj = QSJSONUtil.convertJSONObject(arg1);
        input.setSourceSite(QSJSONUtil.toString(obj, "source_site"));
        putBucketExternalMirrorOutput = testBucket.putExternalMirror(input);
    }

    @Then("^put bucket external mirror status code is (\\d+)$")
    public void put_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(putBucketExternalMirrorOutput.getStatueCode(), arg1);
    }

    @When("^get bucket external mirror$")
    public void get_bucket_external_mirror() throws Throwable {
        getBucketExternalMirrorOutput = testBucket.getExternalMirror();
    }

    @Then("^get bucket external mirror status code is (\\d+)$")
    public void get_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(getBucketExternalMirrorOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket external mirror should have source_site \"([^\"]*)\"$")
    public void get_bucket_external_mirror_should_have_source_site(String arg1) throws Throwable {
        System.out.println(
                "get_bucket_external_mirror_should_have_source_site:"
                        + getBucketExternalMirrorOutput.getSourceSite());
    }

    @When("^delete bucket external mirror$")
    public void delete_bucket_external_mirror() throws Throwable {
        deleteBucketExternalMirrorOutput = testBucket.deleteExternalMirror();
    }

    @Then("^delete bucket external mirror status code is (\\d+)$")
    public void delete_bucket_external_mirror_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(deleteBucketExternalMirrorOutput.getStatueCode(), arg1);
    }
}
