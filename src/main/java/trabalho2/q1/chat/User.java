package trabalho2.q1.chat;

import java.util.Scanner;

public class User {

    public static void main(String[] args) {

        TCPClient client = new TCPClient();
        Scanner scan = new Scanner(System.in);
        System.out.println("Type close to exit");
        while (true) {

            String message = scan.nextLine();
            if (message.equals("close"))
                break;
            client.sendRequest(message);
            System.out.println("Received: " + client.getResponse());
        }

        client.close();

    }

}
