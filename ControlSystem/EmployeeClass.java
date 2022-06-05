package ControlSystem;

import Exceptions.EmployeeHasNoRevisionsException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class EmployeeClass implements Employee {

    private static final String WRITE_DATE_FORMAT = "dd-MM-yyyy";

    private String username;
    private Job job;
    private int level;
    private Map<String, Project> projects;
    private List<Revision> revisions;

    /**
     * Construtor da class EmployeeClass
     * @param username nome do employee
     * @param job cargo manager / developer
     * @param level nivel de acesso do employee
     */
    public EmployeeClass(String username, Job job, int level) {
        this.username = username;
        this.job = job;
        this.level = level;
        this.projects = new HashMap<>();
        this.revisions = new LinkedList<>();
    }

    /**
     * Adiciona um projeto ao employee
     * @param project projeto a ser adicionado ao employee
     */
    @Override
    public void addProject(Project project) {
        projects.put(project.getId(), project);
    }

    /**
     * Adiciona uma revisao ao employee
     * @param revision revisao a ser adicionada
     */
    @Override
    public void addRevision(Revision revision) {
        revisions.add(revision);
    }

    /**
     * Obtem o nome do employee
     * @return nome do employee
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Obtem o numero de projetos onde o employee pertence
     * @return numero de projetos onde o employee pertence
     */
    @Override
    public int getNumProjects() {
        int sum = 0;
        for (Project project : projects.values()) {
            if (project.getManagerUsername().equals(username))
                sum++;
        }
        return projects.size() - sum;
    }

    /**
     * Obtem o nivel de acesso do employee
     * @return nivel de acesso do employee
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Obtem o numero de revisoes do employee
     * @return numero de revisoes do employee
     */
    @Override
    public int getUpdates() {
        return revisions.size();
    }

    /**
     * Obtem o numero de projetos comuns que este employee tem com outro
     * @param employee nome do outro employee a ser comparado com este
     * @return numero de projetos comuns que este employee tem com o outro
     */
    @Override
    public int getCommonProjectsNum(Employee employee) {
        int sum = 0;
        String username = employee.getUsername();

        if (this.username.equals(username))
            return 0;

        for (Project project : projects.values()) {
            if (project instanceof InHouse) {
                InHouse inHouse = ((InHouse) project);
                if (inHouse.hasEmployee(username))
                    sum++;
            }
        }
        return sum;
    }

    /**
     * Lista as revisoes do employee
     * @return iterador com todas as revisoes do employee
     */
    @Override
    public Iterator<Revision> listRevisions() {
        revisions.sort(new SortRevisionManages());
        return revisions.iterator();
    }

    /**
     * Obtem a data da ultima revisao efetuada pelo employee
     * @return data da ultima revisao efetuada pelo employee
     */
    @Override
    public LocalDate getLastDate() {
        LocalDate date = null;
        for (Revision revision : revisions) {
            LocalDate revisionDate = revision.getDate();
            if (date == null)
                date = revisionDate;
            if (date.isBefore(revision.getDate()))
                date = revisionDate;
        }
        return date;
    }

    /**
     * Obtem a data da ultima revisao efetuada pelo employee formatada
     * @return data da ultima revisao efetuada pelo employee formatada
     */
    @Override
    public String getLastDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
        return formatter.format(getLastDate());
    }
}
