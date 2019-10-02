package trabalho2.q1.calculator;

import trabalho2.q2.calculator.Proxy;

import java.util.Scanner;

public class User {

    public static void main(String[] args) {

        TCPClient client = new TCPClient();
        Scanner scan = new Scanner(System.in);
        System.out.println("Type close to exit");
        while (true) {

            double op1, op2;
            System.out.println("Insert operation: ");
            String message = scan.nextLine();
            if (message.equals("close"))
                break;
            client.sendRequest(message);
            System.out.println(client.getResponse());
        }

        client.close();

    }

}
