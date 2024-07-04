package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.EmployeeDao;
import it.florentino.dark.timeplanapp.model.entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListController {

    public List<EmployeeBean> loadEmployeeList(List<EmployeeBean> employeeBeanList) throws ServiceException, InvalidInputException {

        List<EmployeeBean> checkedList = new ArrayList<>();
        Employee newEmployee;


        try{
            EmployeeDao dao = new EmployeeDao();
            for(EmployeeBean bean: employeeBeanList){

                newEmployee = new Employee(bean.getName(), bean.getSurname(), bean.getContractType(), bean.getManagerID());
                newEmployee = dao.insertEmployee(newEmployee);
                bean = new EmployeeBean(newEmployee.getName(), newEmployee.getSurname(), newEmployee.getContractType(), newEmployee.getManagerID());
                checkedList.add(bean);

            }
        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return checkedList;

    }


}
