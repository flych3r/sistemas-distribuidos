package trabalho1.q3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try {
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public static byte[] getAnswer(String str) {

        try {
            String[] strings = str.split(" ");
            double ans = 0;

            switch (strings[1]) {
                case "+":
                    ans = Double.parseDouble(strings[0]) + Double.parseDouble(strings[2]);
                    break;

                case "-":
                    ans = Double.parseDouble(strings[0]) - Double.parseDouble(strings[2]);
                    break;

                case "*":
                    ans = Double.parseDouble(strings[0]) * Double.parseDouble(strings[2]);
                    break;

                case "/":
                    if (Double.parseDouble(strings[2]) == 0)
                        return "Divisão por 0".getBytes();
                    ans = Double.parseDouble(strings[0]) / Double.parseDouble(strings[2]);
                    break;

                default:
                    return "Operador desconhecido.".getBytes();
            }

            return Double.toString(ans).getBytes();
        } catch (Exception e) {
            return "Formato não rechonhecido. Use o padrão N1 op N2.".getBytes();
        }
    }

    public void run() {
        try {                             // an echo server

            String data = in.readUTF();                      // read a line of data from the stream
            out.writeUTF(data);
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/}
        }


    }
}
