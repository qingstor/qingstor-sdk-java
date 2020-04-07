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
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.utils.QSJSONUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

public class BucketPolicyTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket = new Bucket(ctx, zone, bucketName);

    private Bucket.PutBucketPolicyOutput putBucketPolicyOutput;
    private Bucket.GetBucketPolicyOutput getBucketPolicyOutput;
    private Bucket.DeleteBucketPolicyOutput deleteBucketPolicyOutput;

    @When("^put bucket policy:$")
    public void put_bucket_policy(String policy) throws Throwable {
        Bucket.PutBucketPolicyInput input = new Bucket.PutBucketPolicyInput();
        System.out.println("put_bucket_policy:" + policy);
        JSONObject o = new QSJSONUtil().toJSONObject(policy);
        JSONObject statment = (JSONObject) o.getJSONArray("statement").get(0);
        JSONArray resource = statment.getJSONArray("resource");
        resource.put(bucketName + "/*");
        System.out.println("put_bucket_policy:" + o.toString());
        input.setBodyInput(o.toString());
        putBucketPolicyOutput = testBucket.putPolicy(input);
    }

    @Then("^put bucket policy status code is (\\d+)$")
    public void put_bucket_policy_status_code_is(int arg1) throws Throwable {
        System.out.println(
                "put_bucket_policy_status_code_is:" + this.putBucketPolicyOutput.getMessage());
        TestUtil.assertEqual(this.putBucketPolicyOutput.getStatueCode(), arg1);
    }

    @When("^get bucket policy$")
    public void get_bucket_policy() throws Throwable {
        getBucketPolicyOutput = testBucket.getPolicy();
    }

    @Then("^get bucket policy status code is (\\d+)$")
    public void get_bucket_policy_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(this.getBucketPolicyOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket policy should have Referer \"([^\"]*)\"$")
    public void get_bucket_policy_should_have_Referer(String arg1) throws Throwable {
        System.out.println(
                "get_bucket_policy_should_have_Referer:\n"
                        + this.getBucketPolicyOutput.getStatement());
    }

    @When("^delete bucket policy$")
    public void delete_bucket_policy() throws Throwable {
        deleteBucketPolicyOutput = testBucket.deletePolicy();
    }

    @Then("^delete bucket policy status code is (\\d+)$")
    public void delete_bucket_policy_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(this.deleteBucketPolicyOutput.getStatueCode(), arg1);
    }
}
