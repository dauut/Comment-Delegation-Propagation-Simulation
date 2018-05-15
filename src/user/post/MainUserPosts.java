package user.post;

import java.util.ArrayList;

public class MainUserPosts {
    private int numOfPost;
    private ArrayList<Posts> mainUserPostList;

    public int getNumOfPost() {
        return numOfPost;
    }

    public void setNumOfPost(int numOfPost) {
        this.numOfPost = numOfPost;
    }

    public ArrayList<Posts> getMainUserPostList() {
        return mainUserPostList;
    }

    public void setMainUserPostList(ArrayList<Posts> mainUserPostList) {
        this.mainUserPostList = mainUserPostList;
    }
}
