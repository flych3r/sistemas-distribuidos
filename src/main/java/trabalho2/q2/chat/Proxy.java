package trabalho2.q2.chat;

public class Proxy {

    private TCPClient client;

    public Proxy() {

        this.client = new TCPClient();

    }

    public String sendMessage(String message) {
        client.sendRequest(message);
        return client.getResponse();
    }

    public void close() {
        client.close();
    }

}
