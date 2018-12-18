package ru.naumen.sd40.log.parser.Gc;

import ru.naumen.sd40.log.parser.IDataSet;

import java.util.HashMap;
import java.util.Map;

import static ru.naumen.sd40.log.parser.Gc.GcConstants.*;

public class GcDataSet implements IDataSet {
    private GCData gcData = new GCData();

    public GCData getGc() {
        return gcData;
    }

    @Override
    public void calculate() {
    }

    @Override
    public boolean isNan() {
        return gcData.isNan();
    }

    @Override
    public String getTrace() {
        return String.format(
                "%d %f %f",
                gcData.getGcTimes(),
                gcData.getCalculatedAvg(),
                gcData.getMaxGcTime()
        );
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put(GCTIMES, gcData.getGcTimes());
        map.put(AVARAGE_GC_TIME, gcData.getCalculatedAvg());
        map.put(MAX_GC_TIME, gcData.getMaxGcTime());

        return map;
    }
}
