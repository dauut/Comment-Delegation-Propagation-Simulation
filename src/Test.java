import io.TableBuilder;
import statistics.ReadCollectedInformation;
import statistics.StatusListParser;
import user.offline.OfflineStatusStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ReadCollectedInformation readCollectedInformation = new ReadCollectedInformation();
        ArrayList<OfflineStatusStructure> offlineStatusStructuresList;
        StatusListParser parser = new StatusListParser();
        ArrayList<int[]> statusList = new ArrayList<>();
        offlineStatusStructuresList = readCollectedInformation.readStatusList();
        long userID = offlineStatusStructuresList.get(0).getUserID();
        int userIterator = 0;

        while (offlineStatusStructuresList.get(userIterator).getUserID() == userID) {
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
        System.out.println("da");

    }

}
