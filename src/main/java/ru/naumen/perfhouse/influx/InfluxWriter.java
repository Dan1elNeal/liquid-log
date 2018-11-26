package ru.naumen.perfhouse.influx;

import org.influxdb.dto.BatchPoints;
import ru.naumen.sd40.log.parser.*;

public abstract class InfluxWriter<T extends DataSet> implements IDatabaseWriter<Long, T> {
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
}
