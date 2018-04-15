package simulation;

import io.ReadFiles;
import user.DelegationInfo;
import user.UserInformations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {

        ArrayList<UserInformations> usersList;
        ReadFiles getUserFromData = new ReadFiles();
        DelegationInfo delegationInfo = new DelegationInfo(); // it may be arraylist in the future

        ArrayList<Long> delegatedUserIDList = new ArrayList<>();
        ArrayList<Date> delegationTimeList = new ArrayList<>();
        //boolean isUserOffline = true;

        long delegatedUserID;
        usersList = getUserFromData.getUserList();

        PickUser pickUser = new PickUser();
        delegatedUserID = pickUser.findDelegation(usersList.get(0).getUserActivites().get(0));

        delegatedUserIDList.add(delegatedUserID);
        delegationTimeList.add(usersList.get(0).getUserActivites().get(0).getCurrentTimestamp());
        delegationInfo.setUserId(usersList.get(0).getUserId());

        int indexOfExistingDelegation;

        for (int i = 0; i < usersList.get(0).getUserActivites().size(); i++) {
            pickUser = new PickUser();
            ArrayList<Long> onlineFriendUserList = new ArrayList<>();
//            onlineFriendUserList = usersList.get(0).getUserActivites().get(i).
            //really bad
            for (int k = 0; k < usersList.get(0).getUserActivites().get(i).getOnlineFriendsList().size(); k++) {
                onlineFriendUserList.add(usersList.get(0).getUserActivites().get(i).getOnlineFriendsList().get(k).getFriendUserID());
            }
            //burasi duzeltilecek. burada contain atanmis listedeki elemanlar uzerinden yapilacak.
            //metodize edilmesi gereken cok sey var
            //kod karman corman oldu
            if (pickUser.isDelegatedUser(onlineFriendUserList, delegatedUserIDList) != -1) {
                delegatedUserID = pickUser.findDelegation(usersList.get(0).getUserActivites().get(i));
                delegatedUserIDList.add(delegatedUserID);
                delegationTimeList.add(usersList.get(0).getUserActivites().get(i).getCurrentTimestamp());
                delegationInfo.setChainDepth(delegatedUserIDList.size());
            } else {
                indexOfExistingDelegation = delegatedUserIDList.indexOf(delegatedUserID);
                //lets delete after that user
                for (int j = indexOfExistingDelegation + 1; j < delegatedUserIDList.size(); j++) {
                    delegatedUserIDList.remove(delegatedUserIDList.get(j));
                    delegationTimeList.remove(delegationTimeList.get(j));
                }
                delegationInfo.setChainDepth(delegationTimeList.size());
            }
        }


    }
}

//Date settings
        /*
        Date simulationStartDate = null;
        Date simulationEndDate = null;
        SimpleDateFormat simpleStartFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        simulationStartDate = simpleStartFormat.parse(Constants.getSimulationStartDate());
        simulationEndDate = simpleStartFormat.parse(Constants.getSimulationEndDate());
        DateTime simulationStart = new DateTime(simulationStartDate);
        DateTime simulationEnd = new DateTime(simulationEndDate);
        */