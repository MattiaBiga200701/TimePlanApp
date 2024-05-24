package it.florentino.dark.timeplanapp.appcontroller;

import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

public class RegistrationController {

    User user = null;
    public void insertUser(UserBean newUser){

        String username = newUser.getUsername();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        Role role = newUser.getRole();

        this.setUser(new User(username , email, password, role));



    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
