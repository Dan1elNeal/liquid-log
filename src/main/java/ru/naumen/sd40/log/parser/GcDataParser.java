package ru.naumen.sd40.log.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GcDataParser implements IDataParser {
    private ITimeParser timeParser;

    private Pattern gcExecutionTime = Pattern.compile(".*real=(.*)secs.*");

    public GcDataParser(String zone) {
        this.timeParser = new GcTimeParser(zone);
    }

    @Override
    public void parseLine(DataSet dataSet, String line) {
        Matcher matcher = gcExecutionTime.matcher(line);
        GCData data = dataSet.getGc();

        if (matcher.find()) {
            double executionTime = Double.parseDouble(matcher.group(1).trim().replace(',', '.'));
            data.addExecutionTime(executionTime);
        }
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParser;
    }
}
