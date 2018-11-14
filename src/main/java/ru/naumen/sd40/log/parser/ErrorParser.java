package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ErrorParser implements IDataParser {
    private Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    public void parseLine(DataSet dataSet, String line) {
        ErrorData data = dataSet.getErrors();

        if (warnRegEx.matcher(line).find()) {
            data.addWarning();
        }

        if (errorRegEx.matcher(line).find()) {
            data.addError();
        }

        if (fatalRegEx.matcher(line).find()) {
            data.addFatal();
        }
    }
}
