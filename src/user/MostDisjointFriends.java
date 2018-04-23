package user;

import java.util.ArrayList;

public class MostDisjointFriends {
    private long userID;
    private ArrayList<Long> mostOnlineFriendsList;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public ArrayList<Long> getMostOnlineFriendsList() {
        return mostOnlineFriendsList;
    }

    public void setMostOnlineFriendsList(ArrayList<Long> mostOnlineFriendsList) {
        this.mostOnlineFriendsList = mostOnlineFriendsList;
    }
}
