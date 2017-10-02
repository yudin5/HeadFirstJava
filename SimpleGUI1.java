package HFJ;

/**
 * Created by Tigrenok on 07.08.2017.
 * Изучаем Java. Стр.385
 */

import javax.swing.*;

public class SimpleGUI1 {
    public static void main(String[] args) {
        //создаем фрейм и кнопку
        JFrame frame = new JFrame();
        JButton button = new JButton("Click me");

        //эта строка завершает работу программы при закрытии окна (если её не добавить, то приложение будет "висеть" постоянно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //добавляем кнопку на панель фрейма
        frame.getContentPane().add(button);

        //присваиваем фрейму размер и делаем видимым
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}