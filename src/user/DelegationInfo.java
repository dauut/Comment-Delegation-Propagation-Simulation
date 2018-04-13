package user;

import java.util.ArrayList;
import java.util.Date;

public class DelegationInfo {
    private long userId;
    private long chainDepth;
    private ArrayList<Long> delegatedUserIDList;
    private ArrayList<Date> delegationTimeList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChainDepth() {
        return chainDepth;
    }

    public void setChainDepth(long chainDepth) {
        this.chainDepth = chainDepth;
    }

    public ArrayList<Long> getDelegatedUserIDList() {
        return delegatedUserIDList;
    }

    public void setDelegatedUserIDList(ArrayList<Long> delegatedUserIDList) {
        this.delegatedUserIDList = delegatedUserIDList;
    }

    public ArrayList<Date> getDelegationTimeList() {
        return delegationTimeList;
    }

    public void setDelegationTimeList(ArrayList<Date> delegationTimeList) {
        this.delegationTimeList = delegationTimeList;
    }
}
