package ru.naumen.sd40.log.parser;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
public class ErrorParser {
    private Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    void parseLine(DataSet dataSet, String line) {
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
