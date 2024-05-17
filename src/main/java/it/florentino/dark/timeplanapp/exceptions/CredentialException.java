package it.florentino.dark.timeplanapp.exceptions;

public class CredentialException extends Exception{

    public CredentialException(){ super("Invalid Credentials;"); }
    public CredentialException(String message) { super(message); }
    public CredentialException(String message, Throwable cause) { super(message, cause); }
}
