package ru.naumen.sd40.log.parser.Sdng;

import ru.naumen.sd40.log.parser.IDataSet;

public class SdngDataSet implements IDataSet {
    private ErrorData errorData = new ErrorData();
    private ActionDoneData actionsDoneData = new ActionDoneData();

    public ActionDoneData getActionsDone() {
        return actionsDoneData;
    }

    public ErrorData getErrors() {
        return errorData;
    }
}
