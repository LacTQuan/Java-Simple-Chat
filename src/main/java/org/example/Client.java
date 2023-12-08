package org.example;

/* CLIENT */
import java.io.*;
import java.net.*;

public class Client {

    private Socket socket;
    private int port = 1234;

    public Client() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            System.out.println("Client socket is created " + socket);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(ChatMsg msg) {
        try {
            OutputStream clientOut = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(clientOut, true);
            pw.println(msg.getUsername() + ": " + msg.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        new Thread(() -> {
            while (true) {
                try {
                    InputStream clientIn = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                    String msg = br.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String args[]) {
//
//        Socket client = null;
//
//        // Default port number we are going to use
//        int portnumber = 1234;
//        if (args.length >= 1){
//            portnumber = Integer.parseInt(args[0]);
//        }
//
//        for (int i=0; i <10; i++) {
//            try {
//                String msg = "";
//
//                // Create a client socket
//                client = new Socket(InetAddress.getLocalHost(), portnumber);
//                System.out.println("Client socket is created " + client);
//
//                // Create an output stream of the client socket
//                OutputStream clientOut = client.getOutputStream();
//                PrintWriter pw = new PrintWriter(clientOut, true);
//
//                // Create an input stream of the client socket
//                InputStream clientIn = client.getInputStream();
//                BufferedReader br = new BufferedReader(new
//                        InputStreamReader(clientIn));
//
//                // Create BufferedReader for a standard input
//                BufferedReader stdIn = new BufferedReader(new
//                        InputStreamReader(System.in));
//
//                System.out.println("Enter your name. Type Bye to exit. ");
//
//                // Read data from standard input device and write it
//                // to the output stream of the client socket.
//                msg = stdIn.readLine().trim();
//                pw.println(msg);
//
//                // Read data from the input stream of the client socket.
//                System.out.println("Message returned from the server = " + br.readLine());
//
//                pw.close();
//                br.close();
//                client.close();
//
//                // Stop the operation
//                if (msg.equalsIgnoreCase("Bye")) {
//                    break;
//                }
//
//            } catch (IOException ie) {
//                System.out.println("I/O error " + ie);
//            }
//        }
//    }
}
