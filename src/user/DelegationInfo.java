package user;

import java.util.ArrayList;
import java.util.Date;

public class DelegationInfo {
    private long userId;
    private long chainDepth;
    private ArrayList<DelegationTimeAndUsers> delegationTimeAndUsersList = new ArrayList<DelegationTimeAndUsers>();

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

    public ArrayList<DelegationTimeAndUsers> getDelegationTimeAndUsersList() {
        return delegationTimeAndUsersList;
    }

    public void setDelegationTimeAndUsersList(ArrayList<DelegationTimeAndUsers> delegationTimeAndUsersList) {
        this.delegationTimeAndUsersList = delegationTimeAndUsersList;
    }
}
