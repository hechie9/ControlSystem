package ControlSystem;

import java.util.*;

public class ManagerClass extends EmployeeClass implements Manager {

    private SortedSet<Developer> developers;
    private Map<String, Project> managedProjects;

    /**
     * Construtor da class ManagerClass
     * @param username nome do manager
     * @param job cargo manager
     * @param level nivel de acesso do manager
     */
    public ManagerClass(String username, Job job, int level) {
        super(username, job, level);
        this.developers = new TreeSet<>();
        this.managedProjects = new HashMap<>();
    }

    /**
     * Adiciona um developer ao manager
     * @param developer developer a ser adicionado
     */
    @Override
    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }

    /**
     * Adiciona um projeto ao manager gerido por ele
     * @param project projeto a ser adicionado
     */
    @Override
    public void addManagedProject(Project project) {
        managedProjects.put(project.getId(), project);
    }

    /**
     * Obtem o numero de developers em equipas geridas pelo manager
     * @return numero de developers em equipas geridas pelo manager
     */
    @Override
    public int getNumDevelopers() {
        return developers.size();
    }

    /**
     * Obtem o numero de projetos geridos pelo manager
     * @return numero de projetos geridos pelo manager
     */
    @Override
    public int getNumManagedProjects() {
        return managedProjects.size();
    }

    /**
     * Verifica se um dado developer esta associado ao manager
     * @param name nome do developer a ser pesquisado
     * @return true se o developer estiver associado ao manager / false caso contrario
     */
    @Override
    public boolean hasDeveloper(String name) {
        for (Developer d : developers) {
            if (d.getUsername().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasManagedProject(String id) {
        for (Project project : managedProjects.values()) {
            if (project.getId().equals(id))
                return true;
        }
        return false;
    }

    /**
     * Lista os developers associados ao manager
     * @return iterador com todos os developers associados ao manager
     */
    @Override
    public Iterator<Developer> listDevelopers() {
        return developers.iterator();
    }
}
