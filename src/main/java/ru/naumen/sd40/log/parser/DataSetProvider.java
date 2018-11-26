package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.writers.IDatabaseWriter;

public class DataSetProvider<T extends DataSet> {
    private IDatabaseWriter<Long, T> writer;
    private long currentKey;
    private IDataSetFactory<T> dataSetFactory;
    private T currentDataSet = null;

    public DataSetProvider(IDatabaseWriter<Long, T> writer, IDataSetFactory<T> dataSetFactory) {
        this.writer = writer;
        this.dataSetFactory = dataSetFactory;
    }

    public T get(Long key) {
        if (currentDataSet == null) {
            currentKey = key;
            currentDataSet = dataSetFactory.create();

            return currentDataSet;
        }

        if (key != currentKey) {
            writer.write(currentKey, currentDataSet);
            currentKey = key;
            currentDataSet = dataSetFactory.create();
        }

        return currentDataSet;
    }

    public void flush() {
        if (currentDataSet != null) {
            writer.write(currentKey, currentDataSet);
            currentDataSet = null;
        }
    }
}
