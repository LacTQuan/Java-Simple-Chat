package org.example;

import java.io.*;
import java.net.Socket;
import org.example.observer.Observer;

public class ClientHandler implements Runnable, Observer {
    private Server server;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
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
//                System.out.println(msg);
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
//            System.out.println(message.getUsername() + ": " + message.getMsg());
            pw.println(message.getUsername() + ": " + message.getMsg());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }
}
