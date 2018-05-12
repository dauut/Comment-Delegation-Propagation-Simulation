package user.post;

import java.util.ArrayList;

public class FriendsPosts {
    private int numOfPost;
    private long friendUserId;
    private ArrayList<Posts> friendPostList;


    public long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(long friendUserId) {
        this.friendUserId = friendUserId;
    }

    public ArrayList<Posts> getFriendPostList() {
        return friendPostList;
    }

    public void setFriendPostList(ArrayList<Posts> friendPostList) {
        this.friendPostList = friendPostList;
    }

    public int getNumOfPost() {
        return numOfPost;
    }

    public void setNumOfPost(int numOfPost) {
        this.numOfPost = numOfPost;
    }

    public ArrayList<Posts> getMainUserPostList() {
        return friendPostList;
    }

    public void setMainUserPostList(ArrayList<Posts> mainUserPostList) {
        this.friendPostList = mainUserPostList;
    }
}
