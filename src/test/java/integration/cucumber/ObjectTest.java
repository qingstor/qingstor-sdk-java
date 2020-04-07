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
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ObjectTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EnvContext ctx = TestUtil.getEnvContext();
    private static Bucket testBucket = new Bucket(ctx, zone, bucketName);

    private static Bucket.PutObjectOutput objectOutput;
    private static Bucket.PutObjectOutput copyOutput;
    private static Bucket.PutObjectOutput moveOutput;
    private static Bucket.GetObjectOutput getContentTypeOutput;

    private static Bucket.PutObjectOutput putObjectOutput;
    private static Bucket.GetObjectOutput getObjectOutput;
    private static Bucket.HeadObjectOutput headObjectOutput;
    private static Bucket.OptionsObjectOutput optionObjectOutput;
    private static Bucket.DeleteObjectOutput deleteObjectOutput;
    private static Bucket.DeleteObjectOutput deleteObjectOutput2;

    @When("^put object with key \"(.*)\"")
    public void put_object_with_key(String objectKey) throws Throwable {
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        File f = new File("config.yaml");
        input.setBodyInputFile(f);
        input.setContentType("video/mp4; charset=utf8");
        input.setContentLength(f.length());
        putObjectOutput = testBucket.putObject(objectKey, input);
    }

    @Then("^put object status code is (\\d+)$")
    public void put_object_status_code_is(int statueCode) throws Throwable {
        System.out.println("put_object_status_code_msg:" + putObjectOutput.getMessage());
        TestUtil.assertEqual(putObjectOutput.getStatueCode(), statueCode);
    }

    @When("^copy object with key \"(.*)\"$")
    public void copy_object_with_key(String objectKey) throws Throwable {
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setXQSCopySource("/" + bucketName + "/" + objectKey);
        copyOutput = testBucket.putObject(objectKey + "copy", input);
    }

    @Then("^copy object status code is (\\d+)$")
    public void copy_object_status_code_is(int statueCode) throws Throwable {

        System.out.println("put_the_copy_object_status_code_message:" + copyOutput.getMessage());
        TestUtil.assertEqual(copyOutput.getStatueCode(), statueCode);
    }

    @When("^move object with key \"(.*)\"$")
    public void move_object_with_key(String objectKey) throws Throwable {
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setXQSMoveSource("/" + bucketName + "/" + objectKey + "copy");
        moveOutput = testBucket.putObject(objectKey + "move", input);
    }

    @Then("^move object status code is (\\d+)$")
    public void move_object_status_code_is(int statueCode) throws Throwable {
        System.out.println("put_the_move_object_status_code_message:" + moveOutput.getMessage());
        TestUtil.assertEqual(moveOutput.getStatueCode(), statueCode);
    }

    @When("^get object with key \"(.*)\"$")
    public void get_object(String objectKey) throws Throwable {
        Bucket.GetObjectInput input = new Bucket.GetObjectInput();
        getObjectOutput = testBucket.getObject(objectKey + "move", input);
    }

    @Then("^get object status code is (\\d+)$")
    public void get_object_status_code_is(int statueCode) throws Throwable {
        TestUtil.assertEqual(getObjectOutput.getStatueCode(), statueCode);
    }

    @Then("^get object content length is (\\d+)$")
    public void get_object_content_length_is(int statueCode) throws Throwable {
        int iLength = 0;
        if (getObjectOutput != null && getObjectOutput.getBodyInputStream() != null) {
            File ff = new File("/tmp/get_object.txt");
            OutputStream out = new FileOutputStream(ff);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = getObjectOutput.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
                iLength += bytesRead;
            }
            out.close();
            getObjectOutput.getBodyInputStream().close();
        }
    }

    @When("^get object \"(.*)\" with query signature$")
    public void get_object_with_query_signature(String statueCode) throws Throwable {
        String reqUrl = testBucket.GetObjectSignatureUrl(statueCode, 1000);
        getObjectOutput = testBucket.GetObjectBySignatureUrl(reqUrl);
    }

    @Then("^get object with query signature content length is (\\d+)$")
    public void get_object_with_query_signature_content_length_is(int statueCode) throws Throwable {
        System.out.println(
                "get_object_with_query_signature_statue:" + getObjectOutput.getStatueCode());
        int iLength = 0;
        if (getObjectOutput != null && getObjectOutput.getBodyInputStream() != null) {
            File ff = new File("/tmp/get_sign_object.txt");
            OutputStream out = new FileOutputStream(ff);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = getObjectOutput.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
                iLength += bytesRead;
            }
            out.close();
            getObjectOutput.getBodyInputStream().close();
        }
        System.out.println("get_object_with_query_signature_length:" + iLength);
    }

    @When("^get object \"(.*)\" with content type \"(.*)\"$")
    public void get_object_with_content_type(String objectKey, String contentType)
            throws Throwable {
        Bucket.GetObjectInput input = new Bucket.GetObjectInput();
        input.setResponseContentType(contentType);
        getContentTypeOutput = testBucket.getObject(objectKey, input);
    }

    @Then("^get object content type is \"(.*)\"$")
    public void get_object_content_type_is(String statueCode) throws Throwable {
        if (getContentTypeOutput.getBodyInputStream() != null) {
            getContentTypeOutput.getBodyInputStream().close();
        }
        TestUtil.assertEqual(statueCode, getContentTypeOutput.getResponseContentType());
    }

    @When("^head object with key \"(.*)\"$")
    public void head_object(String objectKey) throws Throwable {
        Bucket.HeadObjectInput input = new Bucket.HeadObjectInput();

        headObjectOutput = testBucket.headObject(objectKey, input);
    }

    @Then("^head object status code is (\\d+)$")
    public void head_object_status_code_is(int statueCode) throws Throwable {
        TestUtil.assertEqual(headObjectOutput.getStatueCode(), statueCode);
    }

    @When("^options object \"(.*)\" with method \"(.*)\" and origin \"(.*)\"$")
    public void options_object_with_method_and_origin(
            String objectKey, String method, String origin) throws Throwable {
        Bucket.OptionsObjectInput input = new Bucket.OptionsObjectInput();
        input.setAccessControlRequestMethod(method);
        input.setOrigin(origin);
        optionObjectOutput = testBucket.optionsObject(objectKey, input);
        System.out.println(optionObjectOutput.getMessage());
    }

    @Then("^options object status code is (\\d+)$")
    public void options_object_status_code_is(int statueCode) throws Throwable {
        TestUtil.assertEqual(optionObjectOutput.getStatueCode(), statueCode);
    }

    @When("^delete object with key \"(.*)\"$")
    public void delete_object(String objectKey) throws Throwable {
        deleteObjectOutput = testBucket.deleteObject(objectKey);
    }

    @Then("^delete object status code is (\\d+)$")
    public void delete_object_status_code_is(int statueCode) throws Throwable {
        TestUtil.assertEqual(deleteObjectOutput.getStatueCode(), statueCode);
    }

    @When("^delete the move object with key \"(.*)\"$")
    public void delete_the_move_object(String objectKey) throws Throwable {
        deleteObjectOutput2 = testBucket.deleteObject(objectKey + "move");
    }

    @Then("^delete the move object status code is (\\d+)$")
    public void delete_the_move_object_status_code_is(int statueCode) throws Throwable {
        TestUtil.assertEqual(deleteObjectOutput2.getStatueCode(), statueCode);
    }
}
