package simulation;

import constants.Constants;
import io.ReadFiles;
import io.WriteFiles;
import io.post.ReadPostInformations;
import statistics.StatusListParserData;
import user.UserInformations;
import user.offline.OfflineStatusStructure;
import user.post.FriendsPostInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataThroghPutSimulation {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        DataThroghPutSimulation main = new DataThroghPutSimulation();
        ArrayList<FriendsPostInformation> usersListPost;
        ArrayList<UserInformations> usersList;
        ReadPostInformations readPostInformations = new ReadPostInformations();
        ArrayList<OfflineStatusStructure> offlineStatusStructuresList;
        StatusListParserData parser = new StatusListParserData();
        ReadFiles getUserFromData = new ReadFiles();

        usersList = getUserFromData.getUserList();
        usersListPost = readPostInformations.getPostsLists();
        offlineStatusStructuresList = parser.parseOneFriendStatusListToArraylist(usersListPost); /* get all offline times */
        main.dataThroughputSimulation(usersList, usersListPost, offlineStatusStructuresList);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("seconds = " + seconds);
        System.out.println("minutes = " + seconds / 60);
        System.out.println("hours = " + seconds / 3600);

    }

    private void dataThroughputSimulation(ArrayList<UserInformations> usersList, ArrayList<FriendsPostInformation> usersListPost,
                                          ArrayList<OfflineStatusStructure> offlineStatusStructuresList) {
        WriteFiles writeFiles = new WriteFiles();
        ArrayList<int[]> statusList;
        HashMap<Long, Integer> postsSizesWithCount = new HashMap<>();
        for (int i = 0; i < usersListPost.size(); i++) {
            // offline time intervals
            int offlineIndex = 0;
            while (offlineIndex < offlineStatusStructuresList.size() && usersListPost.get(i).getUserID() != offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                offlineIndex++;
            }
            long friendUserID = offlineStatusStructuresList.get(offlineIndex).getFriendUserID();
            while (offlineIndex < offlineStatusStructuresList.size() && usersListPost.get(i).getUserID() == offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                statusList = offlineStatusStructuresList.get(offlineIndex).getStatustList();
                for (int k = 0; k < statusList.size() - 1; k++) {

                    //control start date and end date -- START
                    // we need these dates for check simulation interval ie: 15 days
                    Date startDate = usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getCurrentTimestamp();
                    Date endDate = usersList.get(i).getUserActivites().get(statusList.get(k)[1]).getCurrentTimestamp();
                    //control start date and end date -- END

                    for (int j = 0; j < usersListPost.get(i).getPostActivities().size(); j++) {

                        if (usersListPost.get(i).getPostActivities().get(j).getCurrentTimestamp().after(startDate) &&
                                usersListPost.get(i).getPostActivities().get(j).getCurrentTimestamp().before(endDate)) {
                            int friendsPostSize = usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().size();

                            for (int x = 0; x < friendsPostSize; x++) {
                                int friendPost = usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().get(x).getNumOfPost();
                                for (int xx = 0; xx < friendPost; xx++) {
                                    if (usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().get(x).getFriendPostList().get(xx) != null) {
                                        long currentPostSize = usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().get(x).getFriendPostList().get(xx).getPostSize();
                                        postsSizesWithCount.put(currentPostSize,0);
                                    }
                                }

                                for (int xx = 0; xx < friendPost; xx++) {
                                    if (usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().get(x).getFriendPostList().get(xx) != null) {
                                        long currentPostSize = usersListPost.get(i).getPostActivities().get(j).getFriendsPosts().get(x).getFriendPostList().get(xx).getPostSize();
                                        if (postsSizesWithCount.containsKey(currentPostSize)) {
                                            postsSizesWithCount.put(currentPostSize,postsSizesWithCount.get(currentPostSize)+1);
                                        }
                                    }
                                }


                            }
                        }

                    }
                }
            }
            writeFiles.writeDataTPResult(postsSizesWithCount,usersListPost.get(i).getUserID(), friendUserID);
        }
    }


}
