package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.CredentialException;

public class LoginBean {

    private String email;
    private String password;
    public LoginBean(String email, String password) throws CredentialException{
        setEmail(email);
        setPassword(password);
    }

    public void setEmail(String email) throws CredentialException{
        if(this.isValidEmail(email)) {
            this.email = email;
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

    public String getEmail() {
        return this.email;
    }


    public String getPassword(){
        return this.password;
    }

    private boolean isValidEmail(String email){
        return email != null;
    }
    private boolean isValidPassword(String password){
        return password != null;
    }

}
