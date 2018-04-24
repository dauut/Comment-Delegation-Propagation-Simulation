package statistics;

import constants.Constants;
import user.offline.OfflineStatusStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
* Read from local files
* collected offline time intervals for
* every friends of every user
* */
public class ReadCollectedInformation {
    public ArrayList<OfflineStatusStructure> readStatusList() {
        System.out.println("ReadcollectedInformation start");
        String mainFolderPath = Constants.getCollectionOutputPath();
        File userFriendsFolder = new File(mainFolderPath);
        File[] listOfUsers = userFriendsFolder.listFiles(); // we get all users path in a File lists
        File file;
        File folder;
        File[] listOfFiles;
        List<Integer> tmpstatusList;
        ArrayList<Integer> statusList;
        OfflineStatusStructure offlineStatusStructure;
        ArrayList<OfflineStatusStructure> offlineStatusList = new ArrayList<>();
        //we will read every folder
        for (int i = 0; i < listOfUsers.length; i++) {
            folder = new File(listOfUsers[i].toString());
            listOfFiles = folder.listFiles();
            //set main userid

            //every file consist an offline status list
            //collect them in an array list
            for (int j = 0; j < listOfFiles.length; j++) {
                offlineStatusStructure = new OfflineStatusStructure();
                offlineStatusStructure.setUserID(Long.parseLong(listOfUsers[i].getName()));
                statusList = new ArrayList<>();

                file = new File(listOfFiles[j].toString());
                String line = null;

                //take friendUserId
                String fileName = listOfFiles[j].getName();
                String[] fileNameSplit = fileName.split("_");
                fileName = fileNameSplit[0];

                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()) {
                        line = scanner.nextLine();
                    }

                    /*read files without brackets and other not numeric chars */
                    String lineWithoutBrackets = line.replaceAll("[\\[\\]]", "");
                    lineWithoutBrackets = lineWithoutBrackets.replaceAll("\\s+", "");
                    //put a line with comma separated to an array list
                    tmpstatusList = Stream.of(lineWithoutBrackets.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    statusList = new ArrayList<>(tmpstatusList);
                    tmpstatusList.clear();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("file not found ! " + file.toString());
                }
                offlineStatusStructure.setFriendUserID(Long.parseLong(fileName));
                offlineStatusStructure.setUserstatusList(statusList);
                offlineStatusList.add(offlineStatusStructure);
            }
        }
        return offlineStatusList;
    }
}