package trabalho2.q1.calculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    static Calculator calculator = new Calculator();

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
            this.clientSocket = aClientSocket;
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            String request;
            while (true) {
                request = getRequest();
                String[] message = request.split(" ");
                String answer;
                double op1 = Double.parseDouble(message[0]);
                double op2 = Double.parseDouble(message[2]);
                switch (message[1]) {
                    case "+":
                        answer = Double.toString(TCPServer.calculator.add(op1, op2));
                        break;
                    case "-":
                        answer = Double.toString(TCPServer.calculator.sub(op1, op2));
                        break;
                    case "*":
                        answer = Double.toString(TCPServer.calculator.mult(op1, op2));
                        break;
                    case "/":
                        answer = Double.toString(TCPServer.calculator.div(op1, op2));
                        break;
                    default:
                        answer = "Unknown operator";
                        break;
                }
                sendResponse(answer);
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

    public String getRequest() throws IOException {
        return in.readUTF();
    }

    public void sendResponse(String response) throws IOException {
        out.writeUTF(response);
    }

}
