package org.example.observer;

import org.example.Message;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Message message);
    void notifyDisconnect(Message message, Observer observer);
}
