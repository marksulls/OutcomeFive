package com.retro.core.format;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

/**
 * formatter for incoming time values
 * @author mark
 */
public final class SqlDateFormatImpl implements Formatter<Date> {
    // logging
    final Logger _log = LoggerFactory.getLogger(SqlDateFormatImpl.class);

    private String pattern;

    public SqlDateFormatImpl(String pattern) {
        _log.debug("using the pattern [{}]",pattern);
        this.pattern = pattern;
    }

    public String print(Date time, Locale locale) {
        _log.debug("printing the time [{}]",time);
        if (time == null) {
            return "";
        }
        String formatted = getDateFormat(locale).format(time);
        _log.debug("formatted time is [{}]",formatted);
        return formatted;
    }

    public Date parse(String formatted, Locale locale) throws ParseException {
        _log.debug("parsing the string [{}]",formatted);
        if (formatted.length() == 0) {
            return null;
        }
        return new Date(getDateFormat(locale).parse(formatted).getTime());
    }

    protected DateFormat getDateFormat(Locale locale) {
        _log.debug("getting date format for pattern [{}], local [{}]",pattern,locale);
        DateFormat dateFormat = new SimpleDateFormat(this.pattern, locale);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}