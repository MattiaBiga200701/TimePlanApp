package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.utils.Role;

public class UserBean {
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserBean(String username, String email, String password, Role role){
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return role;
    }
}
