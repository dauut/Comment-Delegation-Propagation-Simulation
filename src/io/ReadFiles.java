package io;

import constants.Constants;
import user.TimeBasedInformation;
import user.UserInformations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class ReadFiles {
    public ArrayList<UserInformations> getUserList() {
        String mainFolderPath = Constants.getDataPath();
        File fbUsersFolder = new File(mainFolderPath);
        File[] listOfUsers = fbUsersFolder.listFiles(); // we get all users path in a File lists
        File file;
        File folder;
        File[] listOfFiles;

        ArrayList<UserInformations> usersList = new ArrayList<>();

        for (int i = 0; i < listOfUsers.length; i++) {
            UserInformations user = new UserInformations();

            user.setUserId(Long.parseLong(listOfUsers[i].getName()));
            System.out.println(user.getUserId());
            folder = new File(listOfUsers[i].toString());
            listOfFiles = folder.listFiles();
            ArrayList<TimeBasedInformation> timeBasedInformationArrayList = new ArrayList<>();

            for (int j = 0; j < listOfFiles.length; j++) {
                TimeBasedInformation timeBasedInformation;
                ParseLines parseLines = new ParseLines();
                ArrayList<String> lines = new ArrayList<>();

                lines.clear();
                file = new File(listOfFiles[j].toString());
                try {
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNext()) {
                        lines.add(scanner.nextLine());
                    }
                    if (lines.size() > 1) {
                        timeBasedInformation = parseLines.parseLines(lines);
                        timeBasedInformationArrayList.add(timeBasedInformation);
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            user.setUserActivites(timeBasedInformationArrayList);
            usersList.add(user);
        }
        return usersList;
    }
}
