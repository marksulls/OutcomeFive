package com.retro.core.format;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import org.joda.time.Duration;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * makes the @SqlTimeFormatter available
 * 
 * @author mark
 */
public final class DurationFormatterFactory implements AnnotationFormatterFactory<DurationFormat> {

    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>(1);
        types.add(Duration.class);
        return Collections.unmodifiableSet(types);
    }

    @Override
    public Printer<Duration> getPrinter(DurationFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<Duration> getParser(DurationFormat annotation,Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Duration> configureFormatterFrom(DurationFormat annotation,Class<?> fieldType) {
        return new DurationFormatImpl(annotation.pattern());
    }
}
