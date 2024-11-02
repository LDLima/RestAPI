package Challenge.UdemyCourse.steps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountSteps {
    public static String getData(Integer qtdDays){
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_MONTH, qtdDays);
        return formatData(day.getTime());
    }

    private static String formatData(Date date){
        DateFormat dateToFormat = new SimpleDateFormat("dd/MM/yyyy"); //Format has to be like this
        return dateToFormat.format(date);
    }
}
