package HFJ;

/*
  Created by Tigrenok on 10.08.2017.
  Изучаем Java, стр.449.
  1. Проектируем GUI, который будет иметь 256 снятых флажков (JCheckBox), 16 меток (JLabel) для названий инструментов и 4 кнопки.
  2. Связываем ActionListener с каждой из 4-х кнопок. Слушатели для флажков нам не нужны, так как динамически изменять схему звучания
     мы не будем. Вместо этого ждем, когда пользователь нажмет кнопку "Старт", затем пробегаем 256 флажков, чтобы получить их состояния,
     и, основываясь на этом, создаем MIDI-дорожку.
  3. Устанавливаем систему MIDI, получая доступ к синтезатору, создаем объект Sequencer и дорожку для него. Используем метод интерфейса
     Sequencer setLoopCount(), позволяющий задать желаемое количество циклов последовательности. Также будем использовать коэффициент темпа
     последовательности для настройки уровня темпа и сохранять новый темп от одной итерации цикла к другой.
  4. При нажатии пользователем кнопки "Старт" начинается действие. Обработчик событий запускает метод buildTrackAndStart(). В нем мы
     пробегаем через все 256 флажков (по одному за один раз, один инструмент на все 16 тактов), чтобы получить их состояния, а затем
     используем эту информацию для создания MIDI-дорожки (с помощью удобного метода makeEvent()). Как только дорожка построена, мы
     запускаем секвенсор, который будет играть (потому что мы его зацикливаем), пока пользователь не нажмет кнопку "Стоп".
*/

import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.awt.event.*;

public class BeatBox {

    JFrame theFrame;
    JPanel mainPanel;
    JList incomingList;
    JTextField userMessage;
    ArrayList<JCheckBox> checkboxList; //массив для хранения флажков
    int nextNum;
    Vector<String> listVector = new Vector<String>();
    String userName;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap = new HashMap<String, boolean[]>();


    Sequencer sequencer; //секвенсор (синтезатор)
    Sequence sequence; //последовательность
    Sequence mySequence = null;
    Track track;

