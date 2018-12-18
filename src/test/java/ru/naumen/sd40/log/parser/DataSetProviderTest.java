package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class DataSetProviderTest {
    private IDatabaseWriter<Long, IDataSet> writer;
    private IDataSetFactory<IDataSet> dataSetFactory;

    @Before
    public void setup() {
        writer = mock(IDatabaseWriter.class);
        dataSetFactory = mock(IDataSetFactory.class);
    }

    @Test
    public void mustReturnSameDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        IDataSet firstDataSet = dataSetProvider.get(1L);
        IDataSet secondDataSet = dataSetProvider.get(1L);

        Assert.assertEquals(firstDataSet, secondDataSet);
    }

    @Test
    public void mustReturnDifferentDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        when(dataSetFactory.create()).then(invocationOnMock -> mock(IDataSet.class));

        IDataSet firstDataSet = dataSetProvider.get(1L);
        IDataSet secondDataSet = dataSetProvider.get(2L);

        Assert.assertNotEquals(firstDataSet, secondDataSet);
    }

    @Test
    public void mustWriteDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        when(dataSetFactory.create()).thenReturn(mock(IDataSet.class));

        IDataSet dataSet = dataSetProvider.get(1L);
        dataSetProvider.get(2L);

        verify(writer).write(eq(1L), eq(dataSet), false);
    }

    @Test
    public void mustNotWriteDataSet() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        dataSetProvider.get(1L);
        dataSetProvider.get(1L);

        verify(writer, never()).write(any(Long.class), any(IDataSet.class), false);
    }

    @Test
    public void mustWriteOnFlush() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        when(dataSetFactory.create()).thenReturn(mock(IDataSet.class));

        IDataSet dataSet = dataSetProvider.get(1L);
        dataSetProvider.flush();

        verify(writer).write(1L, dataSet, false);
    }

    @Test
    public void mustReturnNewDataSetAfterFlush() {
        DataSetProvider dataSetProvider = new DataSetProvider(writer, dataSetFactory, false);

        when(dataSetFactory.create()).then(invocationOnMock -> mock(IDataSet.class));

        IDataSet firstDataSet = dataSetProvider.get(1L);
        dataSetProvider.flush();
        IDataSet secondDataSet = dataSetProvider.get(1L);

        Assert.assertNotEquals(firstDataSet, secondDataSet);
    }
}
