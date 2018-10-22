package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.influxdb.dto.BatchPoints;

import ru.naumen.perfhouse.influx.InfluxDAO;

/**
 * Created by doki on 22.10.16.
 */
public class App {
    /**
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */
    private final static HashMap<Long, DataSet> DATA = new HashMap<>();

    private final static int FIVE_MINUTES = 5 * 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException {
        String influxDb = null;

        if (args.length > 1) {
            influxDb = args[1];
            influxDb = influxDb.replaceAll("-", "_");
        }

        InfluxDAO storage = null;
        if (influxDb != null) {
            storage = new InfluxDAO(System.getProperty("influx.host"), System.getProperty("influx.user"),
                    System.getProperty("influx.password"));
            storage.init();
            storage.connectToDB(influxDb);
        }

        InfluxDAO finalStorage = storage;
        String finalInfluxDb = influxDb;
        BatchPoints points = null;

        if (storage != null) {
            points = storage.startBatchPoints(influxDb);
        }

        String fileName = args[0];
        String timeZone = args.length > 2 ? args[2] : "GMT";
        String mode = System.getProperty("parse.mode", "");

        switch (mode) {
            case "sdng":
                parseLogFile(fileName, new SdngDataParser(timeZone));
                break;
            case "gc":
                parseLogFile(fileName, new GcDataParser(timeZone));
                break;
            case "top":
                parseLogFile(fileName, new TopDataParser(fileName, timeZone));
                break;
            default:
                String errorMessage = "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode;
                throw new IllegalArgumentException(errorMessage);
        }

        if (System.getProperty("NoCsv") == null) {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }

        BatchPoints finalPoints = points;
        DATA.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            if (System.getProperty("NoCsv") == null) {
                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
            }
            if (!dones.isNan()) {
                finalStorage.storeActionsFromLog(finalPoints, finalInfluxDb, k, dones, erros);
            }

            GCParser gc = set.getGc();
            if (!gc.isNan()) {
                finalStorage.storeGc(finalPoints, finalInfluxDb, k, gc);
            }

            TopData cpuData = set.cpuData();
            if (!cpuData.isNan()) {
                finalStorage.storeTop(finalPoints, finalInfluxDb, k, cpuData);
            }
        });

        storage.writeBatch(points);
    }

    private static void parseLogFile(String fileName, IDataParser dataParser) throws IOException, ParseException {
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

                DataSet dataSet = DATA.computeIfAbsent(key, k -> new DataSet());
                dataParser.parseLine(dataSet, line);
            }
        }
    }
}
