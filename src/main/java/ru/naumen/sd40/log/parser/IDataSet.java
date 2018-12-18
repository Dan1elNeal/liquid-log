package ru.naumen.sd40.log.parser;

import java.util.Map;

/**
 * Created by doki on 22.10.16.
 */
public interface IDataSet {
    void calculate();
    boolean isNan();
    String getTrace();
    Map<String, Object> getDataAsMap();
}
