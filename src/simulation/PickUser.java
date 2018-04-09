package simulation;

import user.OnlineFriendsAndStatus;
import user.TimeBasedInformation;

import java.util.concurrent.ThreadLocalRandom;

public class PickUser {
    public long findDelegation(TimeBasedInformation timeBasedInformation) {
        long delegated;
        int sizeOnlineFriend = timeBasedInformation.getOnlineFriendsCount();
        int randomIndexOfUser = ThreadLocalRandom.current().nextInt(0, sizeOnlineFriend + 1);

        delegated = timeBasedInformation.getOnlineFriendsList().get(randomIndexOfUser).getFriendUserID();

        System.out.println("picked index = " + randomIndexOfUser + ". Delegated userid = " + delegated);
        
        return delegated;
    }
}
