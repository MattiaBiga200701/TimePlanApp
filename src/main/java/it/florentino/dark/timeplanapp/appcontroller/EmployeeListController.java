package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.EmployeeDao;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;



public class EmployeeListController {

    public EmployeeBean insertEmployee(EmployeeBean newEmployeeBean) throws ServiceException, InvalidInputException{

        Employee newEmployee = this.createEmployeeFromBean(newEmployeeBean);

        try{

            EmployeeDao dao = new EmployeeDao();
            if(dao.isEmployeeRegistered(newEmployee)) {
                dao.insertEmployee(newEmployee);
            }else throw new InvalidInputException("Employee not registered");

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }catch(NotUniqueEmailException e){
            throw new InvalidInputException(e.getMessage());
        }

        return newEmployeeBean;


    }

    public void removeEmployee(EmployeeBean employeeBeanToRemove) throws ServiceException{

        Employee employeeToRemove = this.createEmployeeFromBean(employeeBeanToRemove);
        try{

            EmployeeDao dao = new EmployeeDao();
            dao.removeEmployee(employeeToRemove);

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

    public Employee createEmployeeFromBean(EmployeeBean employeeBean){

        String employeeName = employeeBean.getName();
        String employeeSurname = employeeBean.getSurname();
        ContractTypes employeeContract = employeeBean.getContractType();
        String employeeEmail = employeeBean.getEmail();
        int managerID = employeeBean.getManagerID();

        return new Employee(employeeName, employeeSurname, employeeContract, employeeEmail, managerID);

    }


}
