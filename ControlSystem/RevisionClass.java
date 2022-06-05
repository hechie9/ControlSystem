package ControlSystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RevisionClass implements Revision, Comparable<Revision> {

    private static final String WRITE_DATE_FORMAT = "dd-MM-yyyy";

    private int number;
    private String owner;
    private LocalDate date;
    private String comment;
    private String artefactName;
    private String projectName;

    /**
     * Construtor da class RevisionClass
     * @param number numero da revisao
     * @param owner dono da revisao
     * @param date data da revisao
     * @param comment comentario da revisao
     */
    public RevisionClass(int number, String owner, LocalDate date, String comment, String artefactName, String projectName) {
        this.number = number;
        this.owner = owner;
        this.date = date;
        this.comment = comment;
        this.artefactName = artefactName;
        this.projectName = projectName;
    }

    /**
     * Obtem o nome do dono da revisao
     * @return nome do dono da revisao
     */
    @Override
    public String getOwner() {
        return owner;
    }

    /**
     * Obtem o comentario da revisao
     * @return comentario da revisao
     */
    @Override
    public String getComment() {
        return comment;
    }

    /**
     * Obtem o nome do artefacto no qual a revisao esta inserida
     * @return nome do artefacto no qual a revisao esta inserida
     */
    @Override
    public String getArtefact() {
        return artefactName;
    }

    /**
     * Obtem o nome do projeto no qual a revisao esta inserida
     * @return nome do projeto no qual a revisao esta inserida
     */
    @Override
    public String getProject() {
        return projectName;
    }

    /**
     * Obtem a data da revisao
     * @return data da revisao
     */
    @Override
    public String getDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
        return formatter.format(date);
    }

    /**
     * Obtem a data da revisao
     * @return data da revisao
     */
    @Override
    public LocalDate getDate() {
        return date;
    }

    /**
     * Obtem o numero da revisao
     * @return numero da revisao
     */
    @Override
    public int getNumber() {
        return number;
    }

    /**
     * Compara duas revisoes ('this' e revision)
     * @param revision revisao a ser comparada com 'this'
     * @return -1 se o numero de 'this' for maior do que o numero de revision / 1 se o numero de 'this' for menor do que o numero de revision / 0 se o numero de 'this' for igual a ao numero de revision
     */
    @Override
    public int compareTo(Revision revision) {
        if (number > revision.getNumber())
            return -1;
        else if (number < revision.getNumber())
            return 1;
        return 0;
    }
}
