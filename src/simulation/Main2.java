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
//        mostOnlineFriendsArrayList = findMostOnlineFriends.findMostOnlineFriendsList(usersList);
//        mostDisjointFriendsArrayList = findMostDisjointFriends.findMostDisjointFriendsList(usersList, offlineStatusStructuresList);

        /*create necessary folders auto
         * delete folders manually*/
        WriteFiles writeFiles = new WriteFiles();
//        writeFiles.createFolder(Constants.getRandomDelegation());
        writeFiles.createFolder(Constants.getMostOnlineFriendDelegation());
        writeFiles.createFolder(Constants.getMostDisjointFriendDelegation());
//        writeFiles.createFolder(Constants.getIdealFriendDelegation());
//        writeFiles.createFolder(Constants.getOptimizedMostDisjointFriendDelegation());

        /*start simulation*/
//        System.out.println(Constants.getRandomDelegation() + " session is started !!");
//        main.randomUserDelegationSimulation(Constants.getRandomDelegation(), usersList, offlineStatusStructuresList,
//                mostOnlineFriendsArrayList, mostDisjointFriendsArrayList);
//        System.out.println(Constants.getMostOnlineFriendDelegation() + " session is started !!");
//        main.randomUserDelegationSimulation(Constants.getMostOnlineFriendDelegation(), usersList, offlineStatusStructuresList);
        System.out.println(Constants.getMostDisjointFriendDelegation() + " session is started !!");
        main.randomUserDelegationSimulation(Constants.getMostDisjointFriendDelegation(), usersList, offlineStatusStructuresList);
//        System.out.println(Constants.getIdealFriendDelegation() + " session is started !!");
//        main.randomUserDelegationSimulation(Constants.getIdealFriendDelegation(), usersList, offlineStatusStructuresList);
//        System.out.println(Constants.getOptimizedMostDisjointFriendDelegation() + " session is started !!");
//        main.randomUserDelegationSimulation(Constants.getOptimizedMostDisjointFriendDelegation(), usersList, offlineStatusStructuresList,
//                mostOnlineFriendsArrayList, mostDisjointFriendsArrayList);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("seconds = " + seconds);
        System.out.println("minutes = " + seconds / 60);
        System.out.println("hours = " + seconds / 3600);

        String totalRuntime = "Total Runtime: \n" + "\tSeconds = " + String.valueOf(seconds) + "\n"
                + "\tMinutes = " + String.valueOf(seconds / 60) + "\n"
                + "\tHours = " + String.valueOf(seconds / 3600);
        File file = new File("C:\\Users\\dauut\\Desktop\\testout\\runtimeInformation.txt");
        writeFiles.writeFile(file, totalRuntime);
    }

    //    private void randomUserDelegationSimulation(String delegationType, ArrayList<UserInformations> usersList,
