package HFJ;

/**
 * Created by Tigrenok on 12.08.2017.
 */

public class SplitTestString {
    public static void main(String[] args) {
        String toTest = "Какой цвет получится при сочетании синего и желтого?/Зелёный./Ну или не пойми что.";
        String[] result = toTest.split("/");

        for (String word : result) {
            System.out.println(word);
        }
    }
}
