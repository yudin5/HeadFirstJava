package HFJ;

/**
 * Created by Tigrenok on 07.08.2017.
 * "Изучаем Java". Стр.372
 */

import javax.sound.midi.*;

public class MiniMiniMusicApp {

    public static void main(String[] args) {
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        mini.play();
    }

    public void play() {
        try {
            //получаем синтезатор
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            //получаем последовательность
            Sequence seq = new Sequence(Sequence.PPQ, 4);

            //запрашиваем трек у последовательности
            Track track = seq.createTrack();

            //Помещаем в трек MIDI-события
            //1 - нажимаем ноту
            ShortMessage a = new ShortMessage();
            //a.setMessage(192, 1, 120, 0);
            a.setMessage(144, 1, 50, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            //2 - отпускаем ноту
            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 50, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            //передаем последовательность синтезатору
            player.setSequence(seq);

            //запускаем синтезатор - нажимаем кнопку Play
            player.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
