package ru.naumen.sd40.log.parser.Top;

import com.google.common.collect.Lists;
import ru.naumen.sd40.log.parser.Constants;

import java.util.List;

public class TopConstants {
    public static final String AVG_LA = "avgLa";
    public static final String AVG_CPU = "avgCpu";
    public static final String AVG_MEM = "avgMem";
    public static final String MAX_LA = "maxLa";
    public static final String MAX_CPU = "maxCpu";
    public static final String MAX_MEM = "maxMem";

    public static List<String> getProps()
    {
        return Lists.newArrayList(Constants.TIME, AVG_LA, AVG_CPU, AVG_MEM, MAX_LA, MAX_CPU, MAX_MEM);
    }
}
