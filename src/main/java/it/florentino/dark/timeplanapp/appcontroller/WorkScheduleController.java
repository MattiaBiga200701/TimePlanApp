package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.NotificationBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.EmployeeDao;
import it.florentino.dark.timeplanapp.model.dao.NotificationDao;
import it.florentino.dark.timeplanapp.model.dao.WorkShiftDao;
import it.florentino.dark.timeplanapp.model.entities.Employee;
import it.florentino.dark.timeplanapp.model.entities.Notification;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.observer.MessageSubject;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.model.entities.WorkShift;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

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

                    storedEmployee = new EmployeeBean(employee.getName(), employee.getSurname(), employee.getContractType(), employee.getEmail(), employee.getManagerID());
                    employeeBeanList.add(storedEmployee);
            }

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return employeeBeanList;
    }

    public WorkShiftBean insertWorkShift(WorkShiftBean workShiftBean) throws ServiceException, InvalidInputException{

        WorkShift newWorkShift = this.createWorkShiftFromBean(workShiftBean);

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

        String employeeName = newWorkShift.getEmployee().getName();
        String employeeSurname = newWorkShift.getEmployee().getSurname();
        ContractTypes employeeContract = newWorkShift.getEmployee().getContractType();
        String employeeEmail = newWorkShift.getEmployee().getEmail();
        int employeeMangerID = newWorkShift.getEmployee().getManagerID();

        return new WorkShiftBean(newWorkShift.getShiftTime(), newWorkShift.getShiftDate(), employeeName, employeeSurname, employeeContract, employeeEmail, employeeMangerID);
    }

    public void removeWorkShift(WorkShiftBean workShiftBeanToRemove) throws ServiceException {


        WorkShift workShiftToRemove = this.createWorkShiftFromBean(workShiftBeanToRemove);

        try{

            WorkShiftDao dao = new WorkShiftDao();
            dao.deleteWorkShift(workShiftToRemove);

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

    public List<WorkShiftBean> workShiftReader(WorkShiftBean workShiftBeanToRead, UserBean loggedUser) throws ServiceException, InvalidInputException{

        String shiftDate = workShiftBeanToRead.getShiftDate();
        int managerID = workShiftBeanToRead.getManagerID();

        Employee employeeToRead = new Employee(managerID);

        WorkShift workShiftToRead = new WorkShift(shiftDate, employeeToRead);

        Role requesterRole = loggedUser.getRole();
        String requesterEmail = loggedUser.getEmail();

        User requester = new User(requesterRole, requesterEmail);

        List<WorkShift> workShiftList;

        List<WorkShiftBean> workShiftBeanList = new ArrayList<>();


        String employeeName;
        String employeeSurname;
        ContractTypes employeeContract;
        String employeeEmail;


        try{

            WorkShiftDao dao = new WorkShiftDao();
            workShiftList = dao.readWorkShiftList(workShiftToRead, requester);

            if(workShiftList.isEmpty()){
                throw new InvalidInputException("Schedulation empty for this date");
            }else {

                for (WorkShift workShiftRead : workShiftList) {


                    employeeName = workShiftRead.getEmployee().getName();
                    employeeSurname = workShiftRead.getEmployee().getSurname();
                    employeeContract = workShiftRead.getEmployee().getContractType();
                    employeeEmail = workShiftRead.getEmployee().getEmail();

                    WorkShiftBean workShiftBeanRead = new WorkShiftBean(workShiftRead.getShiftTime(), employeeName, employeeSurname, employeeEmail, employeeContract);

                    workShiftBeanList.add(workShiftBeanRead);

                }
            }


        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return workShiftBeanList;
    }

    public WorkShift createWorkShiftFromBean(WorkShiftBean workShiftBean){

        String employeeName = workShiftBean.getEmployeeName();
        String employeeSurname = workShiftBean.getEmployeeSurname();
        ContractTypes employeeContract = workShiftBean.getEmployeeContract();
        String employeeEmail = workShiftBean.getEmployeeEmail();
        int managerID = workShiftBean.getManagerID();

        Employee employee = new Employee(employeeName, employeeSurname,employeeContract, employeeEmail, managerID);

        return  new WorkShift(workShiftBean.getShiftTime(), workShiftBean.getShiftDate(), employee);
    }

    public List<NotificationBean> readMessages(UserBean readerBean) throws ServiceException, InvalidInputException {

        Role readerRole = readerBean.getRole();
        int readerManagerID = readerBean.getManagerID();

        User reader = new User(readerRole, readerManagerID);

        List<Notification> notifications;

        List<NotificationBean> notificationsBean = new ArrayList<>();

        try{

            NotificationDao dao = new NotificationDao();
            notifications = dao.readNotifications(reader);

            if(notifications == null){
                throw new InvalidInputException("You have no Notification");
            }

            for(Notification notificationRead : notifications){

                String message = notificationRead.getMessage();
                Role role = notificationRead.getRole();
                int managerID = notificationRead.getManagerID();

                NotificationBean notificationBeanRead = new NotificationBean(message, role, managerID);

                notificationsBean.add(notificationBeanRead);

            }

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return notificationsBean;
    }

    public NotificationBean insertMessage(NotificationBean newNotificationBean, UserBean sender) throws ServiceException, InvalidInputException {

        String message = newNotificationBean.getMessage();
        Role role = newNotificationBean.getRole();
        int managerID = newNotificationBean.getManagerID();

        Notification newNotification = new Notification(message, role, managerID);

        try{

            NotificationDao dao = new NotificationDao();
            newNotification = dao.insertNotification(newNotification);
            this.notifyEmployees(sender);

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }



        message = newNotification.getMessage();
        role = newNotificationBean.getRole();
        managerID = newNotificationBean.getManagerID();

        return new NotificationBean(message, role, managerID);
    }

    private void notifyEmployees(UserBean sender){
        MessageSubject spreader = MessageSubject.getInstance();
        spreader.setSender(sender);
    }

}
