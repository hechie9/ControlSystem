package ControlSystem;

import java.util.Comparator;

public class SortRevisionManages implements Comparator<Revision> {
    @Override
    public int compare(Revision r1, Revision r2) {
        if (r1.getDate().isAfter(r2.getDate()))
            return -1;
        else if (r1.getDate().isBefore(r2.getDate()))
            return 1;
        else {
            if (r1.getNumber() > r2.getNumber())
                return -1;
            else if (r1.getNumber() < r2.getNumber())
                return 1;
            else {
                return r1.getProject().compareTo(r2.getProject());
            }
        }
    }
}
