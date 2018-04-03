package io;

import user.TimeBasedInformations;
import user.UserInformations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFiles {
    public static void main(String[] args) {
        String mainFolderPath = "C:\\Users\\dauut\\Desktop\\Facebook_online_status";
//        String mainFolderPath = "C:\\Users\\dauut\\Desktop\\a2";
        File fbUsersFolder = new File(mainFolderPath);
        File[] listOfUsers = fbUsersFolder.listFiles(); // we get all users path in a File lists
        File file = null;
        File folder = null;
        File[] listOfFiles = null;

        ArrayList<UserInformations> usersList = new ArrayList<>();

        for (int i = 0; i < listOfUsers.length; i++) {
            UserInformations user = new UserInformations();

            user.setUserId(Long.parseLong(listOfUsers[i].getName()));
            System.out.println(user.getUserId());
            folder = new File(listOfUsers[i].toString());
            listOfFiles = folder.listFiles();
            ArrayList<TimeBasedInformations> timeBasedInformationsArrayList = new ArrayList<>();

            for (int j = 0; j < listOfFiles.length; j++) {
                TimeBasedInformations timeBasedInformations = new TimeBasedInformations();
                ParseLines parseLines = new ParseLines();
                ArrayList<String> lines = new ArrayList<>();

                lines.clear();
//                System.out.println(listOfFiles[j].toString());
                file = new File(listOfFiles[j].toString());
                try {
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNext()) {
                        lines.add(scanner.nextLine());
                    }
                    if (lines.size() > 1) {
                        timeBasedInformations = parseLines.parseLines(lines);
                        timeBasedInformationsArrayList.add(timeBasedInformations);
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            user.setUserActivites(timeBasedInformationsArrayList);
            usersList.add(user);
        }
    }
}
