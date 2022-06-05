package ControlSystem;

import java.time.LocalDate;
import java.util.Iterator;

public interface Artefact extends Comparable<Artefact> {

    /**
     * Adiciona uma revisao no artefacto
     * @param revision revisao a ser adicionada
     */
    void addRevision(Revision revision);

    /**
     * Obtem o nome do dono do artefacto
     * @return nome do dono do artefacto
     */
    String getOwner();

    /**
     * Obtem o nome do artefacto
     * @return nome do artefacto
     */
    String getName();

    /**
     * Obtem a descricao do artefacto
     * @return descricao do artefacto
     */
    String getDescription();

    /**
     * Obtem o nivel de acesso do artefacto
     * @return nivel de acesso do artefacto
     */
    int getLevel();

    /**
     * Obtem o numero de revisoes do artefacto
     * @return numero de revisoes do artefacto
     */
    int getNumRevisions();

    /**
     * Obtem a revisao atual do artefacto
     * @return revisao atual do artefacto
     */
    int getRevisionNum();

    /**
     * Obtem a data da ultima revisao do artefacto
     * @return data da ultima revisao do artefacto
     */
    LocalDate getLastDate();

    /**
     * Verifica se dois artefactos sao iguais ('this' e artefact)
     * @param artefact artefacto a ser comparado com 'this'
     * @return true se os artefactos forem iguais / false caso contrario
     */
    boolean equals(Artefact artefact);

    /**
     * Lista as revisoes associadas ao artefacto
     * @return iterador com todas as revisoes associadas ao artefacto
     */
    Iterator<Revision> listRevisions();
}
