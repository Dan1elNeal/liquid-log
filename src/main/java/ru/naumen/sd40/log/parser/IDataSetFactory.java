package ru.naumen.sd40.log.parser;

public interface IDataSetFactory<T extends DataSet> {
    T create();
}
