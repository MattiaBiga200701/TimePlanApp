package it.florentino.dark.timeplanapp.observer;

import it.florentino.dark.timeplanapp.beans.UserBean;


import java.util.ArrayList;
import java.util.List;


public abstract class Subject {

    private final List<Observer> observers;

    protected Subject(){
        this.observers = new ArrayList<>();
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
