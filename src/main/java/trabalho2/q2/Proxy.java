package trabalho2.q2;

public class Proxy {

    private TCPClient client;

    public Proxy() {

        this.client = new TCPClient();

    }

    public double add(double op1, double op2) {

        String response = null;
        String message = op1 + " + " + op2;
        client.sendRequest(message);
        response = client.getResponse();
        return Double.parseDouble(response);
    }

    public double sub(Double op1, Double op2) {

        String response = null;
        String message = op1 + " - " + op2;
        client.sendRequest(message);
        response = client.getResponse();
        return Double.parseDouble(response);

    }

    public double mult(Double op1, Double op2) {

        String response = null;
        String message = op1 + " * " + op2;
        client.sendRequest(message);
        response = client.getResponse();
        return Double.parseDouble(response);

    }

    public double div(Double op1, Double op2) {

        String response = null;
        String message = op1 + " / " + op2;
        client.sendRequest(message);
        response = client.getResponse();
        return Double.parseDouble(response);

    }

    public void close() {
        client.close();
    }

}
