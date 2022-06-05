package ControlSystem;

import java.util.Comparator;

public class OutsourcedByKeywordComparator implements Comparator<Outsourced> {
    @Override
    public int compare(Outsourced o1, Outsourced o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
