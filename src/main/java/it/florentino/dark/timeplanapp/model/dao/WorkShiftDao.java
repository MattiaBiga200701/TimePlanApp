package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.WorkShift;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

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

            this.cs = conn.prepareCall("{work_shift_insertion(?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(newWorkshift);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException("DAO error: " + e.getMessage());
        }

        return newWorkshift;

    }

    public void deleteWorkShift(WorkShift workShiftToDelete) throws DAOException {

        try{

            this.cs = conn.prepareCall("{delete_work_shift(?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(workShiftToDelete);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException("DAO error: " + e.getMessage());
        }
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
