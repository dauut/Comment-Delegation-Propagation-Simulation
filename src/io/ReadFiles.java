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
//        String mainFolderPath = "C:\\Users\\dauut\\Desktop\\a";
        File fbUsersFolder = new File(mainFolderPath);
        File[] listOfUsers = fbUsersFolder.listFiles(); // we get all users path in a File lists
        File file = null;
        File folder = null;
        File[] listOfFiles = null;

        ArrayList<UserInformations> usersList = new ArrayList<>();
        UserInformations user = new UserInformations();

        ArrayList<String> lines = new ArrayList<>();
        ParseLines parseLines = new ParseLines();

        ArrayList<TimeBasedInformations> timeBasedInformationsArrayList = new ArrayList<>();
        TimeBasedInformations timeBasedInformations = new TimeBasedInformations();

        for (int i = 0; i < listOfUsers.length; i++) {
            user.setUserId(Long.parseLong(listOfUsers[i].getName()));
            System.out.println(user.getUserId());
            folder = new File(listOfUsers[i].toString());
            listOfFiles = folder.listFiles();

            for (int j = 0; j < listOfFiles.length; j++) {
//                System.out.println(listOfFiles[j].toString());
                lines.clear();
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
        System.out.println(usersList);
    }
}
