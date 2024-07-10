package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.EmployeeDao;
import it.florentino.dark.timeplanapp.model.dao.WorkShiftDao;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.model.entities.WorkShift;


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

    public WorkShiftBean insertWorkShift(WorkShiftBean workShiftBean) throws ServiceException, InvalidInputException{


        String employeeName = workShiftBean.getEmployeeName();
        String employeeSurname = workShiftBean.getEmployeeSurname();
        ContractTypes employeeContract = workShiftBean.getEmployeeContract();
        int employeeMangerID = workShiftBean.getManagerID();

        Employee employeeAssigned = new Employee(employeeName, employeeSurname, employeeContract, employeeMangerID);

        WorkShift newWorkShift = new WorkShift(workShiftBean.getShiftTime(), workShiftBean.getShiftDate(), employeeAssigned);

        int shiftCount;

        try{
            WorkShiftDao dao = new WorkShiftDao();

            shiftCount = dao.shiftCount(newWorkShift);

            if(newWorkShift.getEmployee().getContractType() == ContractTypes.PART_TIME && shiftCount == 4){
                throw new InvalidInputException("Maximum number of shifts reached for part-time");
            }else if(newWorkShift.getEmployee().getContractType() == ContractTypes.FULL_TIME && shiftCount == 8){
                throw new InvalidInputException("Maximum number of shifts reached for full-time");
            }

            if(dao.isShiftAssigned(newWorkShift)){
                throw new InvalidInputException("Shift already assigned");
            }


            newWorkShift = dao.insertWorkShift(newWorkShift);

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        employeeName = newWorkShift.getEmployee().getName();
        employeeSurname = newWorkShift.getEmployee().getSurname();
        employeeContract = newWorkShift.getEmployee().getContractType();
        employeeMangerID = newWorkShift.getEmployee().getManagerID();

        return new WorkShiftBean(newWorkShift.getShiftTime(), newWorkShift.getShiftDate(), employeeName, employeeSurname, employeeContract, employeeMangerID);
    }

    public List<WorkShiftBean> workShiftReader(WorkShiftBean workShiftBeanToRead) throws ServiceException, InvalidInputException{

        String shiftDate = workShiftBeanToRead.getShiftDate();
        int managerID = workShiftBeanToRead.getManagerID();

        Employee employeeToRead = new Employee(managerID);

        WorkShift workShiftToRead = new WorkShift(shiftDate, employeeToRead);

        List<WorkShift> workShiftList;

        List<WorkShiftBean> workShiftBeanList = new ArrayList<>();


        String employeeName;
        String employeeSurname;
        ContractTypes employeeContract;


        try{

            WorkShiftDao dao = new WorkShiftDao();
            workShiftList = dao.readWorkShiftList(workShiftToRead);

            if(workShiftList == null){
                throw new InvalidInputException("Schedulation empty for this date");
            }else {

                for (WorkShift workShiftRead : workShiftList) {


                    employeeName = workShiftRead.getEmployee().getName();
                    employeeSurname = workShiftRead.getEmployee().getSurname();
                    employeeContract = workShiftRead.getEmployee().getContractType();

                    WorkShiftBean workShiftBeanRead = new WorkShiftBean(workShiftRead.getShiftTime(), employeeName, employeeSurname, employeeContract);

                    workShiftBeanList.add(workShiftBeanRead);

                }
            }


        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return workShiftBeanList;


    }

}
