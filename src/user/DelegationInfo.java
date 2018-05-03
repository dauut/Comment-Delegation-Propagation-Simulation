package user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class DelegationInfo {
    private long userId;
    private int totalOfflineTime;
    private int totalOfflineCount;
    private int interruptedSessionCount;
    private int interruptTime;
    private ArrayList<ArrayList<Integer>> chainDepth;
    private ArrayList<ArrayList<Long>> delegatedUserIDList;
    private ArrayList<ArrayList<Date>> delegationTimeList;
    private ArrayList<EachChainDuration> chainDurationListsList;
    private ArrayList<HashSet<Long>> chainLengthAndUsers;

    public ArrayList<HashSet<Long>> getChainLengthAndUsers() {
        return chainLengthAndUsers;
    }

    public void setChainLengthAndUsers(ArrayList<HashSet<Long>> chainLengthAndUsers) {
        this.chainLengthAndUsers = chainLengthAndUsers;
    }

    public int getInterruptTime() {
        return interruptTime;
    }

    public ArrayList<EachChainDuration> getChainDurationListsList() {
        return chainDurationListsList;
    }

    public void setChainDurationListsList(ArrayList<EachChainDuration> chainDurationListsList) {
        this.chainDurationListsList = chainDurationListsList;
    }

    public void setInterruptTime(int interruptTime) {
        this.interruptTime = interruptTime;
    }

    public int getInterruptedSessionCount() {
        return interruptedSessionCount;
    }

    public void setInterruptedSessionCount(int interruptedSessionCount) {
        this.interruptedSessionCount = interruptedSessionCount;
    }

    public int getTotalOfflineCount() {
        return totalOfflineCount;
    }

    public void setTotalOfflineCount(int totalOfflineCount) {
        this.totalOfflineCount = totalOfflineCount;
    }

    public int getTotalOfflineTime() {
        return totalOfflineTime;
    }

    public void setTotalOfflineTime(int totalOfflineTime) {
        this.totalOfflineTime = totalOfflineTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<ArrayList<Integer>> getChainDepth() {
        return chainDepth;
    }

    public void setChainDepth(ArrayList<ArrayList<Integer>> chainDepth) {
        this.chainDepth = chainDepth;
    }

    public ArrayList<ArrayList<Long>> getDelegatedUserIDList() {
        return delegatedUserIDList;
    }

    public void setDelegatedUserIDList(ArrayList<ArrayList<Long>> delegatedUserIDList) {
        this.delegatedUserIDList = delegatedUserIDList;
    }

    public ArrayList<ArrayList<Date>> getDelegationTimeList() {
        return delegationTimeList;
    }

    public void setDelegationTimeList(ArrayList<ArrayList<Date>> delegationTimeList) {
        this.delegationTimeList = delegationTimeList;
    }
}
