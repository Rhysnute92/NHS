package uk.ac.cf.spring.nhs.Common.util;

import java.time.LocalDate;

public class DateUtils {

    /**
     * Returns the current day of the month.
     *
     * @return          the current day of the month
     */
    public static int getCurrentDayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * Returns the number of days in the current month.
     *
     * @return          the number of days in the current month
     */
    public static int getDaysInCurrentMonth() {
        return LocalDate.now().lengthOfMonth();
    }

}
