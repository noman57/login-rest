package com.ufril.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Noman
 */
public class DateUtils {

    public static Date getTodayStart() {
        final TimeZone timeZone = TimeZone.getDefault();
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);// forID( "America/Montreal" );
        DateTime now = DateTime.now(dateTimeZone);
        DateTime todayStart = now.withTimeAtStartOfDay();
        return todayStart.toDate();
    }

    public static Date getTomorrowStart() {
        final TimeZone timeZone = TimeZone.getDefault();
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);// forID( "America/Montreal" );
        DateTime now = DateTime.now(dateTimeZone);
        DateTime tomorrowStart = now.plusDays( 1 ).withTimeAtStartOfDay();
        return tomorrowStart.toDate();
    }

    public static String getServerTimeZoneDisplayName(Locale locale) {
        final TimeZone timeZone = TimeZone.getDefault();
        final boolean daylight = timeZone.inDaylightTime(new Date());
        return timeZone.getDisplayName(daylight, TimeZone.LONG, locale);
    }

//    public static Date getInterval(TimeZone timeZone, ) {
//        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);// forID( "America/Montreal" );
//        DateTime now = DateTime.now( dateTimeZone );
//        DateTime todayStart = now.withTimeAtStartOfDay();
//        DateTime tomorrowStart = now.plusDays( 1 ).withTimeAtStartOfDay();
//        Interval today = new Interval( todayStart, tomorrowStart );
//    }

    public static boolean isExpired(Date expiryDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        final Calendar expire = Calendar.getInstance();
        expire.setTimeInMillis(expiryDate.getTime());
        return cal.after(expire);
    }

    public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
