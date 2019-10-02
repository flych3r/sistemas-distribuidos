package trabalho2.q2.calculator;

public class Squeleton {

    private Calculator calculator;

    Squeleton() {
        this.calculator = new Calculator();
    }

    public String add(String args) {
        String[] op = args.split(" ");
        double op1 = Double.parseDouble(op[0]);
        double op2 = Double.parseDouble(op[2]);
        return Double.toString(calculator.add(op1, op2));
    }

    public String sub(String args) {
        String[] op = args.split(" ");
        double op1 = Double.parseDouble(op[0]);
        double op2 = Double.parseDouble(op[2]);
        return Double.toString(calculator.sub(op1, op2));
    }

    public String mult(String args) {
        String[] op = args.split(" ");
        double op1 = Double.parseDouble(op[0]);
        double op2 = Double.parseDouble(op[2]);
        return Double.toString(calculator.mult(op1, op2));
    }

    public String div(String args) {
        String[] op = args.split(" ");
        double op1 = Double.parseDouble(op[0]);
        double op2 = Double.parseDouble(op[2]);
        return Double.toString(calculator.div(op1, op2));
    }

}