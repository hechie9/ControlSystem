package ControlSystem;

import Exceptions.*;

import java.time.LocalDate;
import java.util.*;

public class ControlSystemClass implements ControlSystem {

    private SortedMap<String, Employee> users;
    private Map<String, Project> projects;
    private Map<String, List<InHouse>> inHousesByKeyword;
    private Map<String, List<Outsourced>> outsourcedByKeyword;
    private List<List<InHouse>> inHousesByConfidentiality;

    /**
     * Construtor da class ControlSystemClass
     */
    public ControlSystemClass() {
        this.users = new TreeMap<>();
        this.projects = new LinkedHashMap<>();
        this.inHousesByKeyword = new HashMap<>();
        this.outsourcedByKeyword = new HashMap<>();
        this.inHousesByConfidentiality = new LinkedList<>();
        inHousesByConfidentialityInit();
    }

    /**
     * Regista um manager no sistema
     * @param username nome do manager a ser registado
     * @param job cargo manager
     * @param level nivel do manager
     * @throws UnknownJobPositionException
     * @throws UserAlreadyExistsException
     */
    @Override
    public void register(String username, Job job, int level) throws UnknownJobPositionException, UserAlreadyExistsException {
        if (hasUser(username))
            throw new UserAlreadyExistsException(username);
        users.put(username, new ManagerClass(username, job, level));
    }

    /**
     * Regista um developer no sistema
     * @param username nome do developer a ser registado
     * @param managerName nome do manager do developer
     * @param job cargo developer
     * @param level nivel do developer
     * @throws UnknownJobPositionException
     * @throws UserAlreadyExistsException
     * @throws ManagerDoesNotExistException
     */
    @Override
    public void register(String username, String managerName, Job job, int level) throws UnknownJobPositionException, UserAlreadyExistsException, ManagerDoesNotExistException {
        if (hasUser(username))
            throw new UserAlreadyExistsException(username);
        else if (!hasManager(managerName))
            throw new ManagerDoesNotExistException(managerName);
        Manager manager = getManager(managerName);
        Developer developer = new DeveloperClass(username, manager, job, level);
        users.put(username, developer);
        manager.addDeveloper(developer);
    }

    /**
     * Cria um novo projeto In-House
     * @param id nome do projeto a ser criado
     * @param managerName nome do manager do projeto
     * @param keywords descricao do projeto
     * @param level nivel do projeto
     * @throws UnknownProjectTypeException
     * @throws ManagerDoesNotExistException
     * @throws ProjectAlreadyExistsException
     * @throws UnderClearanceLevelException
     */
    @Override
    public void createProject(String id, String managerName, String keywords, int level) throws ManagerDoesNotExistException, ProjectAlreadyExistsException, UnderClearanceLevelException {
        if (!hasManager(managerName))
            throw new ManagerDoesNotExistException(managerName);
        else if (hasProject(id))
            throw new ProjectAlreadyExistsException(id);
        else if (getManager(managerName).getLevel() < level)
            throw new UnderClearanceLevelException(managerName, getManager(managerName).getLevel());

        Manager manager = getManager(managerName);
        InHouse project = new InHouseClass(id, manager, keywords, level);
        projects.put(id, project);

        for (String keyword : keywords.split(" ")) {
            if (!inHousesByKeyword.containsKey(keyword)) {
                inHousesByKeyword.put(keyword, new LinkedList<InHouse>());
            }
            inHousesByKeyword.get(keyword).add(project);
        }

        inHousesByConfidentiality.get(project.getLevel()).add(project);

        manager.addManagedProject(project);
    }

