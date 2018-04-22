package statistics;

import user.offline.OfflineStatusStructure;

import java.util.ArrayList;

public class StatusListParser {
    public ArrayList<int[]> parseOneFriendStatusListToArraylist(OfflineStatusStructure offlineStatusStructure) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        int[] status = new int[2];

        for (int i = 0; i < offlineStatusStructure.getUserstatusList().size(); i++) {
           if (offlineStatusStructure.getUserstatusList().get(i) - offlineStatusStructure.getUserstatusList().get(i-1) != 1){

           }

        }


        return arrayList;
    }
}
