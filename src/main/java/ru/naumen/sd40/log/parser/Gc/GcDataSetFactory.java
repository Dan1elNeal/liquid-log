package ru.naumen.sd40.log.parser.Gc;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataSetFactory;

@Component
public class GcDataSetFactory implements IDataSetFactory<GcDataSet> {
    @Override
    public GcDataSet create() {
        return new GcDataSet();
    }
}
