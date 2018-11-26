package ru.naumen.sd40.log.parser.Top;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataSetFactory;

@Component
public class TopDataSetFactory implements IDataSetFactory<TopDataSet> {
    @Override
    public TopDataSet create() {
        return new TopDataSet();
    }
}
