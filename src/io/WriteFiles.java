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
import java.util.*;

/*
 * write necessary log files
 * */

public class WriteFiles {
    /*
    * In this method there are summary results
    * @main user id
    * @time interval owner friend user id,
    * @Longest Chain Depth
    * @Offline Days, Hours and Minutes
    * @Total online friends count during time intervals
    * @Chain length, chain duration in current length and online friends count
    * */
    public void writeAllResult(ArrayList<DelegationInfo> delegationInfosArrayList, int indexOfUser,
                               long friendUserID, long mainUserId, String delegationType,
                               int onlineFriendsCount) {
        File dir;
        //String dirPath = Constants.getOutputFolderPath() + "\\" + delegationType + "\\" + mainUserId;
        String dirPath = Constants.getOutputFolderPath() + "\\" + delegationType + "\\" + "summary";
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + "summary");
            } else {
                System.out.println("Failed to create directory! " + "summary");
            }
        }
//        File file = new File(dirPath + "\\" + friendUserID + "_Simulation_general_info" + ".txt");
        File file = new File(dirPath + "\\" + mainUserId + "_Simulation_general_info" + ".txt");
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
        longestChainDepth = 0;
        try {
            longestChainDepth = Collections.max(longestChainLengthList);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            System.out.println(longestChainLengthList);
        }
        //longestChainDelegationTour = Collections.max(longestChainDelegationTourList);
        ArrayList<String> chainInfos = new ArrayList<>();
        String tmp;
        for (int i = 0; i < delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().size(); i++) {
            String CL = String.valueOf(delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().get(i).getChainIndex());
            String CD = String.valueOf(delegationInfosArrayList.get(indexOfUser).getChainDurationListsList().get(i).getChainDuration());
            String OFC = String.valueOf(delegationInfosArrayList.get(indexOfUser).getChainLengthAndUsers().get(i).size());

            CL = fixedLengthString(CL, 5);
            CD = fixedLengthString(CD, 5);
            OFC = fixedLengthString(OFC, 5);

//            tmp = "\t\tCL=" + CL + "\t\t CD=" + CD + "\t\tOFC=" + OFC + "\t||\t";
            tmp = "\t" + CL + "\t" + CD + "\t" + OFC + "\t";
            chainInfos.add(tmp);
        }
        String chainInfosFormatted = chainInfos.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();

        TableBuilder tb = new TableBuilder();

        tb.addRow("\tUserId", " Time Interval of FriendID", " Days", " Hours", " Minutes"
                , " OfflineCount", " LCD", " Interrupted Count"
                , " interruption total time", " Total Off Minutes",
                "Total Online Friends", "CL - CD - OFC", "\n");
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
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount())
                , String.valueOf(longestChainDepth)
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getInterruptedSessionCount())
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getInterruptTime())
                , String.valueOf(delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime())
                , String.valueOf(onlineFriendsCount)
                , chainInfosFormatted);
        writeFile(file, tb.toString());
    }
    /*
    * Detailed logs, this method causes heavy io processes
    * */
    public void arrayListWrite(ArrayList<UserInformations> userList, int userIndex, ArrayList<String> tableBuilder,
                               long friendUserID, ArrayList<String> tableBuilder1, String delegationType) {
        File dir;
        String dirPath = Constants.getOutputFolderPath() + "\\" + delegationType + "\\" + userList.get(userIndex).getUserId();
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


        //detailedLog
        boolean detailedLog = false;
        if (detailedLog) {
            File dirLog;
            String dirLogPath = dirPath + "\\DetailedLogs";
            dirLog = new File(dirLogPath);
            if (!dirLog.exists()) {
                if (dirLog.mkdir()) {
                    System.out.println("Directory is created! " + "DetailedLogs");
                } else {
                    System.out.println("Failed to create directory! " + "DetailedLogs");
                }
            }

            File detailedSimulationLog = new File(dirLogPath + "\\" + friendUserID + "_detailedLog.txt");
            String formattedDetailString = tableBuilder1.toString()
                    .replace(",", "")  //remove the commas
                    .replace("[", "")  //remove the right bracket
                    .replace("]", "")  //remove the left bracket
                    .trim();
            writeFile(detailedSimulationLog, formattedDetailString);
        }
    }

    /* FOR DECENT OUTPUT THIS METHOD IS CRUCIAL*/
    public static String fixedLengthString(String string, int length) {
        return String.format("%1$" + length + "s", string);
    }

    public void createFolder(String path) {
        File dir;
        String dirPath = Constants.getOutputFolderPath() + "\\" + path + "\\";
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + path);
            } else {
                System.out.println("Failed to create directory! " + path);
            }
        }
    }

    public void writeDataTPResult(HashMap<Long, Integer> postSizeCounts, long userId, long friendUserId){
        File dir;
        String dirPath = Constants.getPostOutputPath() + "\\" + userId;
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + userId);
            } else {
                System.out.println("Failed to create directory! " + userId);
            }
        }
        File file = new File(dirPath + "\\" + "_Simulation_general_info" + ".txt");

        String postSizeCounterStr = postSizeCounts.toString();
        String formattedpostSizeCounterStr = postSizeCounterStr.toString()
                .replace(",", "")  //remove the commas
                .replace("{", "")  //remove the right bracket
                .replace("}", "")  //remove the left bracket
                .trim();

        TableBuilder tb = new TableBuilder();

        tb.addRow("\tUserId", " Time Interval of FriendID", " Total Off Minutes", "Post Size = Count", "\n");
        tb.addRow(String.valueOf(userId), String.valueOf(friendUserId), String.valueOf(1), formattedpostSizeCounterStr);
        writeFile(file,tb.toString());
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

