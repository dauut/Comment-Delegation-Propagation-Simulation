package user;

import java.util.ArrayList;

public class UserInformations {
    private long userId;
    private ArrayList<TimeBasedInformation> userActivites = new ArrayList<TimeBasedInformation>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<TimeBasedInformation> getUserActivites() {
        return userActivites;
    }

    public void setUserActivites(ArrayList<TimeBasedInformation> userActivites) {
        this.userActivites = userActivites;
    }

}
