package simulation;

import user.TimeBasedInformation;
import user.UserFriendsOfflineStatus;
import user.UserInformations;

import java.util.ArrayList;
import java.util.HashSet;


/*
* In this class, we collect all online friends in simulation time.
* we will use every friends online - offline time interval for simulation
* */
public class CollectUsers {

    public ArrayList<UserFriendsOfflineStatus> findUserOnlineOfflineTimes(ArrayList<UserInformations> usersList, int userindex) {
        ArrayList<UserFriendsOfflineStatus> userFriendsOfflineStatusList = new ArrayList<>();
        UserFriendsOfflineStatus userFriendsOfflineStatus;
        ArrayList<Long> allFriendList;
        allFriendList = findAllOnlineFrineds(usersList, userindex);

        int startIdex = 0;
        int stopIndex = 0;

        for (int i = 0; i < allFriendList.size(); i++){
            userFriendsOfflineStatus = new UserFriendsOfflineStatus();


        }

        return userFriendsOfflineStatusList;
    }


    private ArrayList<Long> findAllOnlineFrineds(ArrayList<UserInformations> usersList, int userindex) {
        HashSet<Long> allOnlineUsers = new HashSet<>();
        for (int i = 0; i < usersList.get(userindex).getUserActivites().size(); i++) {
            TimeBasedInformation timeBasedInformation = new TimeBasedInformation();
            timeBasedInformation = usersList.get(userindex).getUserActivites().get(i);

            for (int j = 0; j < timeBasedInformation.getOnlineFriendsList().size(); j++) {
                allOnlineUsers.add(timeBasedInformation.getOnlineFriendsList().get(j).getFriendUserID());
            }

        }

        ArrayList<Long> userFrienLongArrayList = new ArrayList<>(allOnlineUsers);
        return userFrienLongArrayList;
    }


}
