package HFJ;

/**
 * Created by Tigrenok on 09.08.2017.
 */

import javax.swing.*;
import java.awt.*;

public class LayoutTest {

    public static void main(String[] args) {
        LayoutTest gui = new LayoutTest();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        JButton button = new JButton("shock me");
        JButton buttonTwo = new JButton("bliss");
        JButton buttonThree = new JButton("huh?");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(button);
        panel.add(buttonTwo);
        panel.add(buttonThree);

        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(300,300);

        frame.setVisible(true);
    }
}
