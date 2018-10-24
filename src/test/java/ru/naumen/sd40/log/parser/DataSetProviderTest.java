package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import ru.naumen.perfhouse.influx.IDatabaseWriter;

public class DataSetProviderTest {
    private IDatabaseWriter<Long, DataSet> writer;

    @Before
    public void setup() {
        writer = mock(IDatabaseWriter.class);
    }

    @Test
    public void mustReturnDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet dataSet = dataSetProvider.get(1L);

        Assert.assertEquals(dataSet.getClass(), DataSet.class);
    }

    @Test
    public void mustReturnSameDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet firstDataSet = dataSetProvider.get(1L);
        DataSet secondDataSet = dataSetProvider.get(1L);

        Assert.assertEquals(firstDataSet, secondDataSet);
    }

    @Test
    public void mustReturnDifferentDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet firstDataSet = dataSetProvider.get(1L);
        DataSet secondDataSet = dataSetProvider.get(2L);

        Assert.assertNotEquals(firstDataSet, secondDataSet);
    }

    @Test
    public void mustWriteDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet dataSet = dataSetProvider.get(1L);
        dataSetProvider.get(2L);

        verify(writer).write(eq(1L), eq(dataSet));
    }

    @Test
    public void mustNotWriteDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        dataSetProvider.get(1L);
        dataSetProvider.get(1L);

        verify(writer, never()).write(any(Long.class), any(DataSet.class));
    }

    @Test
    public void mustWriteOnFlush() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet dataSet = dataSetProvider.get(1L);
        dataSetProvider.flush();

        verify(writer).write(1L, dataSet);
    }

    @Test
    public void mustReturnNewDataSetAfterFlush() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer);

        DataSet firstDataSet = dataSetProvider.get(1L);
        dataSetProvider.flush();
        DataSet secondDataSet = dataSetProvider.get(1L);

        Assert.assertNotEquals(firstDataSet, secondDataSet);
    }
}
