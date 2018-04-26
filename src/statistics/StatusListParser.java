package statistics;

import user.UserInformations;
import user.offline.OfflineStatusStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatusListParser {
    public ArrayList<OfflineStatusStructure> parseOneFriendStatusListToArraylist(ArrayList<UserInformations> userList) {
        System.out.println("parseOneFriendStatusListToArraylist start");
        ReadCollectedInformation readCollectedInformation = new ReadCollectedInformation();
        ArrayList<OfflineStatusStructure> offlineStatusStructuresList;
        ArrayList<int[]> statusList;
        offlineStatusStructuresList = readCollectedInformation.readStatusList();
        for (int k = 0; k < userList.size(); k++) {
            long userID = userList.get(k).getUserId();
            int userIterator = 0;
            while (offlineStatusStructuresList.get(userIterator).getUserID() != userID){
                userIterator++;
            }

            while ( userIterator<offlineStatusStructuresList.size()&&offlineStatusStructuresList.get(userIterator).getUserID() == userID) {
                statusList = new ArrayList<>();
                int[] tmpArr = new int[offlineStatusStructuresList.get(userIterator).getUserstatusList().size()];
                Iterator<Integer> iterator = offlineStatusStructuresList.get(userIterator).getUserstatusList().iterator();

                for (int j = 0; j < tmpArr.length; j++) {
                    tmpArr[j] = iterator.next();
                }

                ArrayList<List<Integer>> sList = new ArrayList<>();
                List<Integer> subList = new ArrayList<>();
                for (int i = 0; i < tmpArr.length - 1; i++) {

                    if (tmpArr[i + 1] - tmpArr[i] == 1) {
                        subList.add(tmpArr[i]);
                    } else {
                        subList.add(tmpArr[i]);
                        sList.add(subList);
                        subList = new ArrayList<>();
                    }

                    if (i == tmpArr.length - 2) {
                        if (tmpArr[i] - tmpArr[i - 1] != 1) {
                            subList.add(tmpArr[i + 1]);
                        }
                        if (!subList.isEmpty())
                            sList.add(subList);
                        subList = new ArrayList<>();
                    }
                }


                for (List<Integer> intList : sList) {
                    int first = intList.get(0);
                    int last = intList.get(intList.size() - 1);
                    int[] status = new int[2];
                    status[0] = first;
                    status[1] = last;
                    statusList.add(status);
                }
                offlineStatusStructuresList.get(userIterator).setStatustList(statusList);
                userIterator++;
            }
        }


        return offlineStatusStructuresList;
    }
}
