package io;

import constants.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/*
 * write necessary log files
 * */
public class WriteFiles {
    public void writeInfoFiles(ArrayList<Long> delegatedUserIdList,ArrayList<Date> delegationTimeList, ArrayList<Integer> chainList, String fileName) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {

//            String data = " This is new content";
            String data = delegatedUserIdList.toString() + " || latest file = " + fileName;// + " " + delegationTimeList.toString() + " " + chainList.toString();
            File file = new File(Constants.getOutputPath());

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);
            bufferedWriter.newLine();
//            System.out.println("Done");

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

