package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;


public class RegistrationController {

    User user = null;
    public void insertUser(UserBean newUser) throws ServiceException, NotUniqueEmailException {

        this.createUserFromBean(newUser);

        try{
            LoginDao loginDao = new LoginDao();
            loginDao.registrationProcedure(this.getUser());
        }catch(DAOException e){
            throw new ServiceException();
        }

    }

    public UserBean checkManagerID(UserBean user) throws ServiceException, CredentialException{

        User managerAssociated;
        this.createUserFromBean(user);

        try{
            LoginDao loginDao = new LoginDao();
            managerAssociated = loginDao.getManagerAssociatedTo(this.getUser());
        }catch(DAOException e ){
            throw new ServiceException();
        }

        if(managerAssociated.getUsername() != null ) {
            return this.createBeanFromUser(managerAssociated);
        }else throw new CredentialException("MangerID not found");

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
