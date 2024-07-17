package it.florentino.dark.timeplanapp.observer;

import it.florentino.dark.timeplanapp.beans.UserBean;

public class MessageSubject extends Subject{

    private static MessageSubject instance = null;

    private UserBean sender;

    private MessageSubject(){}

    public static MessageSubject getInstance(){
        if(MessageSubject.instance == null){
            MessageSubject.instance = new MessageSubject();
        }
        return MessageSubject.instance;
    }


    public synchronized void setState(UserBean sender){

        this.setSender(sender);
        this.notifyObserver();


    }

    public synchronized UserBean getState(){
        return this.getSender();
    }


    private  void setSender(UserBean sender) {
        this.sender = sender;
    }

    private UserBean getSender() {
        return this.sender;
    }

}
