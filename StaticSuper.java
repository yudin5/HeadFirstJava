package HFJ;

/**
 * Created by Tigrenok on 26.07.2017.
 */

public class StaticSuper {

    static {
        System.out.println("Родительский статический блок ");
    }

    StaticSuper() {
        System.out.println("Родительский конструктор");
    }
}