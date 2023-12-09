package org.example.observer;

import org.example.Message;

public interface Observer {
    void update(Message message);
}
