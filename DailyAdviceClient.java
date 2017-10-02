package HFJ;

/**
 * Created by Tigrenok on 14.08.2017.
 * Изучаем Java, стр.511
 */

import java.io.*;
import java.net.*; //для Socket

public class DailyAdviceClient {
    public void go() {
        try {
            //Создаем соединение через сокет и приложению, работающему на порту 4242, на том же компьютере, где выполняется данный код
            Socket s = new Socket("127.0.0.1", 4242);

            //Создаем поток типа InputStreamReader  и связываем его с низкоуровневым потоком из сокета
            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            //Подключаем BufferedReader к InputStreamReader (который уже соединен с исходящим потоком сокета)
            BufferedReader reader = new BufferedReader(streamReader);

            String advice = reader.readLine();
            System.out.println("Сегодня ты должен: " + advice);

            //Здесь закрываются все потоки. Так как мы закрываем самый высокоуровневый поток BufferedReader, то все остальные
            //закрываются автоматически
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();
    }
}
