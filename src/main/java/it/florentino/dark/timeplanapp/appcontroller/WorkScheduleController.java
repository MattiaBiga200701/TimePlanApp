package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.EmployeeDao;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleController {


    public List<EmployeeBean> loadEmployeeList(UserBean loggedBeanUser) throws ServiceException, InvalidInputException {

        List<EmployeeBean> employeeBeanList = new ArrayList<>();
        EmployeeBean storedEmployee;
        List<Employee> employeeList;
        User loggedUser = new User(loggedBeanUser.getManagerID());

        try {

            EmployeeDao dao = new EmployeeDao();
            employeeList = dao.readEmployeesList(loggedUser);

            for(Employee employee: employeeList){

                    storedEmployee = new EmployeeBean(employee.getName(), employee.getSurname(), employee.getContractType(), employee.getManagerID());
                    employeeBeanList.add(storedEmployee);
            }

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return employeeBeanList;
    }
}
