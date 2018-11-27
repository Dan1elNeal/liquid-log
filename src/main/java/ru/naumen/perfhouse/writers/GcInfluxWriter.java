package ru.naumen.perfhouse.writers;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.Gc.GCData;
import ru.naumen.sd40.log.parser.Gc.GcDataSet;

public class GcInfluxWriter extends InfluxWriter<GcDataSet> {
    public GcInfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        super(dbName, storage, withTrace);
    }

    @Override
    public void write(Long key, GcDataSet dataSet) {
        GCData gc = dataSet.getGc();

        if (!gc.isNan()) {
            storage.storeGc(points, dbName, key, gc);
        }
    }
}
