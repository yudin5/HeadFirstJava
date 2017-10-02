package HFJ;

/**
 * Created by Tigrenok on 12.08.2017.
 * Класс для карточек. Создаем самостоятельно.
 */

class QuizCard {

    private String question;
    private String answer;

    String getAnswer() {
        return answer;
    }

    String getQuestion() {
        return question;
    }

    //Конструктор
    QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
