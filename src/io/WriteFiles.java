package io;

import constants.Constants;
import user.DelegationInfo;
import user.UserInformations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/*
 * write necessary log files
 * */

public class WriteFiles {
    public void writeInfoFiles(ArrayList<Long> delegatedUserIdList, ArrayList<Date> delegationTimeList,
                               ArrayList<Integer> chainList, String fileName, int theTimestampIndex,
                               ArrayList<UserInformations> userList, int userIndex, int theTour, long friendUserID) {
        TableBuilder tb = new TableBuilder();
        File dir;
        String dirPath = Constants.getOutputFolderPath() + "\\" + userList.get(userIndex).getUserId();
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + userList.get(userIndex).getUserId());
            } else {
                System.out.println("Failed to create directory! " + userList.get(userIndex).getUserId());
            }
        }
//        BufferedWriter bufferedWriter = null;
//        FileWriter fileWriter = null;
//        WriteFiles writeFiles = new WriteFiles();
        // String data = delegatedUserIdList.toString() + " || " + delegationTimeList.toString() + " || " + chainList.toString() + " || latest file = " + fileName;// + " " + delegationTimeList.toString() + " " + chainList.toString();
//        String data = theTour + ". " + delegatedUserIdList.toString() + " || " +
//                userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp() + " || " +
//                chainList.toString() + " || " + " || latest file = " + fileName;

//        tb.addRow(String.valueOf(theTour),
//                delegatedUserIdList.toString(),
//                String.valueOf(userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp()),
//                chainList.toString(),
//                fileName);
        tb.addRow(String.valueOf(theTour),
                delegatedUserIdList.toString(),
                String.valueOf(userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp()),
                chainList.toString());
        //File file = new File(dirPath + "\\" + "Simulation_" + userList.get(userIndex).getUserId() + ".txt");
//        File file = new File(dirPath + "\\" + friendUserID + "_Simulation" + ".txt");
//        writeFile(file, data);
        //writeFile(file, tb.toString());
        /*
         * DD/MM/yyyy hh:mm format
         * */
//        File chainFlow = new File(dirPath + "\\" + "ChainFlow_" + userList.get(userIndex).getUserId() + ".txt");
        File chainFlow = new File(dirPath + "\\" + friendUserID + "_ChainFlow" + ".txt");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        String dateString = sdf.format(userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp());
//        String chainData = theTour + ". " + userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp()
//                + " || " + chainList.toString();
        tb = new TableBuilder();
        tb.addRow(String.valueOf(theTour), dateString, String.valueOf(userList.get(userIndex).getUserActivites().get(theTimestampIndex).getOnlineFriendsList().size()), chainList.toString());

        String chainData = theTour + ". " + dateString + " || " + chainList.toString();
        writeFile(chainFlow, tb.toString());
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeAllResult(ArrayList<DelegationInfo> delegationInfosArrayList, int indexOfUser, long friendUserID, long mainUserId) {
        File dir;
        String dirPath = Constants.getOutputFolderPath() + "\\" + mainUserId;
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + mainUserId);
            } else {
                System.out.println("Failed to create directory! " + mainUserId);
            }
        }
//        File file = new File(dirPath + "\\" + friendUserID + "_Simulation_general_info" + ".txt");
        File file = new File(dirPath + "\\" + "Simulation_general_info" + ".txt");
        File fileChainInfo = new File(dirPath + "\\" + "Chain_Duration_info" + ".txt");
        int days = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 24 / 60;
        int hours = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 60 % 24;
        int minutes = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() % 60;

        ArrayList<Integer> longestChainLengthList = new ArrayList<>();
        // ArrayList<Integer> longestChainDelegationTourList = new ArrayList<>();
        int longestChainDepth;
        //int longestChainDelegationTour;
        for (int i = 0; i < delegationInfosArrayList.get(indexOfUser).getChainDepth().size(); i++) {
            longestChainLengthList.add(Collections.max(delegationInfosArrayList.get(indexOfUser).getChainDepth().get(i)));
            //longestChainDelegationTourList.add(delegationInfosArrayList.get(indexOfUser).getChainDepth().get(i).size());
        }
        longestChainDepth = Collections.max(longestChainLengthList);
        //longestChainDelegationTour = Collections.max(longestChainDelegationTourList);
        ArrayList<String> chainInfos = new ArrayList<>();
        String tmp;
        for (int i = 0; i < delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().size(); i++) {
            tmp = "\t\tCL=" + String.valueOf(delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().get(i).getChainIndex()) + "\t\t CD="
                    + String.valueOf(delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().get(i).getChainDuration());
            chainInfos.add(tmp);
        }
        String chainInfosFormatted = chainInfos.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();

        TableBuilder tb = new TableBuilder();

        tb.addRow(" UserId", " Time Interval of FriendID", " Offline Days", " Hours", " Minutes", " TOT in Minutes"
                , " TotalOfflineCount", " LCD", " Interrupted Session Count"
                , " interruption total time", " Total Off Minutes", "Chain Length and Durations", "\n");
//        String line = "UserId = " + delegationInfosArrayList.get(indexOfUser).getUserId() +
//                "; Offline Days = " + days + " Hours = " + hours + " Minutes = " + minutes +
//                "\n TotalOfflineCount = " + delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount() +
//                "; Longest Chain Depth = " + longestChainDepth + "; Interrupted Session Count:" + delegationInfosArrayList.get(indexOfUser).getInterruptedSessionCount() +
//                "\n interruption total time = " + delegationInfosArrayList.get(indexOfUser).getInterruptTime();// + "; Longest Chain Delegation Tour = " + longestChainDelegationTour;
//        writeFile(file, line);


        tb.addRow(String.valueOf(delegationInfosArrayList.get(indexOfUser).getUserId())
                , String.valueOf(friendUserID)
                , String.valueOf(days)
                , String.valueOf(hours)
                , String.valueOf(minutes)
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime())
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount())
                , String.valueOf(longestChainDepth)
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getInterruptedSessionCount())
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getInterruptTime())
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime())
                , chainInfosFormatted);
        writeFile(file, tb.toString());
    }

    public void arrayListWrite(ArrayList<UserInformations> userList, int userIndex, ArrayList<String> tableBuilder, long friendUserID, ArrayList<String> tableBuilder1) {
        File dir;
        String dirPath = Constants.getOutputFolderPath() + "\\" + userList.get(userIndex).getUserId();
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + userList.get(userIndex).getUserId());
            } else {
                System.out.println("Failed to create directory! " + userList.get(userIndex).getUserId());
            }
        }

        File chainFlow = new File(dirPath + "\\" + friendUserID + "_ChainFlow" + ".txt");
        String formattedString = tableBuilder.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
        writeFile(chainFlow, formattedString);
        File dirLog;
        String dirLogPath = dirPath + "\\DetailedLogs";
        dirLog = new File(dirLogPath);
        if (!dirLog.exists()) {
            if (dirLog.mkdir()) {
                System.out.println("Directory is created! " + userList.get(userIndex).getUserId());
            } else {
                System.out.println("Failed to create directory! " + userList.get(userIndex).getUserId());
            }
        }

        File detailedSimulationLog = new File(dirLogPath + "\\" + friendUserID + "_detailedLog.txt");
        String formattedDetailString = tableBuilder1.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
        writeFile(detailedSimulationLog, formattedDetailString);
//        writeFile(chainFlow, tableBuilder.toString());
    }


    public void writeFile(File file, String line) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            // true = append file
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(line);

            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

