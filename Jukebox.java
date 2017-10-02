package HFJ;

/**
 * Created by Tigrenok on 18.08.2017.
 * Изучаем Java, стр.567. Класс для тестирования песен - классов "Song"
 */

import java.util.*;
import java.io.*;

public class Jukebox {

    ArrayList<Song> songList = new ArrayList<Song>();

    public static void main(String[] args) {
        new Jukebox().go();
    }

    class ArtistCompare implements Comparator<Song> {
        //Реализуем единственный метод компаратора - compare()
        public int compare(Song one, Song two) {
            //Перекладываем всю работу по сравнению на строковые объекты, так как они уже умеют сортироваться в алфавитном порядке
            return one.getArtist().compareTo(two.getArtist());
        }
    }

    public void go() {
        getSongs();
        System.out.println("До сортировки:");
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println("После сортировки с помощью Comparable:");
        System.out.println(songList);

        //Экземпляр вложенного класса Comparator
        ArtistCompare artistCompare = new ArtistCompare();
        //Вызываем метод sort(), передаем ему список и ссылку на новый объект Comparator
        Collections.sort(songList, artistCompare);

        System.out.println("После сортировки с помощью Comparator:");
        System.out.println(songList);
        System.out.println("После преобразования в множество (Set):");
        //Создаем множество
        HashSet<Song> songSet = new HashSet<Song>();
        //Добавляем во множество весь список
        songSet.addAll(songList);
        System.out.println(songSet);
        System.out.println("После преобразования в TreeSet:");
        //Создаем TreeSet, позволяя остаться множеству отсортированным
        TreeSet<Song> songTreeSet = new TreeSet<Song>();
        songTreeSet.addAll(songList);
        System.out.println(songTreeSet);
    }

    void getSongs() {
        try {
            File file = new File("SongList.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                addSong(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addSong(String lineToParse) {
        String[] tokens = lineToParse.split("/");

        Song nextSong = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
        songList.add(nextSong);
    }

}
