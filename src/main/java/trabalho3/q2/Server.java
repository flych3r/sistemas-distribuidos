package trabalho3.q2;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args){
        System.setSecurityManager(new SecurityManager());
        try{
            RemoteInterface servant = new Servant();
            RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(servant, 0);
            Naming.rebind("Files", stub);
            System.out.println("Server ready");
        }catch(Exception e) {
            System.out.println("Server main " + e.getMessage());
        }
    }
}
