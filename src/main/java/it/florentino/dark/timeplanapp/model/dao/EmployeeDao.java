package it.florentino.dark.timeplanapp.model.dao;


import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeDao {

    private final Connection conn;
    private CallableStatement cs;

    public EmployeeDao() throws DAOException{
        try{
            this.conn = ConnectionManager.getConnection();
        }catch(ConnectionException e){
            throw new DAOException(e.getMessage());
        }
    }

    public Employee insertEmployee(Employee newEmployee) throws DAOException{

        String name = newEmployee.getName();
        String surname = newEmployee.getSurname();
        String contractType = newEmployee.getContractType().getId();
        int managerID = newEmployee.getManagerID();


        try{

            this.cs = this.conn.prepareCall("{ call employee_insertion(?, ?, ?, ?)}");
            this.cs.setString(1, name);
            this.cs.setString(2, surname);
            this.cs.setString(3, contractType);
            this.cs.setInt(4,managerID);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return new Employee(name, surname, ContractTypes.fromString(contractType), managerID);
    }

}
