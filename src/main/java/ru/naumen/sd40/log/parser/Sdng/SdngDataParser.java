package ru.naumen.sd40.log.parser.Sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

@Component
public class SdngDataParser implements IDataParser<SdngDataSet> {
    @Autowired
    private ActionDoneParser actionDoneParser;

    @Autowired
    private ErrorParser errorParser;

    @Override
    public void parseLine(SdngDataSet dataSet, String line) {
        actionDoneParser.parseLine(dataSet, line);
        errorParser.parseLine(dataSet, line);
    }
}
