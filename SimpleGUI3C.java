package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGUI3C implements ActionListener {

    JFrame frame;

    public static void main(String[] args) {
        SimpleGUI3C gui = new SimpleGUI3C();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Chage colors");
        button.addActionListener(this);

        MyDrawPanelGradient drawPanel = new MyDrawPanelGradient();

        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setSize(300,300);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //когда пользователь нажимает кнопку, вызываем для фрейма метод repaint()
        //это значит, что метод paintComponent() вызывается для каждого виджета во фрейме
        frame.repaint();
    }
}
