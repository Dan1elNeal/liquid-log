package ru.naumen.sd40.log.parser;

public interface IParsingMode<T extends IDataSet> extends IRenderMode {
    IDataParser getDataParser();
    ITimeParser getTimeParser();
    IDataSetFactory<T> getDataSetFactory();
}
