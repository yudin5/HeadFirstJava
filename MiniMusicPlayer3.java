package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 * Версия 3
 */

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {

    static JFrame f = new JFrame("Мой первый музыкальный клип");
    static MyDrawPanel3 ml;

    public static void main(String[ ] args) {
        MiniMusicPlayer3 mini = new MiniMusicPlayer3();
        mini.go();
    }

    //устанавливаем GUI
    public void setUpGui() {
        ml = new MyDrawPanel3();
        f.setContentPane(ml);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
    }

    public void go() {
        setUpGui();

        try {
            //создаем и открываем синтезатор
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            //регистрируем события синтезатором. Метод, отвечающий за регистрацию, принимает объект слушателя и целочисленный массив,
            //представляющий собой список событий ControllerEvent, которые нам нужны. Нас интересует только одно событие - #127
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(ml, new int[] {127});

            //создаем последовательность и дорожку
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            //создаем случайные ноты
            //вызываем метод makeEvent(), чтобы создать сообщение и событие, а затем добавляем результат (MidiEvent) в дорожку
            int r = 0; //переменная для случайной ноты
            for (int i = 0; i < 60; i+=4) {
                r = (int) (Math.random() * 50 + 1); //генерим случайную ноту
                //нажимаем ноту
                track.add(makeEvent(144,1,i,100,i));
                //ловим ритм
                track.add(makeEvent(176,1,127,0,i));
                //отпускаем ноту
                track.add(makeEvent(128,1,i,100,i+2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) { }
        return event;
    }

    class MyDrawPanel3 extends JPanel implements ControllerEventListener {

        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true; //нам нужно, чтобы только синтезатор перерисовывал фрейм...мало ли кто может вызвать
            repaint();
        }

        public void paintComponent(Graphics g) {
            if (msg) { //нам нужно, чтобы только синтезатор перерисовывал фрейм...мало ли кто может вызвать repaint()
                Graphics2D g2 = (Graphics2D) g;
                //произвольный цвет для прямоугольника
                int red = (int) (Math.random() * 250);
                int green = (int) (Math.random() * 250);
                int blue = (int) (Math.random() * 250);

                g.setColor(new Color(red, green, blue));

                //задаем случайный размер и положение прямоугольника
                //высота и ширина
                int ht = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);

                //положение начальной координаты
                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x,y,ht,width);
                msg = false;
            }
        }
    }
}
