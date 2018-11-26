package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ErrorParser implements IDataParser {
    private final static Pattern WARN_REG_EX = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private final static Pattern ERROR_REG_EX = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private final static Pattern FATAL_REG_EX = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    public void parseLine(DataSet dataSet, String line) {
        ErrorData data = dataSet.getErrors();

        if (WARN_REG_EX.matcher(line).find()) {
            data.addWarning();
        }

        if (ERROR_REG_EX.matcher(line).find()) {
            data.addError();
        }

        if (FATAL_REG_EX.matcher(line).find()) {
            data.addFatal();
        }
    }
}
