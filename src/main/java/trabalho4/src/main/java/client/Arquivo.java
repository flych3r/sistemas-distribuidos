package client;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Base64;

public class Arquivo {

//Referente ao caminho que a imagem será pega ou salva
    private static String path = System.getProperty("user.dir") + "/src/files/";

//Recebe o caminho da imagem, lê essa imagem e a tranasforma no formato base64
//Importante para transformar a imagem em uma String e poder enviar para o servidor
    public static String encodeFileToBase64Binary(String file) throws IOException {

        byte[] fileContent = FileUtils.readFileToByteArray(new File(file));
        return  Base64.getEncoder().encodeToString(fileContent);

    }

//Recebe a imagem do servidor junto com o seu nome
//Importante para transformar a imagem de String para file e nomea-lá
    public static void decodeBase64BinaryToFile(String encodedFile, String outputFile) throws IOException {

        byte[] fileContent = Base64.getDecoder().decode(encodedFile);
        OutputStream os = new FileOutputStream(outputFile);

        os.write(fileContent);
    }

//Função que faz o download de imagem, recebe o url e o nome que a imagem irá receber
    public static boolean downloadFile(String url, String fileName) throws RemoteException {
//      Leitura da url
        if(fileName.isEmpty()) {
            String[] listUrl = url.split("/");
            fileName = listUrl[listUrl.length - 1];
        }

//      Download da imagem 1024 bytes por vez e salva a imagem no formato jpg
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path + fileName + ".jpg")) {
                  byte[] dataBuffer = new byte[1024];
                  int bytesRead;
                  while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                  }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }

}