    //JTextField fieldTempo; - доработать позже
    //названия инструментов в виде строкового массива - предназначены для создания меток в пользовательском интерфейсе на каждый ряд
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
                                "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
                                "High Agogo", "Open Hi Conga"};
    //числа представляют собой фактические барабанные клавиши. Канал барабана - это что-то вроде ф-но, но каждая клавиша на нем -
    // - отдельный барабан. Номер 35 - это клавиша для Bass Drum и т.д.
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        //new BeatBox().startUp(args[0]); //это пользовательский идентификатор = отображаемое имя
        new BeatBox().startUp("Tiger");
    }

    //Настройка сети и ввода/вывода, создание и запуск потока для чтения сообщений
    public void startUp(String name) {
        userName = name;
        //Открываем соединение с сервером
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());
            Thread remote = new Thread(new RemoteReader());
            remote.start();
        } catch (Exception ex) {
            System.out.println("couldn't connect - you'll have to play alone.");
        }
        setUpMidi();
        buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);

        //пустая граница позволяет создать поля мужду краями панели и местом размещения компонентов
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //флажки (и кнопки) будут храниться в "блоках"; все они будут выровнены по вертикали
        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        //кнопка "Старт". В качестве слушателя передаем объект внутреннего класса MyStartListener
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        //кнопка "Стоп". По аналогии с кнопкой "Старт".
        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);


        //кнопка "Tempo Up" - увеличить темп
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        //upTempo.addActionListener(new MyFieldTempoListener());
        buttonBox.add(upTempo);

        //кнопка "Tempo Down" - увеличить темп
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        //downTempo.addActionListener(new MyFieldTempoListener());
        buttonBox.add(downTempo);

        //кнопка для отправки сообщений
        JButton sendIt = new JButton("Send It");
        sendIt.addActionListener(new MySendListener());
        buttonBox.add(sendIt);

        //кнопка "Serialize It"
        JButton serializeIt = new JButton("Serialize It");
        serializeIt.addActionListener(new MySaveListener());
        buttonBox.add(serializeIt);

        //кнопка "Restore"
        JButton restore = new JButton("Restore");
        restore.addActionListener(new MyReadListener());
        buttonBox.add(restore);

        userMessage = new JTextField();
        buttonBox.add(userMessage);

        //Далее идет компонент JList - в нем отображаются входящие сообщения, которые можно выбирать из списка,
        //а не только просматривать. Благодаря этому мы можем загружать и воспроизводить прикрепляемые к ним шаблоны.
        incomingList = new JList();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector); //Нет начальных данных

        /*Окно с темпом. fieldTempo. Доработать позже.
        fieldTempo = new JTextField(3);
        fieldTempo.setText("120 BPM");
        buttonBox.add(fieldTempo);
        */

        //создаем заголовки для отображения инструментов
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        //размещаем кнопки и названия инструментов
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        //размещаем панель во фрейме
        theFrame.getContentPane().add(background);

        //сетка 16х16 для флажков
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        //передаем в основную панель сетку в качестве параметра
        mainPanel = new JPanel(grid);
        //помещаем ее по центру
        background.add(BorderLayout.CENTER, mainPanel);

        //создаем флажки, присваиваем им значения false (чтобы они не были установлены), а затем добавляем их в массив ArrayList и на панель
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        //устанавливаем MIDI-систему
        setUpMidi();

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    //Метод setUpMidi для получения синтезатора, секвенсора и дорожки
    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) { e.printStackTrace(); }
    }

    //Метод для преобразования флажков в MIDI - события и добавления их на дорожку
    public void buildTrackAndStart() {
        //создаем список из 16 элементов, чтобы хранить значения для каждого инструмента на все 16 тактов
        ArrayList<Integer> trackList = null; //инструменты для каждого трека

        //удаляем старую дорожку и создаем новую
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            //делаем это для каждого из 16-ти рядов (то есть для Bass, Congo и т.д.)
            trackList = new ArrayList<Integer>();

            //делаем это для каждого текущего ряда
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkboxList.get(j + (16*i));
                //если флажок установлен на этом такте, то помещаем значение клавиши в текущую ячейку массива -
                // - ячейка представляет такт. Если нет, то инструмент не должен играть в этом такте, поэтому присвоим ему 0.
                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add(new Integer(key));
                } else {
                    trackList.add(null); //Этот слот в треке должен быть пустым
                }
            }
            //для этого инструмента и для всех 16 тактов создаем события и добавляем их на дорожку
            makeTracks(trackList);
        }

        //мы должны быть уверены, что событие на такте 16 существует, иначе BeatBox может не пройти все 16 тактов, перед повтором
        track.add(makeEvent(192, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); //позволяет задать кол-во повторений цикла или непрерывный цикл
            sequencer.start(); //проигрываем мелодию!
            sequencer.setTempoInBPM(120);
        } catch (Exception e) { e. printStackTrace(); }
    }

    //Первый из внутренних классов - слушателей для кнопок. Для кнопки "Старт".
    class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    //Второй - для кнопки "Стоп".
    class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    //Третий - для кнопки увеличения темпа. Коэффициент темпа определяет темп синтезатора. По умолчанию он равен 1.
    //Щелчком кнопкой мыши его можно изменить на +/- 3%
    class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    //Четвертый - для кнопки уменьшения темпа
    class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (sequencer.getTempoFactor() * 0.97));
        }
    }

    /* Пятый - для текстового поля со значением темпа. Тест.
    class MyFieldTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fieldTempo.setText(String.format("%.2f", sequencer.getTempoFactor() * 120) + " BPM");
        }
    }
    */

    //Шестой внутренний класс. Для записи (сериализации) состояния объекта - для сохранения музыкальной схемы. Кнопка "Serialize It"
    public class MySaveListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            //создаем булев массив для хранения состояния каждого флажка
            boolean[] checkboxState = new boolean[256];
            //проходим через checkboxList (ArrayList, содержащий состояния флажков), считываем состояния и добавляем полученные
            //значения в булев массив.
            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if (check.isSelected()) {
                    checkboxState[i] = true;
                }
            }
            //записываем/сериализуем булев массив
            try {
                //Вызываем диалоговое окно для выбора файла.
                JFileChooser fileSave = new JFileChooser();
                fileSave.showSaveDialog(theFrame);
                //Передаем абсолютный путь к файлу в FileOutputStream, используя fileSave.getSelectedFile().getAbsolutePath()
                FileOutputStream fileStream = new FileOutputStream(new File(fileSave.getSelectedFile().getAbsolutePath()));
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(checkboxState);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Седьмой внутренний класс. Для отправки сообщений. Упаковывает также "мелодию".
    public class MySendListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
                //создаем булев массив для хранения состояния каждого флажка
                boolean[] checkboxState = new boolean[256];
                //проходим через checkboxList (ArrayList, содержащий состояния флажков), считываем состояния и добавляем полученные
                //значения в булев массив.
                for (int i = 0; i < 256; i++) {
                    JCheckBox check = (JCheckBox) checkboxList.get(i);
                    if (check.isSelected()) {
                        checkboxState[i] = true;
                    }
                }

                String messageToSend = null;
                try {
                    out.writeObject(userName + nextNum++ + ": " + userMessage.getText());
                    out.writeObject(checkboxState);
                } catch (Exception ex) {
                    System.out.println("Sorry dude. Could not send it to the server.");
                }
                userMessage.setText("");
        }
    }

    //Восьмой внутренний класс. Для восстановления (десериализации) ранее сохранённой схемы. Кнопка "Restore"
    public class MyReadListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            boolean[] checkboxState = null;
            try {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(theFrame);
                //Считываем объект из файла и определяем его как булев массив.
                FileInputStream fileIn = new FileInputStream(new File(fileOpen.getSelectedFile().getAbsolutePath()));
                ObjectInputStream is = new ObjectInputStream(fileIn);
                checkboxState = (boolean[]) is.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //Теперь восстанавливаем состояние каждого флажка
            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if (checkboxState[i]) {
                    check.setSelected(true);
                } else {
                    check.setSelected(false);
                }
            }
            //останавливаем проигрывание мелодии и восстанавливаем последовательность, используя новые состояния флажков в ArrayList
            sequencer.stop();
            buildTrackAndStart();
        }
    }

    //Девятый слушатель. Срабатывает, когда пользователь выбирает сообщения из списка. При этом мы сразу загружаем соответствующий
    //музыкальный шаблон (хранящийся в переменной otherSeqsMap типа HashMap) и указываем проиграть его. Мы добавили несколько условий if
    //из-за особенностей, связанных с получением событий ListSelectionEvent
    public class MyListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent le) {
            if (!le.getValueIsAdjusting()) {
                String selected = (String) incomingList.getSelectedValue();
                if (selected != null) {
                    //Переходим к отображению и изменяем последовательность
                    boolean[] selectedState = (boolean[]) otherSeqsMap.get(selected);
                    changeSequence(selectedState);
                    sequencer.stop();
                    buildTrackAndStart();
                }
            }
        }
    }

    //Задача потока RemoteReader - читать данные, присылаемые сервером. Под данными понимаются два сериализованных объекта:
    //строковое сообщение и музыкальная последовательность (ArrayList с состоянием флажков).
    public class RemoteReader implements Runnable {
        boolean[] checkboxState = null;
        String nameToShow = null;
        Object obj = null;
        public void run() {
            try {
                while((obj = in.readObject()) != null) {
                    System.out.println("got an object from server");
                    System.out.println(obj.getClass());
                    String nameToShow = (String) obj;
                    checkboxState = (boolean[]) in.readObject();
                    otherSeqsMap.put(nameToShow, checkboxState);
                    listVector.add(nameToShow);
                    incomingList.setListData(listVector);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public class MyPlayMineListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            if (mySequence != null) {
                sequence = mySequence; //восстановление до оригинальной последовательности
            }
        }
    }

    //Метод вызывается, когда пользователь выбирает пункт из списка. Мы сразу устанавливаем выбранный шаблон.
    public void changeSequence(boolean[] checkboxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = (JCheckBox) checkboxList.get(i);
            if (checkboxState[i]) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }
        }
    }


    //Метод для создания события для одного инструмента за каждый проход цикла для всех 16 тактов. Если "0", то инструмент не играет.
    public void makeTracks(ArrayList list) {
        Iterator it = list.iterator();
        for (int i = 0; i < 16; i++) {
            Integer num = (Integer) it.next();
            if (num != null) {
                //создаем события включения и выключения их в дорожку
                int numKey = num.intValue();
                track.add(makeEvent(144, 9, numKey, 100, i));
                track.add(makeEvent(144, 9, numKey, 100, i+1));
            }
        }
    }

    //Метод MakeEvent - удобный метод для создания событий.
    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) { e.printStackTrace(); }
        return event;
    }
}