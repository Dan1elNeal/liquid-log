package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.*;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class LogParser {
    /**
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */
    private final static int FIVE_MINUTES = 5 * 60 * 1000;

    private InfluxDAO influxDao;
    private Map<String, IParsingMode> parsingModes;

    @Autowired
    LogParser(InfluxDAO influxDao, Map<String, IParsingMode> parsingModes) {
        this.influxDao = influxDao;
        this.parsingModes = parsingModes;
    }

    public void parse(
            String dbName,
            String mode,
            String fileName,
            String timezone,
            boolean withTrace
    ) throws IOException, ParseException {
        IParsingMode parsingMode = this.parsingModes.get(mode);

        if (parsingMode == null) {
            String errorMessage = "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode;
            throw new IllegalArgumentException(errorMessage);
        }

        IDataParser dataParser = parsingMode.getDataParser();
        ITimeParser timeParser = parsingMode.getTimeParser();
        IDataSetFactory dataSetFactory = parsingMode.getDataSetFactory();

        IDatabaseWriter databaseWriter = new InfluxWriter(dbName, influxDao, withTrace);
        DataSetProvider dataSetProvider = new DataSetProvider(databaseWriter, dataSetFactory, withTrace);

        parseLogFile(fileName, timezone, dataParser, timeParser, dataSetProvider);

        databaseWriter.save();
    }

    private void parseLogFile(
            String fileName,
            String timezone,
            IDataParser dataParser,
            ITimeParser timeParser,
            DataSetProvider dataSetProvider
    ) throws IOException, ParseException {
        timeParser.setTimeZone(timezone);
        timeParser.setLogFileName(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                long time = timeParser.parseLine(line);

                if (time == 0) {
                    continue;
                }

                long count = time / FIVE_MINUTES;
                long key = count * FIVE_MINUTES;

                IDataSet dataSet = dataSetProvider.get(key);
                dataParser.parseLine(dataSet, line);
            }

            dataSetProvider.flush();
        }
    }
}
