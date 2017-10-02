package HFJ;

/**
 * Created by Tigrenok on 10.08.2017.
 * Изучаем Java, стр.447
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JListTest implements ListSelectionListener {

    JFrame frame; //фрейм
    JList list; //наш список
    JPanel panel; //панель для списка

    public static void main(String[] args) {
        JListTest gui = new JListTest();
        gui.go();
    }

    public void go() {
        //создаем список значений и передаем его в качестве параметра в конструктор JList
        String[] listEntries = {"alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta"};
        list = new JList(listEntries);

        //создаем для списка вертикальную полосу прокрутки
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //инициализируем панель и добавляем на нее наш скроллер
        panel = new JPanel();
        panel.add(scroller);

        //устанавливаем количество строк, изображаемых до прокрутки
        list.setVisibleRowCount(5);

        //ограничиваем выбор до одной строки
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //регистрируем события выбора в списке
        list.addListSelectionListener(this);

        //инициализируем наконец-то фрейм и добавляем на него панель (которая содержит список)
        frame = new JFrame("Тестируем список");
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    //обрабатываем события - выясняем, какая строка в списке была выбрана
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if ( !lse.getValueIsAdjusting()) { //если не вставить такую проверку условия, то получим событие дважды
            String selection = (String) list.getSelectedValue(); //метод getSelectedValue() возвращает Object. Список не ограничен только String
            System.out.println(selection);
        }
    }
}
