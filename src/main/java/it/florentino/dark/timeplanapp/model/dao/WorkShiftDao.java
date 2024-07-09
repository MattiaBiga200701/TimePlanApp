package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.WorkShift;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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

            this.cs = this.conn.prepareCall("{ call work_shift_insertion(?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(newWorkshift);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return newWorkshift;

    }

    public void deleteWorkShift(WorkShift workShiftToDelete) throws DAOException {

        try{

            this.cs = this.conn.prepareCall("{ call delete_work_shift(?, ?, ?, ?, ?, ?)}");
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
        int managerID = newWorkshift.getEmployee().getManagerID();
        String shiftDate = newWorkshift.getShiftDate();
        int shiftCount;

        try{
            this.cs = this.conn.prepareCall("{call shiftCount(?, ?, ?, ?, ?, ?)}");
            this.cs.setString(1, shiftDate);
            this.cs.setString(2, employeeName);
            this.cs.setString(3, employeeSurname);
            this.cs.setString(4,employeeContract);
            this.cs.setInt(5, managerID);
            this.cs.registerOutParameter(6, Types.NUMERIC);
            this.cs.executeQuery();
            shiftCount = this.cs.getInt(6);
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

    private void setParametersFromEntity(WorkShift entity) throws SQLException{

        String employeeName = entity.getEmployee().getName();
        String employeeSurname =entity.getEmployee().getSurname();
        String employeeContract = entity.getEmployee().getContractType().getId();
        int managerID = entity.getEmployee().getManagerID();

        this.cs.setString(1, entity.getShiftDate());
        this.cs.setString(2, entity.getShiftTime().getId());
        this.cs.setString(3, employeeName);
        this.cs.setString(4, employeeSurname);
        this.cs.setString(5, employeeContract);
        this.cs.setInt(6, managerID);
    }
}
