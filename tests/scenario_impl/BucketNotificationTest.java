package scenario_impl;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Types.NotificationsModel;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class BucketNotificationTest {

    private static String bucketName = TestUtil.getBucketName();
    private static String zone = TestUtil.getZone();
    private static EvnContext evnContext = TestUtil.getEvnContext();
    private static Bucket testBucket = new Bucket(evnContext, zone, bucketName);

    private Bucket.PutBucketNotificationOutput putBucketNotificationOutput;
    private Bucket.GetBucketNotificationOutput getBucketNotificationOutput;

    @When("^put bucket notification:$")
    public void put_bucket_Notification(String arg1) throws Throwable {
        Bucket.PutBucketNotificationInput input = new Bucket.PutBucketNotificationInput();
        NotificationsModel notification = new NotificationsModel();
        notification.setID("notification id");
        notification.setEventTypes("create_object");
        notification.setCloudfunc("create_object");

        List<NotificationsModel> nn = new ArrayList<NotificationsModel>();
        nn.add(notification);
        input.setNotification(nn);
        putBucketNotificationOutput = testBucket.putNotification(input);
    }

    @Then("^put bucket notification status code is (\\d+)$")
    public void put_bucket_Notification_status_code_is(int arg1) throws Throwable {
        System.out.println("put_bucket_notification_status_code_msg:" + this.putBucketNotificationOutput.getMessage());
        TestUtil.assertEqual(this.putBucketNotificationOutput.getStatueCode(), arg1);
    }

    @When("^get bucket notification$")
    public void get_bucket_Notification() throws Throwable {
        getBucketNotificationOutput = testBucket.getNotification();
    }

    @Then("^get bucket notification status code is (\\d+)$")
    public void get_bucket_Notification_status_code_is(int arg1) throws Throwable {
        System.out.println("get_bucket_Notification_status_code_msg:" + this.getBucketNotificationOutput.getMessage());
        TestUtil.assertEqual(this.getBucketNotificationOutput.getStatueCode(), arg1);
    }

    @When("^delete bucket notification$")
    public void delete_bucket_Notification() throws Throwable {
        deleteBucketNotificationOutput = testBucket.deleteNotification();
    }

    @Then("^delete bucket notification status code is (\\d+)$")
    public void delete_bucket_Notification_status_code_is(int arg1) throws Throwable {
        TestUtil.assertEqual(deleteBucketNotificationOutput.getStatueCode(), arg1);
    }

}
