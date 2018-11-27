package ru.naumen.sd40.log.parser.Top;

import ru.naumen.sd40.log.parser.IDataSet;

public class TopDataSet implements IDataSet {
    private TopData cpuData = new TopData();

    public TopData cpuData() {
        return cpuData;
    }
}
