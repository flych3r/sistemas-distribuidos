package trabalho1.q2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789);
            // create socket at agreed port
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String str = new String(request.getData());
                request.setData(getAnswer(str));

                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
                        request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
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

}




