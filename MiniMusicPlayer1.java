package HFJ;

/**
 * Created by Tigrenok on 08.08.2017.
 * Изучаем Java, стр.419
 */

import javax.sound.midi.*;

public class MiniMusicPlayer1 {

    public static void main(String[ ] args) {
        try {
            //создаем и открываем синтезатор
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            //создаем последовательность и дорожку
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            //Создаем группу событий, чтобы ноты продолжали подниматься (от ноты ф-но 5 до ноты 61
            //вызываем метод makeEvent(), чтобы создать сообщение и событие, а затем добавляем результат (MidiEvent) в дорожку
            for (int i = 5; i < 61; i+=4) {
                track.add(makeEvent(144,1, i,100, i));
                track.add(makeEvent(128,1, i,100,i+2));
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
}
