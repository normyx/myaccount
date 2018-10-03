package org.mgoulene.web.rest.util;

import java.time.LocalDate;

public class LocalDateUtil {
    public static LocalDate getLocalDate(LocalDate month, int dayInMonth) {
        return LocalDate.of(month.getYear(), month.getMonthValue(), month.lengthOfMonth() < dayInMonth ? month.lengthOfMonth(): dayInMonth);
    }
}