package statistics;

import simulation.StatusChanger;
import user.MostDisjointFriends;
import user.UserInformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class FindMostDisjointFriends {


    /*
     * Find most disjoint friends for all users for one based offline interval
     * */
    public ArrayList<MostDisjointFriends> findMostDisjointFriendsList(ArrayList<UserInformations> usersList) {
        ArrayList<int[]> statusList;
        int statusChangeCount = ThreadLocalRandom.current().nextInt(20, 40);
        StatusChanger statusChanger = new StatusChanger();
        MostDisjointFriends mostDisjointFriends;
        ArrayList<MostDisjointFriends> mostDisjointFriendsArrayList = new ArrayList<>();

        for (int i = 0; i < usersList.size(); i++) {
            mostDisjointFriends = new MostDisjointFriends();
            statusList = statusChanger.getUserStatusList(usersList.get(i).getUserActivites().size(), statusChangeCount);
            mostDisjointFriends.setUserID(usersList.get(i).getUserId());
            mostDisjointFriends.setMostOnlineFriendsList(findMostDisjointFriends(usersList, i, statusList));
            mostDisjointFriendsArrayList.add(mostDisjointFriends);
        }

        return mostDisjointFriendsArrayList;
    }

    private void findMostDisjointFriends(ArrayList<UserInformations> usersList, int userIndex, ArrayList<int[]> statusList) {
        ArrayList<Long> userOnlineFriendsList;
        CollectUsersOfflineTimeStatus userFriendsOnline = new CollectUsersOfflineTimeStatus();
        userOnlineFriendsList = userFriendsOnline.findAllOnlineFrineds(usersList, userIndex);
        HashMap<Long, Integer> disjointFriendsStatistics = new HashMap<>();

        for (int i = 0; i < userOnlineFriendsList.size(); i++) {
            disjointFriendsStatistics.put(userOnlineFriendsList.get(i), 0);
        }
        /*
         * iterate on that current timestamp for online users
         * */

        for (int k = 0; k < statusList.size() - 1; k++) { // we have interrupted time intervals
            int onlineFriendsIterator = 0;
            for (int j = statusList.get(k)[0]; j < statusList.get(k)[1]; j++) { //one time interval
                while (onlineFriendsIterator < disjointFriendsStatistics.size()) {
                    if (usersList.get(userIndex).getUserActivites().get(j).getOnlineFriendsHashSet().contains(
//delete here
                    )) {


                    }
                }
            }
        }
    }
}