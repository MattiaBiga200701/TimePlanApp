package it.florentino.dark.timeplanapp.Observer;

import it.florentino.dark.timeplanapp.beans.UserBean;

public class MessageSubject extends Subject{

    private static MessageSubject instance = null;




    private MessageSubject(){}

    public static MessageSubject getInstance(){
        if(MessageSubject.instance == null){
            MessageSubject.instance = new MessageSubject();
        }
        return MessageSubject.instance;
    }


    public void setSender(UserBean sender) {
        this.notifyObserver(sender);
    }

}
