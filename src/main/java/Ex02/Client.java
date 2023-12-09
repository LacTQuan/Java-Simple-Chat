package Ex02;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket socket;
    private UI ui;
    private Thread receiveThread;

    public Client() {
    }

    public Client(UI ui) {
        this.ui = ui;
        try {
            int port = 1234;
            socket = new Socket(InetAddress.getLocalHost(), port);
            System.out.println("Client socket is created " + socket);
            receiveThread = new Thread(new Receive());
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message msg) {
        try {
            OutputStream clientOut = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(clientOut, true);
            pw.println(msg.getUsername() + ": " + msg.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            receiveThread.interrupt();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Receive implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (socket.isClosed()) break;
                    InputStream clientIn = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                    String msg = br.readLine();
                    Message message = new Message(msg.split(": ")[0], msg.split(": ")[1]);
                    ui.addChatMsg(message);
                    ui.displayChatMsgs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
