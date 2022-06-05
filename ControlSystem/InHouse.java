package ControlSystem;

import Exceptions.ArtefactAlreadyInProjectException;
import Exceptions.ArtefactDoesNotExistException;
import Exceptions.MemberAlreadyInTeamException;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.SortedSet;

public interface InHouse extends Project {

    /**
     * Adiciona um empregado na equipa do projeto
     * @param employee empregado a ser adicionado
     * @throws MemberAlreadyInTeamException
     */
    void addEmployee(Employee employee) throws MemberAlreadyInTeamException;

    /**
     * Adiciona um artefacto ao projeto
     * @param artefact artefacto a ser adicionado
     * @throws ArtefactAlreadyInProjectException
     */
    void addArtefact(Artefact artefact) throws ArtefactAlreadyInProjectException;

    /**
     * Adiciona uma revisao ao projeto
     * @param revision revisao a ser adicionada ao projeto
     */
    void addRevision(Revision revision);

    /**
     * Obtem as keywords do projeto formatadas para o comando confidentiality
     * @return string com as keywords do projeto formatadas para o comando confidentiality
     */
    String getKeywordsFormatted();

    /**
     * Reordena os artefactos (util para o comando projects)
     * @return conjunto ordenado com todos os artefactos do projetos reordenados
     */
    SortedSet<Artefact> sortArtefacts();
    /**
     * Obtem o nivel de acesso do projeto
     * @return nivel de acesso do projeto
     */
    int getLevel();

    /**
     * Obtem o numero de membros na equipa do projeto
     * @return numero de membros na equipa do projeto
     */
    int getNumEmployees();

    /**
     * Obtem o numero de artefactos do projeto
     * @return numero de artefactos do projeto
     */
    int getNumArtefacts();

    /**
     * Obtem o numero de revisoes totais do projeto
     * @return numero de revisoes totais do projeto
     */
    int getNumRevisions();

    /**
     * Verifica se um dado membro existe na equipa do projeto
     * @param name nome do membro a pesquisar
     * @return true se o membro pertencer a equipa do projeto / false caso contrario
     */
    boolean hasEmployee(String name);

    /**
     * Verifica se um dado artefacto existe no projeto
     * @param name nome do artefacto a pesquisar
     * @return true se o artefacto existir no projeto / false caso contrario
     */
    boolean hasArtefact(String name);

    /**
     * Obtem o artefacto com um dado nome
     * @param name nome do artefacto
     * @return artefacto com um dado nome
     */
    Artefact getArtefact(String name) throws ArtefactDoesNotExistException;

    /**
     * Lista a equipa do projeto
     * @return iterador com todos os membros da equipa do projeto
     */
    Iterator<Employee> listTeam();

    /**
     * Lista os artefactos do projeto
     * @return iterador com todos os artefactos do projeto
     */
    Iterator<Artefact> listArtefacts();

    /**
     * Obtem a data da revisao mais recente do projeto
     * @return data da revisao mais recente do projeto
     */
    LocalDate getRecentDateRevision();

    /**
     * Obtem a data da revisao mais recente do projeto formatada
     * @return string com a data da revisao mais recente do projeto formatada
     */
    String getRecentDateRevisionAsString();
}
