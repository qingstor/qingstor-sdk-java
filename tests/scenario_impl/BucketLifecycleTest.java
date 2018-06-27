package scenario_impl;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Types.RuleModel;
import com.qingstor.sdk.service.Types.FilterModel;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class BucketLifecycleTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EvnContext evnContext = TestUtil.getEvnContext();
    private static Bucket testBucket = new Bucket(evnContext, zone, bucketName);

    private Bucket.PutBucketLifecycleOutput putBucketLifecycleOutput;
    private Bucket.GetBucketLifecycleOutput getBucketLifecycleOutput;
    private Bucket.DeleteBucketLifecycleOutput deleteBucketLifecycleOutput;

    @When("^put bucket lifecycle:$")
    public void put_bucket_Lifecycle(String arg1) throws Throwable {
        Bucket.PutBucketLifecycleInput input = new Bucket.PutBucketLifecycleInput();
        RuleModel rule = new RuleModel();
        rule.setStatus("enabled");
        rule.setID("the rule id");
        FilterModel fm = new FilterModel();
        fm.setPrefix("group");
        rule.setFilter(fm);
        List<RuleModel> rr = new ArrayList<RuleModel>();
        rr.add(rule);
        input.setLifecycle(rr);
        putBucketLifecycleOutput = testBucket.putLifecycle(input);
    }

    @Then("^put bucket lifecycle status code is (\\d+)$")
    public void put_bucket_Lifecycle_status_code_is(int arg1) throws Throwable {
        System.out.println("put_bucket_Lifecycle_status_code_msg:" + this.putBucketLifecycleOutput.getMessage());
        TestUtil.assertEqual(this.putBucketLifecycleOutput.getStatueCode(), arg1);
    }

    @When("^get bucket lifecycle$")
    public void get_bucket_Lifecycle() throws Throwable {
        getBucketLifecycleOutput = testBucket.getLifecycle();
    }

    @Then("^get bucket lifecycle status code is (\\d+)$")
    public void get_bucket_Lifecycle_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(getBucketLifecycleOutput.getStatueCode(), arg1);
    }

    @Then("^get bucket lifecycle should have filter prefix \"([^\"]*)\"$")
    public void get_bucket_Lifecycle_should_have_filter_prefix(String arg1) throws Throwable {
        System.out.println("get_bucket_Lifecycle_should_have_filter_prefix_msg:" + this.getBucketLifecycleOutput.getRule());
    }
    
    @When("^delete bucket lifecycle$")
    public void delete_bucket_Lifecycle() throws Throwable {
        deleteBucketLifecycleOutput = testBucket.deleteLifecycle();
    }


    @Then("^delete bucket lifecycle status code is (\\d+)$")
    public void delete_bucket_Lifecycle_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(deleteBucketLifecycleOutput.getStatueCode(), arg1);
    }


}
