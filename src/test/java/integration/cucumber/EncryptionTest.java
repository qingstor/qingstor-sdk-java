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
import java.io.*;
import java.nio.file.Files;

public class EncryptionTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket;

    private static Bucket.PutObjectOutput putObjectOutput;

    private static Bucket.PutObjectOutput copyObjectOutput;

    private static Bucket.PutObjectOutput moveOutput;

    private static Bucket.GetObjectOutput getObjectOutput;

    private static Bucket.HeadObjectOutput headObjectOutput;

    private static Bucket.DeleteObjectOutput deleteObjectOutput;

    private static Bucket.DeleteObjectOutput deleteObjectOutput2;

    @When("^put encryption object \"([^\"]*)\" with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void put_encryption_object_with(
            String objectName,
            String encryption_algorithm,
            String encryption_key,
            String encryption_key_md5)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        testBucket = new Bucket(ctx, zone, bucketName);
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        File f = new File("/tmp/encryption_test_object");
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        raf.setLength(1048576);
        input.setBodyInputFile(f);
        input.setContentType("video/mp4; charset=utf8");
        input.setContentLength(f.length());
        input.setXQSEncryptionCustomerAlgorithm(encryption_algorithm);
        input.setXQSEncryptionCustomerKey(encryption_key);
        input.setXQSEncryptionCustomerKeyMD5(encryption_key_md5);
        putObjectOutput = testBucket.putObject(objectName, input);
        Files.delete(f.toPath());
    }

    @Then("^put encryption object status code is (\\d+)$")
    public void put_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("put_encryption_object_status_code_is" + putObjectOutput.getMessage());
        TestUtil.assertEqual(this.putObjectOutput.getStatueCode(), statusCode);
    }

    @When("^copy encryption object \"([^\"]*)\" with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void copy_encryption_object_with(
            String objectName,
            String encryption_algorithm,
            String encryption_key,
            String encryption_key_md5)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setXQSCopySource("/" + bucketName + "/" + objectName);
        input.setXQSCopySourceEncryptionCustomerAlgorithm(encryption_algorithm);
        input.setXQSCopySourceEncryptionCustomerKey(encryption_key);
        input.setXQSCopySourceEncryptionCustomerKeyMD5(encryption_key_md5);
        copyObjectOutput = testBucket.putObject("copy" + objectName, input);
    }

    @Then("^copy encryption object status code is (\\d+)$")
    public void copy_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("copy_encryption_object_status_code_is" + copyObjectOutput.getMessage());
        TestUtil.assertEqual(this.copyObjectOutput.getStatueCode(), statusCode);
    }

    @When("^move encryption object with key \"([^\"]*)\"$")
    public void move_encryption_object_with_key(String objectName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setXQSMoveSource("/" + bucketName + "/" + "copy" + objectName);
        moveOutput = testBucket.putObject(objectName + "move", input);
    }

    @Then("^move encryption object status code is (\\d+)$")
    public void move_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("copy_encryption_object_status_code_is" + moveOutput.getMessage());
        TestUtil.assertEqual(this.moveOutput.getStatueCode(), statusCode);
    }

    @When("^get encryption object \"([^\"]*)\" with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void get_encryption_object_with(
            String objectName,
            String encryption_algorithm,
            String encryption_key,
            String encryption_key_md5)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.GetObjectInput input = new Bucket.GetObjectInput();
        input.setXQSEncryptionCustomerAlgorithm(encryption_algorithm);
        input.setXQSEncryptionCustomerKey(encryption_key);
        input.setXQSEncryptionCustomerKeyMD5(encryption_key_md5);
        getObjectOutput = testBucket.getObject(objectName + "move", input);
    }

    @Then("^get encryption object status code is (\\d+)$")
    public void get_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("get_encryption_object_status_code_is" + getObjectOutput.getMessage());
        TestUtil.assertEqual(this.getObjectOutput.getStatueCode(), statusCode);
    }

    @Then("^get encryption object content length is (\\d+)$")
    public void get_encryption_object_content_length_is(int contentLength) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(
                "get_encryption_object_status_code_is:"
                        + getObjectOutput.getMessage()
                        + "---"
                        + this.getObjectOutput.getContentLength());
        TestUtil.assertEqual(this.getObjectOutput.getContentLength().intValue(), contentLength);
    }

    @When(
            "^head encryption object with key \"([^\"]*)\" with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void head_encryption_object_with_key_with(
            String objectName,
            String encryption_algorithm,
            String encryption_key,
            String encryption_key_md5)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.HeadObjectInput input = new Bucket.HeadObjectInput();
        input.setXQSEncryptionCustomerAlgorithm(encryption_algorithm);
        input.setXQSEncryptionCustomerKey(encryption_key);
        input.setXQSEncryptionCustomerKeyMD5(encryption_key_md5);
        headObjectOutput = testBucket.headObject(objectName, input);
    }

    @Then("^head encryption object status code is (\\d+)$")
    public void head_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(
                "head_encryption_object_status_code_is:" + headObjectOutput.getMessage());
        TestUtil.assertEqual(this.headObjectOutput.getStatueCode(), statusCode);
    }

    @When("^delete encryption object \"([^\"]*)\"$")
    public void delete_encryption_object(String objectName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        deleteObjectOutput = testBucket.deleteObject(objectName, null);
    }

    @Then("^delete encryption object status code is (\\d+)$")
    public void delete_encryption_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(deleteObjectOutput.getStatueCode(), statusCode);
    }

    @When("^delete encryption the move object \"([^\"]*)\"$")
    public void delete_encryption_the_move_object(String objectName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        deleteObjectOutput2 = testBucket.deleteObject(objectName + "move", null);
    }

    @Then("^delete encryption the move object status code is (\\d+)$")
    public void delete_encryption_the_move_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(this.deleteObjectOutput2.getStatueCode(), statusCode);
    }
}
