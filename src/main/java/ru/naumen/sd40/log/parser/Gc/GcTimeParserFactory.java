package ru.naumen.sd40.log.parser.Gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.ITimeParserFactory;

@Component
public class GcTimeParserFactory implements ITimeParserFactory<GcTimeParser> {
    @Autowired
    private GcTimeParser timeParser;

    @Override
    public GcTimeParser create() {
        return timeParser;
    }
}
