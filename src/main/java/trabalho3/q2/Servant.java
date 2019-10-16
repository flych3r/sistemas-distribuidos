import java.io.*;
import java.net.URL;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

public class Servant implements RemoteInterface {

    private String path;

    public Servant() throws RemoteException {
        path = "../files/";
    }

    @Override
    public String[] listFiles() throws RemoteException {
        File files = new File(path);
        return files.list();
    }

    @Override
    public boolean downloadFile(String url, String fileName) throws RemoteException {
        if(fileName.isEmpty()) {
            String[] listUrl = url.split("/");
            fileName = listUrl[listUrl.length - 1];
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path + fileName)) {
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

    @Override
    public boolean removeFile(String fileName) throws RemoteException {
            File file = new File(path + fileName);
            return file.delete();
    }

    @Override
    public List<String> readFile(String fileName) throws RemoteException {
        List<String> file = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path + fileName));
            String line;
            while ((line = reader.readLine()) != null)
            {
                file.add(line);
            }
            reader.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
