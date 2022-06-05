package ControlSystem;

public interface Developer extends Employee, Comparable<Developer> {

    /**
     * Obtem o nome do manager
     * @return nome do manager
     */
    String getManager();
}
