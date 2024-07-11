package it.florentino.dark.timeplanapp.model.dao;


import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void insertEmployee(Employee newEmployee) throws DAOException, NotUniqueEmailException{

        int status;

        try{

            this.cs = this.conn.prepareCall("{ call employee_insertion(?, ?, ?, ?, ?, ?)}");
            this.setParametersFromEntity(newEmployee);
            this.cs.registerOutParameter(6, Types.INTEGER);
            this.cs.executeQuery();
            status = this.cs.getInt(6);
            if(status == 1){
                throw new NotUniqueEmailException();
            }

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

    }

    public void removeEmployee(Employee employeeToRemove) throws DAOException{

        try{

            this.cs = conn.prepareCall("{call remove_employee(?)}");
            this.cs.setString(1, employeeToRemove.getEmail());
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

    }

    public List<Employee> readEmployeesList(User managerAssociated) throws DAOException{

        int managerID = managerAssociated.getManagerID();
        String name;
        String surname;
        ContractTypes contractType;
        String email;
        List<Employee> employeeList = new ArrayList<>();

        try{
            this.cs = this.conn.prepareCall("{call read_employee_list(?)}");
            this.cs.setInt(1, managerID);
            ResultSet rs = this.cs.executeQuery();

            while(rs.next()){
                name = rs.getString(1);
                surname = rs.getString(2);
                contractType = ContractTypes.fromString(rs.getString(3));
                email = rs.getString(4);
                managerID = rs.getInt(5);

                Employee employee = new Employee(name, surname, contractType, email,  managerID);
                employeeList.add(employee);
            }

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return employeeList;
    }

    public boolean isEmployeeRegistered(Employee newEmployee) throws DAOException{

        String email = newEmployee.getEmail();
        int managerID = newEmployee.getManagerID();
        int checkCount;

        try{
            this.cs = conn.prepareCall("call isEmployeeRegistered(?, ?, ?)");
            this.cs.setString(1, email);
            this.cs.setInt(2, managerID);
            this.cs.registerOutParameter(3, Types.INTEGER);
            this.cs.executeQuery();
            checkCount = this.cs.getInt(3);
        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        if(checkCount == 1){
            return true;
        }else if (checkCount == 0 ){
            return false;
        }
        return false;
    }


    private void setParametersFromEntity(Employee employee) throws SQLException{
        String name = employee.getName();
        String surname = employee.getSurname();
        String contractType = employee.getContractType().getId();
        String email = employee.getEmail();
        int managerID = employee.getManagerID();

        this.cs.setString(1, name);
        this.cs.setString(2, surname);
        this.cs.setString(3, contractType);
        this.cs.setString(4,email);
        this.cs.setInt(5,managerID);
    }

}
