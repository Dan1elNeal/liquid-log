package ru.naumen.sd40.log.parser.Render;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.ITimeParserFactory;

@Component
public class RenderTimeParserFactory implements ITimeParserFactory<RenderTimeParser> {
    @Autowired
    private RenderTimeParser timeParser;

    @Override
    public RenderTimeParser create() {
        return timeParser;
    }
}
