package ru.naumen.perfhouse.influx;

public interface IDatabaseWriter<K, D> {
    void write(K key, D data);
    void save();
}
