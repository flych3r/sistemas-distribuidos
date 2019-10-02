package trabalho2.q2.chat;

public class Squeleton {

    private Chat chat;

    Squeleton() {
        this.chat = new Chat();
    }

    public String sendMessage(String message) {
        return chat.sendMessage(message);
    }

}