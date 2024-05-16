package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.model.dao.LoginDao;
import it.florentino.dark.timeplanapp.model.entities.User;

public class LoginController {

    private LoginDao loginDao;
    private User user;

    public void authenticate(LoginBean credentials){
        String username = credentials.getUsername();
        String password = credentials.getPassword();

    }

}
