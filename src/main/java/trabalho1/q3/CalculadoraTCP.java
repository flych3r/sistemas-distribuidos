//package trabalho1.q3;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CalculadoraTCP {

    public static Map<Integer, Operation> clients = new HashMap<Integer, Operation>();

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
    boolean op_ = false;
    double n1;
    boolean n1_ = false;
    double n2;
    boolean n2_ = false;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {

        this.op = op;
        this.op_ = true;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(String n1) {

        this.n1 = Double.parseDouble(n1);
        this.n1_ = true;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(String n2) {

        this.n2 = Double.parseDouble(n2);
        this.n2_ = true;
    }

    public boolean isOp_() {
        return op_;
    }

    public boolean isN1_() {
        return n1_;
    }

    public boolean isN2_() {
        return n2_;
    }

    public void clearOperation() {
        this.op_ = false;
        this.n1_ = false;
        this.n2_ = false;
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

    boolean CONNECTED = true;

    public Connection(Socket aClientSocket) {
        try {
            CalculadoraTCP.clients.putIfAbsent(aClientSocket.getPort(), new Operation());
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
                boolean op = CalculadoraTCP.clients.get(clientSocket.getPort()).isOp_();
                if (!op) {
                    out.writeUTF("Digite o operador");
                    String data = in.readUTF();
                    if (data.equals("Quit")) {
                        CONNECTED = false;
                        break;
                    }
                    CalculadoraTCP.clients.get(clientSocket.getPort()).setOp(data);
                }
                boolean n1 = CalculadoraTCP.clients.get(clientSocket.getPort()).isN1_();
                if (!n1) {
                    out.writeUTF("Digite o primeiro numero");
                    String data = in.readUTF();
                    if (data.equals("Quit")) {
                        CONNECTED = false;
                        break;
                    }
                    CalculadoraTCP.clients.get(clientSocket.getPort()).setN1(data);
                }
                boolean n2 = CalculadoraTCP.clients.get(clientSocket.getPort()).isN2_();
                if (!n2) {
                    out.writeUTF("Digite o segundo numero");
                    String data = in.readUTF();
                    if (data.equals("Quit")) {
                        CONNECTED = false;
                        break;
                    }
                    CalculadoraTCP.clients.get(clientSocket.getPort()).setN2(data);
                }
                out.writeUTF(CalculadoraTCP.clients.get(clientSocket.getPort()).getAnswer());
                CalculadoraTCP.clients.get(clientSocket.getPort()).clearOperation();
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
