package trabalho2.q2;

public class Dispatcher {

    private Squeleton squeleton;

    public Dispatcher() {
        squeleton = new Squeleton();
    }

    public String invoque(String message) {

        char op = message.split(" ")[1].charAt(0);
        String answer;

        switch (op) {
            case '+':
                answer = squeleton.add(message);
                break;
            case '-':
                answer = squeleton.sub(message);
                break;
            case '*':
                answer = squeleton.mult(message);
                break;
            case '/':
                answer = squeleton.div(message);
                break;
            default:
                answer = "Unknown operator";
                break;
        }

        return answer;

    }

}
