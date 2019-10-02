package trabalho1.q5;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int serverPort;
    private int clientPort;

    public Client(int serverPort, int clientPort) {
        this.serverPort = serverPort;
        this.clientPort = clientPort;
    }

    public static void main(String[] args) {
        Client client = new Client(7896, Integer.parseInt(args[0]));
        client.run();
    }

    public void run() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", this.serverPort, InetAddress.getByName("localhost"), this.clientPort);
            Scanner scanner = new Scanner(System.in);
            PrintStream print = new PrintStream(socket.getOutputStream());

            ServerMessenger serverMessenger = new ServerMessenger(socket.getInputStream());
            new Thread(serverMessenger).start();

            String s = "";
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.equals("\\q"))
                    break;
                print.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) try {
                socket.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}
