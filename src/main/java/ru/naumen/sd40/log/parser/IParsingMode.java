package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.writers.IDatabaseWriter;

public interface IParsingMode<T extends IDataSet> {
    IDataParser getDataParser();
    ITimeParser getTimeParser();
    IDataSetFactory<T> getDataSetFactory();
    IDatabaseWriter getDatabaseWriter(String dbName, InfluxDAO influxDAO, boolean withTrace);
}
