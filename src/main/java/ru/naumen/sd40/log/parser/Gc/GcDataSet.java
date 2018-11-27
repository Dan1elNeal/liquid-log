package ru.naumen.sd40.log.parser.Gc;

import ru.naumen.sd40.log.parser.IDataSet;

public class GcDataSet implements IDataSet {
    private GCData gcData = new GCData();

    public GCData getGc() {
        return gcData;
    }
}
