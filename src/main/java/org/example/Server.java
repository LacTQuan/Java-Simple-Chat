package org.example;

import org.example.observer.Observer;
import org.example.observer.Subject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Server implements Subject {
    private ServerSocket serverSocket;
    private List<Observer> clients;

    public Server() {
        try {
            int port = 1234;
            serverSocket = new ServerSocket(port);
            clients = Collections.synchronizedList(new ArrayList<>());
            System.out.println("Server is running on port " + port);
            start();
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    public void start() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Client connected " + client.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(this, client);
                addObserver(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        clients.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        clients.remove(observer);
        System.out.println("Client disconnected: " + ((ClientHandler) observer).getUsername());
    }

    @Override
    public void notifyObservers(Message message) {
        Iterator<Observer> it = clients.iterator();
        while (it.hasNext()) {
            Observer o = it.next();
            o.update(message);
        }
    }

    @Override
    public void notifyDisconnect(Message message, Observer observer) {
        Iterator<Observer> it = clients.iterator();
        while (it.hasNext()) {
            Observer o = it.next();
            if (o != observer) {
                o.update(message);
            }
        }
    }
}


