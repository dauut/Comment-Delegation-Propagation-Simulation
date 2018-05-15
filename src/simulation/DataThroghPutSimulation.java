package simulation;

import io.WriteFiles;
import io.post.ReadPostInformations;
import statistics.StatusListParserData;
import user.offline.OfflineStatusStructure;
import user.post.FriendsPostInformation;

import java.util.ArrayList;

public class DataThroghPutSimulation {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        DataThroghPutSimulation main = new DataThroghPutSimulation();
        ArrayList<FriendsPostInformation> usersList;
        ReadPostInformations readPostInformations = new ReadPostInformations();
        ArrayList<OfflineStatusStructure> offlineStatusStructuresList;
        StatusListParserData parser = new StatusListParserData();

        usersList = readPostInformations.getPostsLists();
        offlineStatusStructuresList = parser.parseOneFriendStatusListToArraylist(usersList);/* get all offline times */
        main.dataThroughputSimulation(usersList, offlineStatusStructuresList);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("seconds = " + seconds);
        System.out.println("minutes = " + seconds / 60);
        System.out.println("hours = " + seconds / 3600);

    }

    private void dataThroughputSimulation(ArrayList<FriendsPostInformation> usersList, ArrayList<OfflineStatusStructure> offlineStatusStructuresList) {
        WriteFiles writeFiles = new WriteFiles();
        ArrayList<int[]> statusList;
        for (int i = 0; i < usersList.size(); i++) {
            // offline time intervals
            int offlineIndex = 0;
            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserID() != offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                offlineIndex++;
            }
            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserID() == offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                long friendUserID = offlineStatusStructuresList.get(offlineIndex).getFriendUserID();
                statusList = offlineStatusStructuresList.get(offlineIndex).getStatustList();
                for (int k = 0; k < statusList.size() - 1; k++) {
                    for (int j = statusList.get(k)[0] + 1; j < statusList.get(k)[1]; j++) {

                    }
                }
            }
        }

//        for (int i = 0; i < usersList.size(); i++) {
//            for (int j = 0; j < usersList.get(i).getPostActivities().size(); j++) {
//                for (int k = 0; k < usersList.get(i).getPostActivities().get(j).getFriendsPosts().size(); k++) {
//                    if (usersList.get(i).getPostActivities().get(j).getFriendsPosts().get(k).getMainUserPostList() != null) {
//                    }
//                }
//            }
//        }
    }


}
