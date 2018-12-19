package ru.naumen.sd40.log.parser.Render;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.DataType;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.IParsingMode;
import ru.naumen.sd40.log.parser.ITimeParser;

@Component("render")
public class RenderMode implements IParsingMode {
    private RenderDataParser dataParser;
    private RenderTimeParserFactory timeParserFactory;
    private RenderDataSetFactory dataSetFactory;
    private DataType dataType = new DataType(RenderConstants.getProps());

    @Autowired
    RenderMode(RenderDataParser dataParser, RenderTimeParserFactory timeParserFactory, RenderDataSetFactory dataSetFactory) {
        this.dataParser = dataParser;
        this.timeParserFactory = timeParserFactory;
        this.dataSetFactory = dataSetFactory;
    }

    @Override
    public IDataParser getDataParser() {
        return this.dataParser;
    }

    @Override
    public ITimeParser getTimeParser() {
        return this.timeParserFactory.create();
    }

    @Override
    public RenderDataSetFactory getDataSetFactory() {
        return dataSetFactory;
    }

    @Override
    public String getView() {
        return "history_render";
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String getBeautifulParserName() {
        return "Render";
    }
}
