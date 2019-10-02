package trabalho2.q1.chat;

import java.util.Scanner;

public class Chat {

    private Scanner scan;

    Chat() {
        scan = new Scanner(System.in);
    }

    public String sendMessage(String message) {
        System.out.println(message);
        return scan.nextLine();
    }

}
