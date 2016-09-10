package com.softserve.webtester.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import java.util.ArrayList;
import java.util.List;

import com.softserve.webtester.dto.RequestCollectionFilterDTO;
import com.softserve.webtester.exception.ResourceNotFoundException;
import com.softserve.webtester.mapper.LabelMapper;
import com.softserve.webtester.mapper.RequestCollectionMapper;
import com.softserve.webtester.mapper.RequestMapper;
import com.softserve.webtester.model.RequestCollection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class RequestCollectionServiceTest {

    private static final int ONCE = 1;
    private static final int NEVER = 0;
    private static final int SOME_INT = 0;
    private static final int ID = 1;
    private static final String NAME = "name";
    private static final String ERROR_MASSAGE = "massage";

    @Mock
    RequestCollectionMapper requestCollectionMapper;

    @Mock
    RequestMapper requestMapper;

    @Mock
    LabelMapper labelMapper;

    @InjectMocks
    RequestCollectionService requestCollectionService;

    @Test
    public void testSave() {
        RequestCollection requestCollection = new RequestCollection();

        doNothing().when(requestCollectionMapper).save(requestCollection);
        when(requestMapper.saveByCollection(requestCollection)).thenReturn(anyInt());
        when(labelMapper.saveByRequestCollection(requestCollection)).thenReturn(anyInt());

        requestCollectionService.save(requestCollection);

        verify(requestCollectionMapper, times(ONCE)).save(requestCollection);
        verify(requestMapper, times(ONCE)).saveByCollection(requestCollection);
        verify(labelMapper, times(NEVER)).saveByRequestCollection(requestCollection);
    }

    @Test(expected = DataAccessException.class)
    public void testInValidSave() {
        RequestCollection requestCollection = new RequestCollection();
        when(requestCollectionMapper.load(anyInt())).thenReturn(null);
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(requestCollectionMapper)
                .save(requestCollection);
        requestCollectionService.save(requestCollection);
    }

    @Test
    public void testLoadAll() {
        List<RequestCollection> list = new ArrayList<>();
        list.add(new RequestCollection());
        list.add(new RequestCollection());
        list.add(new RequestCollection());
        int expected = list.size();
        when(requestCollectionMapper.loadAll(null, null)).thenReturn(list);
        assertTrue(requestCollectionService.loadAll(new RequestCollectionFilterDTO()).size() == expected);
    }

    @Test(expected = DataAccessException.class)
    public void testInValidLoadAll() {
        when(requestCollectionMapper.loadAll(null, null)).thenThrow(new DataAccessResourceFailureException("massage"));
        requestCollectionService.loadAll(new RequestCollectionFilterDTO());
    }

    @Test
    public void testLoad() {
        RequestCollection requestCollection = new RequestCollection();
        when(requestCollectionMapper.load(ID)).thenReturn(requestCollection);
        assertEquals(requestCollection, requestCollectionService.load(ID));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInValidLoad() {
        when(requestCollectionMapper.load(ID)).thenReturn(null);
        requestCollectionService.load(ID);
    }

    @Test
    public void testUpdate() {
        RequestCollection requestCollection = new RequestCollection();
        requestCollection.setId(ID);
        
        doNothing().when(requestCollectionMapper).update(requestCollection);
        when(requestMapper.deleteByRequestCollectionId(ID)).thenReturn(anyInt());
        when(labelMapper.deleteByRequestCollectionId(ID)).thenReturn(anyInt());
        when(requestMapper.saveByCollection(requestCollection)).thenReturn(anyInt());
        when(labelMapper.saveByRequestCollection(requestCollection)).thenReturn(anyInt());

        requestCollectionService.update(requestCollection);

        verify(requestCollectionMapper, times(1)).update(requestCollection);
        verify(requestMapper, times(ONCE)).deleteByRequestCollectionId(ID);
        verify(labelMapper, times(ONCE)).deleteByRequestCollectionId(ID);
        verify(requestMapper, times(ONCE)).saveByCollection(requestCollection);
        verify(labelMapper, times(NEVER)).saveByRequestCollection(requestCollection);
    }

    @Test(expected = DataAccessException.class)
    public void testInValidUpdate() {
        RequestCollection requestCollection = new RequestCollection();
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(requestCollectionMapper)
                .update(requestCollection);
        requestCollectionService.update(requestCollection);
    }

    @Test
    public void testDelete() {
        int[] requestCollectionIdArray = new int[SOME_INT];
        doNothing().when(requestCollectionMapper).deleteRequestCollections(requestCollectionIdArray);
        requestCollectionService.delete(requestCollectionIdArray);
        verify(requestCollectionMapper, times(1)).deleteRequestCollections(requestCollectionIdArray);
    }

    @Test(expected = DataAccessException.class)
    public void testInValidDelete() {
        int[] requestCollectionIdArray = new int[SOME_INT];
        doThrow(new DataAccessResourceFailureException(ERROR_MASSAGE)).when(requestCollectionMapper)
                .deleteRequestCollections(requestCollectionIdArray);
        requestCollectionService.delete(requestCollectionIdArray);

    }

    @Test
    public void testiIsRequestCollectionNameFree() {
        when(requestCollectionMapper.isRequestCollectionNameFree(NAME, ID)).thenReturn(true);
        assertTrue(requestCollectionService.isRequestCollectionNameFree(NAME, ID));
    }

    @Test(expected = DataAccessException.class)
    public void testInValidIsRequestCollectionNameFree() {
        when(requestCollectionMapper.isRequestCollectionNameFree(NAME, ID))
                .thenThrow(new DataAccessResourceFailureException(ERROR_MASSAGE));
        requestCollectionService.isRequestCollectionNameFree(NAME, ID);
    }

}
