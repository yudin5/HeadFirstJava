package HFJ;

/**
 * Created by Tigrenok on 12.08.2017.
 * Изучаем Java, стр.484
 */

import java.io.*;

public class ReadAFile {
    public static void main(String[] args) {

        try {
            File myFile = new File("Foo.txt");
            FileReader fileReader = new FileReader(myFile);

            BufferedReader reader = new BufferedReader(fileReader);

            String line = null;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
