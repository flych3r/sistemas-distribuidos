package client;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class TCPClient {


    public static void main(String[] args) {

//Referente a se o cliente deseja continuar ou não a execução do programa
        int continuar;


//Execução principal do programa, continua enquanto a String continuar seja diferente de zero
        do {

            Scanner scanner = new Scanner(System.in);
//Referente a imagem do banco ou baixada pelo usuario
            String foto;
//Referente se o usuario deseja utilizar uma imagem do banco ou baixar
            int choose = 0;
//Referente ao serviço escolhido pelo usuario
            int method = 0;
//Referente ao arquivo final com a imagem recebida pelo servidor
            String outputFile;
//Referente ao caminho da imagem utilizada
            String file;


            do {
                System.out.println("Digite 1 para usar uma imagem do banco ou 2 para fazer o download: ");
                choose = scanner.nextInt();
            } while (choose != 1 && choose != 2);


            do {
                if (choose == 1) {
                    System.out.println("Digite o nome da imagem de sua escolha: ");
                    foto = scanner.next();
                    file = System.getProperty("user.dir") + "/src/files/" + foto + ".jpg";
                } else {
                    System.out.println("Digite a URL da imagem: ");
                    String URL = scanner.next();
                    System.out.println("Digite o nome da imagem de sua escolha: ");
                    foto = scanner.next();
                    try {
                        Arquivo.downloadFile(URL, foto);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    file = System.getProperty("user.dir") + "/src/files/" + foto + ".jpg";
                }
            } while (!(new File(file)).exists());

            do {
                System.out.println("Digite 1 para legendar a imagem ou 2 para mostrar os elementos na imagem: ");
                method = scanner.nextInt();
            } while (method != 1 && method != 2);

            String outFoto;

            do {
                System.out.println("Digite o nome de saida para a imagem: ");
                outFoto = scanner.next();
                outputFile = System.getProperty("user.dir") + "/src/files/" + outFoto + ".jpg";
            } while (outFoto.isEmpty());

            // arguments supply message and hostname
            Socket s = null;
            Gson gson = new Gson();
//          Referente ao numero da requisição executada
            int requestID = 0;

            try {
                int serverPort = 12000;
                s = new Socket("localhost", serverPort);
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

//              Transformação da imagem utilizada no momento
                String encodedImage = Arquivo.encodeFileToBase64Binary(file);
//              Criação de uma Messagem para uma riquisição
                Message msg = new Message(0, requestID, "SemanticImage", method, encodedImage);
//              Transformação da Messagem em Json
                String json = gson.toJson(msg);
//              Envia a messagem para o servidor
                out.writeUTF(":" + json.length() + ":" + json);
//              Recebe a messagem do servidor
                BufferedReader input = new BufferedReader(new InputStreamReader(in));
                json = input.readLine();
//              Transforma a messagem em um objeto
                msg = gson.fromJson(json, Message.class);

//              Verifica se o id da resposta é o mesmo da requisição
                if (msg.getRequestId() == requestID) {
                    Arquivo.decodeBase64BinaryToFile(msg.getArguments(), outputFile);
                    DisplayImage.displayImage(outputFile);
                }

                requestID++;
            } catch (UnknownHostException e) {
                System.out.println("Socket:" + e.getMessage());
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("readline:" + e.getMessage());
            } finally {
                if (s != null) try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }

//             Verificação para o usuario continuar ou não
                System.out.println("Deseja Continuar? Sim = 1  Não = 0 ");
                scanner.nextLine();
                continuar = scanner.nextInt();
        }while (continuar != 0);
    }
}
