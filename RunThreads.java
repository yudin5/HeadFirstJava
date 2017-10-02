package HFJ;

/**
 * Created by Tigrenok on 14.08.2017.
 * Изучаем Java, стр.533
 */

public class RunThreads implements Runnable {

    public static void main(String[] args) {
        RunThreads runner = new RunThreads();
        //Создаем 2 потока с одним заданием
        Thread alpha = new Thread(runner);
        Thread beta = new Thread(runner);
        //Присваиваем потокам имена
        alpha.setName("Поток альфа");
        beta.setName("Поток бета");
        //Запускаем потоки
        alpha.start();
        beta.start();
    }

    //Каждый поток проходит через цикл, выводя на экран своё имя при каждой итерации
    public void run() {
        for (int i = 0; i < 25; i++) {
            String threadName = Thread.currentThread().getName();
            System.out.println("Сейчас работает " + threadName);
        }
    }
}
