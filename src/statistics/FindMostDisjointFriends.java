package statistics;

import io.post.ParsePostInformation;
import simulation.StatusChanger;
import user.MostDisjointFriends;
import user.TimeBasedInformation;
import user.UserInformations;
import user.offline.OfflineStatusStructure;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("Duplicates")
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
            System.out.println("Find most disjoint friend for user = " + usersList.get(i).getUserId());
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

            System.out.println("startindex = " + startIndex);
            int endIndex = offlineIndex - 1;
            System.out.println("endindex   = " + endIndex);

            //some users have no online friends so this time interval index came 0 and pop up exception
            if (startIndex >= endIndex) {
                startIndex = 0;
            }
            int mostDisjointOfflineFriendIndex = ThreadLocalRandom.current().nextInt(startIndex, endIndex);

            statusList = offlineStatusStructuresList.get(mostDisjointOfflineFriendIndex).getStatustList();
            mostDisjointFriends.setUserID(usersList.get(i).getUserId());
            mostDisjointFriends.setMostOnlineFriendsList(findMostDisjointFriends(usersList, i, statusList));
            mostDisjointFriendsArrayList.add(mostDisjointFriends);
        }

        return mostDisjointFriendsArrayList;
    }

    public MostDisjointFriends findOptimizedMostDisjointFriends(ArrayList<UserInformations> usersList, int userIndex, ArrayList<int[]> statusList, long friendUserID) {
        MostDisjointFriends mostDisjointFriends = new MostDisjointFriends();

        mostDisjointFriends.setUserID(friendUserID);
        mostDisjointFriends.setMostOnlineFriendsList(findMostDisjointFriends(usersList, userIndex, statusList));

        return mostDisjointFriends;
    }

    public ArrayList<Long> getMostDisjointFriendsLast5Days(ArrayList<UserInformations> usersList, int userIndex
            , ArrayList<int[]> statusList, int leftIndex) {
        ArrayList<Long> userMostDisjointFriendsList = new ArrayList<>();
        ArrayList<int[]> statusList1 = new ArrayList<>(statusList);
        ArrayList<Long> userOnlineFriendsList;
        CollectUsersOfflineTimeStatus userFriendsOnline = new CollectUsersOfflineTimeStatus();
        userOnlineFriendsList = userFriendsOnline.findAllOnlineFrineds(usersList, userIndex);
        HashMap<Long, Integer> disjointFriendsStatistics = new HashMap<>();
        ArrayList<TimeBasedInformation> timeBasedInformationArrayList = new ArrayList<>(usersList.get(userIndex).getUserActivites());
        ArrayList<Long> sortedDisjointFriendsStatistics = null;
        for (int i = 0; i < userOnlineFriendsList.size(); i++) {
            disjointFriendsStatistics.put(userOnlineFriendsList.get(i), 0);
        }

        if (leftIndex > 7200) {

            int fiveDaysAgoIndex = leftIndex - 7200;
            int first = 0;
            int last = 0;
            int flagStart = 0;
            int flagEnd = 0;

            if (fiveDaysAgoIndex < statusList1.get(0)[0]) {
                flagStart = 1;
            }

            if (leftIndex < statusList1.get(0)[0]) {
                flagEnd = 1;
            }

            if (fiveDaysAgoIndex > statusList1.get(statusList1.size() - 1)[1]) {
                flagStart = -1;
            }

            if (leftIndex > statusList1.get(statusList1.size() - 1)[1]) {
                flagEnd = -1;
            }

            if (flagEnd * flagStart == 1) {
                // there is no time interval during 5 days
                System.out.println("");
            } else if (flagStart == 1) {
                first = 0;
            } else if (flagEnd == -1) {
                last = statusList1.size() - 1;
            }

            if (flagStart == 0) {
                int i = 0;
                while (i < statusList1.size() - 1 && fiveDaysAgoIndex > statusList1.get(i)[0]) {
                    i++;
                }

                if (i >= statusList1.size() - 1) {
                    first = statusList1.size() - 1;
//                    statusList1.get(first)[0] = fiveDaysAgoIndex;
                } else if (fiveDaysAgoIndex <= statusList1.get(i - 1)[1]) {
                    first = i - 1;
//                    statusList1.get(first)[0] = fiveDaysAgoIndex;
                } else {
                    first = i; //online a dustu
                }
            }

            if (flagEnd == 0) {
                int i = 0;
                while (i < statusList1.size() - 1 && leftIndex > statusList1.get(i)[1]) {
                    i++;
                }

                if (i >= statusList1.size() - 1) {
                    last = statusList1.size() - 1;
//                    statusList1.get(last)[1] = leftIndex;
                } else if (leftIndex >= statusList1.get(i)[0]) {
                    last = i;
//                    statusList1.get(last)[1] = leftIndex;
                } else {
                    last = i - 1; //online
                }

            }


            if (flagEnd * flagStart != 1 && first < last) {
                for (int x = first; x < last; x++) {

                    for (int intervalIndex = statusList1.get(x)[0]; intervalIndex < statusList1.get(x)[1]; intervalIndex++) {
                        Iterator iter = usersList.get(userIndex).getUserActivites().get(intervalIndex).getOnlineFriendsHashSet().iterator();

                        // turn for every online user in that time interval activity
                        while (iter.hasNext()) {
                            long friendUserId = (long) iter.next();
                            for (long disjointUserId : disjointFriendsStatistics.keySet()) {
                                if (disjointUserId == friendUserId) {
                                    if (disjointFriendsStatistics.containsKey(friendUserId)) {
                                        disjointFriendsStatistics.put(friendUserId,
                                                disjointFriendsStatistics.get(friendUserId) + 1);
                                    }
                                }
                            }
                        }
                    }

                }

                List<Map.Entry<Long, Integer>> list = new ArrayList<>(disjointFriendsStatistics.entrySet());
                list.sort(Map.Entry.comparingByValue());
                sortedDisjointFriendsStatistics = new ArrayList<>();

                for (int k = list.size() - 1; k > 0; k--) {
                    sortedDisjointFriendsStatistics.add(list.get(k).getKey());
                }

                return sortedDisjointFriendsStatistics;
            } else {
                // bu kisimda 5 gun once offline interval yok. bakalim buraya dusecek mi
//                System.out.println("5 gun oncesinde userin offline oldugu hicbir time interval bulunmamaktadir.");
                userMostDisjointFriendsList = null;
                return userMostDisjointFriendsList;
            }


        } else {
            int goBackCounterFromLast = 7200 - leftIndex;
            int fiveDaysAgoIndex = timeBasedInformationArrayList.size() - goBackCounterFromLast;
            int first = 0;
            int last = 0;
            int flagStart = 0;
            int flagEnd = 0;

            if (leftIndex <= statusList1.get(0)[0]) {
                flagEnd = 1;
            }
            if (fiveDaysAgoIndex > statusList1.get(statusList1.size() - 1)[1]) {
                flagStart = 1;
            }

            if (flagEnd * flagStart == 1) {
                first = 0;
                last = statusList.size();
            }

            if (flagEnd == 0) {
                int i = 0;
                while (leftIndex >= statusList1.get(i)[0] && i < statusList1.size() - 1) {
                    i++;
                }

                if (i > statusList1.size() - 1) {
                    first = statusList1.size() - 1;
//                    statusList1.get(first)[1]=leftIndex;
                } else if (leftIndex <= statusList1.get(i - 1)[1]) {
                    first = i - 1;
//                    statusList1.get(first)[1]=leftIndex;
                } else {
                    first = i - 1;
                }

            }

            if (flagStart == 0) {
                int i = statusList1.size() - 1;
                while (fiveDaysAgoIndex <= statusList1.get(i)[1] && i > 0) {
                    i--;
                }
                if (i < 0) {
                    last = i + 1;
                } else if (fiveDaysAgoIndex >= statusList1.get(i + 1)[0]) {
                    last = i + 1;
//                    statusList1.get(last)[0] = fiveDaysAgoIndex;
                } else {
                    last = i + 1;
                }
            }


            //first part
            if (flagEnd * flagStart != 1) {
                for (int x = 0; x < first; x++) {

                    for (int intervalIndex = statusList1.get(x)[0]; intervalIndex < statusList1.get(x)[1]; intervalIndex++) {
                        Iterator iter = usersList.get(userIndex).getUserActivites().get(intervalIndex).getOnlineFriendsHashSet().iterator();

                        // turn for every online user in that time interval activity
                        while (iter.hasNext()) {
                            long friendUserId = (long) iter.next();
                            for (long disjointUserId : disjointFriendsStatistics.keySet()) {
                                if (disjointUserId == friendUserId) {
                                    if (disjointFriendsStatistics.containsKey(friendUserId)) {
                                        disjointFriendsStatistics.put(friendUserId,
                                                disjointFriendsStatistics.get(friendUserId) + 1);
                                    }
                                }
                            }
                        }
                    }

                }

                for (int x = last; x < statusList1.size(); x++) {

                    for (int intervalIndex = statusList1.get(x)[0]; intervalIndex < statusList1.get(x)[1]; intervalIndex++) {
                        Iterator iter = usersList.get(userIndex).getUserActivites().get(intervalIndex).getOnlineFriendsHashSet().iterator();

                        // turn for every online user in that time interval activity
                        while (iter.hasNext()) {
                            long friendUserId = (long) iter.next();
                            for (long disjointUserId : disjointFriendsStatistics.keySet()) {
                                if (disjointUserId == friendUserId) {
                                    if (disjointFriendsStatistics.containsKey(friendUserId)) {
                                        disjointFriendsStatistics.put(friendUserId,
                                                disjointFriendsStatistics.get(friendUserId) + 1);
                                    }
                                }
                            }
                        }
                    }

                }

                List<Map.Entry<Long, Integer>> list = new ArrayList<>(disjointFriendsStatistics.entrySet());
                list.sort(Map.Entry.comparingByValue());
                sortedDisjointFriendsStatistics = new ArrayList<>();

                for (int k = list.size() - 1; k > 0; k--) {
                    sortedDisjointFriendsStatistics.add(list.get(k).getKey());
                }

                return sortedDisjointFriendsStatistics;
            }else {
                // bu kisimda 5 gun once offline interval yok. bakalim buraya dusecek mi
//                System.out.println("5 gun oncesinde userin offline oldugu hicbir time interval bulunmamaktadir.");
                userMostDisjointFriendsList = null;
                return userMostDisjointFriendsList;
            }
        }


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