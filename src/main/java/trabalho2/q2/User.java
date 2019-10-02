package trabalho2.q2;

import java.util.Scanner;

public class User {

    public static void main(String[] args) {

        Proxy proxy = new Proxy();
        Scanner scan = new Scanner(System.in);
        System.out.println("Type close to exit");
        while (true) {

            double op1, op2;
            System.out.println("Insert operation: ");
            String operation = scan.nextLine();
            if (operation.equals("close"))
                break;
            String[] ops = operation.split(" ");
            switch (ops[1]) {
                case "+":
                    proxy.add(Double.parseDouble(ops[0]), Double.parseDouble(ops[2]));
                    break;
                case "-":
                    proxy.sub(Double.parseDouble(ops[0]), Double.parseDouble(ops[2]));
                    break;
                case "*":
                    proxy.mult(Double.parseDouble(ops[0]), Double.parseDouble(ops[2]));
                    break;
                case "/":
                    proxy.div(Double.parseDouble(ops[0]), Double.parseDouble(ops[2]));
                    break;
            }
        }

        proxy.close();

    }

}
