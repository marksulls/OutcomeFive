package com.retro.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.TimeZone;

/**
 * Utility to assist in translating common date strings we encounter into {@link Timestamp} instances.  
 *
 * @author <a href="mailto:mark@powerproleasing.com">Mark Sullivan<a/>
 */
public class DateUtil {
    // logging
    final static Logger _log = LoggerFactory.getLogger(DateUtil.class);
    
    private static final ThreadLocal<DateFormat> iosFormatter;
    private static final ThreadLocal<DateFormat> msFormatter;
    private static final ThreadLocal<DateFormat> oneSiteFormatter;
    
    static {
        // create the static date formatters as ThreadLocal instances, DateFormat is not thread safe!
        // this is the most performant solution as it avoids locking or creating new instances in each method
        iosFormatter = new ThreadLocal<DateFormat>() {
            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
        msFormatter = new ThreadLocal<DateFormat>() {
            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            }
        };
        oneSiteFormatter = new ThreadLocal<DateFormat>() {
            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            }
        };
    }
    
    public static String safeFormat(Timestamp timestamp) {
        // return empty string
        if(timestamp == null) {
            return "";
        }
        // else format it
        return iosFormatter.get().format(timestamp);
    }
    
    public static String safeFormat(Date date) {
        // return empty string
        if(date == null) {
            return "";
        }
        // else format it
        return iosFormatter.get().format(date);
    }
    
    public static String getOffsetString(TimeZone tz) {
//        long offset = TimeUnit.MILLISECONDS.toHours(tz.getOffset(new Date().getTime()));
        // since the iPad doesn't respect DST, use the raw offset. TODO: I hope this is correct
//        long offset = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long offset = TimeUnit.MILLISECONDS.toHours(tz.getOffset(System.currentTimeMillis()));
        String mysqlTz = null;
        // this is ghetto
        if(offset == 0) {
            mysqlTz ="+00:00";
        } else if(offset < 0) {
            mysqlTz = "-"; // 00:00";
            if(offset < -9) {
                mysqlTz += Math.abs(offset) + ":00";
            } else {
                mysqlTz += "0" + Math.abs(offset) + ":00";
            }
        } else {
            mysqlTz = "+"; // 00:00";
            if(offset > 9) {
                mysqlTz += Math.abs(offset) + ":00";
            } else {
                mysqlTz += "0" + Math.abs(offset) + ":00";
            }
        }
        return mysqlTz;
    }
    
    /**
     * takes in the API/MSSQL format:
     * 1/1/1900 12:00:00 AM
     * and returns a <tt>Timestamp</tt>
     * @param dateString
     * @return
     */
    public static Timestamp msToTimestamp(String dateString) {
        // this is a quick out
        if(dateString == null || dateString.isEmpty() || dateString.equals("1/1/1900 12:00:00 AM")) {
            return null;
        }
        // transform to date
        Date date;
        try {
            date = msFormatter.get().parse(dateString);
        } catch (ParseException e) {
            _log.error("bad date passed [{}] - [{}]",dateString,e);
            return null;
        } catch (NumberFormatException e) {
            _log.error("bad date passed [{}] - [{}]",dateString,e);
            return null;
        }
        _log.debug("[{}]",date.getTime());
        return new Timestamp(date.getTime());
    }
    
    /**
     * takes in the API/MSSQL format:
     * 1/1/1900 12:00:00 AM
     * and returns a <tt>Timestamp</tt>
     * @param dateString
     * @return
     */
    public static String timestampToOneSite(Timestamp timestamp) {
        // this is a quick out
//        if(dateString.equals("1/1/1900 12:00:00 AM")) {
//            return null;
//        }
        // transform to date
        return oneSiteFormatter.get().format(timestamp);
    }
    
    /**
     * takes in the API/MSSQL format:
     * 1/1/1900 12:00:00 AM
     * and returns a <tt>Timestamp</tt>
     * @param dateString
     * @return
     */
    public static Timestamp oneSiteToTimestamp(String dateString) {
        // this is a quick out
        if(dateString.equals("1/1/1900 12:00:00 AM")) {
            return null;
        }
        // transform to date
        return stringToTimestamp(dateString);
    }
    
    public static Timestamp stringToTimestamp(String dateString) {
    	Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(dateString);
        return new Timestamp(cal.getTimeInMillis());
    }
}
