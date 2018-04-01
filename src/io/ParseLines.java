package io;

import user.OnlineFriendsAndStatus;
import user.TimeBasedInformations;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParseLines {
    public TimeBasedInformations parseLines(ArrayList<String> lines) {
        String delims = "[ ]";

        /*first line to parse we have timestamp in this line*/
        String[] firstLineToken = lines.get(0).split(delims);

        TimeBasedInformations timeBasedInformations = new TimeBasedInformations();
        String timestamp = firstLineToken[4] + " " + firstLineToken[5] + " " + firstLineToken[6];
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date parsedDate = simpleDateFormat.parse(timestamp);
            timeBasedInformations.setCurrentTimestamp(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //third line has online friends count
        String[] thirdLineTokens = lines.get(2).split(delims);
        timeBasedInformations.setOnlineFriendsCount(Integer.parseInt(thirdLineTokens[2]));

        //last line has various information
        String[] lastLineTokens = lines.get(lines.size()-1).split(delims);
        if (lastLineTokens[0].equals("idle:")) {
            timeBasedInformations.setIdleFriendsCount(Integer.parseInt(lastLineTokens[1]));
            timeBasedInformations.setActiveFriendsCount(Integer.parseInt(lastLineTokens[3]));
            timeBasedInformations.setMobileUsersCount(Integer.parseInt(lastLineTokens[6]));
            timeBasedInformations.setWebUsersCount(Integer.parseInt(lastLineTokens[9]));
            timeBasedInformations.setMobileActiveCount(Integer.parseInt(lastLineTokens[12]));
            timeBasedInformations.setMobileIdleCount(Integer.parseInt(lastLineTokens[15]));
        }
        String onlineStatusTokens[] = null;

        ArrayList<OnlineFriendsAndStatus> friensStatusList = new ArrayList<>();
//        System.out.println(lines.size());
        for (int i = 3; i < lines.size()-2; i++){
            OnlineFriendsAndStatus onlineFriendsAndStatus = new OnlineFriendsAndStatus();
            onlineStatusTokens = lines.get(i).split(delims);

//            System.out.println(onlineStatusTokens[0]);
            onlineFriendsAndStatus.setFriendUserID(Long.parseLong(onlineStatusTokens[0]));
            onlineFriendsAndStatus.setStatus(onlineStatusTokens[1]);
            onlineFriendsAndStatus.setDeviceType(onlineStatusTokens[2]);

            friensStatusList.add(onlineFriendsAndStatus);
        }

        timeBasedInformations.setOnlineFriendsList(friensStatusList);

        return timeBasedInformations;
    }
}
