package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 */

import javax.swing.*;
import java.awt.*;

public class SimpleAnimation {

    int x = 70;
    int y = 70;

    public static void main(String[] args) {
        SimpleAnimation gui = new SimpleAnimation();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawPanelAnimation drawPanel = new MyDrawPanelAnimation();

        frame.getContentPane().add(drawPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        for (int i = 0; i < 130; i++) {
            x++;
            y++;

            drawPanel.repaint();

            try {
                Thread.sleep(20);
            } catch (Exception e) { }
        }
    }

    class MyDrawPanelAnimation extends JPanel{
        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            //"Закрась прямоугольник, начиная с координат 0 и 0 от левого верхнего края и сделай его таким же широким и высоким, как эта панель
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            //g.fillOval(x-1, y-1,40,40);   //это для пробы :)

            g.setColor(Color.green);
            g.fillOval(x, y,40,40);
        }
    }
}
