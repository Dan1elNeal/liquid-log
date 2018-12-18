package ru.naumen.sd40.log.parser.Render;

import ru.naumen.sd40.log.parser.IDataSet;

public class RenderDataSet implements IDataSet {
    RenderTimeData renderTimeData = new RenderTimeData();

    public RenderTimeData getRenderTimeData() {
        return renderTimeData;
    }
}
