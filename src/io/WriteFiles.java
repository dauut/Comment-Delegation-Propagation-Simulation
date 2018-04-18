package io;

import com.sun.org.apache.bcel.internal.generic.DUP;
import constants.Constants;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import user.DelegationInfo;
import user.UserInformations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

/*
 * write necessary log files
 * */

public class WriteFiles {
    public void writeInfoFiles(ArrayList<Long> delegatedUserIdList, ArrayList<Date> delegationTimeList,
                               ArrayList<Integer> chainList, String fileName, int theTimestampIndex,
                               ArrayList<UserInformations> userList, int userIndex, int theTour) {
//        File dir;
//        dir = new File("C:\\Users\\DavutU\\Desktop\\testout\\" + userList.get(userIndex).getUserId());
//        if (!dir.exists()) {
//            if (dir.mkdir()) {
//                System.out.println("Directory is created! " + userList.get(userIndex).getUserId());
//            } else {
//                System.out.println("Failed to create directory! " + userList.get(userIndex).getUserId());
//            }
//        }
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
//        WriteFiles writeFiles = new WriteFiles();
        // String data = delegatedUserIdList.toString() + " || " + delegationTimeList.toString() + " || " + chainList.toString() + " || latest file = " + fileName;// + " " + delegationTimeList.toString() + " " + chainList.toString();
        String data = theTour + ". " + delegatedUserIdList.toString() + " || " +
                userList.get(userIndex).getUserActivites().get(theTimestampIndex).getCurrentTimestamp() + " || " +
                chainList.toString() + " || " + " || latest file = " + fileName;
        File file = new File(Constants.getOutputPath());
        writeFile(file, data);

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

    //    public void writeAllResult(ArrayList<DelegationInfo> delegationInfosArrayList) {
////        File file = new File("C:\\Users\\DavutU\\Desktop\\testout\\"+delegationInfosArrayList);
//        File dir;
//        File file;
//        File generalInfoFile;
//        for (int i = 0; i < delegationInfosArrayList.size(); i++) {
//            generalInfoFile = new File("C:\\Users\\DavutU\\Desktop\\testout\\" + delegationInfosArrayList.get(i).getUserId() + "\\info.txt");
//            BufferedWriter bufferedWriter = null;
//            FileWriter fileWriter = null;
//            String generalInfo = "User id = " + delegationInfosArrayList.get(i).getUserId() + "; TotalOfflineTime = " +
//                    delegationInfosArrayList.get(i).getTotalOfflineTime() + "; TotalOfflineCount = " +
//                    delegationInfosArrayList.get(i).getTotalOfflineCount();
//            dir = new File("C:\\Users\\DavutU\\Desktop\\testout\\" + delegationInfosArrayList.get(i).getUserId());
//            if (!dir.exists()) {
//                if (dir.mkdir()) {
//                    System.out.println("Directory is created! " + delegationInfosArrayList.get(i).getUserId());
//                } else {
//                    System.out.println("Failed to create directory! " + delegationInfosArrayList.get(i).getUserId());
//                }
//            }
//            int fileNameCounter = 0;
//            writeFile(generalInfoFile, generalInfo);
//            for (int k = 0; k < delegationInfosArrayList.get(i).getDelegatedUserIDList().size(); k++) {
//                file = new File("C:\\Users\\DavutU\\Desktop\\testout\\" + delegationInfosArrayList.get(i).getUserId() + "\\testnum_" + fileNameCounter + ".txt");
//                ArrayList<Long> tmpDelegatedUserIDList;
//                ArrayList<Date> tmpDelegationTimeList;
//                ArrayList<Integer> tmpChainDepth;
//
//                tmpDelegatedUserIDList = delegationInfosArrayList.get(i).getDelegatedUserIDList().get(k);
//                tmpDelegationTimeList = delegationInfosArrayList.get(i).getDelegationTimeList().get(k);
//                tmpChainDepth = delegationInfosArrayList.get(i).getChainDepth().get(k);
//                for (int j = 0; j < tmpDelegatedUserIDList.size(); j++) {
//                    String data = tmpDelegatedUserIDList.get(j).toString() + " || " +
//                            tmpDelegationTimeList.get(j).toString() + " || " +
//                            tmpChainDepth.get(j).toString();
//                    writeFile(file, data);
//
//                }
//
//
//                fileNameCounter++;
//            }
//
//        }
//    }
    public void writeAllResult(ArrayList<DelegationInfo> delegationInfosArrayList, int indexOfUser) {
        File file = new File(Constants.getGeneralInfoOutputPath());
        int days = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 24 / 60;
        int hours = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() / 60 % 24;
        int minutes = delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() % 60;

        String line = "UserId = " + delegationInfosArrayList.get(indexOfUser).getUserId()+
                "; TotalOfflineTime = " + delegationInfosArrayList.get(indexOfUser).getTotalOfflineTime() +
                "; TotalOfflineCount = " + delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount();

        String line1 = "UserId = " + delegationInfosArrayList.get(indexOfUser).getUserId()+
                "; Days = " + days + "; hours = "+ hours+ "; minutes = "+ minutes +
                "; TotalOfflineCount = " + delegationInfosArrayList.get(indexOfUser).getTotalOfflineCount();
        writeFile(file, line1);
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

