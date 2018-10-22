package ru.naumen.sd40.log.parser;

public class GcDataParser implements IDataParser {
    private ITimeParser timeParser;

    public GcDataParser(String zone) {
        this.timeParser = new GcTimeParser(zone);
    }

    @Override
    public void parseLine(DataSet dataSet, String line) {
        dataSet.parseGcLine(line);
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParser;
    }
}
