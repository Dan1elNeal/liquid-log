package ru.naumen.sd40.log.parser.Render;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataSetFactory;

@Component
public class RenderDataSetFactory implements IDataSetFactory<RenderDataSet> {
    @Override
    public RenderDataSet create() {
        return new RenderDataSet();
    }
}
