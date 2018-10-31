package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.influx.IDatabaseWriter;

public class DataSetProvider {
    private IDatabaseWriter<Long, DataSet> writer;
    private long currentKey;
    private DataSet currentDataSet = null;

    public DataSetProvider(IDatabaseWriter<Long, DataSet> writer) {
        this.writer = writer;
    }

    public DataSet get(Long key) {
        if (currentDataSet == null) {
            currentKey = key;
            currentDataSet = new DataSet();

            return currentDataSet;
        }

        if (key != currentKey) {
            writer.write(currentKey, currentDataSet);
            currentKey = key;
            currentDataSet = new DataSet();
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
