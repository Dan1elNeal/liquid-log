package ru.naumen.sd40.log.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GcTimeParser implements ITimeParser {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            new Locale("ru", "RU"));

    private static final Pattern PATTERN = Pattern
            .compile("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}).*");

    @Override
    public void setTimeZone(String timezone) {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    @Override
    public void setLogFileName(String logFileName) {

    }

    @Override
    public long parseLine(String line) throws ParseException {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            Date parse = DATE_FORMAT.parse(matcher.group(1));
            return parse.getTime();
        }
        return 0L;
    }
}
