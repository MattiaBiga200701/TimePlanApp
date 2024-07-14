package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.UserDao;
import it.florentino.dark.timeplanapp.model.dao.UserDaoCSV;
import it.florentino.dark.timeplanapp.model.dao.UserDaoMySQL;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.DaoSetter;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;


public class RegistrationController {

    User user = null;
    public void insertUser(UserBean newUser) throws ServiceException {

        this.createUserFromBean(newUser);
        UserDao dao;
        try{

            if(DaoSetter.getDao().equals("CSV")) {
                dao = new UserDaoCSV();
            }else {
                dao = new UserDaoMySQL();
            }

            dao.registrationProcedure(this.getUser());
        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

    public UserBean checkManagerID(UserBean user) throws ServiceException, CredentialException{

        User managerAssociated;
        this.createUserFromBean(user);
        UserDao dao;
        try{

            if(DaoSetter.getDao().equals("CSV")) {
                dao = new UserDaoCSV();
            }else {
                dao = new UserDaoMySQL();
            }

            managerAssociated = dao.getManagerAssociatedTo(this.getUser());
        }catch(DAOException e ){
            throw new ServiceException(e.getMessage());
        }

        if(managerAssociated.getUsername() != null) {
            return this.createBeanFromUser(managerAssociated);
        }else throw new CredentialException("MangerID not found");

    }

    public UserBean createManagerID(UserBean newUser) throws ServiceException, CredentialException{

        UserDao dao;
        try{

            if(DaoSetter.getDao().equals("CSV")) {
                dao = new UserDaoCSV();
            }else {
                dao = new UserDaoMySQL();
            }

            this.createUserFromBean(newUser);
            this.user = dao.createManagerID(this.user);
        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return this.createBeanFromUser(this.user);

    }

    public boolean isEmailUnique(UserBean newUserBean) throws ServiceException{

        String email = newUserBean.getEmail();
        User newUser = new User(email);
        boolean check;
        UserDao dao;
        DaoSetter.setDao("MySQL");
        try{

            if(DaoSetter.getDao().equals("CSV")) {
                dao = new UserDaoCSV();
            }else {
                dao = new UserDaoMySQL();
            }

            check = dao.isEmailUnique(newUser);

        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        return check;
    }

    public void createUserFromBean(UserBean user){

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        Role role = user.getRole();
        int managerID = user.getManagerID();

        this.setUser(new User(username, email, password, role, managerID));
    }

    public UserBean createBeanFromUser(User user) throws CredentialException {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        Role role = user.getRole();
        int managerID = user.getManagerID();

        return  new UserBean(username, email, password, role, managerID);

    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
