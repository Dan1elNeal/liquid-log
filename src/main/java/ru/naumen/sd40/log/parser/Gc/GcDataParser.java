package ru.naumen.sd40.log.parser.Gc;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GcDataParser implements IDataParser<GcDataSet> {
    private final static Pattern GC_EXECUTION_TIME = Pattern.compile(".*real=(.*)secs.*");

    @Override
    public void parseLine(GcDataSet dataSet, String line) {
        Matcher matcher = GC_EXECUTION_TIME.matcher(line);
        GCData data = dataSet.getGc();

        if (matcher.find()) {
            double executionTime = Double.parseDouble(matcher.group(1).trim().replace(',', '.'));
            data.addExecutionTime(executionTime);
        }
    }
}
