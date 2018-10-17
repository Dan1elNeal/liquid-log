package ru.naumen.sd40.log.parser;

import java.text.ParseException;

public interface ITimeParser {
    long parseLine(String line) throws ParseException;
}
