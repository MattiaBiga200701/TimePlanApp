package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NotificationBean {

    private String message;

    private Role role;

    private int managerID;

    public NotificationBean(String message, Role role, int managerID) throws InvalidInputException {

        this.setMessage(message);
        this.setRole(role);
        this.setManagerID(managerID);

    }

    public void setMessage(String message) throws InvalidInputException{
        if(isValidMessage(message)) {
            this.message = message;
        }else throw new InvalidInputException("Invalid message");
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

    public void setManagerID(int managerID) throws InvalidInputException {
        if(managerID >= 100000 && managerID <= 999999) {
            this.managerID = managerID;
        }else throw new InvalidInputException("Invalid ManagerID");
    }

    public int getManagerID() {
        return this.managerID;
    }

    private boolean isValidMessage(String message){
        String messageFormat = "^\\S+(\\s+\\S+)*\\s+\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(messageFormat);
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }
}
