package ru.naumen.sd40.log.parser.Top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.ITimeParserFactory;

@Component
public class TopTimeParserFactory implements ITimeParserFactory<TopTimeParser> {
    @Autowired
    private TopTimeParser timeParser;

    @Override
    public TopTimeParser create() {
        return timeParser;
    }
}
