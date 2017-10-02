package HFJ;

/**
 * Created by Tigrenok on 06.08.2017.
 */

import javax.sound.midi.*;



public class MusicTest {

    public void play() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            System.out.println("Мы получили синтезатор");
        } catch (MidiUnavailableException e) {
            System.out.println("Fail");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MusicTest mt = new MusicTest();
        mt.play();
    }
}