    /**
     * Cria um novo projeto Outsourced
     * @param id nome do projeto a ser criado
     * @param managerName nome do manager do projeto
     * @param keywords descricao do projeto
     * @param company companhia a qual o projeto pertence
     * @throws ManagerDoesNotExistException
     * @throws ProjectAlreadyExistsException
     */
    @Override
    public void createProject(String id, String managerName, String keywords, String company) throws ManagerDoesNotExistException, ProjectAlreadyExistsException {
        if (!hasManager(managerName))
            throw new ManagerDoesNotExistException(managerName);
        if (hasProject(id))
            throw new ProjectAlreadyExistsException(id);
        Manager manager = getManager(managerName);
        Outsourced project = new OutsourcedClass(id, manager, keywords, company);
        projects.put(id, project);

        for (String keyword : keywords.split(" ")) {
            if (!outsourcedByKeyword.containsKey(keyword)) {
                outsourcedByKeyword.put(keyword, new LinkedList<Outsourced>());
            }
            outsourcedByKeyword.get(keyword).add(project);
        }
        manager.addManagedProject(project);
    }

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
    @Override
    public void addEmployeeToProject(String managerName, String id, String member) throws ManagerDoesNotExistException, ProjectDoesNotExistException, ProjectNotManagedByUserException, MemberAlreadyInTeamException, EmployeeDoesNotExistException, InsufficientClearanceLevelException {
        if (!hasManager(managerName))
            throw new ManagerDoesNotExistException(managerName);
        if (!hasInHouse(id))
            throw new ProjectDoesNotExistException(id);

        InHouse inHouse = getInHouse(id);

        if (!inHouse.getManagerUsername().equals(managerName))
            throw new ProjectNotManagedByUserException(id, inHouse.getManagerUsername());

        if (inHouse.hasEmployee(member) || member.equals(managerName))
            throw new MemberAlreadyInTeamException(member);
        if (getEmployee(member).getLevel() < inHouse.getLevel())
            throw new InsufficientClearanceLevelException(member);
        if (!hasUser(member))
            throw new EmployeeDoesNotExistException(member);

        Employee employee = getEmployee(member);
        inHouse.addEmployee(employee);
        employee.addProject(inHouse);
        inHouse.getManager().addProject(inHouse);
    }

    /**
     * Adiciona um artefacto a um projeto
     * @param artefact artefacto a ser adicionado
     * @param id id do projeto
     * @param date data da criacao do artefacto
     * @throws UserDoesNotExistException
     * @throws ProjectDoesNotExistException
     * @throws MemberNotInTeamException
     * @throws ArtefactAlreadyInProjectException
     * @throws ArtefactHasHigherLevelException
     * @throws EmployeeDoesNotExistException
     */
    @Override
    public void addArtefactToProject(Artefact artefact, String id, LocalDate date) throws UserDoesNotExistException, ProjectDoesNotExistException, MemberNotInTeamException, ArtefactAlreadyInProjectException, ArtefactHasHigherLevelException, EmployeeDoesNotExistException {
        String employeeName = artefact.getOwner();

        if (!hasUser(employeeName))
            throw new UserDoesNotExistException(employeeName);
        if (!hasInHouse(id))
            throw new ProjectDoesNotExistException(id);

        InHouse inHouse = getInHouse(id);

        if (!inHouse.hasEmployee(employeeName) && !inHouse.getManagerUsername().equals(employeeName))
            throw new MemberNotInTeamException(employeeName, id);
        if (inHouse.hasArtefact(artefact.getName()))
            throw new ArtefactAlreadyInProjectException(artefact.getName());
        if (artefact.getLevel() > inHouse.getLevel())
            throw new ArtefactHasHigherLevelException(artefact.getName());

        inHouse.addArtefact(artefact);
        Employee employee = getEmployee(employeeName);
        Revision revision = new RevisionClass(1, employeeName, date, artefact.getDescription(), artefact.getName(), inHouse.getId());
        inHouse.addRevision(revision);
        employee.addRevision(revision);
    }

    /**
     * Adiciona LinkedLists vazias na List inHousesByConfidentiality (util para o comando confidentiality)
     */
    @Override
    public void inHousesByConfidentialityInit() {
        for (int i = 0; i < 6; i++) {
            inHousesByConfidentiality.add(i, new LinkedList<>());
        }
    }

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
    @Override
    public int addRevision(String username, String id, String artefactName, LocalDate date, String comment) throws UserDoesNotExistException, ProjectDoesNotExistException, ArtefactDoesNotExistException, MemberNotInTeamException, EmployeeDoesNotExistException {
        if (!hasUser(username))
            throw new UserDoesNotExistException(username);
        if (isOutsourced(id))
            throw new ProjectDoesNotExistException(id);

        InHouse inHouse = getInHouse(id);

        if (!inHouse.hasArtefact(artefactName))
            throw new ArtefactDoesNotExistException(artefactName);
        else if (!inHouse.hasEmployee(username))
            throw new MemberNotInTeamException(username, id);

        Artefact artefact = inHouse.getArtefact(artefactName);
        Revision revision = new RevisionClass(artefact.getRevisionNum(), username, date, comment, artefact.getName(), id);
        Employee employee = getEmployee(username);
        artefact.addRevision(revision);
        employee.addRevision(revision);
        inHouse.addRevision(revision);
        return artefact.getRevisionNum() - 1;
    }

