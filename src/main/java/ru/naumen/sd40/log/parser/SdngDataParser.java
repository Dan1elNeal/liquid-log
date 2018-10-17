package ru.naumen.sd40.log.parser;

public class SdngDataParser implements IDataParser {
    private ITimeParser timeParser;

    public SdngDataParser(String zone) {
        this.timeParser = new SdngTimeParser(zone);
    }

    @Override
    public void parseLine(DataSet dataSet, String line) {
        dataSet.parseLine(line);
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParser;
    }
}
