package ru.naumen.sd40.log.parser.Sdng;

import ru.naumen.sd40.log.parser.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SdngTimeParser implements ITimeParser {
    private static final Pattern TIME_PATTERN =
            Pattern.compile("^\\d+ \\[.*?\\] \\((\\d{2} .{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3})\\)");

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd MMM yyyy HH:mm:ss,SSS", new Locale("ru", "RU"));

    @Override
    public void setTimeZone(String timezone) {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    @Override
    public void setLogFileName(String logFileName) {

    }

    @Override
    public long parseLine(String line) throws ParseException {
        Matcher matcher = TIME_PATTERN.matcher(line);
        if (matcher.find()) {
            String timeString = matcher.group(1);
            return DATE_FORMAT.parse(timeString).getTime();
        }

        return 0L;
    }
}