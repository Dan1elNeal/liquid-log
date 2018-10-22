package ru.naumen.sd40.log.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopDataParser implements IDataParser {
    private Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    private ITimeParser timeParser;

    public TopDataParser(String file, String zone) {
        this.timeParser = new TopTimeParser(file, zone);
    }

    @Override
    public void parseLine(DataSet data, String line) {
        //get load average
        Matcher loadAverage = Pattern.compile(".*load average:(.*)").matcher(line);
        if (loadAverage.find()) {
            data.cpuData().addLa(Double.parseDouble(loadAverage.group(1).split(",")[0].trim()));
            return;
        }

        //get cpu and mem
        Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
        if (cpuAndMemMatcher.find()) {
            data.cpuData().addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
            data.cpuData().addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
        }
    }

    @Override
    public ITimeParser getTimeParser() {
        return timeParser;
    }
}
