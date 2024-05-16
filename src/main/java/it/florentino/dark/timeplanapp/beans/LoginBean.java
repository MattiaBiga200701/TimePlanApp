package it.florentino.dark.timeplanapp.beans;

public class LoginBean {

    private String username;
    private String password;
    public LoginBean(String username, String password){
        setUsername(username);
        setPassword(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

}
