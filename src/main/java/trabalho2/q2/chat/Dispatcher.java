package trabalho2.q2.chat;

public class Dispatcher {

    private Squeleton squeleton;

    public Dispatcher() {
        squeleton = new Squeleton();
    }

    public String invoke(String message) {

        return squeleton.sendMessage(message);

    }

}
