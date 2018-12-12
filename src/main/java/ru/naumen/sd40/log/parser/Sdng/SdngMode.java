package ru.naumen.sd40.log.parser.Sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.DataType;
import ru.naumen.perfhouse.writers.IDatabaseWriter;
import ru.naumen.perfhouse.writers.SdngInfluxWriter;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IParsingMode;
import ru.naumen.sd40.log.parser.ITimeParser;

@Component("sdng")
public class SdngMode implements IParsingMode {
    private SdngDataParser dataParser;
    private SdngTimeParserFactory timeParserFactory;
    private SdngDataSetFactory dataSetFactory;
    private DataType dataType = new DataType(SdngConstants.getProps());

    @Autowired
    SdngMode(SdngDataParser dataParser, SdngTimeParserFactory timeParserFactory, SdngDataSetFactory dataSetFactory) {
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
        return new SdngInfluxWriter(dbName, influxDAO, withTrace);
    }

    @Override
    public SdngDataSetFactory getDataSetFactory() {
        return dataSetFactory;
    }

    @Override
    public String getView() {
        return "history_actions";
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String getBeautifulParserName() {
        return "Performed actions";
    }
}
