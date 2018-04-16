package simulation;

import io.ReadFiles;
import io.WriteFiles;
import user.DelegationInfo;
import user.UserInformations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws ParseException {

        ArrayList<UserInformations> usersList;
        ArrayList<int[]> statusList;
        ReadFiles getUserFromData = new ReadFiles();
        StatusChanger statusChanger = new StatusChanger();
        PickUser pickUser = new PickUser();
        ArrayList<DelegationInfo> delegationInfoArrayList = new ArrayList<>();
        DelegationInfo delegationInfo = new DelegationInfo(); // it may be arraylist in the future
        ArrayList<Long> delegatedUserIDList = new ArrayList<>();
        ArrayList<Date> delegationTimeList = new ArrayList<>();
        ArrayList<Integer> chainList = new ArrayList<>();
        WriteFiles write = new WriteFiles();
        boolean isUserOffline = true;

        //load data and variables
        long delegatedUserID;
        usersList = getUserFromData.getUserList();

        //how many times he/she will be offline during 15 days
        int statusChangeCount = ThreadLocalRandom.current().nextInt(20, 40);

        //get all offline times
        statusList = statusChanger.getUserStatusList(usersList.get(0).getUserActivites().size(), statusChangeCount);

        /*
         * Thus far we have user list with their activities
         * online and offline times
         */

        //turn for every user
        for (int i = 0; i < usersList.size(); i++) {
            delegationInfo = new DelegationInfo();
            statusList.clear();
            statusList = statusChanger.getUserStatusList(usersList.get(i).getUserActivites().size(), statusChangeCount);
            delegationInfo.setUserId(usersList.get(i).getUserId());

            // offline time intervals
//            for (int k = 0; k < statusList.size(); k++) {
            for (int k = 0; k < 1; k++) {
                System.out.println("user goes offline start timestampid= " + usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getFileName());
                //start offline time to end offline time
                // and set first delegation
                delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                delegatedUserIDList.add(delegatedUserID);
                delegationTimeList.add(usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getCurrentTimestamp());
                chainList.add(delegatedUserIDList.size());
                //moveforward during time intervals
                for (int j = statusList.get(k)[0] + 1; j < statusList.get(k)[1]; j++) {
                    /*butun olay burada gerceklesecek
                     * delegasyon secimleri
                     * cikti tasarimi ve dosyalarin yazdirilmasi*/

                    pickUser = new PickUser();
                    int delegatedOnlineResultIndex;
                    delegatedOnlineResultIndex = pickUser.isDelegatedUserOnline(usersList.get(i).getUserActivites().get(j), delegatedUserIDList);
                    /*if one of the delegated user not online
                     * delegate new user */
                    if (-1 == delegatedOnlineResultIndex) {
                        delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(j));
                        delegatedUserIDList.add(delegatedUserID);
                        delegationTimeList.add(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                        chainList.add(delegatedUserIDList.size());
                        write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName());
                    } else if (delegatedUserIDList.get(delegatedUserIDList.size() - 1).equals(delegatedUserIDList.get(delegatedOnlineResultIndex))) {
                        //do nothing
//                        System.out.println("last delegated user still online");
                        write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName());
                    } else {//resize the chain
                        //we have index of older delegated user
                        int counter = delegatedOnlineResultIndex;
                        while (counter < delegatedUserIDList.size()) {
                            delegatedUserIDList.remove(delegatedUserIDList.get(counter));
                            delegationTimeList.remove(delegationTimeList.get(counter));
                            chainList.add(delegatedUserIDList.size());
                            counter++;
                            write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName());
                        }
                    }

                }
                System.out.println("user get online for a while, latest timestamp id= " + usersList.get(i).getUserActivites().get(statusList.get(k)[1]).getFileName());

            }

            delegationInfo.setDelegatedUserIDList(delegatedUserIDList);
            delegationInfo.setDelegationTimeList(delegationTimeList);
            delegationInfo.setChainDepth(chainList);
            delegationInfoArrayList.add(delegationInfo);
            System.out.println();
        }


//        delegatedUserID = pickUser.findDelegation(usersList.get(0).getUserActivites().get(0));
//        delegatedUserIDList.add(delegatedUserID);
//        delegationTimeList.add(usersList.get(0).getUserActivites().get(0).getCurrentTimestamp());
//        delegationInfo.setUserId(usersList.get(0).getUserId());
//
//        int indexOfExistingDelegation;
//
//        for (int i = 0; i < usersList.get(0).getUserActivites().size(); i++) {
//            pickUser = new PickUser();
//            ArrayList<Long> onlineFriendUserList = new ArrayList<>();
////            onlineFriendUserList = usersList.get(0).getUserActivites().get(i).
//            //really bad
//            for (int k = 0; k < usersList.get(0).getUserActivites().get(i).getOnlineFriendsList().size(); k++) {
//                onlineFriendUserList.add(usersList.get(0).getUserActivites().get(i).getOnlineFriendsList().get(k).getFriendUserID());
//            }
//            //burasi duzeltilecek. burada contain atanmis listedeki elemanlar uzerinden yapilacak.
//            //metodize edilmesi gereken cok sey var
//            //kod karman corman oldu
//            if (pickUser.isDelegatedUser(onlineFriendUserList, delegatedUserIDList) != -1) {
//                delegatedUserID = pickUser.findDelegation(usersList.get(0).getUserActivites().get(i));
//                delegatedUserIDList.add(delegatedUserID);
//                delegationTimeList.add(usersList.get(0).getUserActivites().get(i).getCurrentTimestamp());
//                delegationInfo.setChainDepth(delegatedUserIDList.size());
//            } else {
//                indexOfExistingDelegation = delegatedUserIDList.indexOf(delegatedUserID);
//                //lets delete after that user
//                for (int j = indexOfExistingDelegation + 1; j < delegatedUserIDList.size(); j++) {
//                    delegatedUserIDList.remove(delegatedUserIDList.get(j));
//                    delegationTimeList.remove(delegationTimeList.get(j));
//                }
//                delegationInfo.setChainDepth(delegationTimeList.size());
//            }
//        }


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