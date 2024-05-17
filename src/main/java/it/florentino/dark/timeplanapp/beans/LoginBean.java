package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.CredentialException;

public class LoginBean {

    private String username;
    private String password;
    public LoginBean(String username, String password) throws CredentialException{
        setUsername(username);
        setPassword(password);
    }

    public void setUsername(String username) throws CredentialException{
        if(this.isValidUsername(username)) {
            this.username = username;
        }else {
            throw new CredentialException();
        }
    }
    public void setPassword(String password) throws CredentialException{
        if(this.isValidPassword(password)) {
            this.password = password;
        }else{
            throw new CredentialException();
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    private boolean isValidUsername(String username){
        return username != null;
    }
    private boolean isValidPassword(String password){
        return password != null;
    }

}
