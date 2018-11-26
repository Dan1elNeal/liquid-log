package ru.naumen.perfhouse.writers;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.Top.TopData;
import ru.naumen.sd40.log.parser.Top.TopDataSet;

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
