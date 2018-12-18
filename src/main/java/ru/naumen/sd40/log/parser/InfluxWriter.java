package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class InfluxWriter implements IDatabaseWriter<Long, IDataSet> {
    protected String dbName;
    protected BatchPoints points;
    protected InfluxDAO storage;
    protected Boolean withTrace;

    public InfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        this.dbName = dbName.replaceAll("-", "_");

        this.storage = storage;
        this.storage.init();
        this.storage.connectToDB(dbName);

        this.withTrace = withTrace;

        this.points = storage.startBatchPoints(dbName);
    }

    @Override
    public void save() {
        storage.writeBatch(points);
    }

    @Override
    public void write(Long key, IDataSet dataSet, boolean withTrace) {
        dataSet.calculate();

        if (withTrace) {
            System.out.println(dataSet.getTrace());
        }

        if (!dataSet.isNan()) {
            storage.storeLogs(points, dbName, key, dataSet);
        }
    }
}
