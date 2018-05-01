package statistics;

import simulation.StatusChanger;
import user.MostDisjointFriends;
import user.UserInformations;
import user.offline.OfflineStatusStructure;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class FindMostDisjointFriends {
    /*
     * Find most disjoint friends for all users for one based offline interval
     * */
    public ArrayList<MostDisjointFriends> findMostDisjointFriendsList(ArrayList<UserInformations> usersList, ArrayList<OfflineStatusStructure> offlineStatusStructuresList) {
        ArrayList<int[]> statusList;
        int statusChangeCount = ThreadLocalRandom.current().nextInt(20, 40);
        StatusChanger statusChanger = new StatusChanger();
        MostDisjointFriends mostDisjointFriends;
        ArrayList<MostDisjointFriends> mostDisjointFriendsArrayList = new ArrayList<>();

        for (int i = 0; i < usersList.size(); i++) {
            mostDisjointFriends = new MostDisjointFriends();
            /*we choose random friend timeline for finding disjointed friends list*/
            int offlineIndex = 0;
            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserId() != offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                offlineIndex++;
            }

            int startIndex = offlineIndex;

            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserId() == offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                offlineIndex++;
            }

            int endIndex = offlineIndex - 1;

            int mostDisjointOfflineFriendIndex = ThreadLocalRandom.current().nextInt(startIndex, endIndex);
            statusList = offlineStatusStructuresList.get(mostDisjointOfflineFriendIndex).getStatustList();
            mostDisjointFriends.setUserID(usersList.get(i).getUserId());
            mostDisjointFriends.setMostOnlineFriendsList(findMostDisjointFriends(usersList, i, statusList));
            mostDisjointFriendsArrayList.add(mostDisjointFriends);
        }

        return mostDisjointFriendsArrayList;
    }

    /*
     * find and sort most disjoint friends for designated user
     *
     * */
    private ArrayList<Long> findMostDisjointFriends(ArrayList<UserInformations> usersList,
                                                    int userIndex, ArrayList<int[]> statusList) {
        ArrayList<Long> userOnlineFriendsList;
        CollectUsersOfflineTimeStatus userFriendsOnline = new CollectUsersOfflineTimeStatus();
        userOnlineFriendsList = userFriendsOnline.findAllOnlineFrineds(usersList, userIndex);
        HashMap<Long, Integer> disjointFriendsStatistics = new HashMap<>();

        for (int i = 0; i < userOnlineFriendsList.size(); i++) {
            disjointFriendsStatistics.put(userOnlineFriendsList.get(i), 0);
        }

        //turn for every offline session
        int totalDisjoint = 0;
        for (int i = 0; i < statusList.size(); i++) {
            //turn during that specified time interval
            for (int j = statusList.get(i)[0]; j < statusList.get(i)[1]; j++) {
                Iterator iter = usersList.get(userIndex).getUserActivites().get(j).getOnlineFriendsHashSet().iterator();

                // turn for every online user in that time interval activity
                while (iter.hasNext()) {
                    long friendUserId = (long) iter.next();
                    for (long disjointUserId : disjointFriendsStatistics.keySet()) {
                        if (disjointUserId == friendUserId) {
                            if (disjointFriendsStatistics.containsKey(friendUserId)) {
                                disjointFriendsStatistics.put(friendUserId,
                                        disjointFriendsStatistics.get(friendUserId) + 1);
                                totalDisjoint += 1;
                            }
                        }
                    }
                }
            }
        }

        List<Map.Entry<Long, Integer>> list = new ArrayList<>(disjointFriendsStatistics.entrySet());
        list.sort(Map.Entry.comparingByValue());
        ArrayList<Long> sortedDisjointFriendsStatistics = new ArrayList<>();

        for (int k = list.size() - 1; k > 0; k--) {
            sortedDisjointFriendsStatistics.add(list.get(k).getKey());
        }

        return sortedDisjointFriendsStatistics;
    }
}