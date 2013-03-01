package com.retro.core.format;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

/**
 * formatter for incoming time values
 * @author mark
 */
public final class SqlTimeFormatImpl implements Formatter<Time> {
    // logging
    final Logger _log = LoggerFactory.getLogger(SqlTimeFormatImpl.class);
    // short date format
    private String pattern;
    
    // create a regular expression to match times
    final static Pattern tinyTime = Pattern.compile("^([0-9]|[2][0-3]):([0-5][0-9])$");
    final static Pattern shortTime = Pattern.compile("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
    final static Pattern longTime = Pattern.compile("^([0-9][0-9]|[2][0-3]):([0-5][0-9])$");

    public SqlTimeFormatImpl(String pattern) {
        _log.debug("using the pattern [{}]",pattern);
        this.pattern = pattern;
    }

    public String print(Time time, Locale locale) {
        _log.debug("printing the time [{}]",time);
        if (time == null) {
            return "";
        }
        String formatted = getDateFormat(locale).format(time);
        _log.debug("formatted time is [{}]",formatted);
        return formatted;
    }

    public Time parse(String formatted, Locale locale) throws ParseException {
        _log.debug("parsing the string [{}]",formatted);
        if (formatted.length() == 0) {
            return null;
        }
        // see if it is a short date
        if(tinyTime.matcher(formatted).matches()) {
            _log.debug("found tiny date");
            // append the 00:0
            formatted = "00:0" + formatted;
        } else if(shortTime.matcher(formatted).matches()) {
            _log.debug("found short date");
            // append the 00
            formatted = "00:" + formatted;
        } else {
            _log.debug("no match");
        }
        Date date = getDateFormat(locale).parse(formatted);
        _log.debug("date is [{}]",date);
        return new Time(date.getTime());
    }

    protected DateFormat getDateFormat(Locale locale) {
        _log.debug("getting date format for pattern [{}], local [{}]",pattern,locale);
        DateFormat dateFormat = new SimpleDateFormat(this.pattern);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}