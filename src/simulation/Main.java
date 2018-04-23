package simulation;

import constants.Constants;
import io.ReadFiles;
import io.WriteFiles;
import statistics.FindMostOnlineFriends;
import user.DelegationInfo;
import user.MostOnlineFriends;
import user.UserInformations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("Duplicates")
public class Main {
    public static void main(String[] args) throws ParseException {
        Main main = new Main();
        main.randomUserDelegationSimulation(Constants.getMostOnlineFriendDelegation());
    }

    private void randomUserDelegationSimulation(String delegationType) {

        ArrayList<UserInformations> usersList;
        ArrayList<int[]> statusList;
        ReadFiles getUserFromData = new ReadFiles();
        StatusChanger statusChanger = new StatusChanger();
        PickUser pickUser = new PickUser();
        ArrayList<DelegationInfo> delegationInfoArrayList = new ArrayList<>();
        DelegationInfo delegationInfo; // it may be arraylist in the future
        ArrayList<Long> delegatedUserIDList;
        ArrayList<Date> delegationTimeList;
        ArrayList<Integer> chainList;
        WriteFiles write = new WriteFiles();
        ArrayList<MostOnlineFriends> mostOnlineFriendsArrayList = new ArrayList<>();
        FindMostOnlineFriends findMostOnlineFriends = new FindMostOnlineFriends();

        //load data and variables
        long delegatedUserID;
        usersList = getUserFromData.getUserList();
        mostOnlineFriendsArrayList = findMostOnlineFriends.findMostOnlineFriendsList(usersList);

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

            System.out.println("simulation start for user = " + usersList.get(i).getUserId());
            //collectUsers.findUserOnlineOfflineTimes(usersList,i);
            delegationInfo = new DelegationInfo();
            statusList.clear();
            statusList = statusChanger.getUserStatusList(usersList.get(i).getUserActivites().size(), statusChangeCount);
            delegationInfo.setUserId(usersList.get(i).getUserId());
            int totalOfflineTime = 0;
            ArrayList<ArrayList<Long>> delegatedUserlistList = new ArrayList<>();
            ArrayList<ArrayList<Date>> delegatedUserTimeListList = new ArrayList<>();
            ArrayList<ArrayList<Integer>> chainListList = new ArrayList<>();
            // offline time intervals
            for (int k = 0; k < statusList.size() - 1; k++) {
                delegatedUserIDList = new ArrayList<>();
                delegationTimeList = new ArrayList<>();
                chainList = new ArrayList<>();

                //calculate total offline time during simulation
                totalOfflineTime = totalOfflineTime + (statusList.get(k)[1] - statusList.get(k)[0]);

                // start offline time to end offline time
                // and set first delegation
                if (delegationType.equals(Constants.getRandomDelegation())) {
                    delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                } else if (delegationType.equals(Constants.getMostOnlineFriendDelegation())) {
                    delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), mostOnlineFriendsArrayList.get(i));
                } else {
                    delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                }

                System.out.println("First delegation = " + delegatedUserID);
                delegatedUserIDList.add(delegatedUserID);
                delegationTimeList.add(usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getCurrentTimestamp());
                chainList.add(delegatedUserIDList.size());

                //move forward during time intervals
                for (int j = statusList.get(k)[0] + 1; j < statusList.get(k)[1]; j++) {
                    pickUser = new PickUser();
                    int delegatedOnlineResultIndex;
                    /*
                     *
                     * */
                    delegatedOnlineResultIndex = pickUser.isDelegatedUserOnline(usersList.get(i).getUserActivites().get(j), delegatedUserIDList);

                    /*if one of the delegated user not online
                     * delegate new user */
                    if (-1 == delegatedOnlineResultIndex) {
                        if (delegationType.equals(Constants.getRandomDelegation())) {
                            delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(j));
                        } else if (delegationType.equals(Constants.getMostOnlineFriendDelegation())) {
                            delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(j), mostOnlineFriendsArrayList.get(i));
                        } else {
                            delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(j));
                        }
                        delegatedUserIDList.add(delegatedUserID);
                        delegationTimeList.add(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                        chainList.add(delegatedUserIDList.size());
                        System.out.println("new delegation = " + delegatedUserID);

                        /*
                         * Unclear parameters In Order;
                         * j: TimestampIndex,
                         * i: userIndex,
                         * k: StatusList Index
                         * */
                        write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName(), j, usersList, i, k);
                    } else if (delegatedUserIDList.get(delegatedUserIDList.size() - 1).equals(delegatedUserIDList.get(delegatedOnlineResultIndex))) {
                        //do nothing
                        //System.out.println("last delegated user still online = " + delegatedUserID);
                        write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName(), j, usersList, i, k);
                    } else {
                        System.out.println("one of the older delegation come back = " + delegatedUserIDList.get(delegatedOnlineResultIndex).toString());
                        //resize the chain
                        //we have index of older delegated user
                        for (int l = delegatedUserIDList.size() - 1; l > delegatedOnlineResultIndex; l--) {
                            delegatedUserIDList.remove(delegatedUserIDList.get(l));
                            delegationTimeList.remove(delegationTimeList.get(l));
                        }
                        System.out.println("new list  = " + delegatedUserIDList);
                        chainList.add(delegatedUserIDList.size());
                        write.writeInfoFiles(delegatedUserIDList, delegationTimeList, chainList, usersList.get(i).getUserActivites().get(j).getFileName(), j, usersList, i, k);
                    }

                }
//                System.out.println("user get online for a while, latest timestamp id= " + usersList.get(i).getUserActivites().get(statusList.get(k)[1]).getFileName());
                delegatedUserlistList.add(delegatedUserIDList);
                delegatedUserTimeListList.add(delegationTimeList);
                chainListList.add(chainList);

            }

            delegationInfo.setDelegatedUserIDList(delegatedUserlistList);
            delegationInfo.setDelegationTimeList(delegatedUserTimeListList);
            delegationInfo.setChainDepth(chainListList);
            delegationInfo.setTotalOfflineTime(totalOfflineTime);
            delegationInfo.setTotalOfflineCount(statusList.size());
            delegationInfoArrayList.add(delegationInfo);
            write.writeAllResult(delegationInfoArrayList, i);
            System.out.println();
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