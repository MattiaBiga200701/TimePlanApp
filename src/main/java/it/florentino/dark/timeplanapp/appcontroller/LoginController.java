package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;

public class LoginController {

    private User user;

    public void authenticate(LoginBean credentials) throws ServiceException{

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        User user = new User(username, null, password, null);
        LoginDao loginDao = new LoginDao();
        try {
            loginDao.loginProcedure(user);
        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }

}
