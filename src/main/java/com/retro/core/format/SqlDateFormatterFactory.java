package com.retro.core.format;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * makes the @SqlTimeFormatter available
 * 
 * @author mark
 */
public final class SqlDateFormatterFactory implements AnnotationFormatterFactory<SqlDateFormat> {

    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>(1);
        types.add(Date.class);
        return Collections.unmodifiableSet(types);
    }

    @Override
    public Printer<Date> getPrinter(SqlDateFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<Date> getParser(SqlDateFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Date> configureFormatterFrom(SqlDateFormat annotation,Class<?> fieldType) {
        return new SqlDateFormatImpl(annotation.pattern());
    }
}
