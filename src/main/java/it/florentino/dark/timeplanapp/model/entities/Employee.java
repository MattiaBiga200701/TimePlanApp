package it.florentino.dark.timeplanapp.model.entities;

import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;

public class Employee {

    private String name;
    private String surname;
    private ContractTypes contractType;

    private int managerID;

    public Employee(String name, String surname, ContractTypes contractType, int managerID){
        this.setName(name);
        this.setSurname(surname);
        this.setContractType(contractType);
        this.setManagerID(managerID);
    }

    public Employee(int managerID ){
        this.setManagerID(managerID);
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

    public void setContractType(ContractTypes contractType) {
        this.contractType = contractType;
    }

    public ContractTypes getContractType() {
        return this.contractType;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }
}
