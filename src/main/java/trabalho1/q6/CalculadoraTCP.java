//package trabalho1.q6;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class CalculadoraTCP {

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

class Operation {
    String op;
    double n1;
    double n2;

    public Operation(String data){
        String[] values = data.split(" ");
        op = values[0];
        n1 = Integer.parseInt(values[1]);
        n2 = Integer.parseInt(values[2]);
    }

    public String getOp() {
        return op;
    }

    public double getN1() {
        return n1;
    }

    public double getN2() {
        return n2;
    }

    public String getAnswer() {

        try {
            double ans = 0;

            switch (this.op) {
                case "+":
                    ans = this.n1 + this.n2;
                    break;

                case "-":
                    ans = this.n1 - this.n2;
                    break;

                case "*":
                    ans = this.n1 * this.n2;
                    break;

                case "/":
                    if (this.n2 == 0)
                        return "Divisão por 0";
                    ans = this.n1 / this.n2;
                    break;

                default:
                    return "Operador desconhecido.";
            }

            return Double.toString(ans);
        } catch (Exception e) {
            return "Formato não rechonhecido. Use o padrão N1 op N2.";
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Operation operation;
    String data;
    ArrayList<String> operations;
    int count;

    boolean CONNECTED = true;

    public Connection(Socket aClientSocket) {

        try {
            count = 0;
            operations = new ArrayList<>();
            System.out.println(aClientSocket.getPort());
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {

        try {                             // an echo server
            out.writeUTF("Digite Quit para sair");


            while (CONNECTED) {
                data = in.readUTF();

                if (data.equals("Quit")) {
                    CONNECTED = false;
                    break;
                }
                if(data.equals("OP")) {
                    out.writeUTF(count + ": " + operations.toString());
                }else {
                    operation = new Operation(data);
                    operations.add(operation.getOp() + " ");
                    out.writeUTF(operation.getAnswer());
                    count++;
                }
            }
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
