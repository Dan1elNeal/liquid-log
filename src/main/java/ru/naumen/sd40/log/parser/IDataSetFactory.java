package ru.naumen.sd40.log.parser;

public interface IDataSetFactory<T extends IDataSet> {
    T create();
}
