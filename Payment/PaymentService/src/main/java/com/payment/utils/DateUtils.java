package com.payment.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

  /**
   * Private constructor because all methods are static, no need to create object.
   */
  private DateUtils() {

  }

  /**
   * Gives system date(current date) with time 00:00:00<br>
   * Example: Sat Jun 17 00:00:00
   * 
   * @return {@link Date} curretn date
   */
  public static Date getCurrentdate() {
    Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
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

  /**
   * Removes time from given date
   * 
   * @param date
   *          {@link Date} object
   * @return {@link Date} without time.
   */
  public static Date removeTime(Date date) {
    return Date.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Gives current time with date.
   * 
   * @return current time and date as {@link Date}
   */
  public static Date getCurrentTimeStamp() {
    return Date.from(Instant.now());
  }

}
