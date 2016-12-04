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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.utils.Base64;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BucketSubServiceTest {

	private static Bucket Bucket;
	private static String bucketName = "";

	private static Bucket.PutBucketOutput putBucketOutput;
	private static Bucket.PutBucketOutput putBucketOutput2;
	private static Bucket.ListObjectsOutput listObjectsOutput;
	private static Bucket.HeadBucketOutput headBucketOutput;
	private static Bucket.DeleteBucketOutput deleteBucketOutput;
	private static Bucket.GetBucketStatisticsOutput getBucketStatisticsOutput;
	private static Bucket.DeleteMultipleObjectsOutput deleteMultipleObjectsOutput;

	@When("^initialize the bucket$")
	public void initialize_the_bucket() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		bucketName = System.currentTimeMillis() + "test";
		EvnContext evnContext = TestUtil.getEvnContext();
		Bucket = new Bucket(evnContext, bucketName);

	}

	@Then("^the bucket is initialized$")
	public void the_bucket_is_initialized() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		TestUtil.assertNotNull(this.Bucket);
	}

	@When("^put bucket$")
	public void put_bucket() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		//Bucket.PutBucketInput input = new Bucket.PutBucketInput();

		putBucketOutput = Bucket.put();
	}

	@Then("^put bucket status code is (\\d+)$")
	public void put_bucket_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		TestUtil.assertEqual(putBucketOutput.getStatueCode(), arg1);
	}

	@When("^put same bucket again$")
	public void put_same_bucket_again() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		//Bucket.PutBucketInput input = new Bucket.PutBucketInput();

		putBucketOutput2 = Bucket.put();
	}

	@Then("^put same bucket again status code is (\\d+)$")
	public void put_same_bucket_again_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		TestUtil.assertEqual(putBucketOutput2.getStatueCode(), arg1);
	}

	@Then("^initialize the bucket without zone$")
	public void initialize_the_bucket_without_zone() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
	}

	@When("^list objects$")
	public void list_objects() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Bucket.ListObjectsInput input = new Bucket.ListObjectsInput();
		input.setLimit(20);
		listObjectsOutput = Bucket.listObjects(input);
	}

	@Then("^list objects status code is (\\d+)$")
	public void list_objects_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println(bucketName+"list_objects:"+listObjectsOutput.getMessage());
		TestUtil.assertEqual(listObjectsOutput.getStatueCode(), arg1);
	}

	@Then("^list objects keys count is (\\d+)$")
	public void list_objects_keys_count_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("list_objects_keys_count_msg:" + listObjectsOutput.getPrefix());
	}

	@When("^head bucket$")
	public void head_bucket() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		headBucketOutput = Bucket.head();
	}

	@Then("^head bucket status code is (\\d+)$")
	public void head_bucket_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		TestUtil.assertEqual(headBucketOutput.getStatueCode(), arg1);
	}

	@When("^delete bucket$")
	public void delete_bucket() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		deleteBucketOutput = Bucket.delete();
	}

	@Then("^delete bucket status code is (\\d+)$")
	public void delete_bucket_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("delete_bucket_message:" + deleteBucketOutput.getMessage());
		TestUtil.assertEqual(deleteBucketOutput.getStatueCode(), arg1);
	}

	@When("^delete multiple objects:$")
	public void delete_multiple_objects(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		Bucket.DeleteMultipleObjectsInput input = new Bucket.DeleteMultipleObjectsInput();
		arg1 = "{\"quiet\":false,\"objects\":[{\"key\":\"object_0\"},{\"key\":\"object_1\"},{\"key\":\"object_2\"}]}";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(arg1.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String result = buf.toString();
			//result = MD5.getMD5ofStr(arg1);
			String result22 = new String(Base64.encode(result.getBytes()));
			String result22aa = new String(Base64.encode(buf.toString().substring(8, 24).getBytes()));
			// System.out.println("MD5(" + sourceStr + ",32) = " + result);
			// System.out.println("MD5(" + sourceStr + ",16) = " +  43319a9d9a8ec16f18b49c77cbfa2114
			// buf.toString().substring(8, 24));					d1187de372f5180fda8a762c952e25b2

		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}

		// arg1.raw().get(1)
		input.setBodyInput("{\"quiet\":false,\"objects\":[{\"key\":\"object_0\"},{\"key\":\"object_1\"},{\"key\":\"object_2\"}]}");
		input.setContentMD5("1UK03AxvZpSNLmYR2oz4qg==");
		deleteMultipleObjectsOutput = Bucket
				.deleteMultipleObjects(input);
		// throw new PendingException();
	}

	@Then("^delete multiple objects code is (\\d+)$")
	public void delete_multiple_objects_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
		System.out.println("delete_multiple_objects:" + deleteMultipleObjectsOutput.getMessage());
		TestUtil.assertEqual(deleteMultipleObjectsOutput.getStatueCode(), arg1);
	}

	@When("^get bucket statistics$")
	public void get_bucket_statistics() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		getBucketStatisticsOutput = Bucket
				.getStatistics();
	}

	@Then("^get bucket statistics status code is (\\d+)$")
	public void get_bucket_statistics_status_code_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("bucket_statistics:"+getBucketStatisticsOutput.getMessage());
		TestUtil.assertEqual(getBucketStatisticsOutput.getStatueCode(), arg1);
	}

	@Then("^get bucket statistics status is \"([^\"]*)\"$")
	public void get_bucket_statistics_status_is(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("get_bucket_statistics_status_msg:" + getBucketStatisticsOutput.getStatus());

	}

}
