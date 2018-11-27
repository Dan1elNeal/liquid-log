package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.*;
import ru.naumen.perfhouse.writers.GcInfluxWriter;
import ru.naumen.perfhouse.writers.IDatabaseWriter;
import ru.naumen.perfhouse.writers.SdngInfluxWriter;
import ru.naumen.perfhouse.writers.TopInfluxWriter;
import ru.naumen.sd40.log.parser.Gc.GcDataParser;
import ru.naumen.sd40.log.parser.Gc.GcTimeParserFactory;
import ru.naumen.sd40.log.parser.Sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.Sdng.SdngTimeParserFactory;
import ru.naumen.sd40.log.parser.Top.TopDataParser;
import ru.naumen.sd40.log.parser.Top.TopTimeParserFactory;

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

    private SdngDataParser sdngDataParser;
    private GcDataParser gcDataParser;
    private TopDataParser topDataParser;

    private SdngTimeParserFactory sdngTimeParserFactory;
    private GcTimeParserFactory gcTimeParserFactory;
    private TopTimeParserFactory topTimeParserFactory;

    @Autowired
    LogParser(
            InfluxDAO influxDao,
            SdngDataParser sdngDataParser,
            GcDataParser gcDataParser,
            TopDataParser topDataParser,
            SdngTimeParserFactory sdngTimeParserFactory,
            GcTimeParserFactory gcTimeParserFactory,
            TopTimeParserFactory topTimeParserFactory
    ) {
        this.influxDao = influxDao;

        this.sdngDataParser = sdngDataParser;
        this.gcDataParser = gcDataParser;
        this.topDataParser = topDataParser;

        this.sdngTimeParserFactory = sdngTimeParserFactory;
        this.gcTimeParserFactory = gcTimeParserFactory;
        this.topTimeParserFactory = topTimeParserFactory;
    }

    public void parse(
            String dbName,
            String mode,
            String fileName,
            String timezone,
            boolean withTrace
    ) throws IOException, ParseException {
        IDataParser dataParser;
        ITimeParser timeParser;
        IDatabaseWriter databaseWriter;

        switch (mode) {
            case "sdng":
                dataParser = sdngDataParser;
                timeParser = sdngTimeParserFactory.create();
                databaseWriter = new SdngInfluxWriter(dbName, influxDao, withTrace);
                break;
            case "gc":
                dataParser = gcDataParser;
                timeParser = gcTimeParserFactory.create();
                databaseWriter = new GcInfluxWriter(dbName, influxDao, withTrace);
                break;
            case "top":
                dataParser = topDataParser;
                timeParser = topTimeParserFactory.create();
                databaseWriter = new TopInfluxWriter(dbName, influxDao, withTrace);
                break;
            default:
                String errorMessage = "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode;
                throw new IllegalArgumentException(errorMessage);
        }

        IDataSetFactory dataSetFactory = dataParser.getDataSetFactory();
        DataSetProvider dataSetProvider = new DataSetProvider(databaseWriter, dataSetFactory);

        parseLogFile(fileName, timezone, dataParser, timeParser, dataSetProvider);

        databaseWriter.save();

        if (withTrace) {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
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
