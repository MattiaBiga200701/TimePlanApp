package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeBean {


    private String name;
    private String surname;
    private ContractTypes contractType;

    private String email;

    private int managerID;

    public EmployeeBean(String name, String surname, ContractTypes contractType, String email,  int managerID) throws InvalidInputException{
        this.setName(name);
        this.setSurname(surname);
        this.setContractType(contractType);
        this.setEmail(email);
        this.setManagerID(managerID);
    }

    public EmployeeBean(String email) throws InvalidInputException{
        this.setEmail(email);
    }


    public void setName(String name) throws InvalidInputException{

        if(this.isValidString(name)){
            this.name = name;
        } else {
            throw new InvalidInputException("Incorrect employee name");
        }
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) throws InvalidInputException{
        if(this.isValidString(surname)){
            this.surname = surname;
        }else{
            throw new InvalidInputException("Incorrect employee surname");
        }

    }

    public String getSurname() {
        return this.surname;
    }

    public void setContractType(ContractTypes contractType) {
        this.contractType = contractType;
    }

    public ContractTypes getContractType() {
        return contractType;
    }

    public void setEmail(String email) throws InvalidInputException {
        if(this.isValidEmail(email)) {
            this.email = email;
        }else throw new InvalidInputException("Invalid email");
    }

    public String getEmail() {
        return this.email;
    }

    private boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        if(email == null) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidString(String inputString){
        String namePattern = "^[A-Z][a-zA-Z]*$";
        Pattern pattern = Pattern.compile(namePattern);
        if(inputString == null) return false;
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }
}


