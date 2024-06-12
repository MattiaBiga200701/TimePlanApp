package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;


public class RegistrationController {

    User user = null;
    public void insertUser(UserBean newUser) throws ServiceException {

        String username = newUser.getUsername();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        Role role = newUser.getRole();
        int managerID = newUser.getManagerID();

        this.setUser(new User(username , email, password, role, managerID));

        try{
            LoginDao loginDao = new LoginDao();
            loginDao.registrationProcedure(this.getUser());
        }catch(DAOException e){
            throw new ServiceException();
        }



    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
