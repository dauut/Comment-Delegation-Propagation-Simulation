package statistics;

import user.TimeBasedInformation;
import user.offline.UserFriendsOfflineStatus;
import user.UserInformations;

import java.util.ArrayList;
import java.util.HashSet;


/*
 * In this class, we collect all online friends in simulation time.
 * we will use every friends online - offline time interval for simulation
 * */

@SuppressWarnings("Duplicates")
public class CollectUsersOfflineTimeStatus {

    //find status list
    public ArrayList<UserFriendsOfflineStatus> findUserOnlineOfflineTimes(ArrayList<UserInformations> usersList, int userIndex) {
        ArrayList<UserFriendsOfflineStatus> userFriendsOfflineStatusList = new ArrayList<>();
        UserFriendsOfflineStatus userFriendsOfflineStatus;
        ArrayList<TimeBasedInformation> timeBasedInformationArrayList = new ArrayList<>(usersList.get(userIndex).getUserActivites());
        ArrayList<Long> allFriendList;
        allFriendList = findAllOnlineFrineds(usersList, userIndex);
        ArrayList<Integer> offlineTimes;
        ArrayList<Integer> onlineTimes;

        // shits gonna be shitter now
        // i needed fast soultions that's why
        for (int i = 0; i < allFriendList.size(); i++) {
            userFriendsOfflineStatus = new UserFriendsOfflineStatus();
            offlineTimes = new ArrayList<>();
            onlineTimes = new ArrayList<>();
            //we need turn over every hacking minutes
            for (int j = 0; j < timeBasedInformationArrayList.size(); j++) {
                boolean find = false;
                int counter = 0;
                //int zeroFriendCount = 0;
                //that current timestamp
                while (!find && counter < timeBasedInformationArrayList.get(j).getOnlineFriendsList().size()) {
                    if (timeBasedInformationArrayList.get(j).getOnlineFriendsList().get(counter).getFriendUserID() != allFriendList.get(i)) {
                        counter++;
                        /*some files have 0 friend count we may count it
                        if (timeBasedInformationArrayList.get(j).getOnlineFriendsList().size() == 0){
                            zeroFriendCount++;
                        }*/
                    } else {
                        find = true;
                    }
                }
                if (!find) {
                    offlineTimes.add(j);
                } else {
                    onlineTimes.add(j);
                }
            }

            userFriendsOfflineStatus.setFriendUserId(allFriendList.get(i));
            userFriendsOfflineStatus.setFriendOfflineStatus(offlineTimes);
            userFriendsOfflineStatus.setFriendOnlineStatus(onlineTimes);
            userFriendsOfflineStatusList.add(userFriendsOfflineStatus);

        }

        return userFriendsOfflineStatusList;
    }


    public ArrayList<Long> findAllOnlineFrineds(ArrayList<UserInformations> usersList, int userIndex) {
        HashSet<Long> allOnlineUsers = new HashSet<>();
        for (int i = 0; i < usersList.get(userIndex).getUserActivites().size(); i++) {
            TimeBasedInformation timeBasedInformation = new TimeBasedInformation();
            timeBasedInformation = usersList.get(userIndex).getUserActivites().get(i);

            for (int j = 0; j < timeBasedInformation.getOnlineFriendsList().size(); j++) {
                allOnlineUsers.add(timeBasedInformation.getOnlineFriendsList().get(j).getFriendUserID());
            }

        }

        ArrayList<Long> userFriendsLongArrayList = new ArrayList<>(allOnlineUsers);
        return userFriendsLongArrayList;
    }


}