package ru.naumen.sd40.log.parser;

public class SdngDataParser implements IDataParser {
    private ITimeParser timeParser;

    private ActionDoneParser actionDoneParser = new ActionDoneParser();
    private ErrorParser errorParser = new ErrorParser();

    public SdngDataParser(String zone) {
        this.timeParser = new SdngTimeParser(zone);
    }

    @Override
    public void parseLine(DataSet dataSet, String line) {
        actionDoneParser.parseLine(dataSet, line);
        errorParser.parseLine(dataSet, line);
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParser;
    }
}
