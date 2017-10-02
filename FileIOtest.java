package HFJ;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Tigrenok on 11.08.2017.
 */

public class FileIOtest implements Serializable {

    private int width;
    private int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int h) {
        height = h;
    }

    public static void main(String[] args) {

        FileIOtest fileIOtest = new FileIOtest();
        fileIOtest.setHeight(50);
        fileIOtest.setWidth(20);

        try {
            FileOutputStream fs = new FileOutputStream("foo.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(fileIOtest);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}