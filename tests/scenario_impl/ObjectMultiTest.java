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
import com.qingstor.sdk.service.Bucket.InitiateMultipartUploadOutput;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.*;

public class ObjectMultiTest {


    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EvnContext evnContext = TestUtil.getEvnContext();
    private static Bucket testBucket = new Bucket(evnContext, zone, bucketName);

    private static String apkContentType = "application/vnd.android.package-archive";
    private static Bucket.UploadMultipartOutput uploadMultipartOutput1;
    private static Bucket.UploadMultipartOutput uploadMultipartOutput2;
    private static Bucket.UploadMultipartOutput uploadMultipartOutput3;
    private static Bucket.ListMultipartOutput listMultipartOutput;
    private static Bucket.CompleteMultipartUploadOutput completeMultipartUploadOutput;
    private static Bucket.AbortMultipartUploadOutput abortMultipartUploadOutput;
    private static Bucket.DeleteObjectOutput deleteObjectOutput;

    private static Bucket.InitiateMultipartUploadOutput initOutput;

    private static String multipart_upload_name = "";
    private static String multipart_upload_id = "";

    @When("^initiate multipart upload with key \"([^\"]*)\"$")
    public void initiate_multipart_upload_with_key(String objectKey) throws Throwable {
        Bucket.InitiateMultipartUploadInput input = new Bucket.InitiateMultipartUploadInput();
        initOutput = testBucket.initiateMultipartUpload(objectKey, input);
        multipart_upload_name = objectKey;
        multipart_upload_id = this.initOutput.getUploadID();
    }

    @Then("^initiate multipart upload status code is (\\d+)$")
    public void initiate_multipart_upload_status_code_is(int statusCode) throws Throwable {
        TestUtil.assertEqual(initOutput.getStatueCode(), statusCode);
    }

    @When("^upload the first part with key \"([^\"]*)\"$")
    public void upload_the_first_part(String objectKey) throws Throwable {
        Runtime run = Runtime.getRuntime();
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_0\nbs=1048576\ncount=6");
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                System.out.println(lineStr);
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int part_number = 0;
        File f = new File("/tmp/sdk_bin_part_0");
        Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
        input.setXQSEncryptionCustomerKey(objectKey);
        input.setContentLength(f.length());
        input.setBodyInputFile(f);
        input.setPartNumber(part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput1 = testBucket.uploadMultipart(multipart_upload_name, input);

    }

    @Then("^upload the first part status code is (\\d+)$")
    public void upload_the_first_part_status_code_is(int statusCode) throws Throwable {
        System.out.println("upload_the_first_part_status_code_msg" + uploadMultipartOutput1.getMessage());
        TestUtil.assertEqual(this.uploadMultipartOutput1.getStatueCode(), statusCode);
    }

    @When("^upload the second part with key \"([^\"]*)\"$")
    public void upload_the_second_part(String objectKey) throws Throwable {
        Runtime run = Runtime.getRuntime();
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_1\nbs=1048576\ncount=6");
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                System.out.println(lineStr);
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int part_number = 1;
        File f = new File("/tmp/sdk_bin_part_1");
        Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
        input.setXQSEncryptionCustomerKey(objectKey);
        input.setContentLength(f.length());
        input.setBodyInputStream(new FileInputStream(f));
        input.setPartNumber( part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput2 = testBucket.uploadMultipart(multipart_upload_name, input);
    }

    @Then("^upload the second part status code is (\\d+)$")
    public void upload_the_second_part_status_code_is(int statusCode) throws Throwable {
        System.out.println("upload_the_first_part_status_code_is2" + uploadMultipartOutput2.getMessage());
        TestUtil.assertEqual(this.uploadMultipartOutput2.getStatueCode(), statusCode);
    }

    @When("^upload the third part with key \"([^\"]*)\"$")
    public void upload_the_third_part(String objectKey) throws Throwable {
        Runtime run = Runtime.getRuntime();
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_2\nbs=1048576\ncount=6");
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                System.out.println(lineStr);
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int part_number = 2;
        File f = new File("/tmp/sdk_bin_part_2");
        Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
        input.setXQSEncryptionCustomerKey(objectKey);
        input.setContentLength(f.length());
        input.setBodyInputStream(new FileInputStream(f));
        input.setPartNumber(part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput3 = testBucket.uploadMultipart(multipart_upload_name, input);
    }

    @Then("^upload the third part status code is (\\d+)$")
    public void upload_the_third_part_status_code_is(int statusCode) throws Throwable {
        TestUtil.assertEqual(this.uploadMultipartOutput3.getStatueCode(), statusCode);
    }

    @When("^list multipart with key \"([^\"]*)\"$")
    public void list_multipart(String key) throws Throwable {
        Bucket.ListMultipartInput input = new Bucket.ListMultipartInput();
        input.setUploadID(initOutput.getUploadID());
        input.setLimit(10);
        listMultipartOutput = testBucket.listMultipart(multipart_upload_name, input);
    }

    @Then("^list multipart status code is (\\d+)$")
    public void list_multipart_status_code_is(int statusCode) throws Throwable {
        System.out.println("list_multipart_status_code_msg" + listMultipartOutput.getMessage());
        TestUtil.assertEqual(this.listMultipartOutput.getStatueCode(), statusCode);
    }

    @Then("^list multipart object parts count is (\\d+)$")
    public void list_multipart_object_parts_count_is(int statusCode) throws Throwable {
        System.out.println("list_multipart_object_parts_count_is:" + this.listMultipartOutput.getCount());
    }

    @When("^complete multipart upload with key \"([^\"]*)\"$")
    public void complete_multipart_upload(String objectKey) throws Throwable {
        Bucket.CompleteMultipartUploadInput input = new Bucket.CompleteMultipartUploadInput();
        input.setUploadID(initOutput.getUploadID());
        String content = "{\n" +
                "    \"object_parts\": [\n" +
                "        {\"part_number\": 0},\n" +
                "        {\"part_number\": 1},\n" +
                "        {\"part_number\": 2}\n" +
                "    ]\n" +
                "}";
        input.setBodyInput(content);

        completeMultipartUploadOutput = testBucket.completeMultipartUpload(objectKey, input);
    }

    @Then("^complete multipart upload status code is (\\d+)$")
    public void complete_multipart_upload_status_code_is(int statusCode) throws Throwable {
        System.out.println("complete_multipart_upload_status_code_msg" + completeMultipartUploadOutput.getMessage());
        TestUtil.assertEqual(completeMultipartUploadOutput.getStatueCode(), statusCode);
    }

    @When("^abort multipart upload with key \"([^\"]*)\"$")
    public void abort_multipart_upload(String objectKey) throws Throwable {
        Bucket.AbortMultipartUploadInput input = new Bucket.AbortMultipartUploadInput();
        input.setUploadID(initOutput.getUploadID());
        abortMultipartUploadOutput = testBucket.abortMultipartUpload(objectKey, input);

    }

    @Then("^abort multipart upload status code is (\\d+)$")
    public void abort_multipart_upload_status_code_is(int statusCode) throws Throwable {
        TestUtil.assertEqual(abortMultipartUploadOutput.getStatueCode(), statusCode);
    }

    @When("^delete the multipart object with key \"([^\"]*)\"$")
    public void delete_the_multipart_object(String objectKey) throws Throwable {
        deleteObjectOutput = testBucket.deleteObject(objectKey);
    }

    @Then("^delete the multipart object status code is (\\d+)$")
    public void delete_the_multipart_object_status_code_is(int statusCode) throws Throwable {
        TestUtil.assertEqual(deleteObjectOutput.getStatueCode(), statusCode);
    }


}

