package ru.naumen.sd40.log.parser.Render;

import ru.naumen.sd40.log.parser.IDataSet;

import static ru.naumen.sd40.log.parser.Render.RenderConstants.*;

import java.util.HashMap;
import java.util.Map;

public class RenderDataSet implements IDataSet {
    RenderTimeData renderTimeData = new RenderTimeData();

    public RenderTimeData getRenderTimeData() {
        return renderTimeData;
    }

    @Override
    public void calculate() {
        renderTimeData.calculate();
    }

    @Override
    public boolean isNan() {
        return renderTimeData.isNan();
    }

    @Override
    public String getTrace() {
        return String.format(
                "%d %f %f %f %f %f %f %f %f",
                renderTimeData.getCount(),
                renderTimeData.getMin(),
                renderTimeData.getMax(),
                renderTimeData.getMean(),
                renderTimeData.getStddev(),
                renderTimeData.getPercent50(),
                renderTimeData.getPercent95(),
                renderTimeData.getPercent99(),
                renderTimeData.getPercent999()
        );
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put(COUNT, renderTimeData.getCount());
        map.put(MIN, renderTimeData.getMin());
        map.put(MAX, renderTimeData.getMax());
        map.put(MEAN, renderTimeData.getMean());
        map.put(STDDEV, renderTimeData.getStddev());
        map.put(PERCENTILE50, renderTimeData.getPercent50());
        map.put(PERCENTILE95, renderTimeData.getPercent95());
        map.put(PERCENTILE99, renderTimeData.getPercent99());
        map.put(PERCENTILE999, renderTimeData.getPercent999());

        return map;
    }
}
