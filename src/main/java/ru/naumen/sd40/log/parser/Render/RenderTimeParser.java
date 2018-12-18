package ru.naumen.sd40.log.parser.Render;

import ru.naumen.sd40.log.parser.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderTimeParser implements ITimeParser {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS",
            new Locale("ru", "RU"));

    private static final Pattern PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}");

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
            Date parsed = DATE_FORMAT.parse(matcher.group(0));
            return parsed.getTime();
        }
        return 0L;
    }
}
