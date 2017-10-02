package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 */

import javax.swing.*;

public class SimpleGUI1Test{

    MyDrawPanelGradient panel;

    public static void main(String[] args) {
        SimpleGUI1Test gui = new SimpleGUI1Test();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        panel = new MyDrawPanelGradient();

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 350);
        frame.setVisible(true);
    }
}

