package ru.naumen.sd40.log.parser.Gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.writers.GcInfluxWriter;
import ru.naumen.perfhouse.writers.IDatabaseWriter;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IParsingMode;
import ru.naumen.sd40.log.parser.ITimeParser;

@Component("gc")
public class GcMode implements IParsingMode {
    private GcDataParser dataParser;
    private GcTimeParserFactory timeParserFactory;
    private GcDataSetFactory dataSetFactory;

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
    public IDatabaseWriter getDatabaseWriter(String dbName, InfluxDAO influxDAO, boolean withTrace) {
        return new GcInfluxWriter(dbName, influxDAO, withTrace);
    }

    @Override
    public GcDataSetFactory getDataSetFactory() {
        return dataSetFactory;
    }
}
