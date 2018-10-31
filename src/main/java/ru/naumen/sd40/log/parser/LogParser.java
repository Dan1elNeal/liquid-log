package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import ru.naumen.perfhouse.influx.IDatabaseWriter;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.influx.InfluxWriter;

/**
 * Created by doki on 22.10.16.
 */
public class LogParser {
    /**
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */
    private final static int FIVE_MINUTES = 5 * 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length <= 1) {
            throw new IllegalArgumentException("Arguments should be: logFileName databaseName [timezone]");
        }

        String dbName = args[1].replaceAll("-", "_");
        String host = System.getProperty("influx.host");
        String user = System.getProperty("influx.user");
        String password = System.getProperty("influx.password");

        InfluxDAO influxDao = new InfluxDAO(host, user, password);
        IDatabaseWriter<Long, DataSet> influxWriter = new InfluxWriter(dbName, influxDao);

        DataSetProvider dataSetProvider = new DataSetProvider(influxWriter);

        String fileName = args[0];
        String timeZone = args.length > 2 ? args[2] : "GMT";
        String mode = System.getProperty("parse.mode", "");

        switch (mode) {
            case "sdng":
                parseLogFile(fileName, dataSetProvider, new SdngDataParser(timeZone));
                break;
            case "gc":
                parseLogFile(fileName, dataSetProvider, new GcDataParser(timeZone));
                break;
            case "top":
                parseLogFile(fileName, dataSetProvider, new TopDataParser(fileName, timeZone));
                break;
            default:
                String errorMessage = "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode;
                throw new IllegalArgumentException(errorMessage);
        }

        influxWriter.save();

        if (System.getProperty("NoCsv") == null) {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
    }

    private static void parseLogFile(
            String fileName,
            DataSetProvider dataSetProvider,
            IDataParser dataParser
    ) throws IOException, ParseException {
        ITimeParser timeParser = dataParser.getTimeParser();

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
