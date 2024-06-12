package it.florentino.dark.timeplanapp.exceptions;

public class NotUniqueEmailException extends Exception{

    public NotUniqueEmailException() { super("Email already exist"); }
}
