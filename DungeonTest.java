package HFJ;

/**
 * Created by Tigrenok on 13.08.2017.
 * Изучаем Java, задача на стр.497
 */

import java.io.*;

public class DungeonTest {
    public static void main(String[] args) {
        DungeonGame d = new DungeonGame();
        System.out.println(d.getX() + d.getY() + d.getZ());

        try {
            //записываем объект
            FileOutputStream fos = new FileOutputStream("dg.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(d);
            oos.close();
            //извлекаем объект
            FileInputStream fis = new FileInputStream("dg.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            d = (DungeonGame) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(d.getX() + d.getY() + d.getZ());
    }
}

class DungeonGame implements Serializable {

    public int x = 3;
    transient long y = 4; //переходная переменная; значение не сохраняется при сериализации/десериализации
    private short z = 5;

    long getY() {
        return y;
    }

    int getX() {
        return x;
    }

    short getZ() {
        return z;
    }
}
