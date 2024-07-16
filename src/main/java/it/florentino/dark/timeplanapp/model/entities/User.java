package it.florentino.dark.timeplanapp.model.entities;

import it.florentino.dark.timeplanapp.utils.enumaration.Role;

public class User {
    private String username;
    private String email;
    private String password;

    private Role role;

    private int managerID;

    public User(String username, String email, String password, Role role, int managerID){
        this(username,email,password,role);
        this.setManagerID(managerID);
    }

    public User(String username, String email, String password, Role role){
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
    }

    public User(String email, String password){
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(Role role,String email){
        this.setRole(role);
        this.setEmail(email);
    }

    public User(Role role, int managerID){
        this.setRole(role);
        this.setManagerID(managerID);
    }

    public User(int managerID){
        this.setManagerID(managerID);
    }

    public User(String email){
        this.setEmail(email);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setManagerID(int managerID){
        this.managerID = managerID;
    }
    public int getManagerID(){ return this.managerID; }

    public String getUsername() {
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() { return this.role; }
}
