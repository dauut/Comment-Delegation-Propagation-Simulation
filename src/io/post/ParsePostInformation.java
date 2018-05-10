package io.post;

import user.post.TimeBasedPostInformation;

import java.util.ArrayList;

public class ParsePostInformation {

    public TimeBasedPostInformation parsePostLines(ArrayList<String> lines){
        TimeBasedPostInformation timeBasedPostInformation = new TimeBasedPostInformation();
        String delims = "[ ]";

        /*first line to parse we have timestamp in this line*/
        String[] firstLineToken = lines.get(0).split(delims);

        String timestamp = firstLineToken[4] + " " + firstLineToken[5] + " " + firstLineToken[6];


        return timeBasedPostInformation;

    }
}
