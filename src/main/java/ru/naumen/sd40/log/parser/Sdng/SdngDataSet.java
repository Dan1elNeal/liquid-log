package ru.naumen.sd40.log.parser.Sdng;

import ru.naumen.sd40.log.parser.IDataSet;
import static ru.naumen.sd40.log.parser.Responses.ResponsesConstants.*;
import static ru.naumen.sd40.log.parser.Sdng.SdngConstants.*;

import java.util.HashMap;
import java.util.Map;

public class SdngDataSet implements IDataSet {
    private ErrorData errorData = new ErrorData();
    private ActionDoneData actionsDoneData = new ActionDoneData();

    public ActionDoneData getActionsDone() {
        return actionsDoneData;
    }

    public ErrorData getErrors() {
        return errorData;
    }

    @Override
    public void calculate() {
        actionsDoneData.calculate();
    }

    @Override
    public boolean isNan() {
        return actionsDoneData.isNan();
    }

    @Override
    public String getTrace() {
        return String.format(
                "%d %f %f %f %f %f %f %f %f %d %d %d %d %d %d %d %d %d",
                actionsDoneData.getCount(),
                actionsDoneData.getMin(),
                actionsDoneData.getMean(),
                actionsDoneData.getStddev(),
                actionsDoneData.getPercent50(),
                actionsDoneData.getPercent95(),
                actionsDoneData.getPercent99(),
                actionsDoneData.getPercent999(),
                actionsDoneData.getMax(),
                errorData.getErrorCount(),
                actionsDoneData.getAddObjectActions(),
                actionsDoneData.getEditObjectsActions(),
                actionsDoneData.getGetCatalogsActions(),
                actionsDoneData.geListActions(),
                actionsDoneData.getCommentActions(),
                actionsDoneData.getFormActions(),
                actionsDoneData.getDtObjectActions(),
                actionsDoneData.getSearchActions()
        );
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put(COUNT, actionsDoneData.getCount());
        map.put(MIN, actionsDoneData.getMin());
        map.put(MEAN, actionsDoneData.getMean());
        map.put(STDDEV, actionsDoneData.getStddev());
        map.put(PERCENTILE50, actionsDoneData.getPercent50());
        map.put(PERCENTILE95, actionsDoneData.getPercent95());
        map.put(PERCENTILE99, actionsDoneData.getPercent99());
        map.put(PERCENTILE999, actionsDoneData.getPercent999());
        map.put(MAX, actionsDoneData.getMax());
        map.put(ERRORS, errorData.getErrorCount());
        map.put(ADD_ACTIONS, actionsDoneData.getAddObjectActions());
        map.put(EDIT_ACTIONS, actionsDoneData.getEditObjectsActions());
        map.put(GET_CATALOGS_ACTION, actionsDoneData.getGetCatalogsActions());
        map.put(LIST_ACTIONS, actionsDoneData.geListActions());
        map.put(COMMENT_ACTIONS, actionsDoneData.getCommentActions());
        map.put(GET_FORM_ACTIONS, actionsDoneData.getFormActions());
        map.put(GET_DT_OBJECT_ACTIONS, actionsDoneData.getDtObjectActions());
        map.put(SEARCH_ACTIONS, actionsDoneData.getSearchActions());

        return map;
    }
}
