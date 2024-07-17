package it.florentino.dark.timeplanapp.observer;



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

    protected void notifyObserver(){

        for(Observer observer : this.observers){

            observer.update();

        }
    }
}