//                                                ArrayList<OfflineStatusStructure> offlineStatusStructuresList,
//                                                ArrayList<MostOnlineFriends> mostOnlineFriendsArrayList,
//                                                ArrayList<MostDisjointFriends> mostDisjointFriendsArrayList) {
    private void randomUserDelegationSimulation(String delegationType, ArrayList<UserInformations> usersList,
                                                ArrayList<OfflineStatusStructure> offlineStatusStructuresList) {
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
        ArrayList<HashSet<Long>> chainLengthAndUsers;
        EachChainDuration eachChainDuration;
        long delegatedUserID;
        HashSet<Long> totalOnlineFriendsInCurrentOfflineSession;
        FindMostDisjointFriends findMostDisjointFriends = new FindMostDisjointFriends();
        FindMostOnlineFriends findMostOnlineFriends = new FindMostOnlineFriends();

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
                chainListList = new ArrayList<>();
                chainDurationsList = new ArrayList<>();
                eachChainDuration = new EachChainDuration();
                chainLengthAndUsers = new ArrayList<>();
                HashSet<Long> tmpFriendsInChainDepth = new HashSet<>();
                totalOnlineFriendsInCurrentOfflineSession = new HashSet<>();
                eachChainDuration.setChainIndex(0);// first index intentionally filled up
                eachChainDuration.setChainDuration(0);//because we will keep every chain size duration in their indexes
                chainDurationsList.add(eachChainDuration);
                chainLengthAndUsers.add(tmpFriendsInChainDepth);

                eachChainDuration = new EachChainDuration();
                eachChainDuration.setChainIndex(1);
                eachChainDuration.setChainDuration(1);
                chainDurationsList.add(eachChainDuration); //in this point we have chain size 1 for 1 minute
                tmpFriendsInChainDepth = new HashSet<>();
                tmpFriendsInChainDepth.addAll(usersList.get(i).getUserActivites().get(0).getOnlineFriendsHashSet()); //first minute
                chainLengthAndUsers.add(tmpFriendsInChainDepth);

                ArrayList<Long> tmpCurrentMostDisjointList = new ArrayList<>();

                long friendUserID = offlineStatusStructuresList.get(offlineIndex).getFriendUserID();
                int interruptedSessionCount = 0;
                int interruptionTime = 0;
                int totalOfflineTime = 0;

                statusList = offlineStatusStructuresList.get(offlineIndex).getStatustList();
                //optimizedMostDisjointFriends = findMostDisjointFriends.findOptimizedMostDisjointFriends(usersList, i, statusList, friendUserID);
                ArrayList<Long> currentIdealList = new ArrayList<>();
                for (int k = 0; k < statusList.size() - 1; k++) {
                    if (delegationType.equals(Constants.getIdealFriendDelegation())) {
                        currentIdealList = new ArrayList<>(findMostOnlineFriends.mostOnlineFriendsIdealCase(usersList, i, statusList.get(k)));
                    }
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
//                        delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), mostOnlineFriendsArrayList.get(i));
                        ArrayList<Long> currentMostOnlineFriendsList = new ArrayList<>(findMostOnlineFriends.getMostOnlineFriendLast5Days(usersList, i, statusList.get(k)[0]));
                        delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUserLast5Days(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), currentMostOnlineFriendsList);
                    } else if (delegationType.equals(Constants.getMostDisjointFriendDelegation())) { //disjoint
                        ArrayList<Long> currentMostDisjointFriendsList;
                        if (findMostDisjointFriends.getMostDisjointFriendsLast5Days(usersList, i, statusList, statusList.get(k)[0]) == null) {
                            currentMostDisjointFriendsList = new ArrayList<>(findMostOnlineFriends.getMostOnlineFriendLast5Days(usersList, i, statusList.get(k)[0]));
                            delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser5Days(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), currentMostDisjointFriendsList);
//                            System.out.println("5 gunluk data yok !! ilk atama icin 5 gunluk most online data kullanildi");
                        } else {
                            currentMostDisjointFriendsList = new ArrayList<>(findMostDisjointFriends.getMostDisjointFriendsLast5Days(usersList, i, statusList, statusList.get(k)[0]));
                            delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser5Days(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), currentMostDisjointFriendsList);
                        }
                        tmpCurrentMostDisjointList = currentMostDisjointFriendsList;
                    } else if (delegationType.equals(Constants.getIdealFriendDelegation())) {
                        delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUserLast5Days(usersList.get(i).getUserActivites().get(statusList.get(k)[0]), currentIdealList);
                    } else {
                        delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(statusList.get(k)[0]));
                    }

                    //System.out.println("First delegation = " + delegatedUserID);
                    delegatedUserIDList.add(delegatedUserID);
                    delegationTimeList.add(usersList.get(i).getUserActivites().get(statusList.get(k)[0]).getCurrentTimestamp());
                    chainList.add(delegatedUserIDList.size());

                    //move forward through time intervals
                    for (int j = statusList.get(k)[0] + 1; j < statusList.get(k)[1]; j++) {
                        //collect all online friends during simulation for that user
                        totalOnlineFriendsInCurrentOfflineSession.addAll(usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet());
                        tb = new TableBuilder();
                        tbDetailed = new TableBuilder();
                        tmpFriendsInChainDepth = new HashSet<>();
                        tmpFriendsInChainDepth.addAll(usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet());
                        totalOfflineTime++;
                        if (usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet().size() == 0) {
                            //System.out.println("There is no user for delegation, end this session");
                            int tmp = j; // for calculate total off time it is important
                            while (usersList.get(i).getUserActivites().get(j).getOnlineFriendsHashSet().size() == 0) {
                                interruptionTime++;
                                j++;
                            }
                            interruptionTime = interruptionTime + statusList.get(k)[1] - j;
                            totalOfflineTime = totalOfflineTime + statusList.get(k)[1] - tmp;
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
                                    //delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUser(usersList.get(i).getUserActivites().get(j), mostOnlineFriendsArrayList.get(i));
                                    ArrayList<Long> currentMostOnlineFriendsList = new ArrayList<>(findMostOnlineFriends.getMostOnlineFriendLast5Days(usersList, i, j));
                                    delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUserLast5Days(usersList.get(i).getUserActivites().get(j), currentMostOnlineFriendsList);
                                } else if (delegationType.equals(Constants.getMostDisjointFriendDelegation())) {

                                    if (findMostDisjointFriends.getMostDisjointFriendsLast5Days(usersList, i, statusList, j) == null) {
                                        ArrayList<Long> currentMostDisjointFriendsList = tmpCurrentMostDisjointList;
                                        delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser5Days(usersList.get(i).getUserActivites().get(j), currentMostDisjointFriendsList);
//                                        System.out.println("j= "+j +  "indisi icin 5 gunluk geriye most disjoint yok. Bir onceki atandi");
                                    } else {
                                        ArrayList<Long> currentMostDisjointFriendsList = new ArrayList<>(findMostDisjointFriends.getMostDisjointFriendsLast5Days(usersList, i, statusList, j));
                                        delegatedUserID = pickUser.findAndPickMostDisjointedFriendsAsDelegatedUser5Days(usersList.get(i).getUserActivites().get(j), currentMostDisjointFriendsList);
                                    }
                                } else if (delegationType.equals(Constants.getIdealFriendDelegation())) {
                                    delegatedUserID = pickUser.findAndPickMostOnlineFriendsAsDelegatedUserLast5Days(usersList.get(i).getUserActivites().get(j), currentIdealList);
                                } else {
                                    delegatedUserID = pickUser.findRandomDelegation(usersList.get(i).getUserActivites().get(j));
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
                        int latestChainDepth = chainList.get(chainList.size() - 1); //last element is current chain size
                        if (chainDurationsList.size() > latestChainDepth) {
                            chainLengthAndUsers.get(latestChainDepth).addAll(tmpFriendsInChainDepth);
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
                            chainLengthAndUsers.add(tmpFriendsInChainDepth);
                        }
                    }
                    //this lines create minute by minute logs.
                    //write.arrayListWrite(usersList, i, tbList, friendUserID, tbDetailedList, delegationType);
                }

                delegationInfo.setDelegatedUserIDList(delegatedUserlistList);
                delegationInfo.setDelegationTimeList(delegatedUserTimeListList);
                delegationInfo.setChainDepth(chainListList);
                delegationInfo.setTotalOfflineTime(totalOfflineTime);
                delegationInfo.setTotalOfflineCount(statusList.size());
                delegationInfo.setInterruptedSessionCount(interruptedSessionCount);
                delegationInfo.setInterruptTime(interruptionTime);
                delegationInfo.setChainDurationListsList(chainDurationsList);
                delegationInfo.setChainLengthAndUsers(chainLengthAndUsers);
                delegationInfoArrayList.add(delegationInfo);
                write.writeAllResult(delegationInfoArrayList, delegationArrayListCounter, friendUserID, usersList.get(i).getUserId(), delegationType, totalOnlineFriendsInCurrentOfflineSession.size());
                offlineIndex++;
                delegationArrayListCounter++;
            }
        }
    }
}