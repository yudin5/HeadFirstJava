package HFJ.ServerTest;

import java.rmi.*;
import java.rmi.server.*;

/**
 * РЕАЛИЗАЦИЯ УДАЛЕННОГО СЕРВИСА
 * Created by Tigrenok on 15.09.2017.
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    @Override
    public String sayHello() throws RemoteException {
        return "Сервер говорит: 'Привет!'";
    }

    //Обязательно нужен конструктор, бросающий RemoteException, так как в объявлении конструктора родительского класса содержится
    //исключение.
    public MyRemoteImpl() throws RemoteException {}

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("Hello", service);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
