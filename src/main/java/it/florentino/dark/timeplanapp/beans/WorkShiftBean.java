package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkShiftBean {

    private ShiftSlots shiftTime;
    private String shiftDate;
    private String employeeName;
    private String employeeSurname;
    private ContractTypes employeeContract;
    private String employeeEmail;
    private int managerID;

    public WorkShiftBean(ShiftSlots shiftTime, String shiftDate, String employeeName, String employeeSurname, ContractTypes employeeContract, String employeeEmail, int managerID) throws InvalidInputException{

        this.setShiftTime(shiftTime);
        this.setShiftDate(shiftDate);
        this.setEmployeeName(employeeName);
        this.setEmployeeSurname(employeeSurname);
        this.setEmployeeContract(employeeContract);
        this.setEmployeeEmail(employeeEmail);
        this.setManagerID(managerID);

    }

    public WorkShiftBean(String shiftDate, int managerID){

        this.setShiftDate(shiftDate);
        this.setManagerID(managerID);
    }

    public WorkShiftBean(ShiftSlots shiftTime, String employeeName, String employeeSurname, String employeeEmail, ContractTypes employeeContract) throws InvalidInputException{
        this.setShiftTime(shiftTime);
        this.setEmployeeName(employeeName);
        this.setEmployeeSurname(employeeSurname);
        this.setEmployeeContract(employeeContract);
        this.setEmployeeEmail(employeeEmail);
    }
    public void setShiftTime(ShiftSlots shiftTime) {
        this.shiftTime = shiftTime;
    }

    public ShiftSlots getShiftTime() {
        return this.shiftTime;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftDate() {
        return this.shiftDate;
    }

    public void setEmployeeName(String employeeName) throws InvalidInputException{
        if(isValidString(employeeName)) {
            this.employeeName = employeeName;
        }else throw new InvalidInputException("Invalid employee name");
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeSurname(String employeeSurname) throws InvalidInputException{
        if(isValidString(employeeSurname)) {
            this.employeeSurname = employeeSurname;
        }else throw new InvalidInputException("Invalid employee surname");
    }

    public String getEmployeeSurname() {
        return this.employeeSurname;
    }

    public void setEmployeeContract(ContractTypes employeeContract) {
        this.employeeContract = employeeContract;
    }

    public ContractTypes getEmployeeContract() {
        return this.employeeContract;
    }

    public void setEmployeeEmail(String employeeEmail) throws InvalidInputException{
        if(isValidEmail(employeeEmail)) {
            this.employeeEmail = employeeEmail;
        }else throw new InvalidInputException("Invalid email");
    }

    public String getEmployeeEmail() {
        return this.employeeEmail;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }

    private boolean isValidString(String inputString){
        return !(inputString.isEmpty());
    }

    private boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        if(email == null) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}