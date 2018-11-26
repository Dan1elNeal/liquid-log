package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.IDatabaseWriter;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.influx.InfluxWriter;

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

    @Autowired
    private InfluxDAO influxDao;

    @Autowired
    private SdngDataParser sdngDataParser;

    @Autowired
    private GcDataParser gcDataParser;

    @Autowired
    private TopDataParser topDataParser;

    private DataSetProvider dataSetProvider;

    public void parse(
            String dbName,
            String mode,
            String fileName,
            String timezone,
            boolean withTrace
    ) throws IOException, ParseException {
        IDatabaseWriter<Long, DataSet> influxWriter = new InfluxWriter(dbName, influxDao, withTrace);
        dataSetProvider = new DataSetProvider(influxWriter);

        IDataParser dataParser;
        ITimeParser timeParser;

        switch (mode) {
            case "sdng":
                dataParser = sdngDataParser;
                timeParser = new SdngTimeParser();
                break;
            case "gc":
                dataParser = gcDataParser;
                timeParser = new GcTimeParser();
                break;
            case "top":
                dataParser = topDataParser;
                timeParser = new TopTimeParser();
                break;
            default:
                String errorMessage = "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode;
                throw new IllegalArgumentException(errorMessage);
        }

        parseLogFile(fileName, timezone, dataParser, timeParser);

        influxWriter.save();

        if (withTrace) {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
    }

    private void parseLogFile(
            String fileName,
            String timezone,
            IDataParser dataParser,
            ITimeParser timeParser
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

                DataSet dataSet = dataSetProvider.get(key);
                dataParser.parseLine(dataSet, line);
            }

            dataSetProvider.flush();
        }
    }
}
