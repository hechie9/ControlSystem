package ControlSystem;

public enum HelpCommands {

    // instrucoes dos comandos disponiveis
    CMD_REGISTER_INFO ("register - adds a new user"),
    CMD_USERS_INFO("users - lists all registered users"),
    CMD_CREATE_INFO ("create - creates a new project"),
    CMD_PROJECTS_INFO ( "projects - lists all projects"),
    CMD_TEAM_INFO ("team - adds team members to a project"),
    CMD_ARTEFACTS_INFO ( "artefacts - adds artefacts to a project"),
    CMD_PROJECT_INFO ("project - shows detailed project information"),
    CMD_REVISION_INFO ("revision - revises an artefact"),
    CMD_MANAGES_INFO ("manages - lists developers of a manager"),
    CMD_KEYWORD_INFO ("keyword - filters projects by keyword"),
    CMD_CONFIDENTIALITY_INFO ( "confidentiality - filters projects by confidentiality level"),
    CMD_WORKAHOLICS_INFO ( "workaholics - top 3 employees with more artefacts updates"),
    CMD_COMMON_INFO ( "common - employees with more projects in common"),
    CMD_HELP_INFO ( "help - shows the available commands"),
    CMD_EXIT_INFO ( "exit - terminates the execution of the program");


    private final String text;

    HelpCommands(String text) {
        this.text = text;
    }

    /**
     *
     * @return the help commands
     */
    public String getText() {
        return text;
    }
}