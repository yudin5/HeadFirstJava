package HFJ;

/**
 * Created by Tigrenok on 07.08.2017.
 * Изучаем Java. Упражнение на стр.379
 */

class MyEx extends Exception {}

public class ExTestDrive {
    public static void main(String[] args) {
        String test = "no";

        try {
            System.out.print("t");
            doRisky(test);
        } catch (MyEx e) {
            System.out.print("r");
            System.out.print("o");
        } finally {
            System.out.print("w");
            System.out.print("s");
        }
    }

    static void doRisky(String t) throws MyEx {
        System.out.print("h");
        if ("yes".equals(t)) {
            System.out.print("a");
        } else {
            throw new MyEx();
        }
    }

}



