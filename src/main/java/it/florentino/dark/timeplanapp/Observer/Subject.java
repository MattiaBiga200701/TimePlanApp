package it.florentino.dark.timeplanapp.Observer;

import it.florentino.dark.timeplanapp.beans.UserBean;


import java.util.Vector;

public abstract class Subject {

    private final Vector<Observer> observers;

    protected Subject(){
        this.observers = new Vector<>();
    }

    public void attach(Observer observer){

        this.observers.add(observer);

    }

    public void detach(Observer observer){

        this.observers.remove(observer);

    }

    protected void notifyObserver(UserBean sender){

        for(Observer observer : this.observers){

            observer.update(sender);

        }
    }
}
