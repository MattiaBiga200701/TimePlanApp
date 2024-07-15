package it.florentino.dark.timeplanapp.model.entities;

import it.florentino.dark.timeplanapp.utils.enumaration.Role;

public class Notification {


    private String message;

    private Role role;

    private int managerID;

    public Notification(String message, Role role, int managerID){

        this.setMessage(message);
        this.setRole(role);
        this.setManagerID(managerID);

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }

    public void setManagerID(int managerID) {
            this.managerID = managerID;
    }

    public int getManagerID() {
            return this.managerID;
    }


}