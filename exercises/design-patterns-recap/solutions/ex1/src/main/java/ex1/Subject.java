package ex1;

import java.util.ArrayList;

/**
 * This class represents an observable object. It can be subclassed to represent an object that the application wants to have observed.
 *
 * A subject can have one or more observers. An observer can be any object that implements the Observer interface.
 */
public abstract class Subject {
    private ArrayList<Observer> observers;

    Subject(){
        observers = new ArrayList<>();
    }
    void registerObserver(Observer o) {
        observers.add(o);
    }

    void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    void notifyObservers(String data) {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(data);
        }
    }
}
