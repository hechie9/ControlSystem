package ControlSystem;

import java.util.Comparator;

public class InHousesByKeywordComparator implements Comparator<InHouse> {
    @Override
    public int compare(InHouse p1, InHouse p2) {
        if (p1.getRecentDateRevision().isAfter(p2.getRecentDateRevision()))
            return -1;
        else if (p1.getRecentDateRevision().isBefore(p2.getRecentDateRevision()))
            return 1;
        else {
            if (p1.getNumRevisions() > p2.getNumRevisions())
                return -1;
            else if (p1.getNumRevisions() < p2.getNumRevisions())
                return 1;
            else {
                return p1.getId().compareTo(p2.getId());
            }
        }
    }
}
