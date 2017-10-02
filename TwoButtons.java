package HFJ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tigrenok on 08.08.2017.
 * Изучаем Java, p.409
 */

public class TwoButtons {
    JFrame frame;
    JLabel label;

    public static void main(String[] args) {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("Change Label");
        //передаем методу регистрации слушателя экземпляр соответствующего внутреннего класса
        labelButton.addActionListener(new LabelListener());

        JButton colorButton = new JButton("Change Color");
        //передаем методу регистрации слушателя экземпляр соответствующего внутреннего класса
        colorButton.addActionListener(new ColorListener());

        label = new JLabel("I'm a label");
        MyDrawPanelGradient drawPanel = new MyDrawPanelGradient();

        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);

        frame.setSize(400,400);
        frame.setVisible(true);
    }

    //внутренний класс - слушатель для МЕТКИ (label)
    class LabelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setText(" Ough!!!.....");
        }
    }

    //внутренний класс - слушатель для КРУГА (перерисовка frame)
    class ColorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    }
}
