package com.softserve.webtester.service;


import com.softserve.webtester.exception.ResourceNotFoundException;
import com.softserve.webtester.mapper.ApplicationMapper;
import com.softserve.webtester.mapper.BuildVersionMapper;
import com.softserve.webtester.mapper.LabelMapper;
import com.softserve.webtester.mapper.ServiceMapper;
import com.softserve.webtester.model.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.mockito.Mockito.doThrow;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MetaDataServiceTest {

    private static final int ONCE = 1;
    private static final int ID = 1;
    private static final String NAME = "name";
    private static final String ERROR_MASSAGE = "massage";

    @Mock
    private ApplicationMapper applicationMapper;

    @Mock
    private ServiceMapper serviceMapper;

    @Mock
    private BuildVersionMapper buildVersionMapper;

    @Mock
    private LabelMapper labelMapper;

    @InjectMocks
    private MetaDataService metaDataService;

    @Test
    public void testLoadLabel() {
        Label label = new Label();
        when(labelMapper.loadLabelById(ID)).thenReturn(label);
        assertEquals(label, metaDataService.loadLabelById(ID));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInValidLoadLabel() {
        when(labelMapper.loadLabelById(ID)).thenReturn(null);
        metaDataService.loadLabelById(ID);
    }

    @Test
    public void testLoadAllLabel() {
        List<Label> labelList = new ArrayList<>();
        when(labelMapper.loadAllLabels()).thenReturn(labelList);
        assertEquals(labelList, metaDataService.loadAllLabels());
    }

    @Test(expected = DataAccessException.class)
    public void testInValidLoadAllLabels() {
        when(labelMapper.loadAllLabels()).thenThrow(new DataAccessResourceFailureException(ERROR_MASSAGE));
        metaDataService.loadAllLabels();
    }

    @Test
    public void testSaveLabel() {
        Label label = new Label();
        when(labelMapper.saveLabel(label)).thenReturn(anyInt());
        metaDataService.saveLabel(label);
        verify(labelMapper, times(ONCE)).saveLabel(label);
    }

    @Test(expected = DataAccessException.class)
    public void testInValidLabelSave() {
        Label label = new Label();
        when(labelMapper.loadLabelById(anyInt())).thenReturn(null);
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(labelMapper).saveLabel(label);
        metaDataService.saveLabel(label);
    }

    @Test
    public void testUpdateLabel() {
        Label label = new Label();
        label.setId(ID);
        when(labelMapper.updateLabel(label)).thenReturn(ID);
        assertEquals(ID, metaDataService.updateLabel(label));
    }

    @Test(expected = DataAccessException.class)
    public void testInValidUpdateLabel() {
        Label label = new Label();
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(labelMapper).updateLabel(label);
        metaDataService.updateLabel(label);
    }

    @Test
    public void testDeleteLabel() {
        assertEquals(anyInt(), metaDataService.deleteLabel(ID));
    }

    @Test(expected = DataAccessException.class)
    public void testInValidDeleteLabel() {
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(labelMapper).deleteLabel(ID);
        metaDataService.deleteLabel(ID);

    }

    @Test
    public void testIsLabelNameFree() {
        when(labelMapper.isLabelNameFree(NAME, ID)).thenReturn(true);
        assertTrue(metaDataService.isLabelNameFree(NAME, ID));
    }

    @Test(expected = DataAccessException.class)
    public void testInValidIsLabelNameFree() {
        when(labelMapper.isLabelNameFree(NAME, ID)).thenThrow(new DataAccessResourceFailureException(ERROR_MASSAGE));
        metaDataService.isLabelNameFree(NAME, ID);
    }

}
