package org.example;

import org.example.observer.Observer;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, Observer {
    private final Server server;
    private final Socket clientSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private String username;

    public ClientHandler(Server server, Socket clientSocket) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.username = "Anonymous";
    }

    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String msg = br.readLine();
                if (msg == null) break;
                this.username = msg.split(": ")[0];
                server.notifyObservers(new Message(msg));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//            server.notifyDisconnect(new Message(username + ": has left the chat."), this);
            server.removeObserver(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Message message) {
        try {
            PrintWriter pw = new PrintWriter(outputStream, true);
            pw.println(message.getUsername() + ": " + message.getMsg());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }
}
