package HFJ;

/**
 * Created by Tigrenok on 26.07.2017.
 */
public class StaticTests extends StaticSuper {
    static int rand;

    static {
        rand = (int) (Math.random()*6);
        System.out.println("Статический блок " + rand);
    }

    StaticTests() {
        System.out.println("Конструктор");
    }

    public static void main(String[] args) {
        System.out.println("Внутри main");
        StaticTests st = new StaticTests();
    }
}
