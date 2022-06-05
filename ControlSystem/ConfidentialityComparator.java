package ControlSystem;

import java.util.Comparator;

public class ConfidentialityComparator implements Comparator<InHouse> {
    @Override
    public int compare(InHouse p1, InHouse p2) {
        return p1.getId().compareTo(p2.getId());
    }
}
