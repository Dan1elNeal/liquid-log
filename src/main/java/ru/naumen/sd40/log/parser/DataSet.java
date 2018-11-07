package ru.naumen.sd40.log.parser;

/**
 * Created by doki on 22.10.16.
 */
public class DataSet {
    private GCData gcData = new GCData();
    private ErrorData errorData = new ErrorData();
    private ActionDoneData actionsDoneData = new ActionDoneData();
    private TopData cpuData = new TopData();

    public ActionDoneData getActionsDone() {
        return actionsDoneData;
    }

    public ErrorData getErrors() {
        return errorData;
    }

    public GCData getGc() {
        return gcData;
    }

    public TopData cpuData() {
        return cpuData;
    }
}
