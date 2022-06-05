package ControlSystem;

import java.util.Iterator;

public interface Manager extends Employee {

    /**
     * Adiciona um developer ao manager
     * @param developer developer a ser adicionado
     */
    void addDeveloper(Developer developer);

    /**
     * Adiciona um projeto ao manager gerido por ele
     * @param project projeto a ser adicionado
     */
    void addManagedProject(Project project);

    /**
     * Obtem o numero de developers em equipas geridas pelo manager
     * @return numero de developers em equipas geridas pelo manager
     */
    int getNumDevelopers();

    /**
     * Obtem o numero de projetos geridos pelo manager
     * @return numero de projetos geridos pelo manager
     */
    int getNumManagedProjects();

    /**
     * Verifica se um dado developer esta associado ao manager
     * @param name nome do developer a ser pesquisado
     * @return true se o developer estiver associado ao manager / false caso contrario
     */
    boolean hasDeveloper(String name);

    boolean hasManagedProject(String id);

    /**
     * Lista os developers associados ao manager
     * @return iterador com todos os developers associados ao manager
     */
    Iterator<Developer> listDevelopers();
}
