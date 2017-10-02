package HFJ;

/**
 * Created by Tigrenok on 16.08.2017.
 * Изучаем Java, задача на стр.554
 * ВАЖНО! Если не синхронизировать метод updateCounter, то данные почти наверняка потеряются (из-за переключения потоков).
 */

public class TestThreads {
    public static void main(String[] args) {
        ThreadOne t1 = new ThreadOne();
        ThreadTwo t2 = new ThreadTwo();
        Thread one = new Thread(t1);
        Thread two = new Thread(t2);
        one.start();
        two.start();
    }
}

class Accum {
        private static Accum a = new Accum();
        private int counter = 0;

        private Accum() {}

        public static Accum getAccum() {
            return a;
        }

        public synchronized void updateCounter(int add) {
            counter += add;
        }

        public int getCount() {
            return counter;
        }

}

class ThreadOne implements Runnable {
    Accum a = Accum.getAccum();
    @Override
    public void run() {
        for (int y = 0; y < 98; y++) {
            a.updateCounter(1000);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) { }
        }
        System.out.println("Один " + a.getCount());
    }
}

class ThreadTwo implements Runnable {
    Accum a = Accum.getAccum();
    @Override
    public void run() {
        for (int x = 0; x < 99; x++) {
            a.updateCounter(1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) { }
        }
        System.out.println("Два " + a.getCount());
    }
}
