package user.post;

import java.util.ArrayList;
import java.util.Date;

public class TimeBasedPostInformation {

    private Date currentTimestamp;
    private int totalFriendsCount;
    private int numOfMobileUsers;
    private int totalMessages;
    private ArrayList<MainUserPosts> mainUserPostsList;
    private ArrayList<Posts> postsArrayList;
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

    public ArrayList<MainUserPosts> getMainUserPostsList() {
        return mainUserPostsList;
    }

    public void setMainUserPostsList(ArrayList<MainUserPosts> mainUserPostsList) {
        this.mainUserPostsList = mainUserPostsList;
    }

    public ArrayList<Posts> getPostsArrayList() {
        return postsArrayList;
    }

    public void setPostsArrayList(ArrayList<Posts> postsArrayList) {
        this.postsArrayList = postsArrayList;
    }
}
