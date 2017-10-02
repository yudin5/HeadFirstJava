package HFJ;

import java.awt.*;
import javax.swing.*;

public class MyDrawPanelBlack extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        //начнем с левого верхнего угла, установим размер, как у панели
        //закрашиваем всю панель чёрным цветом (по умолчанию)
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        //случайный цвет
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);

        Color randomColor = new Color(red, green, blue);
        g.setColor(randomColor);
        g.fillOval(10,10,100,100);
    }
}
