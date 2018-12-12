package ru.naumen.sd40.log.parser.Gc;

import com.google.common.collect.Lists;
import ru.naumen.sd40.log.parser.Constants;

import java.util.List;

public class GcConstants {
    public static final String GCTIMES = "gcTimes";
    public static final String AVARAGE_GC_TIME = "avgGcTime";
    public static final String MAX_GC_TIME = "maxGcTime";

    public static List<String> getProps()
    {
        return Lists.newArrayList(Constants.TIME, GCTIMES, AVARAGE_GC_TIME, MAX_GC_TIME);
    }
}
