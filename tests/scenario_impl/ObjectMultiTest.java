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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.runner.RunWith;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Bucket.InitiateMultipartUploadOutput;
import com.qingstor.sdk.service.QingStor;
import com.qingstor.sdk.service.QingStor.ListBucketsInput;
import com.qingstor.sdk.service.QingStor.ListBucketsOutput;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;

public class ObjectMultiTest {


	private static Bucket Bucket;
    private static String bucketName = TestUtil.getBucketName();
    public static String zone = TestUtil.getZone();
    //private String multiObjectName = "test";
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
        // Write code here that turns the phrase above into concrete actions
        EvnContext evnContext = TestUtil.getEvnContext();
        Bucket = new Bucket(evnContext, zone, bucketName);

        Bucket.InitiateMultipartUploadInput input = new Bucket.InitiateMultipartUploadInput();
        //input.setContentType(apkContentType);

        initOutput = Bucket.initiateMultipartUpload(objectKey,input);
        multipart_upload_name = objectKey;
        multipart_upload_id = this.initOutput.getUploadID();
    }



    @Then("^multipart upload is initialized$")
    public void the_object_multipart_upload_is_initialized() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertNotNull(this.initOutput);
        System.out.println("the_object_multipart_upload_is_initialized:"+this.initOutput.getUploadID());
        multipart_upload_id = this.initOutput.getUploadID();
    }

    @When("^initiate multipart upload$")
    public void initiate_multipart_upload() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException(); 03ce946f30943afa97b7cd82a11cd45d
    }

    @Then("^initiate multipart upload status code is (\\d+)$")
    public void initiate_multipart_upload_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
        TestUtil.assertEqual(initOutput.getStatueCode(),statusCode);
    }

    @When("^upload the first part with key \"([^\"]*)\"$")
    public void upload_the_first_part(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //inputI.setContentType(apkContentType);

        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_0\nbs=1m\ncount=5");// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
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
        input.setPartNumber((long) part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput1 = Bucket.uploadMultipart(multipart_upload_name,input);

    }

    @Then("^upload the first part status code is (\\d+)$")
    public void upload_the_first_part_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("upload_the_first_part_status_code_msg"+uploadMultipartOutput1.getMessage());
        TestUtil.assertEqual(this.uploadMultipartOutput1.getStatueCode(),statusCode);
    }

    @When("^upload the second part with key \"([^\"]*)\"$")
    public void upload_the_second_part(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_1\nbs=1m\ncount=5");// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int part_number = 0;
        File f = new File("/tmp/sdk_bin_part_1");
        Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
        input.setXQSEncryptionCustomerKey(objectKey);
        input.setContentLength( f.length());
        input.setBodyInputFile(f);
        input.setPartNumber((long) part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput2 = Bucket.uploadMultipart(multipart_upload_name,input);
    }

    @Then("^upload the second part status code is (\\d+)$")
    public void upload_the_second_part_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("upload_the_first_part_status_code_is2"+uploadMultipartOutput2.getMessage());
        TestUtil.assertEqual(this.uploadMultipartOutput2.getStatueCode(),statusCode);
    }

    @When("^upload the third part with key \"([^\"]*)\"$")
    public void upload_the_third_part(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec("dd\nif=/dev/zero\nof=/tmp/sdk_bin_part_2\nbs=1m\ncount=5");// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int part_number = 0;
        File f = new File("/tmp/sdk_bin_part_2");
        Bucket.UploadMultipartInput input = new Bucket.UploadMultipartInput();
        input.setXQSEncryptionCustomerKey(objectKey);
        input.setContentLength( f.length());
        input.setBodyInputFile(f);
        input.setPartNumber((long) part_number);
        input.setUploadID(multipart_upload_id);
        uploadMultipartOutput3 = Bucket.uploadMultipart(multipart_upload_name,input);
    }

    @Then("^upload the third part status code is (\\d+)$")
    public void upload_the_third_part_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(this.uploadMultipartOutput3.getStatueCode(),statusCode);
    }

    @When("^list multipart with key \"([^\"]*)\"$")
    public void list_multipart(String key) throws Throwable {
        // Write code here that turns the phrase above into concrete actions


        Bucket.ListMultipartInput input = new Bucket.ListMultipartInput();
        input.setUploadID(initOutput.getUploadID());
        input.setLimit(10l);
        listMultipartOutput = Bucket.listMultipart(multipart_upload_name,input);
    }

    @Then("^list multipart status code is (\\d+)$")
    public void list_multipart_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("list_multipart_status_code_msg"+listMultipartOutput.getMessage());
        TestUtil.assertEqual(this.listMultipartOutput.getStatueCode(),statusCode);
    }

    @Then("^list multipart object parts count is (\\d+)$")
    public void list_multipart_object_parts_count_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("list_multipart_object_parts_count_is:"+this.listMultipartOutput.getCount());
    }

    @When("^complete multipart upload with key \"([^\"]*)\"$")
    public void complete_multipart_upload(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.CompleteMultipartUploadInput input = new Bucket.CompleteMultipartUploadInput();
        //inputI.setContentType(apkContentType);



        input.setUploadID(initOutput.getUploadID());
        String content = "{\n" +
                "    \"object_parts\": [\n" +
                "        {\"part_number\": 0},\n" +
                "        {\"part_number\": 1},\n" +
                "        {\"part_number\": 2}\n" +
                "    ]\n" +
                "}";
        input.setBodyInput(content);

        completeMultipartUploadOutput = Bucket.completeMultipartUpload(objectKey,input);
    }

    @Then("^complete multipart upload status code is (\\d+)$")
    public void complete_multipart_upload_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("complete_multipart_upload_status_code_msg"+completeMultipartUploadOutput.getMessage());
        TestUtil.assertEqual(completeMultipartUploadOutput.getStatueCode(),statusCode);
    }

    @When("^abort multipart upload with key \"([^\"]*)\"$")
    public void abort_multipart_upload(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Bucket.AbortMultipartUploadInput input = new Bucket.AbortMultipartUploadInput();
        //inputI.setContentType(apkContentType);


        input.setUploadID(initOutput.getUploadID());
        abortMultipartUploadOutput = Bucket.abortMultipartUpload(objectKey,input);

    }

    @Then("^abort multipart upload status code is (\\d+)$")
    public void abort_multipart_upload_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TestUtil.assertEqual(abortMultipartUploadOutput.getStatueCode(),statusCode);
    }

    @When("^delete the multipart object with key \"([^\"]*)\"$")
    public void delete_the_multipart_object(String objectKey) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();

        deleteObjectOutput = Bucket.deleteObject(objectKey);

    }

    @Then("^delete the multipart object status code is (\\d+)$")
    public void delete_the_multipart_object_status_code_is(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
        TestUtil.assertEqual(deleteObjectOutput.getStatueCode(), statusCode);
    }

    
}

