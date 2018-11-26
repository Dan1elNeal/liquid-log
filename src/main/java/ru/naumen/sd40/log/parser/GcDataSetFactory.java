package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

@Component
public class GcDataSetFactory implements IDataSetFactory<GcDataSet> {
    @Override
    public GcDataSet create() {
        return new GcDataSet();
    }
}
