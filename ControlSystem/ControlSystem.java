package ControlSystem;

import Exceptions.*;

import java.time.LocalDate;
import java.util.Iterator;

public interface ControlSystem {

    /**
     * Regista um manager no sistema
     * @param username nome do manager a ser registado
     * @param job cargo manager
     * @param level nivel do manager
     * @throws UnknownJobPositionException
     * @throws UserAlreadyExistsException
     */
    void register(String username, Job job, int level) throws UnknownJobPositionException, UserAlreadyExistsException;

    /**
     * Regista um developer no sistema
     * @param username nome do developer a ser registado
     * @param manager nome do manager do developer
     * @param job cargo developer
     * @param level nivel do developer
     * @throws UnknownJobPositionException
     * @throws UserAlreadyExistsException
     * @throws ManagerDoesNotExistException
     */
    void register(String username, String manager, Job job, int level) throws UnknownJobPositionException, UserAlreadyExistsException, ManagerDoesNotExistException;

    /**
     * Cria um novo projeto In-House
     * @param id nome do projeto a ser criado
     * @param managerName nome do manager do projeto
     * @param keywords descricao do projeto
     * @param level nivel do projeto
     * @throws UnknownProjectTypeException
     * @throws ManagerDoesNotExistException
     * @throws ProjectAlreadyExistsException
     */
    void createProject(String id, String managerName, String keywords, int level) throws ManagerDoesNotExistException, ProjectAlreadyExistsException, UnderClearanceLevelException;

    /**
     * Cria um novo projeto Outsourced
     * @param id nome do projeto a ser criado
     * @param managerName nome do manager do projeto
     * @param keywords descricao do projeto
     * @param company companhia a qual o projeto pertence
     * @throws ManagerDoesNotExistException
     * @throws ProjectAlreadyExistsException
     */
    void createProject(String id, String managerName, String keywords, String company) throws ManagerDoesNotExistException, ProjectAlreadyExistsException;

    /**
     * Adiciona um membro a um projeto
     * @param managerName nome do manager do projeto
     * @param id id do projeto
     * @param member nome do membro a ser adicionado
     * @throws ManagerDoesNotExistException
     * @throws ProjectDoesNotExistException
     * @throws ProjectNotManagedByUserException
     * @throws MemberAlreadyInTeamException
     * @throws EmployeeDoesNotExistException
     * @throws InsufficientClearanceLevelException
     */
    void addEmployeeToProject(String managerName, String id, String member) throws ManagerDoesNotExistException, ProjectDoesNotExistException, ProjectNotManagedByUserException, MemberAlreadyInTeamException, EmployeeDoesNotExistException, InsufficientClearanceLevelException;

    /**
     * Adiciona um artefacto a um projeto
     * @param artefactName artefacto a ser adicionado
     * @param id id do projeto
     * @param date data da criacao do artefacto
     * @throws UserDoesNotExistException
     * @throws ProjectDoesNotExistException
     * @throws MemberNotInTeamException
     * @throws ArtefactAlreadyInProjectException
     * @throws ArtefactHasHigherLevelException
     * @throws EmployeeDoesNotExistException
     */
    void addArtefactToProject(Artefact artefactName, String id, LocalDate date) throws UserDoesNotExistException, ProjectDoesNotExistException, MemberNotInTeamException, ArtefactAlreadyInProjectException, ArtefactHasHigherLevelException, EmployeeDoesNotExistException;

    /**
     * Adiciona LinkedLists vazias na List inHousesByConfidentiality (util para o comando confidentiality)
     */
    void inHousesByConfidentialityInit();

    /**
     * Adiciona uma revisao a um dado artefacto num projeto
     * @param username nome do employee que fez a revisao
     * @param id id do projeto
     * @param artefactName nome do artefacto
     * @param date data da revisao
     * @param comment comentario da revisao
     * @return numero da revisao que acabou de ser criada
     * @throws UserDoesNotExistException
     * @throws ProjectDoesNotExistException
     * @throws ArtefactDoesNotExistException
     * @throws MemberNotInTeamException
     * @throws EmployeeDoesNotExistException
     */
    int addRevision(String username, String id, String artefactName, LocalDate date, String comment) throws UserDoesNotExistException, ProjectDoesNotExistException, ArtefactDoesNotExistException, MemberNotInTeamException, EmployeeDoesNotExistException;

    /**
     * Lista os employees do sistema
     * @return iterador com todos os employees do sistema
     * @throws EmptyUsersException
     */
    Iterator<Employee> listUsers() throws EmptyUsersException;

    /**
     * Lista os projetos do sistema
     * @return iterador com todos os projetos do sistema
     * @throws EmptyProjectsException
     */
    Iterator<Project> listProjects() throws EmptyProjectsException;

    /**
     * Obtem o employee com um dado nome
     * @param username nome do employee a ser pesquisado
     * @return employee com o dado nome
     * @throws EmployeeDoesNotExistException
     */
    Employee getEmployee(String username) throws EmployeeDoesNotExistException;

    /**
     * Obtem o manager com um dado nome
     * @param username nome do manager a ser pesquisado
     * @return manager com o dado nome
     * @throws ManagerDoesNotExistException
     */
    Manager getManager(String username) throws ManagerDoesNotExistException;

    /**
     * Obtem o enum Job a partir de uma string
     * @param job nome do job
     * @return enum Job
     * @throws UnknownJobPositionException
     */
    Job getJob(String job) throws UnknownJobPositionException;

