package ru.naumen.sd40.log.parser;

import java.util.List;

public class DataType
{
    private List<String> properties;

    public DataType(List<String> properties)
    {
        this.properties = properties;
    }

    public List<String> getTypeProperties()
    {
        return this.properties;
    }
}
