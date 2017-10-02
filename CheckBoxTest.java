package HFJ;

/**
 * Created by Tigrenok on 10.08.2017.
 */

import javax.swing.*;
import java.awt.event.*;

//класс должен реализовывать ItemListener, а не ActionListener
public class CheckBoxTest implements ItemListener {

    JCheckBox checkBox;

    public static void main(String[] args) {
        CheckBoxTest gui = new CheckBoxTest();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame("Тестируем чек бокс");
        checkBox = new JCheckBox("Имба флаг");

        frame.getContentPane().add(checkBox);
        //начинаем отслеживать события флажка - установлен он или снят
        checkBox.addItemListener(this);

        /*
        Установка или сняти флажка производится с помощью:
        check.setSelected(true);
        check.setSelected(false);
         */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    //обрабатываем событие и определяем, установлен флажок или снят; выводим результат на консоль
    @Override
    public void itemStateChanged(ItemEvent e) {
        String onOrOff = "off";
        if (checkBox.isSelected()) onOrOff = "on";
        System.out.println("Флажок - " + onOrOff);
    }
}
