package ru.naumen.perfhouse.writers;

public interface IDatabaseWriter<K, D> {
    void write(K key, D data);
    void save();
}
