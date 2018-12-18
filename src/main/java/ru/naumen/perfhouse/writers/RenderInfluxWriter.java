package ru.naumen.perfhouse.writers;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.Render.RenderDataSet;
import ru.naumen.sd40.log.parser.Render.RenderTimeData;

public class RenderInfluxWriter extends InfluxWriter<RenderDataSet> {
    public RenderInfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        super(dbName, storage, withTrace);
    }

    @Override
    public void write(Long key, RenderDataSet dataSet) {
        RenderTimeData data = dataSet.getRenderTimeData();
        data.calculate();

        if (!data.isNan()) {
            storage.storeRender(points, dbName, key, data);
        }
    }
}
