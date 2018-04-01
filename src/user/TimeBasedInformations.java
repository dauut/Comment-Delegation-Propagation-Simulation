package user;

import java.util.ArrayList;
import java.util.Date;

public class TimeBasedInformations {
    int onlineFriendsCount;
    Date currentTimestamp;
    ArrayList<OnlineFriendsAndStatus> onlineFriendsList = new ArrayList<>();
    int idleFriendsCount;
    int activeFriendsCount;
    int mobileUsersCount;
    int webUsersCount;
    int mobileActiveCount;
    int mobileIdleCount;

    public int getOnlineFriendsCount() {
        return onlineFriendsCount;
    }

    public void setOnlineFriendsCount(int onlineFriendsCount) {
        this.onlineFriendsCount = onlineFriendsCount;
    }

    public Date getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Date currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public ArrayList<OnlineFriendsAndStatus> getOnlineFriendsList() {
        return onlineFriendsList;
    }

    public void setOnlineFriendsList(ArrayList<OnlineFriendsAndStatus> onlineFriendsList) {
        this.onlineFriendsList = onlineFriendsList;
    }

    public int getIdleFriendsCount() {
        return idleFriendsCount;
    }

    public void setIdleFriendsCount(int idleFriendsCount) {
        this.idleFriendsCount = idleFriendsCount;
    }

    public int getActiveFriendsCount() {
        return activeFriendsCount;
    }

    public void setActiveFriendsCount(int activeFriendsCount) {
        this.activeFriendsCount = activeFriendsCount;
    }

    public int getMobileUsersCount() {
        return mobileUsersCount;
    }

    public void setMobileUsersCount(int mobileUsersCount) {
        this.mobileUsersCount = mobileUsersCount;
    }

    public int getWebUsersCount() {
        return webUsersCount;
    }

    public void setWebUsersCount(int webUsersCount) {
        this.webUsersCount = webUsersCount;
    }

    public int getMobileActiveCount() {
        return mobileActiveCount;
    }

    public void setMobileActiveCount(int mobileActiveCount) {
        this.mobileActiveCount = mobileActiveCount;
    }

    public int getMobileIdleCount() {
        return mobileIdleCount;
    }

    public void setMobileIdleCount(int mobileIdleCount) {
        this.mobileIdleCount = mobileIdleCount;
    }

}
