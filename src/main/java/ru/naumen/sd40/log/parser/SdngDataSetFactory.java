package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

@Component
public class SdngDataSetFactory implements IDataSetFactory<SdngDataSet> {
    @Override
    public SdngDataSet create() {
        return new SdngDataSet();
    }
}
