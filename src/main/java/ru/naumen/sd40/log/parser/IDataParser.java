package ru.naumen.sd40.log.parser;

public interface IDataParser<T extends DataSet> {
    void parseLine(T data, String line);
    IDataSetFactory<T> getDataSetFactory();
}
