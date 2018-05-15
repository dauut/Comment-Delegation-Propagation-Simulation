package user.post;

import java.util.Date;

public class Posts {
    private String postType;
    private long postID;
    private Date postCreateDate;
    private int likeCount;
    private int commentCount;
    private int commentCharCount;
    private long postSize;

    public long getPostSize() {
        return postSize;
    }

    public void setPostSize(long postSize) {
        this.postSize = postSize;
    }


    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
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
