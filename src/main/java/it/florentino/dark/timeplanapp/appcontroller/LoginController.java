package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;

public class LoginController {

    private User user = null;

    public void authenticate(LoginBean credentials) throws ServiceException, CredentialException{

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        this.user = new User(username, null, password, null);
        LoginDao loginDao = new LoginDao();
        try {
            loginDao.loginProcedure(this.user);
            if(this.user.getRole() == null){
                throw new CredentialException();
            }
            switch(this.user.getRole()){
                case MANAGER -> System.out.println("Manager logged");
                case EMPLOYEE -> System.out.println("Employee logged");
                default -> throw new CredentialException();
            }
        }catch(DAOException e){
            throw new ServiceException();
        }

    }

}
