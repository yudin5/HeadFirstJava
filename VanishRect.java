package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 * Изучаем Java. Головоломка на стр.426
 */

import javax.swing.*;
import java.awt.*;

public class VanishRect {
    int x = 1;
    int y = 1;

    public static void main(String[] args) {
        VanishRect gui = new VanishRect();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawP drawP = new MyDrawP();
        frame.getContentPane().add(drawP);
        frame.setSize(500,270);
        frame.setVisible(true);

        for (int i = 0; i < 125; i++, x++, y++) {
            x++;
            drawP.repaint();
            try {
                Thread.sleep(50);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class MyDrawP extends JPanel {
        public void paintComponent (Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0,0,500,250);
            g.setColor(Color.blue);
            g.fillRect(x,y,500-x*2,250-y*2);
        }
    }
}
