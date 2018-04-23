package statistics;

import io.ReadFiles;
import user.MostOnlineFriends;
import user.TimeBasedInformation;
import user.UserInformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
* it returns object of MostOnlineFriendsList
* */
public class FindMostOnlineFriends {
    public ArrayList<MostOnlineFriends> findMostOnlineFriendsList(ArrayList<UserInformations> usersList){
        //ArrayList<UserInformations> usersList;
        ReadFiles getUserFromData = new ReadFiles();
        FindMostOnlineFriends findMostOnlineFriend;
        //usersList = getUserFromData.getUserList();
        MostOnlineFriends mostOnlineFriends;
        ArrayList<MostOnlineFriends> mostOnlineFriendsArrayList = new ArrayList<>();
        for (int i = 0; i < usersList.size(); i++){
            mostOnlineFriends = new MostOnlineFriends();
            findMostOnlineFriend = new FindMostOnlineFriends();
            mostOnlineFriends.setUserID(usersList.get(i).getUserId());
            mostOnlineFriends.setMostOnlineFriendsList(findMostOnlineFriend.findMostOnlineFriends(usersList,i));
            mostOnlineFriendsArrayList.add(mostOnlineFriends);
        }
        return mostOnlineFriendsArrayList;
    }

    private ArrayList<Long> findMostOnlineFriends(ArrayList<UserInformations> usersList, int userIndex) {
        ArrayList<Long> userOnlineFriendsList;
        CollectUsersOfflineTimeStatus userFriendsOnline = new CollectUsersOfflineTimeStatus();
        ArrayList<TimeBasedInformation> timeBasedInformationArrayList = new ArrayList<>(usersList.get(userIndex).getUserActivites());
        userOnlineFriendsList = userFriendsOnline.findAllOnlineFrineds(usersList, userIndex);
        HashMap<Long, Integer> onlineFriendsStatistics = new HashMap<>();

        for (int i = 0; i < userOnlineFriendsList.size(); i++) {
            onlineFriendsStatistics.put(userOnlineFriendsList.get(i), 0);
        }

        for (int j = 0; j < timeBasedInformationArrayList.size(); j++) {
            for (int k = 0; k < timeBasedInformationArrayList.get(j).getOnlineFriendsList().size(); k++) {
                if (onlineFriendsStatistics.containsKey(timeBasedInformationArrayList.
                        get(j).getOnlineFriendsList().get(k).getFriendUserID())) {
                    onlineFriendsStatistics.put(timeBasedInformationArrayList.
                                    get(j).getOnlineFriendsList().get(k).getFriendUserID(),
                            onlineFriendsStatistics.get(timeBasedInformationArrayList.
                                    get(j).getOnlineFriendsList().get(k).getFriendUserID()) + 1);
                }
            }
        }

        List<Map.Entry<Long, Integer>> list = new ArrayList<>(onlineFriendsStatistics.entrySet());
        list.sort(Map.Entry.comparingByValue());
        ArrayList<Long> sortedMostOnlineFriends = new ArrayList<>();

        for (int k = list.size()-1 ; k > 0;k--){
            sortedMostOnlineFriends.add(list.get(k).getKey());
        }

        return sortedMostOnlineFriends;
    }
}
