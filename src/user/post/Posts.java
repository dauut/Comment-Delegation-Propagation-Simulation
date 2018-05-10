package user.post;

import java.util.Date;

public class Posts {
    private long friendUserID;
    private int numOfPost;
    private String postType;
    private int postID;
    private Date postCreateDate;
    private int likeCount;
    private int commentCount;
    private int commentCharCount;
    private int postSize;

    public int getPostSize() {
        return postSize;
    }

    public void setPostSize(int postSize) {
        this.postSize = postSize;
    }

    public long getFriendUserID() {
        return friendUserID;
    }

    public void setFriendUserID(long friendUserID) {
        this.friendUserID = friendUserID;
    }

    public int getNumOfPost() {
        return numOfPost;
    }

    public void setNumOfPost(int numOfPost) {
        this.numOfPost = numOfPost;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public Date getPostCreateDate() {
        return postCreateDate;
    }

    public void setPostCreateDate(Date postCreateDate) {
        this.postCreateDate = postCreateDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCharCount() {
        return commentCharCount;
    }

    public void setCommentCharCount(int commentCharCount) {
        this.commentCharCount = commentCharCount;
    }
}
