package ru.naumen.sd40.log.parser.Sdng;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataSetFactory;

@Component
public class SdngDataSetFactory implements IDataSetFactory<SdngDataSet> {
    @Override
    public SdngDataSet create() {
        return new SdngDataSet();
    }
}
