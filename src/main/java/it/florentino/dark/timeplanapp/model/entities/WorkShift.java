package it.florentino.dark.timeplanapp.model.entities;


import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;



public class WorkShift {


    private ShiftSlots shiftTime;

    private String shiftDate;

    private Employee employee;

    public WorkShift(ShiftSlots shiftTime, String shiftDate, Employee employee){
        this.setShiftTime(shiftTime);
        this.setShiftDate(shiftDate);
        this.setEmployee(employee);
    }

    public WorkShift(String shiftDate, Employee employee){

        this.setShiftDate(shiftDate);
        this.setEmployee(employee);

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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
