package com.retro.core.format;

import java.sql.Time;
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
public final class SqlTimeFormatterFactory implements AnnotationFormatterFactory<SqlTimeFormat> {

    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>(1);
        types.add(Time.class);
        return Collections.unmodifiableSet(types);
    }

    @Override
    public Printer<Time> getPrinter(SqlTimeFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<Time> getParser(SqlTimeFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Time> configureFormatterFrom(SqlTimeFormat annotation,Class<?> fieldType) {
        return new SqlTimeFormatImpl(annotation.pattern());
    }
}
