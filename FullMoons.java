package HFJ;

import java.util.Calendar;

import static java.lang.System.out;

/**
 * Created by Tigrenok on 26.07.2017.
 * Программа для расчета полнолуний. "Изучаем Java", стр.342.
 */
public class FullMoons {

    static final int DAY_IM = 60 * 60 * 24 * 1000; //количество миллисекунд в сутках

    public static void main(String[] args) throws Exception {
        Calendar c = Calendar.getInstance(); //получаем экземпляр класса Calendar
        //c.setTimeZone(TimeZone.getTimeZone("MST")); //эта строка не обязательно можно её скрыть - игрался с временными зонами
        c.set(2004,0,7,15,40); //устанавливаем дату
        System.out.println("Выполнение программы: \n"); //это просто сообщение с переносом строки

        for (int x = 0; x < 3; x++) { //выведем на экран 3 следующих полнолуния
            long day1 = c.getTimeInMillis(); //получаем установленное время в миллисекундах
            day1 += (DAY_IM * 29.52); //прибавляем цикл полной луны. Раз в 29,52 дня.
            c.setTimeInMillis(day1); //устанавливаем это новое время
            out.println(String.format("Полнолуние было в %tc", c)); //выводим на печать в соответствии с форматированием
        }
        //FullMoons fullMoons = new FullMoons();
        //System.out.println(fullMoons.getClass());
    }
}
