package ru.naumen.sd40.log.parser;

public class DataSetProvider<T extends IDataSet> {
    private IDatabaseWriter<Long, T> writer;
    private long currentKey;
    private IDataSetFactory<T> dataSetFactory;
    private T currentDataSet = null;
    private boolean withTrace;

    public DataSetProvider(IDatabaseWriter<Long, T> writer, IDataSetFactory<T> dataSetFactory, boolean withTrace) {
        this.writer = writer;
        this.dataSetFactory = dataSetFactory;
        this.withTrace = withTrace;
    }

    public T get(Long key) {
        if (currentDataSet == null) {
            currentKey = key;
            currentDataSet = dataSetFactory.create();

            return currentDataSet;
        }

        if (key != currentKey) {
            writer.write(currentKey, currentDataSet, withTrace);
            currentKey = key;
            currentDataSet = dataSetFactory.create();
        }

        return currentDataSet;
    }

    public void flush() {
        if (currentDataSet != null) {
            writer.write(currentKey, currentDataSet, withTrace);
            currentDataSet = null;
        }
    }
}
