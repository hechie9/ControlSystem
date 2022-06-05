import ControlSystem.*;
import Exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // comandos disponiveis
    private static final String QUIT = "EXIT";
    private static final String HELP = "HELP";
    private static final String REGISTER = "REGISTER";
    private static final String USERS = "USERS";
    private static final String CREATE = "CREATE";
    private static final String PROJECTS = "PROJECTS";
    private static final String ARTEFACTS = "ARTEFACTS";
    private static final String PROJECT = "PROJECT";
    private static final String REVISION = "REVISION";
    private static final String MANAGES = "MANAGES";
    private static final String KEYWORD = "KEYWORD";
    private static final String CONFIDENTIALITY = "CONFIDENTIALITY";
    private static final String WORKAHOLICS = "WORKAHOLICS";
    private static final String TEAM = "TEAM";
    private static final String COMMON = "COMMON";

    // feedback dado pelo programa
    private static final String BYE_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
    private static final String HELP_TITLE = "Available commands:";
    private static final String USER_REGISTERED = "User %s was registered as %s with clearance level %d.\n";
    private static final String LIST_USERS_TITLE = "All registered users:";
    private static final String LIST_PROJECTS_TITLE = "All projects:";
    private static final String MANAGER_INFO = "manager %s [%d, %d, %d]\n";
    private static final String DEVELOPER_INFO = "developer %s is managed by %s [%d]\n";
    private static final String INHOUSE_INFO = "in-house %s is managed by %s [%d, %d, %d, %d]\n";
    private static final String OUTSOURCED_INFO = "outsourced %s is managed by %s and developed by %s\n";
    private static final String CREATED_PROJECT = "%s project was created.\n";
    private static final String TEAM_TITLE = "Latest team members:";
    private static final String ADDED_EMPLOYEE_TO_TEAM = "%s: added to the team.\n";
    private static final String ARTEFACTS_TITLE = "Latest project artefacts:";
    private static final String ADDED_ARTEFACT_TO_PROJECT = "%s: added to the project.\n";
    private static final String PROJECT_FORMAT = "%s [%d] managed by %s [%d]:\n";
    private static final String EMPLOYEE_INFO = "%s [%d]\n";
    private static final String ARTEFACT_INFO = "%s [%d]\n";
    private static final String REVISION_INFO = "revision %d %s %s %s\n";
    private static final String REVISION_ADDED = "Revision %d of artefact %s was submitted.\n";
    private static final String MANAGER_TITLE = "Manager %s:\n";
    private static final String REVISION_EMPLOYEE_INFO = "%s, %s, revision %d, %s, %s\n";
    private static final String KEYWORD_TITLE = "All projects with keyword %s:\n";
    private static final String KEYWORD_INHOUSE = "in-house %s is managed by %s [%d, %d, %d, %d, %s]\n";
    private static final String KEYWORD_OUTSOURCED = "outsourced %s is managed by %s and developed by %s\n";
    private static final String CONFIDENTIALITY_TITLE = "All projects within levels %d and %d:\n";
    private static final String CONFIDENTIALITY_INFO = "%s is managed by %s and has keywords %s\n";
    private static final String WORKAHOLICS_INFO = "%s: %d updates, %d projects, last update on %s\n";
    private static final String COMMON_INFO = "%s %s have %d projects in common.\n";

    // manipulacao de datas
    private static final String WRITE_DATE_FORMAT = "dd-MM-yyyy";

    public static void main(String[] args) {
        ControlSystem system = new ControlSystemClass();
        Scanner in = new Scanner(System.in);
        String comm = getCommand(in);

        while (!comm.equals(QUIT)) {
            switch (comm) {
                case HELP:
                    help();
                    break;
                case REGISTER:
                    register(in, system);
                    break;
                case USERS:
                    listUsers(system);
                    break;
                case CREATE:
                    createProject(in, system);
                    break;
                case PROJECTS:
                    listProjects(system);
                    break;
                case TEAM:
                    addTeamToProject(in, system);
                    break;
                case ARTEFACTS:
                    addArtefactsToProject(in, system);
                    break;
                case PROJECT:
                    projectInfo(in, system);
                    break;
                case REVISION:
                    addRevisionToArtefact(in, system);
                    break;
                case MANAGES:
                    managesInfo(in, system);
                    break;
                case KEYWORD:
                    listProjectsByKeyword(in, system);
                    break;
                case CONFIDENTIALITY:
                    listByConfidentiality(in, system);
                    break;
                case WORKAHOLICS:
                    listWokaholics(system);
                    break;
                case COMMON:
                    listCommon(system);
                    break;
                default:
                    System.out.println(UNKNOWN_COMMAND);
            }
            comm = getCommand(in);
        }
        System.out.println(BYE_MESSAGE);
    }


    private static String getCommand(Scanner in) {
        String comm = in.next();
        return comm.toUpperCase();
    }

    /**
     * Prints every available command
     */
    private static void help() {
        System.out.println(HELP_TITLE);
        for (HelpCommands h : HelpCommands.values())
            System.out.println(h.getText());
    }

    private static void register(Scanner in, ControlSystem system) {

        String manager = "";
        String jobName = in.next().toUpperCase();
        String username = in.next();

        try {
            Job job = system.getJob(jobName);

            switch (job) {
                case DEVELOPER:
                    manager = in.next();
            }
            int level = in.nextInt();
            in.nextLine();

            switch (job) {
                case DEVELOPER:
                    system.register(username, manager, job, level);
                    System.out.printf(USER_REGISTERED, username, "developer", level);
                    break;
                case MANAGER:
                    system.register(username, job, level);
                    System.out.printf(USER_REGISTERED, username, "manager", level);
                    break;
            }
        } catch (UserAlreadyExistsException | ManagerDoesNotExistException e1) {
            System.out.println(e1.getMessage());
        } catch (UnknownJobPositionException e2) {
            System.out.println(e2.getMessage());
            in.nextLine();
        }
    }

    private static void listUsers(ControlSystem system) {
        try {
            Iterator<Employee> it = system.listUsers();
            System.out.println(LIST_USERS_TITLE);
            while (it.hasNext()) {
                Employee current = (Employee) it.next();
                if (current instanceof Manager) {
                    Manager manager = (Manager) current;
                    System.out.printf(MANAGER_INFO, manager.getUsername(), manager.getNumDevelopers(), manager.getNumManagedProjects(), manager.getNumProjects() + manager.getNumManagedProjects());
                } else if (current instanceof Developer) {
                    Developer developer = (Developer) current;
                    System.out.printf(DEVELOPER_INFO, developer.getUsername(), developer.getManager(), developer.getNumProjects());
                }
            }
        } catch (EmptyUsersException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createProject(Scanner in, ControlSystem system) {
        String manager = in.next();
        String projectTypeName = in.next();
        String id = in.nextLine().trim();
        int numKeywords = in.nextInt();
        String keywords = "";
        for (int i = 0; i < numKeywords; i++) {
            keywords += in.next() + " ";
        }
        keywords = keywords.trim();
        in.nextLine();

        try {
            ProjectType projectType = system.getProjectType(projectTypeName);

            switch (projectType) {
                case INHOUSE:
                    int level = in.nextInt();
                    in.nextLine();
                    system.createProject(id, manager, keywords, level);
                    break;
                case OUTSOURCED:
                    String company = in.next();
                    system.createProject(id, manager, keywords, company);
                    break;
            }
            System.out.printf(CREATED_PROJECT, id);
        } catch (ManagerDoesNotExistException | ProjectAlreadyExistsException | UnderClearanceLevelException e1) {
            System.out.println(e1.getMessage());
        } catch (UnknownProjectTypeException e2) {
            in.nextLine();
            System.out.println(e2.getMessage());
        }
    }

    private static void listProjects(ControlSystem system) {
        try {
            Iterator<Project> it = system.listProjects();
            System.out.println(LIST_PROJECTS_TITLE);
            while (it.hasNext()) {
                Project current = (Project) it.next();
                if (current instanceof InHouse) {
                    InHouse inHouse = (InHouse) current;
                    System.out.printf(INHOUSE_INFO, inHouse.getId(), inHouse.getManagerUsername(), inHouse.getLevel(), inHouse.getNumEmployees(), inHouse.getNumArtefacts(), inHouse.getNumRevisions());
                } else if (current instanceof Outsourced) {
                    Outsourced outsourced = (Outsourced) current;
                    System.out.printf(OUTSOURCED_INFO, outsourced.getId(), outsourced.getManagerUsername(), outsourced.getCompany());
                }
            }
        } catch (EmptyProjectsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addTeamToProject(Scanner in, ControlSystem system) {

        boolean flag = true;

        String manager = in.next();
        String id = in.nextLine().trim();
        int amount = in.nextInt();
        List<String> members = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            members.add(in.next());
        }
        for (String member : members) {
            try {
                system.addEmployeeToProject(manager, id, member);
                if (flag) {
                    System.out.println(TEAM_TITLE);
                    flag = false;
                }
                System.out.printf(ADDED_EMPLOYEE_TO_TEAM, member);
            } catch (MemberAlreadyInTeamException | EmployeeDoesNotExistException | InsufficientClearanceLevelException e1) {
                if (flag) {
                    System.out.println(TEAM_TITLE);
                    flag = false;
                }
                System.out.println(e1.getMessage());
            } catch (ProjectNotManagedByUserException | ProjectDoesNotExistException | ManagerDoesNotExistException e2) {
                System.out.println(e2.getMessage());
                break;
            }
        }
    }

    private static void addArtefactsToProject(Scanner in, ControlSystem system) {

        boolean flag = true;

        String member = in.next();
        String id = in.nextLine().trim();
        String dateString = in.nextLine().trim();
        int amount = in.nextInt();
        in.nextLine();
        List<Artefact> artefacts = new LinkedList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
        LocalDate date = LocalDate.parse(dateString, formatter);

        for (int i = 0; i < amount; i++) {
            String name = in.next();
            int level = in.nextInt();
            String description = in.nextLine().trim();
            artefacts.add(new ArtefactClass(member, name, level, description, date, id));
        }

        for (Artefact artefact : artefacts) {
            try {
                system.addArtefactToProject(artefact, id, date);
                if (flag) {
                    System.out.println(ARTEFACTS_TITLE);
                    flag = false;
                }
                System.out.printf(ADDED_ARTEFACT_TO_PROJECT, artefact.getName());
            } catch (ArtefactAlreadyInProjectException | ArtefactHasHigherLevelException e1) {
                if (flag) {
                    System.out.println(ARTEFACTS_TITLE);
                    flag = false;
                }
                System.out.println(e1.getMessage());;
            } catch (UserDoesNotExistException | ProjectDoesNotExistException | MemberNotInTeamException | EmployeeDoesNotExistException e2) {
                System.out.println(e2.getMessage());
                break;
            }
        }
    }

    private static void projectInfo(Scanner in, ControlSystem system) {
        String id = in.nextLine().trim();
        try {
            InHouse inHouse = system.getProjectInfo(id);
            System.out.printf(PROJECT_FORMAT, inHouse.getId(), inHouse.getLevel(), inHouse.getManagerUsername(), inHouse.getManager().getLevel());

            Iterator<Employee> itTeam = system.listTeamInProject(id);
            while (itTeam.hasNext()) {
                Employee current = (Employee) itTeam.next();
                System.out.printf(EMPLOYEE_INFO, current.getUsername(), current.getLevel());
            }

            Iterator<Artefact> itArtefact = system.listArtefactsInProject(id);
            while (itArtefact.hasNext()) {
                Artefact artefact = (Artefact) itArtefact.next();
                System.out.printf(ARTEFACT_INFO, artefact.getName(), artefact.getLevel());

                Iterator<Revision> itRevision = system.listRevisions(artefact);
                while (itRevision.hasNext()) {
                    Revision revision = (Revision) itRevision.next();
                    System.out.printf(REVISION_INFO, revision.getNumber(), revision.getOwner(), revision.getDateAsString(), revision.getComment());
                }
            }
        } catch (ProjectDoesNotExistException | OutsourcedProjectException e) {
            System.out.println(e.getMessage());;
        }
    }

    private static void addRevisionToArtefact(Scanner in, ControlSystem system) {
        String username = in.next();
        String id = in.nextLine().trim();
        String artefact = in.next();
        String dateAsString = in.next().trim();
        String comment = in.nextLine().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
        LocalDate date = LocalDate.parse(dateAsString, formatter);

        try {
            int revisionNum = system.addRevision(username, id, artefact, date, comment);
            System.out.printf(REVISION_ADDED, revisionNum, artefact);
        } catch (UserDoesNotExistException | EmployeeDoesNotExistException|  ProjectDoesNotExistException | ArtefactDoesNotExistException | MemberNotInTeamException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void managesInfo(Scanner in, ControlSystem system) {
        String username = in.nextLine().trim();
        try {
            Iterator<Developer> itDev = system.listManagedDevelopers(username);
            System.out.printf(MANAGER_TITLE, username);
            while (itDev.hasNext()) {
                Developer current = (Developer) itDev.next();
                System.out.println(current.getUsername());
                Iterator<Revision> itRev = current.listRevisions();
                while (itRev.hasNext()) {
                    Revision r = (Revision) itRev.next();
                    System.out.printf(REVISION_EMPLOYEE_INFO, r.getProject(), r.getArtefact(), r.getNumber(), r.getDateAsString(), r.getComment());
                }
            }
        } catch (ManagerDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listProjectsByKeyword(Scanner in, ControlSystem system) {
        String keyword = in.nextLine().trim();
        try {
            Iterator<InHouse> itInHouse = system.listInHousesByKeyword(keyword);
            System.out.printf(KEYWORD_TITLE, keyword);
            while (itInHouse.hasNext()) {
                InHouse current = (InHouse) itInHouse.next();
                System.out.printf(KEYWORD_INHOUSE, current.getId(), current.getManagerUsername(), current.getLevel(), current.getNumEmployees(), current.getNumArtefacts(), current.getNumRevisions(), current.getRecentDateRevisionAsString());
            }
            Iterator<Outsourced> itOutsourced = system.listOutsourcedByKeyword(keyword);
            while (itOutsourced.hasNext()) {
                Outsourced current = (Outsourced) itOutsourced.next();
                System.out.printf(KEYWORD_OUTSOURCED, current.getId(), current.getManagerUsername(), current.getCompany());
            }
        } catch (NoProjectsWithKeywordException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listByConfidentiality(Scanner in, ControlSystem system) {
        int lower = in.nextInt();
        int upper = in.nextInt();
        in.nextLine();

        if (lower > upper) {
            int aux = lower;
            lower = upper;
            upper = aux;
        }

        try {
            Iterator<InHouse> it = system.listByConfidentiality(lower, upper);
            System.out.printf(CONFIDENTIALITY_TITLE, lower, upper);
            while (it.hasNext()) {
                InHouse current = (InHouse) it.next();
                System.out.printf(CONFIDENTIALITY_INFO, current.getId(), current.getManagerUsername(), current.getKeywordsFormatted());
            }
        } catch (NoProjectsWithinLevelsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listWokaholics(ControlSystem system) {
        try {
            Iterator<Employee> it = system.listWorkaholics();
            while (it.hasNext()) {
                Employee current = (Employee) it.next();
                if (current instanceof Manager)
                    System.out.printf(WORKAHOLICS_INFO, current.getUsername(), current.getUpdates(), current.getNumProjects() + ((Manager) current).getNumManagedProjects(), current.getLastDateFormatted());
                else
                    System.out.printf(WORKAHOLICS_INFO, current.getUsername(), current.getUpdates(), current.getNumProjects(), current.getLastDateFormatted());
            }

        } catch (NoWorkaholicsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listCommon(ControlSystem system) {
        try {
            Iterator<Employee> it = system.listCommon();
            while (it.hasNext()) {
                Employee first = (Employee) it.next();
                Employee second = (Employee) it.next();
                System.out.printf(COMMON_INFO, first.getUsername(), second.getUsername(), first.getCommonProjectsNum(second));
            }
        } catch (NoCommonProjectsException e) {
            System.out.println(e.getMessage());
        }
    }
}