package it.florentino.dark.timeplanapp.model.entities;

public class Employee {

    private String name;
    private String surname;
    private String contractType;

    public Employee(String name, String surname, String contractType){
        this.setName(name);
        this.setSurname(surname);
        this.setContractType(contractType);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractType() {
        return contractType;
    }
}
