package ControlSystem;

public class OutsourcedClass extends ProjectClass implements Outsourced {

    private String company;

    /**
     * Construtor da class OutsourcedClass
     * @param id id do projeto
     * @param manager dono do projeto
     * @param keywords descricao do projeto
     * @param company nome da companhia a qual o projeto pertence
     */
    public OutsourcedClass(String id, Manager manager, String keywords, String company) {
        super(id, manager, keywords);
        this.company = company;
    }

    /**
     * Obtem o nome da companhia a qual o projeto pertence
     * @return nome da companhia a qual o projeto pertence
     */
    @Override
    public String getCompany() {
        return company;
    }
}
