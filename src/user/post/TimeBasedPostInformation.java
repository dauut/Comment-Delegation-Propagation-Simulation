package user.post;

import java.util.ArrayList;
import java.util.Date;

public class TimeBasedPostInformation {

    private long mainUserId;
    private Date currentTimestamp;
    private int totalFriendsCount;
    private int numOfMobileUsers;
    private int totalMessages;
    private MainUserPosts mainUserPosts;
    private ArrayList<FriendsPosts> friendsPosts;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Date currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public int getTotalFriendsCount() {
        return totalFriendsCount;
    }

    public void setTotalFriendsCount(int totalFriendsCount) {
        this.totalFriendsCount = totalFriendsCount;
    }

    public int getNumOfMobileUsers() {
        return numOfMobileUsers;
    }

    public void setNumOfMobileUsers(int numOfMobileUsers) {
        this.numOfMobileUsers = numOfMobileUsers;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    public MainUserPosts getMainUserPosts() {
        return mainUserPosts;
    }

    public void setMainUserPosts(MainUserPosts mainUserPosts) {
        this.mainUserPosts = mainUserPosts;
    }

    public ArrayList<FriendsPosts> getFriendsPosts() {
        return friendsPosts;
    }

    public void setFriendsPosts(ArrayList<FriendsPosts> friendsPosts) {
        this.friendsPosts = friendsPosts;
    }

    public long getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(long mainUserId) {
        this.mainUserId = mainUserId;
    }
}
