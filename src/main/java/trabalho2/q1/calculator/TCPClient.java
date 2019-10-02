package trabalho2.q1.calculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public TCPClient() {
        try {
            socket = new Socket("localhost", 7896);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String request) {
        try {
            out.writeUTF(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
