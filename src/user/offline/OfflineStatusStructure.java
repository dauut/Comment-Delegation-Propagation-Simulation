package user.offline;

import java.util.ArrayList;

public class OfflineStatusStructure {
    private long userID;
    private long friendUserID;
    private ArrayList<Integer> UserstatusList;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getFriendUserID() {
        return friendUserID;
    }

    public void setFriendUserID(long friendUserID) {
        this.friendUserID = friendUserID;
    }

    public ArrayList<Integer> getUserstatusList() {
        return UserstatusList;
    }

    public void setUserstatusList(ArrayList<Integer> UserstatusList) {
        this.UserstatusList = UserstatusList;
    }
}
