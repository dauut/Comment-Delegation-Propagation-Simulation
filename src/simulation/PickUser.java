package simulation;

import user.MostDisjointFriends;
import user.MostOnlineFriends;
import user.TimeBasedInformation;
import user.offline.OfflineStatusStructure;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*
 * this class pick delegated user
 * it needs time based information of current time
 * */

@SuppressWarnings("Duplicates")
public class PickUser {
    public long findRandomDelegation(TimeBasedInformation timeBasedInformation) {

        // in case size error
        long delegated = 0;
        int sizeOnlineFriend = timeBasedInformation.getOnlineFriendsList().size();
        int randomIndexOfUser = ThreadLocalRandom.current().nextInt(0, sizeOnlineFriend + 1);

        //avoid from indexoutofboundexception
        if (randomIndexOfUser == sizeOnlineFriend) {
            randomIndexOfUser = randomIndexOfUser - 1;
        }

        try {
            delegated = timeBasedInformation.getOnlineFriendsList().get(randomIndexOfUser).getFriendUserID();
        } catch (IndexOutOfBoundsException e) {
//            System.out.println("IndexOutOfBoundsException, sizeOnlineFriend=" + sizeOnlineFriend +
//                    ", randomIndexOfUser=" + randomIndexOfUser);
        }

        //System.out.println("picked index = " + randomIndexOfUser + " Delegated userid = " + delegated);

        return delegated;
    }


    /*
     * took online friends list and check if one of the
     * delegated user is online that current time
     * */
    public int isDelegatedUserOnline(TimeBasedInformation onlineFriendsList, ArrayList<Long> delegatedList) {
        for (int i = 0; i < delegatedList.size(); i++) {
            for (int j = 0; j < onlineFriendsList.getOnlineFriendsList().size(); j++) {
                if (onlineFriendsList.getOnlineFriendsList().get(j).getFriendUserID() == delegatedList.get(i)) {
                    return i;
                }
            }

        }
        return -1;
    }

    /*we have solid information about userID so it should contain that ID*/
    public int[] findUserIndexForOfflineSatatusList(Long userId, ArrayList<OfflineStatusStructure> offlineStatusList) {
        int[] result = new int[2];
        int index = 0;
        boolean find = false;
        int endIndex;

        while (!find && index < offlineStatusList.size()) {
            if (userId == offlineStatusList.get(index).getUserID()) {
                find = true;
            }
            index++;
        }

        index = index - 1;
        endIndex = index;
        find = false;

        while (!find && endIndex < offlineStatusList.size()) {
            if (userId != offlineStatusList.get(endIndex).getUserID()) {
                find = true;
            }
            endIndex++;
        }

        endIndex = endIndex - 1;

        result[0] = index;
        result[1] = endIndex;
        System.out.println("start index = " + index + " end index = " + endIndex);
        return result;
    }

    /*
    * this two methods are same intentionally
    * due to openness
    * */
    public long findAndPickMostOnlineFriendsAsDelegatedUser(TimeBasedInformation timeBasedInformation, MostOnlineFriends mostOnlineFriends) {
        long delegatedUserId = 0;
        int sizeCurrentOnlineFriends = timeBasedInformation.getOnlineFriendsList().size();
        int i = 0;
        boolean find = false;

        while (!find && i < mostOnlineFriends.getMostOnlineFriendsList().size()){
            if (timeBasedInformation.getOnlineFriendsHashSet().contains(mostOnlineFriends.getMostOnlineFriendsList().get(i))){
                find = true;
                //System.out.println("most online friends in that list is = " + mostOnlineFriends.getMostOnlineFriendsList().get(i));
                delegatedUserId = mostOnlineFriends.getMostOnlineFriendsList().get(i);
            }else{
                i++;
            }

        }

        return delegatedUserId;
    }

    public long findAndPickMostDisjointedFriendsAsDelegatedUser(TimeBasedInformation timeBasedInformation, MostDisjointFriends mostDisjointFriends) {
        long delegatedUserId = 0;
        int sizeCurrentOnlineFriends = timeBasedInformation.getOnlineFriendsList().size();
        int i = 0;
        boolean find = false;

        while (!find && i < mostDisjointFriends.getMostOnlineFriendsList().size()){
            if (timeBasedInformation.getOnlineFriendsHashSet().contains(mostDisjointFriends.getMostOnlineFriendsList().get(i))){
                find = true;
                //System.out.println("most online friends in that list is = " + mostOnlineFriends.getMostOnlineFriendsList().get(i));
                delegatedUserId = mostDisjointFriends.getMostOnlineFriendsList().get(i);
            }else{
                i++;
            }

        }

        return delegatedUserId;
    }
    /*
     * take a bulk session to parts
     * retired
     * */
    public ArrayList<int[]> parsedStatus(OfflineStatusStructure offlineStatusStructure) {
        ArrayList<int[]> statusList = new ArrayList<>();
        int[] status = new int[2];
        int counter = offlineStatusStructure.getUserstatusList().get(0);
        while (counter < offlineStatusStructure.getUserstatusList().size()) {
            if (offlineStatusStructure.getUserstatusList().get(counter) + 1 == offlineStatusStructure.getUserstatusList().get(counter + 1)) {
                counter++;
            } else {

            }
        }

        return statusList;
    }
}