    /**
     * Lista os employees do sistema
     * @return iterador com todos os employees do sistema
     * @throws EmptyUsersException
     */
    @Override
    public Iterator<Employee> listUsers() throws EmptyUsersException {
        if (users.isEmpty())
            throw new EmptyUsersException();
        return users.values().iterator();
    }

    /**
     * Lista os projetos do sistema
     * @return iterador com todos os projetos do sistema
     * @throws EmptyProjectsException
     */
    @Override
    public Iterator<Project> listProjects() throws EmptyProjectsException {
        if (projects.isEmpty())
            throw new EmptyProjectsException();
        return projects.values().iterator();
    }

    /**
     * Obtem o employee com um dado nome
     * @param username nome do employee a ser pesquisado
     * @return employee com o dado nome
     * @throws EmployeeDoesNotExistException
     */
    @Override
    public Employee getEmployee(String username) throws EmployeeDoesNotExistException {
        if (!users.containsKey(username))
            throw new EmployeeDoesNotExistException(username);
        return users.get(username);
    }

    /**
     * Obtem o manager com um dado nome
     * @param username nome do manager a ser pesquisado
     * @return manager com o dado nome
     * @throws ManagerDoesNotExistException
     */
    @Override
    public Manager getManager(String username) throws ManagerDoesNotExistException {
        if (!users.containsKey(username) || (users.containsKey(username) && !(users.get(username) instanceof Manager)))
            throw new ManagerDoesNotExistException(username);
        return (Manager) users.get(username);
    }

