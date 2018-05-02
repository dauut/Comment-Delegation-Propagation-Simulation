package simulation;

import constants.Constants;
import io.ReadFiles;
import io.TableBuilder;
import io.WriteFiles;
import statistics.FindMostDisjointFriends;
import statistics.FindMostOnlineFriends;
import statistics.StatusListParser;
import user.*;
import user.offline.OfflineStatusStructure;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("Duplicates")
public class Main2 {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Main2 main = new Main2();
        ArrayList<UserInformations> usersList;
        ArrayList<OfflineStatusStructure> offlineStatusStructuresList;
        StatusListParser parser = new StatusListParser();
        ReadFiles getUserFromData = new ReadFiles();
        ArrayList<MostOnlineFriends> mostOnlineFriendsArrayList;
        ArrayList<MostDisjointFriends> mostDisjointFriendsArrayList;
        FindMostOnlineFriends findMostOnlineFriends = new FindMostOnlineFriends();
        FindMostDisjointFriends findMostDisjointFriends = new FindMostDisjointFriends();

        usersList = getUserFromData.getUserList();
        offlineStatusStructuresList = parser.parseOneFriendStatusListToArraylist(usersList);/* get all offline times */

        /*find most online and most disjoint friends for delegation*/
        mostOnlineFriendsArrayList = findMostOnlineFriends.findMostOnlineFriendsList(usersList);
        mostDisjointFriendsArrayList = findMostDisjointFriends.findMostDisjointFriendsList(usersList, offlineStatusStructuresList);

        /*create necessary folders auto
        * delete folders manually*/
        WriteFiles writeFiles = new WriteFiles();
        String randomPath = Constants.getRandomDelegation();
        String msofPath = Constants.getMostOnlineFriendDelegation();
        String mdjfPath = Constants.getMostDisjointFriendDelegation();
        writeFiles.createFolder(randomPath);
        writeFiles.createFolder(msofPath);
        writeFiles.createFolder(mdjfPath);

        /*start simulation*/
        System.out.println(Constants.getRandomDelegation() + " session is started !!");
        main.randomUserDelegationSimulation(Constants.getRandomDelegation(), usersList, offlineStatusStructuresList,
                mostOnlineFriendsArrayList,mostDisjointFriendsArrayList);
        System.out.println(Constants.getMostOnlineFriendDelegation() + " session is started !!");
        main.randomUserDelegationSimulation(Constants.getMostOnlineFriendDelegation(), usersList, offlineStatusStructuresList,
                mostOnlineFriendsArrayList,mostDisjointFriendsArrayList);
        System.out.println(Constants.getMostDisjointFriendDelegation() + " session is started !!");
        main.randomUserDelegationSimulation(Constants.getMostDisjointFriendDelegation(), usersList, offlineStatusStructuresList,
                mostOnlineFriendsArrayList,mostDisjointFriendsArrayList);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("seconds = " + seconds);
        System.out.println("minutes = " + seconds / 60);
        System.out.println("hours = " + seconds / 3600);

