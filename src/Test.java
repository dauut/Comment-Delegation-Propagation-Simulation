import io.TableBuilder;
import io.WriteFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        TableBuilder tb1 = new TableBuilder();
        String chainInfos = "[CL=0 CD=0, CL=1 CD=8135, CL=2 CD=3464, CL=3 CD=1604, CL=4 CD=279, CL=5 CD=145, CL=6 CD=47]";
        ArrayList<String> ch = new ArrayList<>();
        ch.add(chainInfos);
        tb1.addRow("Total Offline Time Minutes\t","Chain Lengths and Durations" , "\n");
        tb1.addRow(String.valueOf(321351613), ch.toString());
        String dirPath = "C:\\Users\\dauut\\Desktop\\testout";
        File fileChainInfo = new File(dirPath + "\\" + "Chain_Duration_info" + ".txt");
        WriteFiles writeFiles = new WriteFiles();
        writeFiles.writeFile(fileChainInfo,tb1.toString());
    }

}
