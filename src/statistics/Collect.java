package statistics;

import constants.Constants;
import io.ReadFiles;
import user.offline.UserFriendsOfflineStatus;
import user.UserInformations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Collect {
    public static void main(String[] args) {

        /*
         * This process could run one time because result won't change
         * it is an expensive action so we will write output to the text file
         * Runtime = 4035.098144041 seconds 1 hour 07 minutes
         * */

        long startTime = System.nanoTime();
        ArrayList<UserInformations> usersList;
        ReadFiles getUserFromData = new ReadFiles();
        CollectUsersOfflineTimeStatus collectUsersOfflineTimeStatus;
        Collect collect = new Collect();
        //gather all user list
        usersList = getUserFromData.getUserList();

        ArrayList<UserFriendsOfflineStatus> userFriendsOfflineStatusList;

        for (int i = 0; i < usersList.size(); i++) {
            //this type object should be recreated
            collectUsersOfflineTimeStatus = new CollectUsersOfflineTimeStatus();
            userFriendsOfflineStatusList = new ArrayList<>();
            userFriendsOfflineStatusList = collectUsersOfflineTimeStatus.findUserOnlineOfflineTimes(usersList, i);
            collect.writeFile(userFriendsOfflineStatusList, usersList.get(i).getUserId() );
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double)totalTime / 1000000000.0;
        System.out.println("Runtime = " + seconds);
    }

    private void writeFile(ArrayList<UserFriendsOfflineStatus> userFriendsOfflineStatusList, long mainUserID) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        File file;
        File dir;
        for (int i = 0; i < userFriendsOfflineStatusList.size(); i++) {
//            String dirPath = Constants.getCollectionOutputPath() + "\\" + mainUserID + "\\"; //offline status
            String dirPath = Constants.getCollectionOutputPathOnline() + "\\" + mainUserID + "\\";  //online status
            dir = new File(dirPath);
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    System.out.println("Directory is created! " + dirPath);
                } else {
                    System.out.println("Failed to create directory! " + dirPath);
                }
            }
            try {
//                file = new File(dirPath+userFriendsOfflineStatusList.get(i).getFriendUserId() + "_userOfflineTimesIndexes"  + ".txt"); //offline
                file = new File(dirPath+userFriendsOfflineStatusList.get(i).getFriendUserId() + "_userOnlineTimesIndexes"  + ".txt"); //online
                // true = append file
                fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                bufferedWriter = new BufferedWriter(fileWriter);
//                bufferedWriter.write(userFriendsOfflineStatusList.get(i).getFriendOfflineStatus().toString()); //offline
                bufferedWriter.write(userFriendsOfflineStatusList.get(i).getFriendOnlineStatus().toString());  //online
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //close
                try {
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                    if (fileWriter != null)
                        fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}
