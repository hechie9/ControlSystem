package ControlSystem;

public abstract class ProjectClass implements Project {

    private String id;
    private Manager manager;
    private String keywords;

    /**
     * Construtor da class ProjectClass
     * @param id id do projeto
     * @param manager dono do projeto
     * @param keywords descricao do projeto
     */
    public ProjectClass(String id, Manager manager, String keywords) {
        this.id = id;
        this.manager = manager;
        this.keywords = keywords;
    }

    /**
     * Obtem o id do projeto
     * @return id do projeto
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Obtem as keywords do projeto
     * @return keywords do projeto
     */
    @Override
    public String getKeywords() {
        return keywords;
    }

    /**
     * Obtem o dono do projeto
     * @return dono do projeto
     */
    @Override
    public Manager getManager() {
        return manager;
    }

    /**
     * Obtem o username do dono do projeto
     * @return username do dono do projeto
     */
    @Override
    public String getManagerUsername() {
        return manager.getUsername();
    }
}
