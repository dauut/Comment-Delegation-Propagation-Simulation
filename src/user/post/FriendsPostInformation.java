package user.post;

import java.util.ArrayList;

public class FriendsPostInformation {

    private long userID;
    private ArrayList<TimeBasedPostInformation> timeBasedPostInformation;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public ArrayList<TimeBasedPostInformation> getTimeBasedPostInformation() {
        return timeBasedPostInformation;
    }

    public void setTimeBasedPostInformation(ArrayList<TimeBasedPostInformation> timeBasedPostInformation) {
        this.timeBasedPostInformation = timeBasedPostInformation;
    }
}
