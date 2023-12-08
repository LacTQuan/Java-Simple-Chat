package org.example;

/* SERVER – may enhance to work for multiple clients */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Server {
    private ServerSocket server;
    private List<Socket> clients;
    private int port = 1234;
    private Object clientsLock = new Object();

    public Server() {
        try {
            server = new ServerSocket(port);
            System.out.println("ServerSocket is created " + server);
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
    }

    public void startListeningForConnections() {
        clients = Collections.synchronizedList(new ArrayList<>());
        Thread listenThread = new Thread(new ListenForConnections());
        listenThread.start();
    }

    public void broadcast(String msg) {
        synchronized (clientsLock) {
            for (Iterator<Socket> iterator = clients.iterator(); iterator.hasNext(); ) {
                Socket client = iterator.next();
                try {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    pw.println(msg);
                } catch (IOException e) {
                    iterator.remove();
                }
            }
        }
    }

    private class ListenForConnections implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Waiting for connect request...");
                    Socket client = server.accept();
                    synchronized (clientsLock) {
                        clients.add(client);
                    }
                    System.out.println("Connect request is accepted...");
                    String clientHost = client.getInetAddress().getHostAddress();
                    int clientPort = client.getPort();
                    System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                    // Start a new thread to receive messages from this client
                    Thread receiveThread = new Thread(() -> receiveMessages(client));
                    receiveThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void receiveMessages(Socket client) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                synchronized (clientsLock) {
                    clients.remove(client);
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startListeningForConnections();
    }
}


