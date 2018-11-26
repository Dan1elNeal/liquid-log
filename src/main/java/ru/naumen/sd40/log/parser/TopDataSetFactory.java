package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

@Component
public class TopDataSetFactory implements IDataSetFactory<TopDataSet> {
    @Override
    public TopDataSet create() {
        return new TopDataSet();
    }
}
