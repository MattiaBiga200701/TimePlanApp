package it.florentino.dark.timeplanapp.exceptions;

public class SetSceneException extends Exception {

    public SetSceneException(String message, Throwable cause) { super(message, cause); }

    public SetSceneException(String message) { super( "SetSceneException: " + message); }
}
