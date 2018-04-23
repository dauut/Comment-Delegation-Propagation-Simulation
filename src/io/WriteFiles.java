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
                               ArrayList<UserInformations> userList, int userIndex, int theTour) {
        File dir;
        String dirPath = Constants.getOutputFolderPath()+ "\\" + userList.get(userIndex).getUserId();
        dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory is created! " + userList.get(userIndex).getUserId());
            } else {
                System.out.println("Failed to create directory! " + userList.get(userIndex).getUserId());
            }
        }
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
//        WriteFiles writeFiles = new WriteFiles();
        // String data = delegatedUserIdList.toString() + " || " + delegationTimeList.toString() + " || " + chainList.toString() + " || latest file = " + fileName;// + " " + delegationTimeList.toString() + " " + chainList.toString();
        String data = theTour + ". " + delegatedUserIdList.toString() + " || " +
                userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp() + " || " +
                chainList.toString() + " || " + " || latest file = " + fileName;
        File file = new File(dirPath +"\\"+ "Simulation_" + userList.get(userIndex).getUserId() + ".txt");
        writeFile(file, data);

        /*
        * DD/MM/yyyy hh:mm format
        * */
        File chainFlow = new File(dirPath+"\\" + "ChainFlow_" + userList.get(userIndex).getUserId() + ".txt");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        String dateString = sdf.format(userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp());
//        String chainData = theTour + ". " + userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp()
//                + " || " + chainList.toString();
        String chainData = theTour + ". " + dateString + " || " + chainList.toString();
        writeFile(chainFlow, chainData);
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

    public void writeAllResult(ArrayList<DelegationInfo> delegationInfosArrayList, int indexOfUser) {
        String dirPath = Constants.getOutputFolderPath()+ "\\" + delegationInfosArrayList.get(indexOfUser).getUserId();
        File file = new File(dirPath+"\\" + "Simulation_general_info_" + delegationInfosArrayList.get(indexOfUser).getUserId() + ".txt");
        int days = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 24 / 60;
        int hours = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 60 % 24;
        int minutes = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() % 60;

        ArrayList<Integer> longestChainLengthList = new ArrayList<>();
        ArrayList<Integer> longestChainDelegationTourList = new ArrayList<>();
        int longestChainDepth;
        int longestChainDelegationTour;
        for (int i = 0; i < delegationInfosArrayList.get(indexOfUser).getChainDepth().size(); i++) {
            longestChainLengthList.add(Collections.max(delegationInfosArrayList.get(indexOfUser).getChainDepth().get(i)));
            longestChainDelegationTourList.add(delegationInfosArrayList.get(indexOfUser).getChainDepth().get(i).size());
        }
        longestChainDepth = Collections.max(longestChainLengthList);
        longestChainDelegationTour = Collections.max(longestChainDelegationTourList);

        String line = "UserId = " + delegationInfosArrayList.get(indexOfUser).getUserId() +
                "; Offline Days = " + days + " Hours = " + hours + " Minutes = " + minutes +
                "\n TotalOfflineCount = " + delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount() +
                "; Longest Chain Depth = " + longestChainDepth + "; Interrupted Session Count:" + delegationInfosArrayList.get(indexOfUser).getInterruptedSessionCount();// + "; Longest Chain Delegation Tour = " + longestChainDelegationTour;
        writeFile(file, line);
    }

    private void writeFile(File file, String line) {
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

