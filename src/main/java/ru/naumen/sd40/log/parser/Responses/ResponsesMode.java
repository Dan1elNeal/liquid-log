package ru.naumen.sd40.log.parser.Responses;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.DataType;
import ru.naumen.sd40.log.parser.IRenderMode;

@Component("responses")
public class ResponsesMode implements IRenderMode {
    private DataType dataType = new DataType(ResponsesConstants.getProps());

    @Override
    public String getView() {
        return "history_responses";
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String getBeautifulParserName() {
        return "Responses";
    }
}
