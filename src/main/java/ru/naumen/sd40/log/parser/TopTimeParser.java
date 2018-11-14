package ru.naumen.sd40.log.parser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class TopTimeParser implements ITimeParser {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm");

    private Pattern timeRegex = Pattern.compile("^_+ (\\S+)");

    private String dataDate;

    private long lastParsedTime = 0L;

    @Override
    public void setTimeZone(String timezone) {
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    @Override
    public void setLogFileName(String logFileName) {
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(logFileName);
        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }

        this.dataDate = matcher.group(0).replaceAll("-", "");
    }

    @Override
    public long parseLine(String line) throws ParseException {
        Matcher matcher = timeRegex.matcher(line);
        if (matcher.find()) {
            this.lastParsedTime = sdf.parse(dataDate + matcher.group(1)).getTime();
        }

        return this.lastParsedTime;
    }
}
