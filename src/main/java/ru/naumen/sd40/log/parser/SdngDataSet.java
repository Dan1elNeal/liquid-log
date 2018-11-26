package ru.naumen.sd40.log.parser;

public class SdngDataSet extends DataSet {
    private ErrorData errorData = new ErrorData();
    private ActionDoneData actionsDoneData = new ActionDoneData();

    public ActionDoneData getActionsDone() {
        return actionsDoneData;
    }

    public ErrorData getErrors() {
        return errorData;
    }
}
