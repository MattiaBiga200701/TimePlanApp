package it.florentino.dark.timeplanapp.beans;

import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean {
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserBean(String username, String email, String password, Role role) throws CredentialException{
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
    }

    public void setUsername(String username) throws CredentialException {
        if(username != null) {
            this.username = username;
        }else throw new CredentialException("Invalid username;");
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) throws CredentialException {

        if(this.isValidPassword(password)) {
            this.password = password;
        }else{
            throw new CredentialException("Invalid password (must be at least 8 characters long)");
        }
    }


    public void setEmail(String email) throws CredentialException {

        if(this.isValidEmail(email)) {
            this.email = email;
        }else{
            throw new CredentialException("Invalid Email");
        }
    }

    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

    private boolean isValidPassword(String password){
        return (password != null && password.length() >= 8);
    }

    private boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        if(email == null) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
