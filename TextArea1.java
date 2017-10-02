package HFJ;

/**
 * Created by Tigrenok on 10.08.2017.
 * Изучаем Java, стр.445
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextArea1 implements ActionListener {

    JTextArea text;

    public static void main(String[] args) {
        TextArea1 gui = new TextArea1();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button  = new JButton("Just Click It");
        //отслеживаем события кнопки
        button.addActionListener(this);
        text = new JTextArea(10,20);
        //устанавливаем перенос слов
        text.setLineWrap(true);

        //объявляем скроллер и устанавливаем только вертикальную прокрутку
        JScrollPane scroller = new JScrollPane(text);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //добавляем скроллер на панель
        panel.add(scroller);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        text.append("button clicked\n");
    }
}
