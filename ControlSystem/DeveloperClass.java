package ControlSystem;

import java.util.LinkedList;
import java.util.List;

public class DeveloperClass extends EmployeeClass implements Developer {

    private Manager manager;

    /**
     * Construtor da class DeveloperClass
     * @param username nome do developer
     * @param manager nome do manager
     * @param job cargo developer
     * @param level nivel de acesso do developer
     */
    public DeveloperClass(String username, Manager manager, Job job, int level) {
        super(username, job, level);
        this.manager = manager;
    }

    /**
     * Obtem o nome do manager
     * @return nome do manager
     */
    @Override
    public String getManager() {
        return manager.getUsername();
    }

    /**
     * Compara dois developers ('this' e developer)
     * @param developer developer a ser comparado com 'this'
     * @return -1 se o nome de 'this' for predecessor do nome de developer / 1 se o nome de 'this' for sucessor do nome de developer / 0 se 'this' for igual a developer (mesmo nome)
     */
    @Override
    public int compareTo(Developer developer) {
        return this.getUsername().compareTo(developer.getUsername());
    }
}
