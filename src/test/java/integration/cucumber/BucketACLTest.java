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
import com.qingstor.sdk.service.Types.ACLModel;
import com.qingstor.sdk.service.Types.GranteeModel;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;

public class BucketACLTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EvnContext evnContext = TestUtil.getEvnContext();
    private static Bucket testBucket = new Bucket(evnContext, zone, bucketName);

    private Bucket.PutBucketACLOutput putBucketACLOutput;
    private Bucket.GetBucketACLOutput getBucketACLOutput;

    @When("^put bucket ACL:$")
    public void put_bucket_ACL(String arg1) throws Throwable {
        Bucket.PutBucketACLInput input = new Bucket.PutBucketACLInput();
        ACLModel acl = new ACLModel();
        acl.setPermission("FULL_CONTROL");
        GranteeModel gm = new GranteeModel();
        gm.setName("QS_ALL_USERS");
        gm.setType("group");
        acl.setGrantee(gm);
        List<ACLModel> aa = new ArrayList<ACLModel>();
        aa.add(acl);
        input.setACL(aa);
        putBucketACLOutput = testBucket.putACL(input);
    }

    @Then("^put bucket ACL status code is (\\d+)$")
    public void put_bucket_ACL_status_code_is(int arg1) throws Throwable {
        System.out.println(
                "put_bucket_ACL_status_code_msg:" + this.putBucketACLOutput.getMessage());
        TestUtil.assertEqual(this.putBucketACLOutput.getStatueCode(), arg1);
    }

    @When("^get bucket ACL$")
    public void get_bucket_ACL() throws Throwable {
        getBucketACLOutput = testBucket.getACL();
    }

    @Then("^get bucket ACL status code is (\\d+)$")
    public void get_bucket_ACL_status_code_is(int arg1) throws Throwable {
        System.out.println(
                "get_bucket_ACL_status_code_msg:" + this.getBucketACLOutput.getMessage());
        TestUtil.assertEqual(this.getBucketACLOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket ACL should have grantee name \"([^\"]*)\"$")
    public void get_bucket_ACL_shoud_have_grantee_name(String arg1) throws Throwable {
        System.out.println(
                "get_bucket_ACL_shoud_have_grantee_name:" + this.getBucketACLOutput.getACL());
    }
}
