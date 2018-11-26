package ru.naumen.sd40.log.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SdngDataParser implements IDataParser {
    @Autowired
    private ActionDoneParser actionDoneParser;

    @Autowired
    private ErrorParser errorParser;

    @Override
    public void parseLine(DataSet dataSet, String line) {
        actionDoneParser.parseLine(dataSet, line);
        errorParser.parseLine(dataSet, line);
    }
}
