package ru.naumen.sd40.log.parser;

public interface IDataParser<T extends IDataSet> {
    void parseLine(T data, String line);
}
