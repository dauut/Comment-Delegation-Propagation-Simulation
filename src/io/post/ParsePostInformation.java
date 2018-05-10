package io.post;

import user.post.TimeBasedPostInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParsePostInformation {

    public TimeBasedPostInformation parsePostLines(ArrayList<String> lines){
        TimeBasedPostInformation timeBasedPostInformation = new TimeBasedPostInformation();
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

        String[] totalFriendsLine = lines.get(1).split(delims);
        timeBasedPostInformation.setTotalFriendsCount(Integer.parseInt(totalFriendsLine[2]));

        String[] mainUserPostsLine = lines.get(3).split(delims);


        return timeBasedPostInformation;

    }
}
