package user.post;

import java.util.ArrayList;

public class FriendsPostInformation {

    private long userID;
    private ArrayList<TimeBasedPostInformation> postActivities;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public ArrayList<TimeBasedPostInformation> getPostActivities() {
        return postActivities;
    }

    public void setPostActivities(ArrayList<TimeBasedPostInformation> postActivities) {
        this.postActivities = postActivities;
    }
}
