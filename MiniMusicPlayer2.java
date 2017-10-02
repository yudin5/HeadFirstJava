package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 * Изучаем Java, стр.419. Версия 2.
 */

import javax.sound.midi.*;

public class MiniMusicPlayer2 implements ControllerEventListener {

    public static void main(String[ ] args) {
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();
    }

    public void go() {
        try {
            //создаем и открываем синтезатор
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            //регистрируем события синтезатором. Метод, отвечающий за регистрацию, принимает объект слушателя и целочисленный массив,
            //представляющий собой список событий ControllerEvent, которые нам нужны. Нас интересует только одно событие - #127
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this, eventsIWant);

            //создаем последовательность и дорожку
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            //Создаем группу событий, чтобы ноты продолжали подниматься (от ноты ф-но 5 до ноты 61
            //вызываем метод makeEvent(), чтобы создать сообщение и событие, а затем добавляем результат (MidiEvent) в дорожку
            for (int i = 5; i < 61; i+=4) {
                track.add(makeEvent(144,1,i,100,i));

                //ловим ритм - добавляем наше собственное событие ControllerEvent. 176 обзначает, что тип события - ControllerEvent
                //аргумент для события #127. Оно ничего не делает пока что. Мы его вставляем, чтобы иметь возможность реагировать на воспроизведение
                //каждой ноты. Его цель - запуск чего-нибудь, что можно отслеживать. Оно запускается в тот же самый момент, когда мы
                //включаем воспроизведение ноты.
                track.add(makeEvent(176,1,127,0, i));

                track.add(makeEvent(128,1,i,100,i+2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
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

    //Метод обработки события (из интерфейса слушателя события ControllerEvent). При каждом получении события мы пишем слово "ля"
    public void controlChange(ShortMessage event) {
        System.out.print("ля ");
    }
}

