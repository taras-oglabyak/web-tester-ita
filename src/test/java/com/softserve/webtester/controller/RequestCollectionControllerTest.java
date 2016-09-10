package com.softserve.webtester.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.softserve.webtester.dto.RequestCollectionFilterDTO;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Label;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.service.EnvironmentService;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.RequestCollectionService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class RequestCollectionControllerTest {

    @Mock
    MetaDataService metaDataService;

    @Mock
    RequestCollectionService requestCollectionService;

    @Mock
    EnvironmentService environmentService;

    @InjectMocks
    RequestCollectionController requestCollectionController;

    @Test
    public void testGetAllRequestCollection() {
        String expected = "collection/collections";
        RequestCollectionFilterDTO requestCollectionFilterDTO = new RequestCollectionFilterDTO();
        when(metaDataService.loadAllLabels()).thenReturn(new ArrayList<Label>());
        when(metaDataService.loadAllBuildVersions()).thenReturn(new ArrayList<BuildVersion>());
        when(requestCollectionService.loadAll(new RequestCollectionFilterDTO()))
                .thenReturn(new ArrayList<RequestCollection>());
        when(environmentService.loadAll()).thenReturn(new ArrayList<Environment>());

        assertEquals(expected,
                requestCollectionController.getAllRequestCollection(requestCollectionFilterDTO, new Model() {

                    @Override
                    public Model addAttribute(String attributeName, Object attributeValue) {
                        return null;
                    }

                    @Override
                    public Model addAttribute(Object attributeValue) {
                        return null;
                    }

                    @Override
                    public Model addAllAttributes(Collection<?> attributeValues) {
                        return null;
                    }

                    @Override
                    public Model addAllAttributes(Map<String, ?> attributes) {
                        return null;
                    }

                    @Override
                    public Model mergeAttributes(Map<String, ?> attributes) {
                        return null;
                    }

                    @Override
                    public boolean containsAttribute(String attributeName) {
                        return false;
                    }

                    @Override
                    public Map<String, Object> asMap() {
                        return null;
                    }
                }));

        verify(metaDataService, times(1)).loadAllLabels();
        verify(metaDataService, times(1)).loadAllBuildVersions();
        verify(requestCollectionService, times(1)).loadAll(requestCollectionFilterDTO);
        verify(environmentService, times(1)).loadAll();

    }

    public void testGetEmptyFormForRequestCollection() {

    }

    public void testSaveRequestCollection() {

    }
}
