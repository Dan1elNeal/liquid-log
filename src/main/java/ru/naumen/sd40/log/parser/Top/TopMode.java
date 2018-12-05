package ru.naumen.sd40.log.parser.Top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.writers.IDatabaseWriter;
import ru.naumen.perfhouse.writers.TopInfluxWriter;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IParsingMode;
import ru.naumen.sd40.log.parser.ITimeParser;

@Component("top")
public class TopMode implements IParsingMode {
    private TopDataParser dataParser;
    private TopTimeParserFactory timeParserFactory;
    private TopDataSetFactory dataSetFactory;

    @Autowired
    TopMode(TopDataParser dataParser, TopTimeParserFactory timeParserFactory, TopDataSetFactory dataSetFactory) {
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
        return new TopInfluxWriter(dbName, influxDAO, withTrace);
    }

    @Override
    public TopDataSetFactory getDataSetFactory() {
        return dataSetFactory;
    }
}
