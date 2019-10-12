package trabalho3.q2;

import java.rmi.*;
import java.util.List;

public interface RemoteInterface extends  Remote {
    String[] listFiles() throws RemoteException;
    boolean downloadFile(String url, String fileName) throws RemoteException;
    boolean removeFile(String fileName) throws RemoteException;
    List<String> readFile(String fileName) throws RemoteException;
}
