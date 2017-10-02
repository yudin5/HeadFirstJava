package HFJ;

/**
 * Created by Tigrenok on 12.08.2017.
 * Изучаем Java, стр.486
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class QuizCardPlayer {

    private JTextArea display;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardIndex;
    private JFrame frame;
    private JButton nextButton;
    private boolean isShowAnswer;

    public static void main(String[] args) {
        QuizCardPlayer reader = new QuizCardPlayer();
        reader.go();
    }

    //Формируем GUI
    public void go() {
        frame = new JFrame("Quiz Card Player");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        display = new JTextArea(10, 20);
        display.setFont(bigFont);

        display.setLineWrap(true);
        display.setEditable(false);

        JScrollPane qScroller = new JScrollPane(display);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        nextButton = new JButton("Show Question");
        mainPanel.add(qScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        //создание меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card set");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(640, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //СЛУШАТЕЛИ КНОПОК И ЭЛЕМЕНТЫ МЕНЮ

    //Показ следующей карты
    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            if (isShowAnswer) { //проверяем значение флага isShowAnswer - в зависимости от результата выполняем соотв.действия
                //показываем ответ, так как вопрос уже был увиден
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next Card");
                isShowAnswer = false;
            } else {
                //показываем следующий вопрос
                if (currentCardIndex < cardList.size()) {
                    showNextCard();
                } else {
                    //больше карточек нет
                    display.setText("That was last card");
                    nextButton.setEnabled(false);
                }
            }
        }
    }

    //Файловое окно для выбора файла для открытия
    public class OpenMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    //Приватный метод для загрузки файла - используется во внутреннем классе OpenMenuListener
    public void loadFile(File file) {
        cardList = new ArrayList<QuizCard>();
        try {
            //Создаем BufferedReader, связанный с новым FileReader. Предоставляем объекту FileReader объект File, выбранный пользователем
            //в окне открытия файла
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            //читаем по одной строке за один раз, передавая результат в метод makeCard(), который разделяет его и преобразует
            //в настоящий объект QuizCard, а затем добавляет в ArrayList
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println("Couldn't read the card file");
            ex.printStackTrace();
        }
        //Время показать первую карточку
        showNextCard();
    }

    //Метод для построения карт из файловых строк
    private void makeCard(String lineToParse) {
        //каждая строка текста соответствует одной флешкарте, но нам нужно разделить вопрос и ответ. Для этого используем метод
        //split() из класса String, который разбивает строку на две лексемы (одна для вопроса и одна для ответа).
        String[] result = lineToParse.split("/");
        QuizCard card = new QuizCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("made a card");
    }

    //Метод для показа карты
    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }
}
