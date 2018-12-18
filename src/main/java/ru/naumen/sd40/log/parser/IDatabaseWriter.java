package ru.naumen.sd40.log.parser;

public interface IDatabaseWriter<K, D> {
    void write(K key, D data, boolean withTrace);
    void save();
}
