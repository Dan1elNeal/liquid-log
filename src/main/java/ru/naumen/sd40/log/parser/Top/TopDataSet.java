package ru.naumen.sd40.log.parser.Top;

import ru.naumen.sd40.log.parser.IDataSet;

import java.util.HashMap;
import java.util.Map;

import static ru.naumen.sd40.log.parser.Top.TopConstants.*;
import static ru.naumen.sd40.log.parser.Top.TopConstants.MAX_CPU;
import static ru.naumen.sd40.log.parser.Top.TopConstants.MAX_MEM;

public class TopDataSet implements IDataSet {
    private TopData cpuData = new TopData();

    public TopData cpuData() {
        return cpuData;
    }

    @Override
    public void calculate() {

    }

    @Override
    public boolean isNan() {
        return cpuData.isNan();
    }

    @Override
    public String getTrace() {
        return String.format(
                "%f %f %f %f %f %f",
                cpuData.getAvgLa(),
                cpuData.getAvgCpuUsage(),
                cpuData.getAvgMemUsage(),
                cpuData.getMaxLa(),
                cpuData.getMaxCpu(),
                cpuData.getMaxMem()
        );
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put(AVG_LA, cpuData.getAvgLa());
        map.put(AVG_CPU, cpuData.getAvgCpuUsage());
        map.put(AVG_MEM, cpuData.getAvgMemUsage());
        map.put(MAX_LA, cpuData.getMaxLa());
        map.put(MAX_CPU, cpuData.getMaxCpu());
        map.put(MAX_MEM, cpuData.getMaxMem());

        return map;
    }
}
