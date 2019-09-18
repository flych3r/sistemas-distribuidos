//package trabalho1.q5;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMessenger implements Runnable {

    private Socket client;
    private Server server;

    public ClientMessenger(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            Scanner scanner = new Scanner(this.client.getInputStream());

            while (scanner.hasNextLine()) {
                server.sendMessages(this.client, scanner.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
