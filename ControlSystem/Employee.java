package ControlSystem;

import Exceptions.EmployeeHasNoRevisionsException;

import java.time.LocalDate;
import java.util.Iterator;

public interface Employee {

    /**
     * Adiciona um projeto ao employee
     * @param project projeto a ser adicionado ao employee
     */
    void addProject(Project project);

    /**
     * Adiciona uma revisao ao employee
     * @param revision revisao a ser adicionada
     */
    void addRevision(Revision revision);

    /**
     * Obtem o nome do employee
     * @return nome do employee
     */
    String getUsername();

    /**
     * Obtem o numero de projetos onde o employee pertence
     * @return numero de projetos onde o employee pertence
     */
    int getNumProjects();

    /**
     * Obtem o nivel de acesso do employee
     * @return nivel de acesso do employee
     */
    int getLevel();

    /**
     * Obtem o numero de revisoes do employee
     * @return numero de revisoes do employee
     */
    int getUpdates();

    /**
     * Obtem o numero de projetos comuns que este employee tem com outro
     * @param employee nome do outro employee a ser comparado com este
     * @return numero de projetos comuns que este employee tem com o outro
     */
    int getCommonProjectsNum(Employee employee);

    /**
     * Lista as revisoes do employee
     * @return iterador com todas as revisoes do employee
     */
    Iterator<Revision> listRevisions();

    /**
     * Obtem a data da ultima revisao efetuada pelo employee
     * @return data da ultima revisao efetuada pelo employee
     */
    LocalDate getLastDate();

    /**
     * Obtem a data da ultima revisao efetuada pelo employee formatada
     * @return data da ultima revisao efetuada pelo employee formatada
     */
    String getLastDateFormatted();
}