        /*
        String totalRuntime = "Total Runtime: \n" + "\tSeconds = " + String.valueOf(seconds) + "\n"
                + "\tMinutes = " + String.valueOf(seconds / 60) + "\n"
                + "\tHours = " + String.valueOf(seconds / 3600);
        WriteFiles writeFiles = new WriteFiles();
        File file = new File("C:\\Users\\dauut\\Desktop\\testout\\runtimeInformation.txt");
        writeFiles.writeFile(file,totalRuntime);*/
    }

    private void randomUserDelegationSimulation(String delegationType, ArrayList<UserInformations> usersList,
                                                ArrayList<OfflineStatusStructure> offlineStatusStructuresList,
                                                ArrayList<MostOnlineFriends> mostOnlineFriendsArrayList,
                                                ArrayList<MostDisjointFriends> mostDisjointFriendsArrayList) {
        ArrayList<int[]> statusList;
        PickUser pickUser = new PickUser();
        ArrayList<DelegationInfo> delegationInfoArrayList;
        DelegationInfo delegationInfo; // it may be arraylist in the future
        ArrayList<Long> delegatedUserIDList;
        ArrayList<Date> delegationTimeList;
        ArrayList<Integer> chainList;
        WriteFiles write = new WriteFiles();
        TableBuilder tb;
        TableBuilder tbDetailed;
        ArrayList<String> tbList;
        ArrayList<String> tbDetailedList;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        ArrayList<EachChainDuration> chainDurationsList;
        EachChainDuration eachChainDuration;
        long delegatedUserID;
        ArrayList<HashMap<Integer, Integer>> onlineFriendsCountByChainLenghtList = new ArrayList<>();
        HashSet<Long> currentOnlineFriends;

        /*
         * Thus far we have user list with their activities
         * online and offline times
         */
        //turn for every user
        for (int i = 0; i < usersList.size(); i++) {
            int delegationArrayListCounter = 0;
            delegationInfoArrayList = new ArrayList<>();
            System.out.println("simulation start for user = " + usersList.get(i).getUserId());
            delegationInfo = new DelegationInfo();
            delegationInfo.setUserId(usersList.get(i).getUserId());

            ArrayList<ArrayList<Long>> delegatedUserlistList = new ArrayList<>();
            ArrayList<ArrayList<Date>> delegatedUserTimeListList = new ArrayList<>();
            ArrayList<ArrayList<Integer>> chainListList;

            // offline time intervals
            int offlineIndex = 0;
            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserId() != offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                offlineIndex++;
            }
            while (offlineIndex < offlineStatusStructuresList.size() && usersList.get(i).getUserId() == offlineStatusStructuresList.get(offlineIndex).getUserID()) {
                statusList = new ArrayList<>();
                chainListList = new ArrayList<>();
                chainDurationsList = new ArrayList<>();
                eachChainDuration = new EachChainDuration();

                eachChainDuration.setChainIndex(0);// first index intentionally filled up
                eachChainDuration.setChainDuration(0);//because we will keep every chain size duration in their indexes
                chainDurationsList.add(eachChainDuration);
                eachChainDuration = new EachChainDuration();
                eachChainDuration.setChainIndex(1);
                eachChainDuration.setChainDuration(1);
                chainDurationsList.add(eachChainDuration); //in this point we have chain size 1 for 1 minute


                long friendUserID = offlineStatusStructuresList.get(offlineIndex).getFriendUserID();
                int interruptedSessionCount = 0;
                int interruptionTime = 0;
                int totalOfflineTime = 0;

                statusList = offlineStatusStructuresList.get(offlineIndex).getStatustList();

                for (int k = 0; k < statusList.size() - 1; k++) {
                    /*with this list we collect the every session then write
                     * thats give us magnificent performance improvement*/
                    tbList = new ArrayList<>();
                    tbDetailedList = new ArrayList<>();
                    delegatedUserIDList = new ArrayList<>();
                    delegationTimeList = new ArrayList<>();
                    chainList = new ArrayList<>();

                    // start offline time to end offline time
                    // and set first delegation
                    if (delegationType.equals(Constants.getRandomDelegation())) {
                        delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                    } else if (delegationType.equals(Constants.getMostOnlineFriendDelegation())) {
                        delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), mostOnlineFriendsArrayList.get(i));
                    } else {
                        //delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                        delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), mostDisjointFriendsArrayList.get(i));
                    }

                    //System.out.println("First delegation = " + delegatedUserID);
                    delegatedUserIDList.add(delegatedUserID);
                    delegationTimeList.add(usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getCurrentTimestamp());
                    chainList.add(delegatedUserIDList.size());

                    //move forward through time intervals
                    for (int j = statusList.get(k)[0] + 1; j < statusList.get(k)[1]; j++) {
                        tb = new TableBuilder();
                        tbDetailed = new TableBuilder();
                        totalOfflineTime++;
                        if (usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet().size() == 0) {
                            //System.out.println("There is no user for delegation, end this session");
                            while (usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet().size() == 0) {
                                interruptionTime++;
                                j++;
                            }
                            j = statusList.get(k)[1];
                            interruptedSessionCount++;
                        } else {
                            pickUser = new PickUser();
                            int delegatedOnlineResultIndex;

                            /*
                             * check delegated user still online or not
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
                                    delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(j), mostDisjointFriendsArrayList.get(i));
                                }
                                delegatedUserIDList.add(delegatedUserID);
                                delegationTimeList.add(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                                chainList.add(delegatedUserIDList.size());

                                String dateString = sdf.format(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                                tb.addRow(String.valueOf(k), dateString, String.valueOf(usersList.get(i).getUserActivites().get(j).getOnlineFriendsList().size()), chainList.toString());
                                tbList.add(tb.toString() + "\n");
                                /*
                                 * Unclear parameters In Order;
                                 * j: TimestampIndex,
                                 * i: userIndex,
                                 * k: StatusList Index
                                 * */
                                tbDetailed.addRow(String.valueOf(k), delegatedUserIDList.toString(), delegationTimeList.toString(), chainList.toString(), usersList.get(i).getUserActivites().get(j).getFileName());
                                tbDetailedList.add(tbDetailed.toString() + "\n");
                            } else if (delegatedUserIDList.get(delegatedUserIDList.size() - 1).equals(delegatedUserIDList.get(delegatedOnlineResultIndex))) {
                                //do nothing
                                String dateString = sdf.format(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                                tb.addRow(String.valueOf(k), dateString, String.valueOf(usersList.get(i).getUserActivites().get(j).getOnlineFriendsList().size()), chainList.toString());
                                tbList.add(tb.toString() + "\n");
                                tbDetailed.addRow(String.valueOf(k), delegatedUserIDList.toString(), delegationTimeList.toString(), chainList.toString(), usersList.get(i).getUserActivites().get(j).getFileName());
                                tbDetailedList.add(tbDetailed.toString() + "\n");
                            } else {
                                //System.out.println("one of the older delegation come back = " + delegatedUserIDList.get(delegatedOnlineResultIndex).toString());
                                //resize the chain
                                //we have index of older delegated user
                                for (int l = delegatedUserIDList.size() - 1; l > delegatedOnlineResultIndex; l--) {
                                    delegatedUserIDList.remove(delegatedUserIDList.get(l));
                                    delegationTimeList.remove(delegationTimeList.get(l));
                                }
                                chainList.add(delegatedUserIDList.size());
                                String dateString = sdf.format(usersList.get(i).getUserActivites().get(j).getCurrentTimestamp());
                                tb.addRow(String.valueOf(k), dateString, String.valueOf(usersList.get(i).getUserActivites().get(j).getOnlineFriendsList().size()), chainList.toString());
                                tbList.add(tb.toString() + "\n");
                                tbDetailed.addRow(String.valueOf(k), delegatedUserIDList.toString(), delegationTimeList.toString(), chainList.toString(), usersList.get(i).getUserActivites().get(j).getFileName());
                                tbDetailedList.add(tbDetailed.toString() + "\n");
                            }

                        }
                        delegatedUserlistList.add(delegatedUserIDList);
                        delegatedUserTimeListList.add(delegationTimeList);
                        chainListList.add(chainList);
                        int latestChainDepth = chainList.get(chainList.size() - 1);
                        if (chainDurationsList.size() > latestChainDepth) {
                            for (int x = 0; x < chainDurationsList.size(); x++) {
                                if (chainDurationsList.get(x).getChainIndex() == latestChainDepth) {
                                    chainDurationsList.get(x).setChainDuration(chainDurationsList.get(x).getChainDuration() + 1);
                                }
                            }
                        } else {
                            eachChainDuration = new EachChainDuration();
                            eachChainDuration.setChainIndex(latestChainDepth);
                            eachChainDuration.setChainDuration(1);
                            chainDurationsList.add(eachChainDuration);
                        }
                    }
                    write.arrayListWrite(usersList, i, tbList, friendUserID, tbDetailedList, delegationType);
                }

                delegationInfo.setDelegatedUserIDList(delegatedUserlistList);
                delegationInfo.setDelegationTimeList(delegatedUserTimeListList);
                delegationInfo.setChainDepth(chainListList);
                delegationInfo.setTotalOfflineTime(totalOfflineTime);
                delegationInfo.setTotalOfflineCount(statusList.size());
                delegationInfo.setInterruptedSessionCount(interruptedSessionCount);
                delegationInfo.setInterruptTime(interruptionTime);
                delegationInfo.setChainDurationListsList(chainDurationsList);
                delegationInfoArrayList.add(delegationInfo);
                write.writeAllResult(delegationInfoArrayList, delegationArrayListCounter, friendUserID, usersList.get(i).getUserId(), delegationType, mostOnlineFriendsArrayList.get(i).getMostOnlineFriendsList().size());
                offlineIndex++;
                delegationArrayListCounter++;
            }
        }
    }
}