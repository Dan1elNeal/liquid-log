package ru.naumen.sd40.log.parser;

public interface ITimeParserFactory<T extends ITimeParser> {
    T create();
}
