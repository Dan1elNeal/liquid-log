package ru.naumen.sd40.log.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SdngDataParser implements IDataParser<SdngDataSet> {
    @Autowired
    private IDataSetFactory<SdngDataSet> dataSetFactory;

    @Autowired
    private ActionDoneParser actionDoneParser;

    @Autowired
    private ErrorParser errorParser;

    @Override
    public void parseLine(SdngDataSet dataSet, String line) {
        actionDoneParser.parseLine(dataSet, line);
        errorParser.parseLine(dataSet, line);
    }

    @Override
    public IDataSetFactory<SdngDataSet> getDataSetFactory() {
        return dataSetFactory;
    }
}
