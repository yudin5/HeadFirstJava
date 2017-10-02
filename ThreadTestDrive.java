package HFJ;

/**
 * Created by Tigrenok on 14.08.2017.
 * Изучаем Java, стр.528
 */

public class ThreadTestDrive {
    public static void main(String[] args) {
        Runnable threadJob = new MyRunnable();
        Thread myThread = new Thread(threadJob);

        myThread.start();

        System.out.println("Возвращаемся в main");
    }
}

class MyRunnable implements Runnable {

    public void run() {
        go();
    }

    public void go() {
        doMore();
    }

    public void doMore() {
        System.out.println("Вершина стека");
    }
}
