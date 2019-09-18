//package trabalho1.q5;

import java.io.InputStream;
import java.util.Scanner;

public class ServerMessenger implements Runnable {

    private InputStream inputStream;

    public ServerMessenger(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {

            Scanner scanner = new Scanner(this.inputStream);

            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
