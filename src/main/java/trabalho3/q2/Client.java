import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        } else System.out.println("Security manager already enabled.");

        RemoteInterface servant = null;

        try {

            servant = (RemoteInterface) Naming.lookup("//localhost/Files");
            System.out.println("Found server");

            String fileName;
            int option;

            System.out.println("Choose one of these options:");
            System.out.println("\t1: List files");
            System.out.println("\t2: Download file");
            System.out.println("\t3: Remove file");
            System.out.println("\t4: Read file");
            System.out.print("\t>> ");

            option = scanner.nextInt();

            switch (option){
                case 1:
                    System.out.println(Arrays.toString(servant.listFiles()));
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Type the url");
                    String url = scanner.nextLine();
                    System.out.println("Type the file name");
                    fileName = scanner.nextLine();
                    if(servant.downloadFile(url, fileName))
                        System.out.println("Download succesfull");
                    else
                        System.out.println("Download failed");
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Type the file name");
                    fileName = scanner.nextLine();
                    if(servant.removeFile(fileName))
                        System.out.println("File deleted");
                    else
                        System.out.println("File not found");
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.println("Type the file name");
                    fileName = scanner.nextLine();
                    List<String> file = servant.readFile(fileName);
                    if(file != null)
                        for (String line: file)
                            System.out.println(line);
                    else
                        System.out.println("File can't be opened.");
                    break;
                default:
                    break;
            };
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}