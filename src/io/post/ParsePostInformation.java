package io.post;

import user.post.MainUserPosts;
import user.post.Posts;
import user.post.TimeBasedPostInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParsePostInformation {

    public TimeBasedPostInformation parsePostLines(ArrayList<String> lines) {
        TimeBasedPostInformation timeBasedPostInformation = new TimeBasedPostInformation();
        MainUserPosts mainUserPosts; // posts of main user
        Posts mainUserPost; // one post line
        Posts friendsPost = new Posts();
        ArrayList<Posts> mainUserPostLists = new ArrayList<>(); // list of all individual posts
        ArrayList<Posts> friendsPostLists = new ArrayList<>();

        String delims = "[ ]";

        /*first line to parse we have timestamp in this line*/
        String[] firstLineToken = lines.get(0).split(delims);
        String timestamp = firstLineToken[4] + " " + firstLineToken[5] + " " + firstLineToken[6];
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date parsedDate = simpleDateFormat.parse(timestamp);
            timeBasedPostInformation.setCurrentTimestamp(parsedDate);
        } catch (ParseException e) {
            System.out.println("Parse exception || currenttimestamp");
            e.printStackTrace();
        }

        //total friends count in this line
        String[] totalFriendsLine = lines.get(1).split(delims);
        timeBasedPostInformation.setTotalFriendsCount(Integer.parseInt(totalFriendsLine[2]));

        if (lines.size()>2) {
            mainUserPosts = new MainUserPosts();
            String[] mainUserPostsLine = lines.get(3).split(delims);
            mainUserPosts.setNumOfPost(Integer.parseInt(mainUserPostsLine[3]));

            /*
             * simple date format
             * */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            if (mainUserPosts.getNumOfPost() > 0) {
                String[] postLine;
                mainUserPost = new Posts();
                for (int i = 0; i < mainUserPosts.getNumOfPost(); i++) {
                    postLine = lines.get(4 + i).split(delims);
                    mainUserPost.setPostType(postLine[1]); //we know exact location of this value

                    for (int k = 2; k < postLine.length - 1; k++){
                        if (postLine[k].equals("id:")){
                            if (!postLine[k+1].equals("")){
                                mainUserPost.setPostID(Long.parseLong(postLine[k+1]));
                            }else{
                                mainUserPost.setPostID(0);
                            }
                        }else if (postLine[k].equals("created:")){
                            if (!postLine[k+1].equals("")){
                                //post create date
                                Date createdDate = null;
                                try {
                                    createdDate = sdf.parse(postLine[k+1]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    System.out.println("Create Date Not Found = " + postLine[k+1]);
                                }
                                mainUserPost.setPostCreateDate(createdDate);
                            }
                        }else if (postLine[k].equals("size:")){
                            if (!postLine[k+1].equals("")){
                                mainUserPost.setPostSize(Long.parseLong(postLine[k+1]));
                            }else{
                                mainUserPost.setPostSize(1);
                            }
                        }else if (postLine[k].equals("like")){
                            if (!postLine[k+2].equals("")){
                                mainUserPost.setLikeCount(Integer.parseInt(postLine[k+2]));
                            }else {
                                mainUserPost.setLikeCount(0);
                            }
                        }else if((postLine[k]+postLine[k+1]).equals("commentcount:")){
                            if (!postLine[k+2].equals("")){
                                mainUserPost.setCommentCount(Integer.parseInt(postLine[k+2]));
                            }else {
                                mainUserPost.setCommentCount(0);
                            }
                        }else if (postLine[k].equals("character")){
                            if (!postLine[k+2].equals("")){
                                mainUserPost.setCommentCharCount(Integer.parseInt(postLine[k+2]));
                            }else{
                                mainUserPost.setCommentCharCount(0);
                            }
                        }

                    }
                    mainUserPostLists.add(mainUserPost);
                    System.out.println();
                }
                mainUserPosts.setMainUserPostList(mainUserPostLists);
            }

            int friendsPostStartIndex = 5 + mainUserPosts.getNumOfPost();


            for (int i = friendsPostStartIndex; i < lines.size(); i++ ){

                if (!lines.get(i).equals("")){
                    String[] friendsPostLine = lines.get(i).split(delims);
                    if (lines.get(i).substring(0,6).equals("Friend")){
                        friendsPost = new Posts();
                        friendsPost.setFriendUserID(Long.parseLong(friendsPostLine[1]));

                        String[] friendsNumOfPost =lines.get(i+2).split(delims);
                        friendsPost.setNumOfPost(Integer.parseInt(friendsNumOfPost[3]));

                        if (friendsPost.getNumOfPost()>0){
// checkout data structure in right here
                            for (int j = 0; j < friendsPost.getNumOfPost(); j++){

                            }


                        }

                    }


                }


            }



            boolean friendsPostFlow = true;

//            while (friendsPostFlow) {
//
//                String[] friendsPost = lines.get(friendsPostStartIndex).split(delims);
//                String theWordOfFriend = friendsPost[0];
//                if (theWordOfFriend.substring(0, 6).equals("Friend")) {
//                    // do stuff in here
//                    friendsPosts.setFriendUserID(Long.parseLong(friendsPost[1]));
//
//                    String[] numOfPostFriends = lines.get(friendsPostStartIndex + 2).split(delims);
//                    friendsPosts.setNumOfPost(Integer.parseInt(numOfPostFriends[3]));
//                    if (friendsPosts.getNumOfPost() > 0) {
//                        String[] postLine;
//                        friendsPosts = new Posts();
//
//
//                        //for loops
//
//
//                        friendsPostStartIndex = friendsPostStartIndex + 4 +friendsPosts.getNumOfPost() ;
//                    }else{
//                        friendsPostStartIndex = friendsPostStartIndex + 4;
//                    }
//                } else {
//                    friendsPostFlow = false;
//                }
//            }
        }

        return timeBasedPostInformation;
    }
}
