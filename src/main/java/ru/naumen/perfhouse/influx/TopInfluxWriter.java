package ru.naumen.perfhouse.influx;

import ru.naumen.sd40.log.parser.TopData;
import ru.naumen.sd40.log.parser.TopDataSet;

public class TopInfluxWriter extends InfluxWriter<TopDataSet> {
    public TopInfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        super(dbName, storage, withTrace);
    }

    @Override
    public void write(Long key, TopDataSet dataSet) {
        TopData cpuData = dataSet.cpuData();
        if (!cpuData.isNan()) {
            storage.storeTop(points, dbName, key, cpuData);
        }
    }
}
