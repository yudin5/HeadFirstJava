package HFJ;

/**
 * Created by Tigrenok on 07.08.2017.
 * HFJ, p.390
 */

import javax.swing.*;
import java.awt.event.*;

public class SimpleGUI1B implements ActionListener{

    JButton button;

    public static void main(String[] args) {
        SimpleGUI1B gui = new SimpleGUI1B();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        button = new JButton("Click me!");

        //Регистрируем нашу заинтересованность в кнопке. Добавляемся в список слушателей.
        button.addActionListener(this);

        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            button.setText("I've been clicked!");
    }
}
