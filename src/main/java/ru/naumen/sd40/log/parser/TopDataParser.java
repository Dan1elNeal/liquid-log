package ru.naumen.sd40.log.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopDataParser implements IDataParser {
    private Pattern loadAveragePattern = Pattern.compile(".*load average:(.*)");
    private Pattern cpuAndMemPattern = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    private ITimeParser timeParser;

    public TopDataParser(String file, String zone) {
        this.timeParser = new TopTimeParser(file, zone);
    }

    @Override
    public void parseLine(DataSet dataSet, String line) {
        TopData data = dataSet.cpuData();

        Matcher loadAverage = loadAveragePattern.matcher(line);
        if (loadAverage.find()) {
            data.addLa(Double.parseDouble(loadAverage.group(1).split(",")[0].trim()));
            return;
        }

        Matcher cpuAndMemMatcher = cpuAndMemPattern.matcher(line);
        if (cpuAndMemMatcher.find()) {
            data.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
            data.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
        }
    }

    @Override
    public ITimeParser getTimeParser() {
        return timeParser;
    }
}
