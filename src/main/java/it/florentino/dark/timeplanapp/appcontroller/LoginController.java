package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

public class LoginController {



    public UserBean authenticate(LoginBean credentials) throws ServiceException, CredentialException{

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String email;
        int managerID;
        Role role;
        User user = new User(username, password);

        try {
            LoginDao loginDao = new LoginDao();
            user = loginDao.loginProcedure(user);

            if(user.getRole() == null){
                throw new CredentialException();
            }


        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        email = user.getEmail();
        role = user.getRole();
        managerID = user.getManagerID();


        return new UserBean(username, email, password, role, managerID);

    }

}
