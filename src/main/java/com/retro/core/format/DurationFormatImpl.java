package com.retro.core.format;

import java.util.Locale;
import java.util.regex.Pattern;

import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

/**
 * formatter for incoming time values
 * @author mark
 */
public final class DurationFormatImpl implements Formatter<Duration> {
    // logging
    final Logger _log = LoggerFactory.getLogger(DurationFormatImpl.class);
    // short date format
    private String pattern;
    private static PeriodFormatter periodFormatter;
    
    // create a regular expression to match times
    final static Pattern tinyTime = Pattern.compile("^([0-9]|[2][0-3]):([0-5][0-9])$");
    final static Pattern shortTime = Pattern.compile("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
    final static Pattern longTime = Pattern.compile("^([0-9][0-9]|[2][0-3]):([0-5][0-9])$");
    
    static {
        periodFormatter = new PeriodFormatterBuilder()
        .printZeroAlways()
        .minimumPrintedDigits(2)
        .appendHours()
        .appendSeparator(":")
        .printZeroAlways()
        .minimumPrintedDigits(2)
        .appendMinutes()
        .appendSeparator(":")
        .printZeroAlways()
        .minimumPrintedDigits(2)
        .appendSeconds()
        .toFormatter();
    }

    public DurationFormatImpl(String pattern) {
        _log.debug("using the pattern [{}]",pattern);
        this.pattern = pattern;
    }

    public String print(Duration time, Locale locale) {
        _log.debug("printing the time [{}]",time);
        if (time == null) {
            return "";
        }
        _log.debug("printing the time [{}]",periodFormatter.print(time.toPeriod()));
//        String formatted = getDateFormat(locale).
//        _log.debug("formatted time is [{}]",formatted);
        return periodFormatter.print(time.toPeriod());
    }

    public Duration parse(String formatted, Locale locale) {
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
        // parse it into a duration
        return periodFormatter.parsePeriod(formatted).toStandardDuration();
    }

    protected DateTimeFormatter getDateFormat(Locale locale) {
        _log.debug("getting date format for pattern [{}], local [{}]",pattern,locale);
        return DateTimeFormat.forPattern(this.pattern);
    }
}