package ru.naumen.sd40.log.parser.Top;

import ru.naumen.sd40.log.parser.DataSet;

public class TopDataSet extends DataSet {
    private TopData cpuData = new TopData();

    public TopData cpuData() {
        return cpuData;
    }
}
