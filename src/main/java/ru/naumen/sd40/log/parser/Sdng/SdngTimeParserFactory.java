package ru.naumen.sd40.log.parser.Sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.ITimeParserFactory;

@Component
public class SdngTimeParserFactory implements ITimeParserFactory<SdngTimeParser> {
    @Autowired
    private SdngTimeParser timeParser;

    @Override
    public SdngTimeParser create() {
        return timeParser;
    }
}
