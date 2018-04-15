package simulation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * this method generate random offline time for specified user
 * return start and end indexes of scale
 *  */

public class StatusChanger {
    public int[] returnOnlineOfflineTimeScale(int activitiesLength) {

        //how many minutes will skip
//        ArrayList<Integer> scale = new ArrayList<>();
        int[] scale = new int[2];
        int randomNum1 = ThreadLocalRandom.current().nextInt(0, activitiesLength);
        int randomNum2 = ThreadLocalRandom.current().nextInt(0, activitiesLength);

        if (randomNum1 < randomNum2) {
            scale[0] = randomNum1;
            scale[1] = randomNum2;
        } else {
            scale[0] = randomNum2;
            scale[1] = randomNum1;
        }


        return scale;
    }
}
