package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopDataParser implements IDataParser {
    private final static Pattern LOAD_AVERAGE_PATTERN = Pattern.compile(".*load average:(.*)");
    private final static Pattern CPU_AND_MEM_PATTERN = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    @Override
    public void parseLine(DataSet dataSet, String line) {
        TopData data = dataSet.cpuData();

        Matcher loadAverage = LOAD_AVERAGE_PATTERN.matcher(line);
        if (loadAverage.find()) {
            data.addLa(Double.parseDouble(loadAverage.group(1).split(",")[0].trim()));
            return;
        }

        Matcher cpuAndMemMatcher = CPU_AND_MEM_PATTERN.matcher(line);
        if (cpuAndMemMatcher.find()) {
            data.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
            data.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
        }
    }
}
