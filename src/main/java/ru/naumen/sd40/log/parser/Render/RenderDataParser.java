package ru.naumen.sd40.log.parser.Render;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RenderDataParser implements IDataParser<RenderDataSet> {
    private static final Pattern RENDER_TIME_PATTERN = Pattern.compile("render time: (\\d+)");

    @Override
    public void parseLine(RenderDataSet data, String line) {
        RenderTimeData renderTimeData = data.getRenderTimeData();

        Matcher renderTimeMatcher = RENDER_TIME_PATTERN.matcher(line);

        if (renderTimeMatcher.find()) {
            String renderTimeString = renderTimeMatcher.group(1);
            int renderTime = Integer.valueOf(renderTimeString);
            renderTimeData.addTime(renderTime);
        }
    }
}
