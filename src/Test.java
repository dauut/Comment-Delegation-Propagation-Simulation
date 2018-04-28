import constants.Constants;
import io.ReadFiles;
import io.TableBuilder;
import statistics.ReadCollectedInformation;
import statistics.StatusListParser;
import user.UserInformations;
import user.offline.OfflineStatusStructure;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ArrayList<UserInformations> userList;
        ReadFiles getUserFromData = new ReadFiles();
        userList = getUserFromData.getUserList();

        //control start date and end date -- START
        // we need these dates for check simulation interval ie: 15 days
        String startDate = Constants.getSimulationStartDate();
        String endDate = Constants.getSimulationEndDate();
        SimpleDateFormat simpleStartFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date parsedStartDate = null;
        Date parsedEndDate = null;
        try {
            parsedStartDate = simpleStartFormat.parse(startDate);
            parsedEndDate = simpleStartFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //control start date and end date -- END
        for (int i = 0; i < userList.size(); i++) {
            File file;
            System.out.println("File deletion for user = " + userList.get(i).getUserId());
            for (int j = 0; j < userList.get(i).getUserActivites().size(); j++) {
                if (userList.get(i).getUserActivites().get(j).getCurrentTimestamp().after(parsedEndDate) ||
                        userList.get(i).getUserActivites().get(j).getCurrentTimestamp().before(parsedStartDate)   ){
                file = new File(userList.get(i).getUserActivites().get(j).getFileName());
                file.delete();
                }
            }
            System.out.println("All files deleted for user = " + userList.get(i).getUserId());
        }
    }

}
