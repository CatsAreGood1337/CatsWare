package cats.gg.CatsWare.util;

import java.util.Calendar;

public class TimeUtil {
    public TimeUtil() {
    }

    public static int get_hour() {
        return Calendar.getInstance().get(11);
    }

    public static int get_day() {
        return Calendar.getInstance().get(5);
    }

    public static int get_month() {
        return Calendar.getInstance().get(2);
    }

    public static int get_minuite() {
        return Calendar.getInstance().get(12);
    }

    public static int get_second() {
        return Calendar.getInstance().get(13);
    }
}
