package ControlSystem;

import java.time.LocalDate;

public interface Revision extends Comparable<Revision> {

    /**
     * Obtem o nome do dono da revisao
     * @return nome do dono da revisao
     */
    String getOwner();

    /**
     * Obtem o comentario da revisao
     * @return comentario da revisao
     */
    String getComment();

    /**
     * Obtem o nome do artefacto no qual a revisao esta inserida
     * @return nome do artefacto no qual a revisao esta inserida
     */
    String getArtefact();

    /**
     * Obtem o nome do projeto no qual a revisao esta inserida
     * @return nome do projeto no qual a revisao esta inserida
     */
    String getProject();

    /**
     * Obtem a data da revisao
     * @return data da revisao
     */
    String getDateAsString();

    /**
     * Obtem a data da revisao
     * @return data da revisao
     */
    LocalDate getDate();

    /**
     * Obtem o numero da revisao
     * @return numero da revisao
     */
    int getNumber();
}
