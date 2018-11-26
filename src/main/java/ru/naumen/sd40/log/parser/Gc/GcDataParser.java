package ru.naumen.sd40.log.parser.Gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IDataSetFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GcDataParser implements IDataParser<GcDataSet> {
    private final static Pattern GC_EXECUTION_TIME = Pattern.compile(".*real=(.*)secs.*");

    @Autowired
    private IDataSetFactory<GcDataSet> dataSetFactory;

    @Override
    public void parseLine(GcDataSet dataSet, String line) {
        Matcher matcher = GC_EXECUTION_TIME.matcher(line);
        GCData data = dataSet.getGc();

        if (matcher.find()) {
            double executionTime = Double.parseDouble(matcher.group(1).trim().replace(',', '.'));
            data.addExecutionTime(executionTime);
        }
    }

    @Override
    public IDataSetFactory<GcDataSet> getDataSetFactory() {
        return dataSetFactory;
    }
}