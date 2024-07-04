package it.florentino.dark.timeplanapp;

public class Session {

    private String email;
    private String username;
    private int managerID;

    public Session(String email, String username, int managerID){
        this.setEmail(email);
        this.setUsername(username);
        this.setManagerID(managerID);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }
}
