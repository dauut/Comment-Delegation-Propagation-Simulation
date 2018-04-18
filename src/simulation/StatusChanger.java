package simulation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * this method generate random offline time for specified user
 * parameters:
 *
 * @ activitiesLength:  activities length
 * @ lastOnline:        when user become online again lastly
 * @ onlineTime:     how much time will remain online
 * @ intervalRange:     scale of new offline time
 * return start and end indexes of scale
 */

public class StatusChanger {
    private int[] returnOnlineOfflineTimeScale(int activitiesLength, int lastOnline, int onlineTime, int intervalRange) {

        //how many minutes will skip
//        ArrayList<Integer> scale = new ArrayList<>();
        int[] scale = new int[2];
        int min = lastOnline + onlineTime;
        int max = min + intervalRange;

        if (activitiesLength < max || activitiesLength < min) {
            max = activitiesLength;
            min = activitiesLength - 1;
        }
        try {
            int randomNum1 = ThreadLocalRandom.current().nextInt(min, max);
            int randomNum2 = ThreadLocalRandom.current().nextInt(min, max);
            if (randomNum1 < randomNum2) {

                scale[0] = randomNum1;
                scale[1] = randomNum2;
            } else {
                scale[0] = randomNum2;
                scale[1] = randomNum1;
            }
            return scale;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("min = " + min);
            System.out.println("max = " + max);
        }finally {
            int randomNum1 = ThreadLocalRandom.current().nextInt(min, max+1);
            int randomNum2 = ThreadLocalRandom.current().nextInt(min, max+1);
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

    public ArrayList<int[]> getUserStatusList(int activitiesLength, int statusChangeCount) {
        StatusChanger statusChanger = new StatusChanger();
        ArrayList<int[]> statusList = new ArrayList<>();
        int[] status;
        int intervalRange;
        int onlineTime = 0; // it is initially 0 then will change
        int lastOnlineTime = 0; // it is initially 0 then will change
        int counter = 0;
        while (counter < statusChangeCount && lastOnlineTime + 1 < activitiesLength) {
            intervalRange = ThreadLocalRandom.current().nextInt(0, 1440); //between 0 to 1 day
            status = statusChanger.returnOnlineOfflineTimeScale(activitiesLength, lastOnlineTime, onlineTime, intervalRange);
            statusList.add(status);
            onlineTime = ThreadLocalRandom.current().nextInt(0, 960); // 0 - 16 hours range
            lastOnlineTime = status[1];
            counter++;
        }

        return statusList;
    }
}
