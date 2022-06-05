package ControlSystem;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ArtefactClass implements Artefact, Comparable<Artefact> {

    private String owner;
    private String name;
    private int level;
    private String description;
    private List<Revision> revisions;
    private LocalDate lastDate;

    /**
     * Construtor da class ArtefactClass
     * @param owner nome do dono do artefacto
     * @param name nome do artefacto
     * @param level nivel de acesso do artefacto
     * @param description descricao do artefacto
     * @param date data em que o artefacto foi criado
     */
    public ArtefactClass(String owner, String name, int level, String description, LocalDate date, String projectName) {
        this.owner = owner;
        this.name = name;
        this.level = level;
        this.description = description;
        this.revisions = new LinkedList<>();
        revisions.add(new RevisionClass(getRevisionNum(), owner, date, description, name, projectName));
        lastDate = date;
    }

    /**
     * Adiciona uma revisao no artefacto
     * @param revision revisao a ser adicionada
     */
    @Override
    public void addRevision(Revision revision) {
        revisions.add(revision);
        lastDate = revision.getDate();
    }

    /**
     * Obtem o nome do dono do artefacto
     * @return nome do dono do artefacto
     */
    @Override
    public String getOwner() {
        return owner;
    }

    /**
     * Obtem o nome do artefacto
     * @return nome do artefacto
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Obtem a descricao do artefacto
     * @return descricao do artefacto
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Obtem o nivel de acesso do artefacto
     * @return nivel de acesso do artefacto
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Obtem o numero de revisoes do artefacto
     * @return numero de revisoes do artefacto
     */
    @Override
    public int getNumRevisions() {
        return revisions.size() - 1;
    }

    /**
     * Obtem o numero da revisao atual do artefacto
     * @return numero da revisao atual do artefacto
     */
    @Override
    public int getRevisionNum() {
        return revisions.size() + 1;
    }

    /**
     * Obtem a data da ultima revisao do artefacto
     * @return data da ultima revisao do artefacto
     */
    @Override
    public LocalDate getLastDate() {
        return lastDate;
    }

    /**
     * Verifica se dois artefactos sao iguais ('this' e artefact)
     * @param artefact artefacto a ser comparado com 'this'
     * @return true se os artefactos forem iguais / false caso contrario
     */
    @Override
    public boolean equals(Artefact artefact) {
        return this.getName().equals(artefact.getName());
    }

    /**
     * Lista as revisoes associadas ao artefacto
     * @return iterador com todas as revisoes associadas ao artefacto
     */
    @Override
    public Iterator<Revision> listRevisions() {
        Collections.sort(revisions);
        return revisions.iterator();
    }

    /**
     * Compara dois artefactos ('this' e artefact)
     * @param artefact artefacto a ser comparado com 'this'
     * @return -1 se artefact for mais antigo que 'this' ou se tiverem a mesma data mas o nome de 'this' for predecessor do nome de artefact / 1 se artefact for mais recente que 'this' ou se tiverem a mesma data mas o nome de 'this' for sucessor do nome de artefact / 0 se 'this' for igual a artefact (mesma data e mesmo nome)
     */
    @Override
    public int compareTo(Artefact artefact) {
        if (this.getLastDate().isAfter(artefact.getLastDate()))
            return -1;
        else if (this.getLastDate().isBefore(artefact.getLastDate()))
            return 1;
        else {
            if (this.getName().compareTo(artefact.getName()) < 0)
                return -1;
            else if (this.getName().compareTo(artefact.getName()) > 0)
                return 1;
            return 0;
        }
    }
}
