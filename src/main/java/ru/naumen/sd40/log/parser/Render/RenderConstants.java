package ru.naumen.sd40.log.parser.Render;

import com.google.common.collect.Lists;
import ru.naumen.sd40.log.parser.Constants;

import java.util.List;

public class RenderConstants {
    public static final String MIN = "render_min";
    public static final String MAX = "render_max";
    public static final String MEAN = "render_mean";
    public static final String STDDEV = "render_stddev";
    public static final String PERCENTILE50 = "render_percent50";
    public static final String PERCENTILE95 = "render_percent95";
    public static final String PERCENTILE99 = "render_percent99";
    public static final String PERCENTILE999 = "render_percent999";
    public static final String COUNT = "render_count";

    public static List<String> getProps()
    {
        return Lists.newArrayList(Constants.TIME, COUNT, MIN, MEAN, STDDEV, PERCENTILE50, PERCENTILE95, PERCENTILE99,
                PERCENTILE999, MAX);
    }
}
