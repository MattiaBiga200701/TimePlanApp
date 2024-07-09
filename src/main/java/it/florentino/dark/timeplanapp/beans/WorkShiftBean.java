package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;

public class WorkShiftBean {

    private ShiftSlots shiftTime;
    private String shiftDate;
    private String employeeName;
    private String employeeSurname;
    private ContractTypes employeeContract;

    private int managerID;

    public WorkShiftBean(ShiftSlots shiftTime, String shiftDate, String employeeName, String employeeSurname, ContractTypes employeeContract, int managerID) throws InvalidInputException{

        this.setShiftTime(shiftTime);
        this.setShiftDate(shiftDate);
        this.setEmployeeName(employeeName);
        this.setEmployeeSurname(employeeSurname);
        this.setEmployeeContract(employeeContract);
        this.setManagerID(managerID);

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

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }

    private boolean isValidString(String inputString){
        return inputString.isEmpty();
    }


}