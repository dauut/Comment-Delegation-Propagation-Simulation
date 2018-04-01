package user;

import java.util.ArrayList;

public class UserInformations {
    private long userId;
    ArrayList<TimeBasedInformations> userActivites = new ArrayList<>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<TimeBasedInformations> getUserActivites() {
        return userActivites;
    }

    public void setUserActivites(ArrayList<TimeBasedInformations> userActivites) {
        this.userActivites = userActivites;
    }

}
