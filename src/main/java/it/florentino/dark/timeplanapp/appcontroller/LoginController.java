package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.LoginBean;
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

public class LoginController {



    public UserBean authenticate(LoginBean credentials) throws ServiceException, CredentialException{

        String email = credentials.getEmail();
        String password = credentials.getPassword();
        String username;
        int managerID;
        Role role;
        User user = new User(email, password);

        DaoSetter.setDao("MySQL");
        UserDao dao;


        try {

            if(DaoSetter.getDao().equals("CSV")) {
                dao = new UserDaoCSV();
            }else {
                dao = new UserDaoMySQL();
            }

            user = dao.loginProcedure(user);

            if(user.getRole() == null){
                throw new CredentialException();
            }


        }catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }

        username = user.getUsername();
        role = user.getRole();
        managerID = user.getManagerID();


        return new UserBean(username, email, password, role, managerID);

    }

}
