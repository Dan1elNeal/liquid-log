package ru.naumen.sd40.log.parser.Gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.DataType;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IParsingMode;
import ru.naumen.sd40.log.parser.ITimeParser;

@Component("gc")
public class GcMode implements IParsingMode {
    private GcDataParser dataParser;
    private GcTimeParserFactory timeParserFactory;
    private GcDataSetFactory dataSetFactory;
    private DataType dataType = new DataType(GcConstants.getProps());

    @Autowired
    GcMode(GcDataParser dataParser, GcTimeParserFactory timeParserFactory, GcDataSetFactory dataSetFactory) {
        this.dataParser = dataParser;
        this.timeParserFactory = timeParserFactory;
        this.dataSetFactory = dataSetFactory;
    }

    @Override
    public IDataParser getDataParser() {
        return this.dataParser;
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParserFactory.create();
    }

    @Override
    public GcDataSetFactory getDataSetFactory() {
        return dataSetFactory;
    }

    @Override
    public String getView() {
        return "history_gc";
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String getBeautifulParserName() {
        return "Garbage Collection";
    }
}
