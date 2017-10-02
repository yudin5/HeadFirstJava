package HFJ.ServerTest;

import java.rmi.*;

/**
 * УДАЛЕННЫЙ ИНТЕРФЕЙС
 * Created by Tigrenok on 15.09.2017.
 */

public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
}
