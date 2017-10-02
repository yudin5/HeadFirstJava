package HFJ;

/**
 * Created by Tigrenok on 11.08.2017.
 * Изучаем Java, стр.474
 */

import java.io.*;

public class GameSaverTest {
    public static void main(String[] args) {
        //создаем несколько персонажей
        GameCharacter one = new GameCharacter(50, "Эльф", new String[] {"лук", "меч", "кастет"});
        GameCharacter two = new GameCharacter(200, "Тролль", new String[] {"голые руки", "большой топор"});
        GameCharacter three = new GameCharacter(120, "Маг", new String[] {"заклинания", "невидимость"});
        GameCharacter four = new GameCharacter(150, "Разбойник", new String[] {"кинжал", "яды"});

        //здесь может быть код, который может изменить значения состояния персонажей

        //применяем сериализацию, сохраняем "персонажей" в файл
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Game.ser"));
            os.writeObject(one);
            os.writeObject(two);
            os.writeUnshared(three);
            os.writeUnshared(four);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //занулим объекты для теста
        one = null;
        two = null;
        three = null;
        four = null;

        //теперь прочитаем их из файла
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("Game.ser"));
            GameCharacter oneRestore = (GameCharacter) is.readObject();
            GameCharacter twoRestore = (GameCharacter) is.readObject();
            GameCharacter threeRestore = (GameCharacter) is.readObject();
            GameCharacter fourRestore = (GameCharacter) is.readObject();

            System.out.println("Тип первого: " + oneRestore.getType());
            System.out.println("Тип второго: " + twoRestore.getType());
            System.out.println("Тип третьего: " + threeRestore.getType());
            System.out.println("Тип четвертого: " + fourRestore.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
