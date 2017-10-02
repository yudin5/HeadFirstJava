package HFJ;

/**
 * Created by Tigrenok on 10.08.2017.
 */

import javax.swing.*;

public class TextFieldTest {
    public static void main(String[] args) {
        TextFieldTest gui = new TextFieldTest();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JTextField textField = new JTextField("Ваше имя: ");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //текст по умолчанию для поля ввода
        textField.setText("Всё, что угодно");
        //добавляем поле ввода на панель
        frame.getContentPane().add(textField);

        frame.setSize(300,300);
        frame.setVisible(true);
    }
}
