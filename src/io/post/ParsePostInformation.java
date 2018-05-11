package io.post;

import user.post.MainUserPosts;
import user.post.Posts;
import user.post.TimeBasedPostInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParsePostInformation {

    public TimeBasedPostInformation parsePostLines(ArrayList<String> lines) throws ParseException {
        TimeBasedPostInformation timeBasedPostInformation = new TimeBasedPostInformation();
        MainUserPosts mainUserPosts = new MainUserPosts();
        Posts mainUserPost = new Posts();
        Posts friendsPosts = new Posts();
        ArrayList<Posts> mainUserPostLists;
        ArrayList<Posts> friendsPostLists;

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


        String[] mainUserPostsLine = lines.get(3).split(delims);
        mainUserPosts.setNumOfPost(Integer.parseInt(mainUserPostsLine[3]));

        /*
        * simple date format
        * */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (mainUserPosts.getNumOfPost() > 0) {
            String[] postLine;
            mainUserPost = new Posts();
            String formattedDate;
            int increaseIndexFlag = 0;
            for (int i = 0; i < mainUserPosts.getNumOfPost(); i++) {
                postLine = lines.get(4 + i).split(delims);
                mainUserPost.setPostType(postLine[1]);

                if (postLine[3].equals("")){
                    increaseIndexFlag = 1;
                }

                Date createdDate = sdf.parse(postLine[5+increaseIndexFlag]);
               // formattedDate = output.format(createdDate);
                mainUserPost.setPostCreateDate(createdDate);
                System.out.println();
//                mainUserPosts.getMainUserPostList().get(i).setPostCreateDate();
                }
            }


            return timeBasedPostInformation;

        }
    }
