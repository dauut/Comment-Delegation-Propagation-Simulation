package io;

import user.OnlineFriendsAndStatus;
import user.TimeBasedInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class ParseLines {
    public TimeBasedInformation parseLines(ArrayList<String> lines) {
        String delims = "[ ]";

        /*first line to parse we have timestamp in this line*/
        String[] firstLineToken = lines.get(0).split(delims);

        TimeBasedInformation timeBasedInformation = new TimeBasedInformation();
        String timestamp = firstLineToken[4] + " " + firstLineToken[5] + " " + firstLineToken[6];
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date parsedDate = simpleDateFormat.parse(timestamp);
            timeBasedInformation.setCurrentTimestamp(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //third line has online friends count
        String[] thirdLineTokens = lines.get(2).split(delims);
        timeBasedInformation.setOnlineFriendsCount(Integer.parseInt(thirdLineTokens[2]));

        //last line has various information
        String[] lastLineTokens = lines.get(lines.size() - 1).split(delims);
        if (lastLineTokens[0].equals("idle:")) {
            timeBasedInformation.setIdleFriendsCount(Integer.parseInt(lastLineTokens[1]));
            timeBasedInformation.setActiveFriendsCount(Integer.parseInt(lastLineTokens[3]));
            timeBasedInformation.setMobileUsersCount(Integer.parseInt(lastLineTokens[6]));
            timeBasedInformation.setWebUsersCount(Integer.parseInt(lastLineTokens[9]));
            timeBasedInformation.setMobileActiveCount(Integer.parseInt(lastLineTokens[12]));
            timeBasedInformation.setMobileIdleCount(Integer.parseInt(lastLineTokens[15]));
        }

        String onlineStatusTokens[];
        HashSet<Long> onlineFriendsHashSet = new HashSet<>();
        ArrayList<OnlineFriendsAndStatus> friensStatusList = new ArrayList<>();
        for (int i = 3; i < lines.size() - 1; i++) {
            OnlineFriendsAndStatus onlineFriendsAndStatus = new OnlineFriendsAndStatus();
            onlineStatusTokens = lines.get(i).split(delims);

            onlineFriendsAndStatus.setFriendUserID(Long.parseLong(onlineStatusTokens[0]));
            onlineFriendsHashSet.add(Long.parseLong(onlineStatusTokens[0]));
            onlineFriendsAndStatus.setStatus(onlineStatusTokens[1]);
            onlineFriendsAndStatus.setDeviceType(onlineStatusTokens[2]);

            friensStatusList.add(onlineFriendsAndStatus);
        }
        timeBasedInformation.setOnlineFriendsHashSet(onlineFriendsHashSet);
        timeBasedInformation.setOnlineFriendsList(friensStatusList);

        return timeBasedInformation;
    }
}
