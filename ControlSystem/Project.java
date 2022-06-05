package ControlSystem;

public interface Project {

    /**
     * Obtem o id do projeto
     * @return id do projeto
     */
    String getId();

    /**
     * Obtem as keywords do projeto
     * @return keywords do projeto
     */
    String getKeywords();

    /**
     * Obtem o manager do projeto
     * @return manager do projeto
     */
    Manager getManager();

    /**
     * Obtem o username do manager do projeto
     * @return username do manager do projeto
     */
    String getManagerUsername();
}
