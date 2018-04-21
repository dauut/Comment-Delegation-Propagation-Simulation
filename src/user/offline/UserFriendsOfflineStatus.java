package user.offline;

import java.util.ArrayList;

public class UserFriendsOfflineStatus {
    private long friendUserId;
    private ArrayList<Integer> friendOfflineStatus;
    private ArrayList<Integer> friendOnlineStatus;

    public ArrayList<Integer> getFriendOfflineStatus() {
        return friendOfflineStatus;
    }

    public void setFriendOfflineStatus(ArrayList<Integer> friendOfflineStatus) {
        this.friendOfflineStatus = friendOfflineStatus;
    }

    public ArrayList<Integer> getFriendOnlineStatus() {
        return friendOnlineStatus;
    }

    public void setFriendOnlineStatus(ArrayList<Integer> friendOnlineStatus) {
        this.friendOnlineStatus = friendOnlineStatus;
    }

    public long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(long friendUserId) {
        this.friendUserId = friendUserId;
    }
}
