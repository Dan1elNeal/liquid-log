package ru.naumen.perfhouse.influx;

import ru.naumen.sd40.log.parser.ActionDoneData;
import ru.naumen.sd40.log.parser.ErrorData;
import ru.naumen.sd40.log.parser.SdngDataSet;

public class SdngInfluxWriter extends InfluxWriter<SdngDataSet> {
    public SdngInfluxWriter(String dbName, InfluxDAO storage, Boolean withTrace) {
        super(dbName, storage, withTrace);
    }

    @Override
    public void write(Long key, SdngDataSet dataSet) {
        ActionDoneData dones = dataSet.getActionsDone();
        dones.calculate();
        ErrorData errors = dataSet.getErrors();

        if (this.withTrace) {
            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", key, dones.getCount(),
                    dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                    dones.getPercent99(), dones.getPercent999(), dones.getMax(),
                    errors.getErrorCount()));
        }

        if (!dones.isNan()) {
            storage.storeActionsFromLog(points, dbName, key, dones, errors);
        }
    }
}
