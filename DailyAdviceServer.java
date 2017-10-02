package HFJ;

/**
 * Created by Tigrenok on 14.08.2017.
 * Изучаем Java, стр.514. Серверная часть.
 */

import java.io.*;
import java.net.*;

public class DailyAdviceServer {

    String[] adviceList = {"Ешьте меньшими порциями.", "Купите облегающие джинсы. Нет, они не делают вас полнее.", "Два слова: не годится",
                            "Будьте честны хотя бы сегодня. Скажите своему начальнику, что вы *на самом деле* о нем думаете.",
                            "Возможно, вам стоит подобрать другую прическу."};

    public void go() {
        try {
            //Благодаря ServerSocket приложение отслеживает клиентские запросы на порту 4242 на том же компьютере, где выполняется код
            ServerSocket serverSock = new ServerSocket(4242);

            //Сервер входит в постоянный цикл, ожидая клиентских подключений (и обслуживая их)
            while(true) {
                //Метод accept() блокирует приложение до тех пор, пока не поступит запрос, после чего возвращает сокет (на анонимном порту)
                //для взаимодействия с клиентом
                Socket sock = serverSock.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }


}
