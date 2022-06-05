package ControlSystem;

import Exceptions.ArtefactAlreadyInProjectException;
import Exceptions.ArtefactDoesNotExistException;
import Exceptions.MemberAlreadyInTeamException;
import Exceptions.UserAlreadyExistsException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InHouseClass extends ProjectClass implements InHouse {

    private static final String WRITE_DATE_FORMAT = "dd-MM-yyyy";

    private int level;
    // mudei esta colecao para o comando team (provavelmente mudar mais tarde)
    private Map<String, Employee> team;
    // mudei esta colecao para o comando project (provavelmente mudar mais tarde)
    private SortedSet<Artefact> artefacts;
    // util para o comando keyword
    private List<Revision> revisions;

    /**
     * Construtor da class InHouseClass
     * @param id id do projeto
     * @param manager nome do manager do projeto
     * @param keywords descricao do projeto
     * @param level nivel de acesso do projeto
     */
    public InHouseClass(String id, Manager manager, String keywords, int level) {
        super(id, manager, keywords);
        this.level = level;
        this.team = new LinkedHashMap<>();
        this.artefacts = new TreeSet<>();
        this.revisions = new LinkedList<>();
    }

    /**
     * Adiciona um empregado na equipa do projeto
     * @param employee empregado a ser adicionado
     * @throws MemberAlreadyInTeamException
     */
    @Override
    public void addEmployee(Employee employee) throws MemberAlreadyInTeamException {
        String username = employee.getUsername();
        if (team.containsKey(username))
            throw new MemberAlreadyInTeamException(username);
        team.put(username, employee);
    }

    /**
     * Adiciona um artefacto ao projeto
     * @param artefact artefacto a ser adicionado
     * @throws ArtefactAlreadyInProjectException
     */
    @Override
    public void addArtefact(Artefact artefact) throws ArtefactAlreadyInProjectException {
        if (hasArtefact(artefact.getName()))
            throw new ArtefactAlreadyInProjectException(artefact.getName());
        artefacts.add(artefact);
    }

    /**
     * Adiciona uma revisao ao projeto
     * @param revision revisao a ser adicionada ao projeto
     */
    @Override
    public void addRevision(Revision revision) {
        revisions.add(revision);
    }

    /**
     * Obtem as keywords do projeto formatadas para o comando confidentiality
     * @return string com as keywords do projeto formatadas para o comando confidentiality
     */
    @Override
    public String getKeywordsFormatted() {
        String keywordsFormatted = getKeywords();
        keywordsFormatted = keywordsFormatted.replaceAll(" ", ", ");
        keywordsFormatted += ".";
        return keywordsFormatted;
    }

    /**
     * Reordena os artefactos (util para o comando projects)
     * @return conjunto ordenado com todos os artefactos do projetos reordenados
     */
    @Override
    public SortedSet<Artefact> sortArtefacts() {
        SortedSet<Artefact> aux = new TreeSet<>();
        for (Artefact a : artefacts)
            aux.add(a);
        return aux;
    }

    /**
     * Obtem o nivel de acesso do projeto
     * @return nivel de acesso do projeto
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Obtem o numero de membros na equipa do projeto
     * @return numero de membros na equipa do projeto
     */
    @Override
    public int getNumEmployees() {
        return team.size();
    }

    /**
     * Obtem o numero de artefactos do projeto
     * @return numero de artefactos do projeto
     */
    @Override
    public int getNumArtefacts() {
        return artefacts.size();
    }

    /**
     * Obtem o numero de revisoes totais do projeto
     * @return numero de revisoes totais do projeto
     */
    @Override
    public int getNumRevisions() {
        int sum = 0;
        for (Artefact artefact : artefacts) {
            sum += artefact.getNumRevisions();
        }
        return sum;
    }

    /**
     * Verifica se um dado membro existe na equipa do projeto
     * @param name nome do membro a pesquisar
     * @return true se o membro pertencer a equipa do projeto / false caso contrario
     */
    @Override
    public boolean hasEmployee(String name) {
        return team.containsKey(name);
    }

    /**
     * Verifica se um dado artefacto existe no projeto
     * @param name nome do artefacto a pesquisar
     * @return true se o artefacto existir no projeto / false caso contrario
     */
    @Override
    public boolean hasArtefact(String name) {
        for (Artefact a : artefacts) {
            if (a.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * Obtem o artefacto com um dado nome
     * @param name nome do artefacto
     * @return artefacto com um dado nome
     */
    @Override
    public Artefact getArtefact(String name) throws ArtefactDoesNotExistException {
        for (Artefact a : artefacts) {
            if (a.getName().equals(name))
                return a;
        }
        throw new ArtefactDoesNotExistException(name);
    }

    /**
     * Lista a equipa do projeto
     * @return iterador com todos os membros da equipa do projeto
     */
    @Override
    public Iterator<Employee> listTeam() {
        return team.values().iterator();
    }

    /**
     * Lista os artefactos do projeto
     * @return iterador com todos os artefactos do projeto
     */
    @Override
    public Iterator<Artefact> listArtefacts() {
        return sortArtefacts().iterator();
    }

    /**
     * Obtem a data da revisao mais recente do projeto
     * @return data da revisao mais recente do projeto
     */
    @Override
    public LocalDate getRecentDateRevision() {

        LocalDate date = null;

        for (Revision r : revisions) {
            if (date == null)
                date = r.getDate();
            else if (r.getDate().isAfter(date)) {
                date = r.getDate();
            }
        }

        return date;
    }

    /**
     * Obtem a data da revisao mais recente do projeto formatada
     * @return string com a data da revisao mais recente do projeto formatada
     */
    @Override
    public String getRecentDateRevisionAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
        return formatter.format(getRecentDateRevision());
    }
}
