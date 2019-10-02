package trabalho1.q6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteCalculadora {
    public static void main(String[] args) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner scan = new Scanner(System.in);
            String data = "";
            System.out.println("Received: " + in.readUTF());
            while (true) {
                System.out.println("Digite a Operacao no estilo Op N1 N2 ou digite OP para ver os operadores ja utilizados ");
                data = scan.nextLine();
                if (data.equals("Quit"))
                    break;
                out.writeUTF(data);
                try {
                    System.out.println("Received: " + in.readUTF());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}