package com.payment.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

  /**
   * Gives system date(current date)
   * 
   * @return {@link Date} curretn date
   */
  public static Date getCurrentdate() {
    return Calendar.getInstance().getTime();
  }

  /**
   * Adds given number of days to given date.
   * 
   * @param date
   *          {@link Date}
   * @param numOfDays
   *          Number of days to be add Example: 1 (adds one day)<br>
   *          If you want to subtract pass negative value Example: -2 (subtracts 2 days).
   * @return
   */
  public static Date addDaysToDate(Date date, int numOfDays) {
    if (date != null) {
      return Date.from(date.toInstant().plus(numOfDays, ChronoUnit.DAYS));
    }

    return null;

  }

}
