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
import com.qingstor.sdk.service.Bucket.InitiateMultipartUploadOutput;
import com.qingstor.sdk.service.Bucket.ListMultipartUploadsInput;
import com.qingstor.sdk.service.Bucket.ListMultipartUploadsOutput;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BucketTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket;

    private static Bucket.PutBucketOutput putBucketOutput;
    private static Bucket.PutBucketOutput putBucketOutput2;
    private static Bucket.ListObjectsOutput listObjectsOutput;
    private static Bucket.HeadBucketOutput headBucketOutput;
    private static Bucket.DeleteBucketOutput deleteBucketOutput;
    private static Bucket.GetBucketStatisticsOutput getBucketStatisticsOutput;
    private static Bucket.DeleteMultipleObjectsOutput deleteMultipleObjectsOutput;
    private static ListMultipartUploadsOutput listMultipartUploadsOutput;

    @When("^initialize the bucket$")
    public void initialize_the_bucket() throws Throwable {
        testBucket = new Bucket(ctx, zone, bucketName);
    }

    @Then("^the bucket is initialized$")
    public void the_bucket_is_initialized() throws Throwable {
        TestUtil.assertNotNull(this.testBucket);
    }

    @When("^put bucket$")
    public void put_bucket() throws Throwable {}

    @Then("^put bucket status code is (\\d+)$")
    public void put_bucket_status_code_is(int arg1) throws Throwable {}

    @When("^put same bucket again$")
    public void put_same_bucket_again() throws Throwable {}

    @Then("^put same bucket again status code is (\\d+)$")
    public void put_same_bucket_again_status_code_is(int arg1) throws Throwable {}

    @When("^list objects$")
    public void list_objects() throws Throwable {
        Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
        input.setLimit(20);
        listObjectsOutput = testBucket.listObjects(input);
    }

    @Then("^list objects status code is (\\d+)$")
    public void list_objects_status_code_is(int arg1) throws Throwable {
        System.out.println(bucketName + "list_objects:" + listObjectsOutput.getMessage());
        TestUtil.assertEqual(listObjectsOutput.getStatueCode(), arg1);
    }

    @Then("^list objects keys count is (\\d+)$")
    public void list_objects_keys_count_is(int arg1) throws Throwable {
        System.out.println("list_objects_keys_count_msg:" + listObjectsOutput.getPrefix());
    }

    @When("^head bucket$")
    public void head_bucket() throws Throwable {
        headBucketOutput = testBucket.head();
    }

    @Then("^head bucket status code is (\\d+)$")
    public void head_bucket_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(headBucketOutput.getStatueCode(), arg1);
    }

    @When("^delete bucket$")
    public void delete_bucket() throws Throwable {}

    @Then("^delete bucket status code is (\\d+)$")
    public void delete_bucket_status_code_is(int arg1) throws Throwable {}

    @When("^delete multiple objects:$")
    public void delete_multiple_objects(String arg1) throws Throwable {
        Bucket.DeleteMultipleObjectsInput input = new Bucket.DeleteMultipleObjectsInput();
        input.setBodyInput(arg1);
        deleteMultipleObjectsOutput = testBucket.deleteMultipleObjects(input);
    }

    @Then("^delete multiple objects code is (\\d+)$")
    public void delete_multiple_objects_code_is(int arg1) throws Throwable {
        System.out.println("delete_multiple_objects:" + deleteMultipleObjectsOutput.getMessage());
        TestUtil.assertEqual(deleteMultipleObjectsOutput.getStatueCode(), arg1);
    }

    @When("^get bucket statistics$")
    public void get_bucket_statistics() throws Throwable {
        getBucketStatisticsOutput = testBucket.getStatistics();
    }

    @Then("^get bucket statistics status code is (\\d+)$")
    public void get_bucket_statistics_status_code_is(int arg1) throws Throwable {
        System.out.println("bucket_statistics:" + getBucketStatisticsOutput.getMessage());
        TestUtil.assertEqual(getBucketStatisticsOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket statistics status is \"([^\"]*)\"$")
    public void get_bucket_statistics_status_is(String arg1) throws Throwable {
        System.out.println(
                "get_bucket_statistics_status_msg:" + getBucketStatisticsOutput.getStatus());
    }

    @Given("^an object created by Initiate Multipart Upload$")
    public void an_object_created_by_Initiate_Multipart_Upload() throws Throwable {

        InitiateMultipartUploadOutput output =
                testBucket.initiateMultipartUpload("testa/aakkaakk上传", null);
    }

    @When("^list multipart uploads$")
    public void list_multipart_uploads() throws Throwable {
        ListMultipartUploadsInput input = new ListMultipartUploadsInput();
        input.setLimit(1);
        listMultipartUploadsOutput = testBucket.listMultipartUploads(input);
        System.out.println(listMultipartUploadsOutput.getMessage());
    }

    @Then("^list multipart uploads count is (\\d+)$")
    public void list_multipart_uploads_count_is(int arg1) throws Throwable {
        System.out.println(listMultipartUploadsOutput.getUploads().size());
        TestUtil.assertEqual(listMultipartUploadsOutput.getUploads().size(), arg1);
    }

    @Given("^an object created by initiate multipart upload$")
    public void an_object_created_by_initiate_multipart_upload() throws Throwable {

        // Write code here that turns the phrase above into concrete actions
        String objectKey = "testa/aakkaakk上传";
        InitiateMultipartUploadOutput out = testBucket.initiateMultipartUpload(objectKey, null);
        System.out.println("InitiateMultipartUploadOutput:" + out.getMessage());
    }

    @When("^list multipart uploads with prefix$")
    public void list_multipart_uploads_with_prefix() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        ListMultipartUploadsInput input = new ListMultipartUploadsInput();
        input.setPrefix("testa");
        listMultipartUploadsOutput = testBucket.listMultipartUploads(input);
    }

    @Then("^list multipart uploads with prefix count is (\\d+)$")
    public void list_multipart_uploads_with_prefix_count_is(int count) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(
                "list_multipart_uploads_with_prefix_count_is:"
                        + listMultipartUploadsOutput.getMessage());
        if (listMultipartUploadsOutput.getUploads().size() - count < 0) {
            throw new Exception(
                    count + " is not equal " + listMultipartUploadsOutput.getUploads().size());
        }
    }
}
