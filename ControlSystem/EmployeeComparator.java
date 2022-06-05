package ControlSystem;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        if (e1.getUpdates() > e2.getUpdates())
            return -1;
        else if (e1.getUpdates() < e2.getUpdates())
            return 1;
        else {
            if (e1.getNumProjects() > e2.getNumProjects())
                return -1;
            else if (e1.getNumProjects() < e2.getNumProjects())
                return 1;
            else {
                if (e1.getLastDate().isAfter(e2.getLastDate()))
                    return -1;
                else if (e1.getLastDate().isBefore(e2.getLastDate()))
                    return 1;
                else {
                    return e1.getUsername().compareTo(e2.getUsername());
                }
            }
        }
    }
}
