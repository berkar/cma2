package se.berkar.common.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateHelper {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private DateHelper() {
        // Empty default constructor
    }

    /**
     * Returns number of days for a given year and month. Can be used to create a "correct" calender GUI etc.
     *
     * @param month 0-11
     * @return number of days for given year and month, 1-31
     */
    public static int daysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        return calendar.get(Calendar.DATE);
    }

    public static Date floor(Date d) {
        DateHolder dh = new DateHolder(d);

        Calendar calendar = Calendar.getInstance();
        calendar.set(dh.getYear(),
                     dh.getMonth(),
                     dh.getDay(),
                     0,
                     0,
                     0);
        return calendar.getTime();
    }

    public static Date roof(Date d) {
        DateHolder dh = new DateHolder(d);

        Calendar calendar = Calendar.getInstance();
        calendar.set(dh.getYear(),
                     dh.getMonth(),
                     dh.getDay(),
                     23,
                     59,
                     59);
        return calendar.getTime();
    }

    public static boolean before(Date d1, Date d2) {
        DateHolder dh1 = new DateHolder(d1);
        DateHolder dh2 = new DateHolder(d2);

        if ((dh1.getYear() < dh2.getYear()) ||
            (dh1.getYear() == dh2.getYear() && dh1.getMonth() < dh2.getMonth()) ||
            (dh1.getYear() == dh2.getYear() && dh1.getMonth() == dh2.getMonth() && dh1.getDay() < dh2.getDay())) {
            return true;
        }
        return false;
    }

    public static boolean equals(Date d1, Date d2) {
        DateHolder dh1 = new DateHolder(d1);
        DateHolder dh2 = new DateHolder(d2);

        if (dh1.getYear() == dh2.getYear() && dh1.getMonth() == dh2.getMonth() && dh1.getDay() == dh2.getDay()) {
            return true;
        }
        return false;
    }

    public static boolean after(Date d1, Date d2) {
        if (!before(d1, d2) && !equals(d1, d2)) {
            return true;
        }
        return false;
    }

    private static class DateHolder {

        private Date date = null;
        private int year;
        private int month;
        private int day;

        public DateHolder(Date date) {
            this.date = new Date(date.getTime());
            initialize();
        }

        private void initialize() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DATE);
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }


    /**
     * Parses a localized date string into a date object
     *
     * @param s the string to parse
     * @return a date object or null if the string was not a parsable date
     */
    public static Date parseDate(String s, Locale locale) {
        try {
            // Sanity check
            if (s == null) {
                return null;
            }

            // Go back and forth to eliminate the datefunctions in all systems that accepts e.g. 2002-01-32 as 2002-02-01 etc
            Date d = DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(s);
            String sd = DateFormat.getDateInstance(DateFormat.SHORT, locale).format(d);

            if (s.equals(sd)) {
                return d;
            }

            //Try to parse the date with finer granularity
            return parseDateShortTime(s, locale);
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Parses a localized date and short time (no seconds) string into a date object
     *
     * @param s the string to parse
     * @return a date object or null if the string was not a parsable date and time
     */
    public static Date parseDateShortTime(String s, Locale locale) {
        try {
            // Sanity check
            if (s == null) {
                return null;
            }

            // Go back and forth to eliminate the datefunctions in all systems that accepts e.g. 2002-01-32 as 2002-02-01 etc
            Date d = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).parse(s);
            String sd = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(d);

            if (s.equals(sd)) {
                return d;
            }

            //Try to parse with finer granularity
            return parseDateTime(s, locale);
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Parses a localized date and time string into a date object
     *
     * @param s the string to parse
     * @return a date object or null if the string was not a parsable date and time
     */
    public static Date parseDateTime(String s, Locale locale) {
        try {
            // Sanity check
            if (s == null) {
                return null;
            }

            // Go back and forth to eliminate the datefunctions in all systems that accepts e.g. 2002-01-32 as 2002-02-01 etc
            Date d = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale).parse(s);
            String sd = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale).format(d);

            if (s.equals(sd)) {
                return d;
            }

            // Not a valid date
            return null;
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Formats a date and time to a localized text representation of the date and time.
     *
     * @param date the Date to be formated
     * @return a string containing the formated time
     */
    public static String getDateShortTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(date);
    }


    /**
     * Formats a date to a localized text representation of the date
     *
     * @param date the Date to be formated
     * @return a string containing the formated date
     */
    public static String getDate(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
    }

    /**
     * Formats a date to a localized text representation of the date
     *
     * @param date the Date to be formated
     * @return a string containing the formated date in an long format
     */
    public static String getLongDate(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
    }

    /**
     * Formats a date and time to a localized text representation of the date and time.
     *
     * @param date the Date to be formated
     * @return a string containing the formated time
     */
    public static String getDateTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale).format(date);
    }

    /**
     * Formats a time (actually also a Date object but where the date part is ignored) to a localized text representation of the time. This includes hours, minutes and seconds.
     *
     * @param date the Date to be formated
     * @return a string containing the formated time
     */
    public static String getTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getTimeInstance(DateFormat.LONG, locale).format(date);
    }

    /**
     * Formats a time (actually also a Date object but where the date part is ignored) to a localized text representation of the time. This includes hours, minutes and seconds.
     *
     * @param date the Date to be formated
     * @return a string containing the formated time
     */
    public static String getShortTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(date);
    }


    /**
     * Formats a text date and time into a date according to format specified.
     *
     * @param date the Date to be parsed
     * @return The created Date
     */
    public static Date parse(String date) {
        if (isNumeric(date)) {
            return new Date(Long.valueOf(date));
        }
        return parse(date, DEFAULT_FORMAT);
    }

    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }

    /**
     * Formats a text date and time into a date according to format specified.
     *
     * @param date   the Date to be parsed
     * @param format Date and Time format string
     * @return The created Date
     */
    public static Date parse(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Formats a date and time into text representation of the date and time according to format specified.
     *
     * @param date the Date to be formated
     * @return a string containing the formated time
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT);
    }

    /**
     * Formats a date and time into text representation of the date and time according to format specified.
     *
     * @param date   the Date to be formated
     * @param format Time format string
     * @return a string containing the formated time
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String convert(String date, String fromFormat, String toFormat) {
        Date tmp = parse(date, fromFormat);
        if (tmp == null) {
            return "";
        }
        return format(tmp, toFormat);
    }

    /**
     * Compares two dates with precision day
     *
     * @return true if date1 is equal to or greater than date2
     */
    public static boolean dateAfterOrEqual(Date date1, Date date2) {
        return dateLessOrEqualDayInternal(date2, date1, false);
    }

    /**
     * Compares two dates with precision minute
     *
     * @return true if date1 is equal to or greater than date2
     */
    public static boolean dateAfterOrEqualMinute(Date date1, Date date2) {
        return dateLessOrEqualMinuteInternal(date2, date1, false);
    }

    /**
     * Compares two dates with precision day
     *
     * @return true if date1 is equal to date2
     */
    public static boolean dateEqual(Date date1, Date date2) {
        return (dateAfterOrEqual(date1, date2) && dateBeforeOrEqual(date1, date2));
    }

    /**
     * Compares two dates with precision second
     *
     * @return true if date1 is equal to date2
     */
    public static boolean dateEqualSecond(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DATE);
        int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
        int minute1 = cal1.get(Calendar.MINUTE);
        int second1 = cal1.get(Calendar.SECOND);

        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DATE);
        int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
        int minute2 = cal2.get(Calendar.MINUTE);
        int second2 = cal2.get(Calendar.SECOND);

        if ((year1 == year2) &&
            (month1 == month2) &&
            (day1 == day2) &&
            (hour1 == hour2) &&
            (minute1 == minute2) &&
            (second1 == second2)) {
            return true;
        }

        return false;
    }

    /**
     * Compares two dates with precision day
     *
     * @return true if date1 is greater than date2
     */
    public static boolean dateAfter(Date date1, Date date2) {
        return dateLessOrEqualDayInternal(date2, date1, true);
    }

    /**
     * Compares two dates with precision day
     *
     * @return true if date1 is equal to or less than date2
     */
    public static boolean dateBeforeOrEqual(Date date1, Date date2) {
        return dateLessOrEqualDayInternal(date1, date2, false);
    }

    /**
     * Compares two dates with precision day
     *
     * @return true if date1 is less than date2
     */
    public static boolean dateBefore(Date date1, Date date2) {
        return dateLessOrEqualDayInternal(date1, date2, true);
    }

    /**
     * Compares date with today with precision day
     *
     * @return true if date is equal to or greater than todays date
     */
    public static boolean dateEqualOrAfterToday(Date date) {
        return dateLessOrEqualDayInternal(new Date(System.currentTimeMillis()), date, false);
    }

    /**
     * Compares date with today with precision day
     *
     * @return true if date is equal to or less than todays date
     */
    public static boolean dateEqualOrBeforeToday(Date date) {
        return dateLessOrEqualDayInternal(date, new Date(System.currentTimeMillis()), false);
    }

    /**
     * Compares two date spans for overlap, the comparison is not strict <p/> e.g.     <---------------------->      fromDate2 & toDate2 1. <------> fromDate1 & toDate1 -> false 2.
     * <-------------->      fromDate1 & toDate1 -> true 3. <--------------->          fromDate1 & toDate1 -> true 4. <----------------------------------------------->       fromDate1 &
     * toDate1 -> true 5.         <--------------->  fromDate1 & toDate1 -> true 6. <------->       fromDate1 & toDate1 -> false
     *
     * @param fromDate1 null if valid since the beginning of time
     * @param toDate1   null if valid until the end of time
     * @param fromDate2 null if valid since the beginning of time
     * @param toDate2   null if valid until the end of time
     * @return true if fromDate1 and toDate1 overlap (fully or partially) fromDate2 and toDate2
     */
    public static boolean datesOverlap(Date fromDate1, Date toDate1, Date fromDate2, Date toDate2) {
        fromDate1 = (fromDate1 == null) ? new Date(0) : fromDate1;
        toDate1 = (toDate1 == null) ? new Date(Long.MAX_VALUE) : toDate1;
        fromDate2 = (fromDate2 == null) ? new Date(0) : fromDate2;
        toDate2 = (toDate2 == null) ? new Date(Long.MAX_VALUE) : toDate2;

        // Sanity check
        if (dateAfter(fromDate2, toDate2) || dateAfter(fromDate1, toDate1)) {
            throw new IllegalArgumentException("fromdate must be before or equal to todate");
        }

        // Normal case 1
        if (dateBefore(toDate1, fromDate2)) {
            return false;
        }

        // Normal case 6
        if (dateAfter(fromDate1, toDate2)) {
            return false;
        }

        // Normal case 3 & 4
        if (dateBeforeOrEqual(fromDate1, fromDate2) &&
            dateAfterOrEqual(toDate1, fromDate2)) {
            return true;
        }

        // Normal case 5
        if (dateAfterOrEqual(toDate1, toDate2) &&
            dateBeforeOrEqual(fromDate1, toDate2)) {
            return true;
        }

        // Normal case 2
        if (dateAfterOrEqual(fromDate1, fromDate2) &&
            dateBeforeOrEqual(toDate1, toDate2)) {
            return true;
        }

        return false;
    }

    /**
     * Same as above but the precision is minutes
     */
    public static boolean datesOverlapMinute(Date fromDate1, Date toDate1, Date fromDate2, Date toDate2) {
        fromDate1 = (fromDate1 == null) ? new Date(0) : fromDate1;
        toDate1 = (toDate1 == null) ? new Date(Long.MAX_VALUE) : toDate1;
        fromDate2 = (fromDate2 == null) ? new Date(0) : fromDate2;
        toDate2 = (toDate2 == null) ? new Date(Long.MAX_VALUE) : toDate2;

        // Sanity check
        if (dateAfterOrEqualMinute(fromDate2, toDate2) || dateAfterOrEqualMinute(fromDate1, toDate1)) {
            throw new IllegalArgumentException("fromdate must be before or equal to todate");
        }

        // Normal case 1
        if (dateAfterOrEqualMinute(fromDate1, toDate2)) {
            return false;
        }

        // Normal case 6
        if (dateAfterOrEqualMinute(fromDate2, toDate1)) {
            return false;
        }

        // Overlapping on left side only
        if (dateAfterOrEqualMinute(fromDate2, fromDate1) &&
            dateAfterOrEqualMinute(toDate1, fromDate2)) {
            return true;
        }

        // Overlapping on right side only
        if (dateAfterOrEqualMinute(fromDate1, fromDate2) &&
            dateAfterOrEqualMinute(toDate2, fromDate1)) {
            return true;
        }

        return false;
    }

    private static boolean dateLessOrEqualDayInternal(Date date1, Date date2, boolean strict) {
        date1 = (date1 == null) ? new Date(0) : date1;
        date2 = (date2 == null) ? new Date(Long.MAX_VALUE) : date2;

        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);

        int date1Year = cal1.get(Calendar.YEAR);
        int date1Month = cal1.get(Calendar.MONTH);
        int date1Day = cal1.get(Calendar.DATE);

        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);

        int date2Year = cal2.get(Calendar.YEAR);
        int date2Month = cal2.get(Calendar.MONTH);
        int date2Day = cal2.get(Calendar.DATE);

        if ((date1Year < date2Year) ||
            (date1Year == date2Year && date1Month < date2Month) ||
            (date1Year == date2Year && date1Month == date2Month && date1Day < date2Day) ||
            (!strict && date1Year == date2Year && date1Month == date2Month && date1Day == date2Day)) {
            return true;
        }

        return false;
    }

    private static boolean dateLessOrEqualMinuteInternal(Date date1, Date date2, boolean strict) {
        date1 = (date1 == null) ? new Date(0) : date1;
        date2 = (date2 == null) ? new Date(Long.MAX_VALUE) : date2;

        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);

        int date1Year = cal1.get(Calendar.YEAR);
        int date1Month = cal1.get(Calendar.MONTH);
        int date1Day = cal1.get(Calendar.DATE);
        int date1Hour = cal1.get(Calendar.HOUR_OF_DAY);
        int date1Minute = cal1.get(Calendar.MINUTE);

        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);

        int date2Year = cal2.get(Calendar.YEAR);
        int date2Month = cal2.get(Calendar.MONTH);
        int date2Day = cal2.get(Calendar.DATE);
        int date2Hour = cal2.get(Calendar.HOUR_OF_DAY);
        int date2Minute = cal2.get(Calendar.MINUTE);

        if ((date1Year < date2Year) ||
            (date1Year == date2Year && date1Month < date2Month) ||
            (date1Year == date2Year && date1Month == date2Month && date1Day < date2Day) ||
            (date1Year == date2Year && date1Month == date2Month && date1Day == date2Day && date1Hour < date2Hour) ||
            (date1Year == date2Year && date1Month == date2Month && date1Day == date2Day && date1Hour == date2Hour && date1Minute < date2Minute) ||
            (!strict && date1Year == date2Year && date1Month == date2Month && date1Day == date2Day && date1Hour == date2Hour && date1Minute == date2Minute)) {
            return true;
        }

        return false;
    }

    /**
     * Returns a string with the weekday and the time of the day in date.
     */
    public static String getDayAndTime(Date date, Locale locale) {
        if (date != null) {
            return getWeekDay(date, locale) + " " + getShortTime(date, locale);
        }
        return "";
    }

    public static String getWeekDay(Date date, Locale locale) {
        if (date != null) {
            return new SimpleDateFormat("EEEEEEEE", locale).format(date);
        }
        return "";
    }

    public static Date substract(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -years);
        cal.add(Calendar.MONTH, -months);
        cal.add(Calendar.DATE, -days);
        cal.add(Calendar.HOUR, -hours);
        cal.add(Calendar.MINUTE, -minutes);
        cal.add(Calendar.SECOND, -seconds);

        return cal.getTime();
    }

    public static Date add(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        cal.add(Calendar.MONTH, months);
        cal.add(Calendar.DATE, days);
        cal.add(Calendar.HOUR, hours);
        cal.add(Calendar.MINUTE, minutes);
        cal.add(Calendar.SECOND, seconds);

        return cal.getTime();
    }

}
