package HFJ;

/**
 * Created by Tigrenok on 15.08.2017.
 * Изучаем Java, стр.537
 */

public class RyanAndMonicaJob implements Runnable {

    //У нас тольок один экземпляр RyanAndMonicaJob. Поэтому оба потока будут получать доступ к одному банковскому счету.
    private BankAccount account = new BankAccount();

    public static void main(String[] args) {
        //Экземпляр Runnable (задача)
        RyanAndMonicaJob theJob = new RyanAndMonicaJob();

        //Создаем 2 потока с одной задачей Runnable. Оба потока будут работать с одним экземпляром счета, к-й находится в классе Runnable
        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);

        one.setName("Райан");
        two.setName("Моника");
        one.start();
        two.start();
    }

    public void run() {
        for (int x = 0; x < 10; x++) {
            makeWithdrawl(10);
            try {
                //Даем возможность другому потоку снять деньги
                Thread.sleep(200);
            } catch (Exception ex) {ex.printStackTrace();}
            if (account.getBalance() < 0) {
                System.out.println("Превышение лимита!");
            }
        }
    }

    //Синхронизированный метод. Пока поток его не выполнит, другой поток не имеет к нему доступа. Поэтому "превышение лимита" не случится.
    private synchronized void makeWithdrawl(int amount) {
        if (account.getBalance() >= amount) {
            System.out.println(Thread.currentThread().getName() + " собирается снять деньги");
            try {
                System.out.println(Thread.currentThread().getName() + " идет подремать");
                Thread.sleep(300);
            } catch (InterruptedException ex) {ex.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + " просыпается");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " заканчивает транзакцию");
        }
        else {
            System.out.println("Извините, для клиента " + Thread.currentThread().getName() + " недостаточно денег");
        }
    }
}

class BankAccount {
    //изначально на счету 100 долларов
    private int balance = 100;

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        balance = balance - amount;
    }
}
