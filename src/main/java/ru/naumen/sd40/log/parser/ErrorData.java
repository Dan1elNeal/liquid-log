package ru.naumen.sd40.log.parser;

public class ErrorData {
    private long warningCount = 0;
    private long errorCount = 0;
    private long fatalCount = 0;

    public void addWarning() {
        warningCount++;
    }

    public void addError() {
        errorCount++;
    }

    public void addFatal() {
        fatalCount++;
    }

    public long getWarningCount() {
        return warningCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public long getFatalCount() {
        return fatalCount;
    }
}
