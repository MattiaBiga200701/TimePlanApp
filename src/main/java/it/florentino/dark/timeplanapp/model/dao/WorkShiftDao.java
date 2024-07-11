package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.model.entities.WorkShift;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkShiftDao {

    private final Connection conn;
    private CallableStatement cs;

    public WorkShiftDao() throws DAOException{
        try {

            this.conn = ConnectionManager.getConnection();

        }catch(ConnectionException e){
            throw new DAOException("Connection error: " + e.getMessage());
        }
    }
    public WorkShift insertWorkShift(WorkShift newWorkshift) throws DAOException {

        try{

            this.cs = this.conn.prepareCall("{ call work_shift_insertion(?, ?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(newWorkshift);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return newWorkshift;

    }

    public void deleteWorkShift(WorkShift workShiftToDelete) throws DAOException {

        try{

            this.cs = this.conn.prepareCall("{ call delete_work_shift(?, ?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(workShiftToDelete);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public int shiftCount(WorkShift newWorkshift) throws DAOException{
        String employeeName = newWorkshift.getEmployee().getName();
        String employeeSurname = newWorkshift.getEmployee().getSurname();
        String employeeContract = newWorkshift.getEmployee().getContractType().getId();
        String employeeEmail = newWorkshift.getEmployee().getEmail();
        int managerID = newWorkshift.getEmployee().getManagerID();
        String shiftDate = newWorkshift.getShiftDate();
        int shiftCount;

        try{
            this.cs = this.conn.prepareCall("{call shiftCount(?, ?, ?, ?, ?, ?, ?)}");
            this.cs.setString(1, shiftDate);
            this.cs.setString(2, employeeName);
            this.cs.setString(3, employeeSurname);
            this.cs.setString(4,employeeContract);
            this.cs.setString(5,employeeEmail);
            this.cs.setInt(6, managerID);
            this.cs.registerOutParameter(7, Types.NUMERIC);
            this.cs.executeQuery();
            shiftCount = this.cs.getInt(7);
        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return shiftCount;

    }

    public boolean isShiftAssigned(WorkShift newWorkShift) throws DAOException{

        String shiftDate = newWorkShift.getShiftDate();
        String shiftTime = newWorkShift.getShiftTime().getId();
        int managerID = newWorkShift.getEmployee().getManagerID();
        int count;

        try{
            this.cs = conn.prepareCall("{call isShiftAssigned(?, ?, ?, ?)}");
            this.cs.setString(1, shiftDate);
            this.cs.setString(2, shiftTime);
            this.cs.setInt(3, managerID);
            this.cs.registerOutParameter(4, Types.INTEGER);
            this.cs.executeQuery();
            count = this.cs.getInt(4);

        }catch(SQLException e){

            throw new DAOException(e.getMessage());
        }

        if(count == 0){
            return false;
        }else return count == 1;
    }

    public List<WorkShift> readWorkShiftList(WorkShift workShift) throws DAOException{

        String shiftDate = workShift.getShiftDate();
        int managerID = workShift.getEmployee().getManagerID();

        List<WorkShift> workShiftList = new ArrayList<>();

        String shiftTime;
        String employeeName;
        String employeeSurname;
        String employeeContract;
        String employeeEmail;

        Employee employeeRead;
        WorkShift workShiftRead;

        try{

            this.cs = conn.prepareCall("{call read_work_shift_list(?, ?)}");
            this.cs.setString(1, shiftDate);
            this.cs.setInt(2, managerID);
            ResultSet rs = this.cs.executeQuery();

            if (!rs.isBeforeFirst()) {
                return Collections.emptyList();
            }

            while(rs.next()) {

                shiftTime = rs.getString(1);
                employeeName = rs.getString(2);
                employeeSurname = rs.getString(3);
                employeeContract = rs.getString(4);
                employeeEmail = rs.getString(5);

                employeeRead = new Employee(employeeName, employeeSurname, ContractTypes.fromString(employeeContract), employeeEmail, managerID);

                workShiftRead = new WorkShift(ShiftSlots.fromString(shiftTime), shiftDate, employeeRead);


                workShiftList.add(workShiftRead);

            }


        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

            return workShiftList;
    }

    private void setParametersFromEntity(WorkShift entity) throws SQLException{

        String employeeName = entity.getEmployee().getName();
        String employeeSurname =entity.getEmployee().getSurname();
        String employeeContract = entity.getEmployee().getContractType().getId();
        String employeeEmail = entity.getEmployee().getEmail();
        int managerID = entity.getEmployee().getManagerID();

        this.cs.setString(1, entity.getShiftDate());
        this.cs.setString(2, entity.getShiftTime().getId());
        this.cs.setString(3, employeeName);
        this.cs.setString(4, employeeSurname);
        this.cs.setString(5, employeeContract);
        this.cs.setString(6,employeeEmail);
        this.cs.setInt(7, managerID);
    }
}
