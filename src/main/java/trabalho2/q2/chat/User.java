package trabalho2.q2.chat;

import java.util.Scanner;

public class User {

    public static void main(String[] args) {

        Proxy proxy = new Proxy();
        Scanner scan = new Scanner(System.in);
        System.out.println("Type close to exit");
        while (true) {
            String message = scan.nextLine();

            if(message.equals("close"))
                break;

            String response = proxy.sendMessage("Received: " + message);

            System.out.println(response);
        }

        proxy.close();

    }

}