    /**
     * Obtem o enum ProjectType a partir de uma string
     * @param projectTypeName nome do projectType
     * @return enum ProjectType
     */
    ProjectType getProjectType(String projectTypeName) throws UnknownProjectTypeException;

    /**
     * Obtem o projeto com um dado id
     * @param id id do projeto a ser pesquisado
     * @return projeto com o dado id
     * @throws ProjectDoesNotExistException
     */
    Project getProject(String id) throws ProjectDoesNotExistException;

    /**
     * Obtem o projeto In-House com um dado id
     * @param id id do projeto In-House a ser pesquisado
     * @return projeto In-House com o dado id
     * @throws ProjectDoesNotExistException
     */
    InHouse getInHouse(String id) throws ProjectDoesNotExistException;

    /**
     * Obtem o projeto In-House com um dado id (usado no comando projects, pois lanca mais uma excecao)
     * @param id id do projeto In-House a ser pesquisado
     * @return projeto In-House com o dado id
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    InHouse getProjectInfo(String id) throws ProjectDoesNotExistException, OutsourcedProjectException;

    /**
     * Verifica se um dado employee existe no sistema
     * @param username nome do employee a ser pesquisado
     * @return true se o employee existir no sistema / false caso contrario
     */
    boolean hasUser(String username);

    /**
     * Verifica se um dado manager existe no sistema
     * @param username nome do manager a ser pesquisado
     * @return true se o manager existir no sistema / false caso contrario
     */
    boolean hasManager(String username);

    /**
     * Verifica se um dado projeto existe no sistema
     * @param id id do projeto a ser pesquisado
     * @return true se o projeto existir no sistema / false caso contrario
     */
    boolean hasProject(String id);

    /**
     * Verifica se um projeto In-House existe no sistema
     * @param id id do projeto In-House a ser pesquisado
     * @return true se o projeto In-House existir no sistema / false caso contrario
     */
    boolean hasInHouse(String id);

    /**
     * Verifica se um dado projeto é Outsourced.
     * @param id id do projeto Outsourced a ser pesquisado
     * @return true se o projeto for Outsourced / false caso contrario
     * @throws ProjectDoesNotExistException
     */
    boolean isOutsourced(String id) throws ProjectDoesNotExistException;

    /**
     * Verifica se nenhum employee realizou pelo menos um update num artefacto
     * @return true se nenhum employee realizou um update / false caso contrario
     */
    boolean noUserMadeAnUpdate();

    /**
     * Verifica se os employees tem um ou mais projetos em comum
     * @return true se os employees tiverem um ou mais projetos em comum / false caso contrario
     */
    boolean hasCommonProjects();

    /**
     * Lista a equipa de um projeto
     * @param id id do projeto
     * @return iterador com todos os membros da equipa do projeto
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    Iterator<Employee> listTeamInProject(String id) throws ProjectDoesNotExistException, OutsourcedProjectException;

    /**
     * Lista os artefactos de um projeto
     * @param id id do projeto
     * @return iterador com todos os artefactos do projeto
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    Iterator<Artefact> listArtefactsInProject(String id) throws ProjectDoesNotExistException, OutsourcedProjectException;

    /**
     * Lista as revisoes de um artefacto
     * @param artefact artefacto
     * @return iterador com todas as revisoes do artefacto
     */
    Iterator<Revision> listRevisions(Artefact artefact);

    /**
     * Lista os developers que estao associados a um manager
     * @param username nome do manager
     * @return iterador com todos os developers associados ao manager
     * @throws ManagerDoesNotExistException
     */
    Iterator<Developer> listManagedDevelopers(String username) throws ManagerDoesNotExistException;

    /**
     * Lista os projetos In-House que contem uma keyword
     * @param keyword filtro keyword
     * @return iterador com todos os projetos In-House que contem a keyword
     * @throws NoProjectsWithKeywordException
     */
    Iterator<InHouse> listInHousesByKeyword(String keyword) throws NoProjectsWithKeywordException ;

    /**
     * Lista os projetos Outsourced que contem uma keyword
     * @param keyword filtro keyword
     * @return iterador com todos os projetos Outsourced que contem a keyword
     * @throws NoProjectsWithKeywordException
     */
    Iterator<Outsourced> listOutsourcedByKeyword(String keyword) throws NoProjectsWithKeywordException;

    /**
     * Lista os projetos In-House num dado intervalo de niveis de confidencialidade
     * @param lower limite inferior de confidencialidade
     * @param upper limite superior de confidencialidade
     * @return iterador com todos os projetos In-House no dado intervalo de niveis de confidencialidade
     * @throws NoProjectsWithinLevelsException
     */
    Iterator<InHouse> listByConfidentiality(int lower, int upper) throws NoProjectsWithinLevelsException;

    /**
     * Lista os 3 employees mais trabalhadores
     * @return iterador com os 3 employees mais trabalhadores
     * @throws NoWorkaholicsException
     */
    Iterator<Employee> listWorkaholics() throws NoWorkaholicsException;

    /**
     * Lista os 2 employees que tem mais projetos em comum
     * @return iterador com os 2 employees que tem mais projetos em comum
     * @throws NoCommonProjectsException
     */
    Iterator<Employee> listCommon() throws NoCommonProjectsException;
}
