package trabalho1.q3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort, InetAddress.getByName("localhost"), Integer.parseInt(args[0]));
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner scan = new Scanner(System.in);
            String data = "";
            System.out.println("Received: " + in.readUTF());
            while (true) {
                try {
                    data = in.readUTF();        // read a line of data from the stream
                    System.out.println("Received: " + data);
                    try {
                        Double.parseDouble(data);
                        System.out.println("Received: " + in.readUTF());
                    } catch (Exception e) {

                    }
                    data = scan.nextLine();
                    if(data.equals("Quit"))
                        break;
                    out.writeUTF(data);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            try {
                out.writeUTF(data);
                data = in.readUTF();        // read a line of data from the stream
            } catch (IOException i) {
//                System.out.println(i);
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