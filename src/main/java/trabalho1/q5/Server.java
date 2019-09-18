//package trabalho1.q5;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<Socket> clients;
    private int serverPort;

    public Server() {
        this.clients = new ArrayList<>();
        this.serverPort = 7896;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.serverPort);
            while (true) {
                Socket client = serverSocket.accept();
                this.clients.add(client);

                ClientMessenger clientMessenger = new ClientMessenger(client, this);
                new Thread(clientMessenger).start();
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

    public void sendMessages(Socket sender, String message) {
        for (Socket client : this.clients) {
            if (!client.equals(sender)) {
                try {
                    PrintStream ps = new PrintStream(client.getOutputStream());
                    ps.println(sender.getPort() + ": " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
