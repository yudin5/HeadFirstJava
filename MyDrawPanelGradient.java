package HFJ;

import java.awt.*;
import javax.swing.*;

public class MyDrawPanelGradient extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //это статичный градиент, ниже реализация рандомного градиента
        //GradientPaint gradient = new GradientPaint(70,70,Color.blue,150,150,Color.orange);
        //g2d.setPaint(gradient);
        //g2d.fillOval(70,70,100,100);

        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        Color startColor = new Color(red, green, blue);

        red = (int) (Math.random() * 255);
        green = (int) (Math.random() * 255);
        blue = (int) (Math.random() * 255);
        Color endColor = new Color(red, green, blue);

        GradientPaint gradient = new GradientPaint(70,70,startColor,150, 150, endColor);
        g2d.setPaint(gradient);
        g2d.fillOval(70,70,100,100);
    }
}
