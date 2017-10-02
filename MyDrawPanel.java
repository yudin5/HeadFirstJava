package HFJ;

/**
 * Created by Tigrenok on 07.08.2017.
 * Изучаем Java, стр.394
 */

import java.awt.*;
import javax.swing.*;

public class MyDrawPanel extends JPanel {
    public void paintComponent(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(20,50,100,100);
    }
}
