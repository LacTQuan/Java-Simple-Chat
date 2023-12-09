package Ex01;

/* SERVER – may enhance to work for multiple clients */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        ServerSocket server = null;
        Socket client;

        // Default port number we are going to use
        int portnumber = 1234;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        // Create Server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);

        // Wait for the data from the client and reply
        while (true) {
            try {
                // Listens for a connection to be made to
                // this socket and accepts it. The method blocks until
                // a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientAdd = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client address = " + clientAdd + " Client port = " + clientPort);

                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message received from client = " + msgFromClient);

                if (msgFromClient.contains("Hello, my name is")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, " + msgFromClient.substring(msgFromClient.indexOf("Hello, my name is") + 18);
                    pw.println(ansMsg);
                }

                // Send response to the client
                else if (!msgFromClient.equalsIgnoreCase("Goodbye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "I've received this message from you: " + msgFromClient;
                    pw.println(ansMsg);
                }

                // Close sockets
                else {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Goodbye";
                    pw.println(ansMsg);
                    server.close();
                    client.close();
                    break;
                }

            } catch (IOException ignored) {
            }
        }
    }
}