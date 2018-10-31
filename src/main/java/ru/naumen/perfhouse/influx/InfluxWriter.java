package ru.naumen.perfhouse.influx;

import org.influxdb.dto.BatchPoints;
import ru.naumen.sd40.log.parser.*;

public class InfluxWriter implements IDatabaseWriter<Long, DataSet> {
    private String dbName;
    private BatchPoints points;
    private InfluxDAO storage;
    private Boolean withTrace;

    public InfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        this.dbName = dbName;

        this.storage = storage;
        this.storage.init();
        this.storage.connectToDB(dbName);

        this.withTrace = withTrace;

        this.points = storage.startBatchPoints(dbName);
    }

    @Override
    public void write(Long key, DataSet dataSet) {
        ActionDoneParser dones = dataSet.getActionsDone();
        dones.calculate();
        ErrorParser errors = dataSet.getErrors();

        if (this.withTrace) {
            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", key, dones.getCount(),
                    dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                    dones.getPercent99(), dones.getPercent999(), dones.getMax(), errors.getErrorCount()));
        }

        if (!dones.isNan()) {
            storage.storeActionsFromLog(points, dbName, key, dones, errors);
        }

        GCParser gc = dataSet.getGc();
        if (!gc.isNan()) {
            storage.storeGc(points, dbName, key, gc);
        }

        TopData cpuData = dataSet.cpuData();
        if (!cpuData.isNan()) {
            storage.storeTop(points, dbName, key, cpuData);
        }
    }

    @Override
    public void save() {
        storage.writeBatch(points);
    }
}