    /**
     * Obtem o enum Job a partir de uma string
     * @param job string com o job
     * @return enum Job
     * @throws UnknownJobPositionException
     */
    @Override
    public Job getJob(String job) throws UnknownJobPositionException {
        try {
            return Job.valueOf(job.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownJobPositionException();
        }
    }

    /**
     * Obtem o enum ProjectType a partir de uma string
     * @param projectTypeName nome do projectType
     * @return enum ProjectType
     */
    @Override
    public ProjectType getProjectType(String projectTypeName) throws UnknownProjectTypeException {
        try {
            return ProjectType.valueOf(projectTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownProjectTypeException();
        }
    }

    /**
     * Obtem o projeto com um dado id
     * @param id id do projeto a ser pesquisado
     * @return projeto com o dado id
     * @throws ProjectDoesNotExistException
     */
    @Override
    public Project getProject(String id) throws ProjectDoesNotExistException {
        if (!projects.containsKey(id))
            throw new ProjectDoesNotExistException(id);
        return projects.get(id);
    }

    /**
     * Obtem o projeto In-House com um dado id
     * @param id id do projeto In-House a ser pesquisado
     * @return projeto In-House com o dado id
     * @throws ProjectDoesNotExistException
     */
    @Override
    public InHouse getInHouse(String id) throws ProjectDoesNotExistException {
        if (!hasInHouse(id))
            throw new ProjectDoesNotExistException(id);
        return (InHouse) projects.get(id);
    }

    /**
     * Obtem o projeto In-House com um dado id (usado no comando projects, pois lanca mais uma excecao)
     * @param id id do projeto In-House a ser pesquisado
     * @return projeto In-House com o dado id
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    @Override
    public InHouse getProjectInfo(String id) throws ProjectDoesNotExistException, OutsourcedProjectException {
        if (isOutsourced(id))
            throw new OutsourcedProjectException(id);
        return (InHouse) projects.get(id);
    }

    /**
     * Verifica se um dado employee existe no sistema
     * @param username nome do employee a ser pesquisado
     * @return true se o employee existir no sistema / false caso contrario
     */
    @Override
    public boolean hasUser(String username) {
        return users.containsKey(username);
    }

    /**
     * Verifica se um dado manager existe no sistema
     * @param username nome do manager a ser pesquisado
     * @return true se o manager existir no sistema / false caso contrario
     */
    @Override
    public boolean hasManager(String username) {
        return users.containsKey(username) && users.get(username) instanceof Manager;
    }

    /**
     * Verifica se um dado projeto existe no sistema
     * @param id id do projeto a ser pesquisado
     * @return true se o projeto existir no sistema / false caso contrario
     */
    @Override
    public boolean hasProject(String id) {
        return projects.containsKey(id);
    }

    /**
     * Verifica se um projeto In-House existe no sistema
     * @param id id do projeto In-House a ser pesquisado
     * @return true se o projeto In-House existir no sistema / false caso contrario
     */
    @Override
    public boolean hasInHouse(String id) {
        return projects.containsKey(id) && projects.get(id) instanceof InHouse;
    }

    /**
     * Verifica se um dado projeto é Outsourced.
     * @param id id do projeto Outsourced a ser pesquisado
     * @return true se o projeto for Outsourced / false caso contrario
     * @throws ProjectDoesNotExistException
     */
    @Override
    public boolean isOutsourced(String id) throws ProjectDoesNotExistException {
        if (!projects.containsKey(id))
            throw new ProjectDoesNotExistException(id);
        return projects.get(id) instanceof Outsourced;
    }

    /**
     * Verifica se nenhum employee realizou pelo menos um update num artefacto
     * @return true se nenhum employee realizou um update / false caso contrario
     */
    @Override
    public boolean noUserMadeAnUpdate() {
        for (Employee employee : users.values()) {
            if (employee.getUpdates() > 0)
                return false;
        }
        return true;
    }

    /**
     * Verifica se os employees tem um ou mais projetos em comum
     * @return true se os employees tiverem um ou mais projetos em comum / false caso contrario
     */
    @Override
    public boolean hasCommonProjects() {

        List<Employee> employees = new LinkedList<>(users.values());

        for (int i = 0; i < employees.size(); i++) {
            Employee current = employees.get(i);
            for (int j = i + 1; j < employees.size(); j++) {
                int currentNum = current.getCommonProjectsNum(employees.get(j));
                if (currentNum > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * Lista a equipa de um projeto
     * @param id id do projeto
     * @return iterador com todos os membros da equipa do projeto
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    @Override
    public Iterator<Employee> listTeamInProject(String id) throws ProjectDoesNotExistException, OutsourcedProjectException {
        if (isOutsourced(id))
            throw new OutsourcedProjectException(id);
        return ((InHouse) projects.get(id)).listTeam();
    }

    /**
     * Lista os artefactos de um projeto
     * @param id id do projeto
     * @return iterador com todos os artefactos do projeto
     * @throws ProjectDoesNotExistException
     * @throws OutsourcedProjectException
     */
    @Override
    public Iterator<Artefact> listArtefactsInProject(String id) throws ProjectDoesNotExistException, OutsourcedProjectException {
        if (isOutsourced(id))
            throw new OutsourcedProjectException(id);
        return ((InHouse) projects.get(id)).listArtefacts();
    }

    /**
     * Lista as revisoes de um artefacto
     * @param artefact artefacto
     * @return iterador com todas as revisoes do artefacto
     */
    @Override
    public Iterator<Revision> listRevisions(Artefact artefact) {
        return artefact.listRevisions();
    }

    /**
     * Lista os developers que estao associados a um manager
     * @param username nome do manager
     * @return iterador com todos os developers associados ao manager
     * @throws ManagerDoesNotExistException
     */
    @Override
    public Iterator<Developer> listManagedDevelopers(String username) throws ManagerDoesNotExistException {
        if (!hasManager(username))
            throw new ManagerDoesNotExistException(username);
        Manager m = getManager(username);
        List<Developer> developers = new LinkedList<>();
        for (Map.Entry<String, Employee> e : users.entrySet()) {
            if (e.getValue() instanceof Developer && m.hasDeveloper(e.getKey())) {
                developers.add((Developer) e.getValue());
            }
        }
        return developers.iterator();
    }

    /**
     * Lista os projetos In-House que contem uma keyword
     * @param keyword filtro keyword
     * @return iterador com todos os projetos In-House que contem a keyword
     * @throws NoProjectsWithKeywordException
     */
    @Override
    public Iterator<InHouse> listInHousesByKeyword(String keyword) throws NoProjectsWithKeywordException {
        List<InHouse> inHousesList = new LinkedList<>();
        if (!inHousesByKeyword.containsKey(keyword) && !outsourcedByKeyword.containsKey(keyword))
            throw new NoProjectsWithKeywordException(keyword);
        if (!inHousesByKeyword.containsKey(keyword))
            return inHousesList.iterator();
        inHousesList = inHousesByKeyword.get(keyword);
        inHousesList.sort(new InHousesByKeywordComparator());
        return inHousesList.iterator();
    }

    /**
     * Lista os projetos Outsourced que contem uma keyword
     * @param keyword filtro keyword
     * @return iterador com todos os projetos Outsourced que contem a keyword
     * @throws NoProjectsWithKeywordException
     */
    @Override
    public Iterator<Outsourced> listOutsourcedByKeyword(String keyword) throws NoProjectsWithKeywordException {
        List<Outsourced> outsourcedList = new LinkedList<>();
        if (!inHousesByKeyword.containsKey(keyword) && !outsourcedByKeyword.containsKey(keyword))
            throw new NoProjectsWithKeywordException(keyword);
        if (!outsourcedByKeyword.containsKey(keyword))
            return outsourcedList.iterator();
        outsourcedList = outsourcedByKeyword.get(keyword);
        outsourcedList.sort(new OutsourcedByKeywordComparator());
        return outsourcedList.iterator();
    }

    /**
     * Lista os projetos In-House num dado intervalo de niveis de confidencialidade
     * @param lower limite inferior de confidencialidade
     * @param upper limite superior de confidencialidade
     * @return iterador com todos os projetos In-House no dado intervalo de niveis de confidencialidade
     * @throws NoProjectsWithinLevelsException
     */
    @Override
    public Iterator<InHouse> listByConfidentiality(int lower, int upper) throws NoProjectsWithinLevelsException {

        boolean containsInHouse = false;

        List<InHouse> inHouses = new LinkedList<>();
        for (int i = lower; i <= upper; i++) {
            if (!inHousesByConfidentiality.get(i).isEmpty()) {
                containsInHouse = true;
                inHouses.addAll(inHousesByConfidentiality.get(i));
            }
        }
        if (!containsInHouse)
            throw new NoProjectsWithinLevelsException(lower, upper);

        inHouses.sort(new ConfidentialityComparator());
        return inHouses.iterator();
    }

    /**
     * Lista os 3 employees mais trabalhadores
     * @return iterador com os 3 employees mais trabalhadores
     * @throws NoWorkaholicsException
     */
    @Override
    public Iterator<Employee> listWorkaholics() throws NoWorkaholicsException {
        if (users.isEmpty() || noUserMadeAnUpdate())
            throw new NoWorkaholicsException();

        List<Employee> employees = new LinkedList<>();
        List<Employee> employeesSorted = new LinkedList<>();

        for (Employee employee : users.values()) {
            if (employee.getUpdates() > 0) {
                employees.add(employee);
            }
        }

        employees.sort(new EmployeeComparator());

        for (int i = 0; i < 3 && i < employees.size(); i++) {
            employeesSorted.add(i, employees.get(i));
        }
        return employeesSorted.iterator();
    }

    /**
     * Lista os 2 employees que tem mais projetos em comum
     * @return iterador com os 2 employees que tem mais projetos em comum
     * @throws NoCommonProjectsException
     */
    @Override
    public Iterator<Employee> listCommon() throws NoCommonProjectsException {
        if (projects.isEmpty() || !hasCommonProjects())
            throw new NoCommonProjectsException();

        int record = 0;
        Employee first = null;
        Employee second = null;

        List<Employee> employees = new LinkedList<>(users.values());
        for (int i = 0; i < employees.size(); i++) {
            Employee current = employees.get(i);
            for (int j = i + 1; j < employees.size(); j++) {
                Employee employeeToCompare = employees.get(j);
                int currentNum = current.getCommonProjectsNum(employeeToCompare);
                if (currentNum > record) {
                    record = currentNum;
                    first = current;
                    second = employeeToCompare;
                }
                else if (currentNum == record && record != 0) {
                    if (current.getUsername().compareTo(first.getUsername()) < 0)
                        first = current;
                    else if (current.getUsername().compareTo(first.getUsername()) == 0) {
                        if (employeeToCompare.getUsername().compareTo(second.getUsername()) < 0)
                            second = employeeToCompare;
                    }
                }
            }
        }

        List<Employee> employeesToReturn = new LinkedList<>();
        employeesToReturn.add(first);
        employeesToReturn.add(second);
        return employeesToReturn.iterator();
    }
}
