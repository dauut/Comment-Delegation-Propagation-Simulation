package user;

import java.util.ArrayList;

public class UserFriendsOfflineStatus {
    private long friendUserId;
    private ArrayList<int[]> friendOfflineStatus;

    public ArrayList<int[]> getFriendOfflineStatus() {
        return friendOfflineStatus;
    }

    public void setFriendOfflineStatus(ArrayList<int[]> friendOfflineStatus) {
        this.friendOfflineStatus = friendOfflineStatus;
    }

    public long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(long friendUserId) {
        this.friendUserId = friendUserId;
    }
}
