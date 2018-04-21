package simulation;

import user.TimeBasedInformation;
import user.offline.OfflineStatusStructure;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/*
* this class pick delegated user
* it needs time based information of current time
* */
public class PickUser {
    public long findRandomDelegation(TimeBasedInformation timeBasedInformation) {

        // in case size error
        long delegated = 0;
        int sizeOnlineFriend = timeBasedInformation.getOnlineFriendsList().size();
        int randomIndexOfUser = ThreadLocalRandom.current().nextInt(0, sizeOnlineFriend + 1);

        //avoid from indexoutofboundexception
        if(randomIndexOfUser == sizeOnlineFriend){
            randomIndexOfUser = randomIndexOfUser - 1;
        }

        try {
            delegated = timeBasedInformation.getOnlineFriendsList().get(randomIndexOfUser).getFriendUserID();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException, sizeOnlineFriend=" + sizeOnlineFriend +
                    ", randomIndexOfUser="+ randomIndexOfUser);
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
//            if (onlineFriendsList.getOnlineFriendsList().contains(delegatedList.get(i))) {
//                return i;
//            }
            for (int j = 0; j < onlineFriendsList.getOnlineFriendsList().size();j++){
                if (onlineFriendsList.getOnlineFriendsList().get(j).getFriendUserID() == delegatedList.get(i)){
                    return i;
                }
            }

        }
        return -1;
    }
    /*we have solid information about userID so it should contain that ID*/
    public int findUserIndexForOfflineSatatusList(Long userId, ArrayList<OfflineStatusStructure> offlineStatusList){
        int index = 0;
        boolean find = false;

        while(!find || index<offlineStatusList.size()){
            if (userId == offlineStatusList.get(index).getUserID()){
                find = true;
            }
        }

        return index;
    }
}
