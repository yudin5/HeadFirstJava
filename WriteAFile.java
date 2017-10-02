package HFJ;

/**
 * Created by Tigrenok on 12.08.2017.
 * Изучаем Java, стр.477
 */

import java.io.*;

public class WriteAFile {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("Foo.txt");
            writer.write("Привет!");
            writer.close();

            //создаем каталог
            File dir = new File("Папка для теста");
            dir.mkdir();

            //вывести содержимое каталога
            if (dir.isDirectory()) {
                String[] dirContents = dir.list();
                for (int i = 0; i < dirContents.length; i++) {
                    System.out.println(dirContents[i]);
                }
            }

            //получаем абсолютный путь файла или каталога
            System.out.println(dir.getAbsolutePath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

