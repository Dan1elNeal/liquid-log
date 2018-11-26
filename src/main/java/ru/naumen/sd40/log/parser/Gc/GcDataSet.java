package ru.naumen.sd40.log.parser.Gc;

import ru.naumen.sd40.log.parser.DataSet;

public class GcDataSet extends DataSet {
    private GCData gcData = new GCData();

    public GCData getGc() {
        return gcData;
    }
}
