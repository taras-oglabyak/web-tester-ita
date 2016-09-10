package com.softserve.webtester.controller;

import com.softserve.webtester.dto.ReportDataDTO;
import com.softserve.webtester.dto.ReportFilterDTO;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.ResponseTimeType;
import com.softserve.webtester.model.Service;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.ReportService;
import com.softserve.webtester.service.ResultHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 *  Unit testing of GraphicReportController class with mock objects
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphicReportControllerTest {

    private static final int ONCE = 1;

    @Mock
    MetaDataService metaDataService;

    @Mock
    ResultHistoryService resultHistoryService;

    @Mock
    ReportService reportService;

    @InjectMocks
    private GraphicReportController graphicReportController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    List<Service> expectedServices = new ArrayList<>();
    List<BuildVersion> expectedBuildVersions = new ArrayList<>();
    List<ReportDataDTO> expectedReportDataDTO = new ArrayList<>();
    Service service = new Service();
    Model model = new Model() {
        @Override
        public Model addAttribute(String s, Object o) {
            return null;
        }

        @Override
        public Model addAttribute(Object o) {
            return null;
        }

        @Override
        public Model addAllAttributes(Collection<?> collection) {
            return null;
        }

        @Override
        public Model addAllAttributes(Map<String, ?> map) {
            return null;
        }

        @Override
        public Model mergeAttributes(Map<String, ?> map) {
            return null;
        }

        @Override
        public boolean containsAttribute(String s) {
            return false;
        }

        @Override
        public Map<String, Object> asMap() {
            return null;
        }
    };

    /**
     * Using a mock MetaDataService, mock ResultHistoryService and mock ReportService objects for
     * testing getGraphic() method that consists of setting expectations on the mock objects with
     * assertEquals statement and doing the verification of the method calls made to a mock objects with verify statement
     */
    @Test
    public void getGraphicTest() {

        when(metaDataService.serviceLoadAll()).thenReturn(expectedServices);
        assertEquals(expectedServices, metaDataService.serviceLoadAll());
        verify(metaDataService, times(ONCE)).serviceLoadAll();

        when(metaDataService.loadAllBuildVersions()).thenReturn(expectedBuildVersions);
        assertEquals(expectedBuildVersions, metaDataService.loadAllBuildVersions());
        verify(metaDataService, times(ONCE)).loadAllBuildVersions();

        ResponseTimeType responseTimeFilterMarker = ResponseTimeType.MAX;
        ReportFilterDTO reportFilterDTO = new ReportFilterDTO(0, null, responseTimeFilterMarker);

        when(reportService.loadReportData(reportFilterDTO)).thenReturn(expectedReportDataDTO);
        assertEquals(expectedReportDataDTO, reportService.loadReportData(reportFilterDTO));
        verify(reportService, times(ONCE)).loadReportData(reportFilterDTO);

        when(metaDataService.serviceLoad(reportFilterDTO.getServiceId())).thenReturn(service);
        assertEquals(service, metaDataService.serviceLoad(reportFilterDTO.getServiceId()));
        verify(metaDataService, times(ONCE)).serviceLoad(reportFilterDTO.getServiceId());

        String viewName = graphicReportController.getGraphic(reportFilterDTO, model);
        assertEquals("graphic", viewName);
    }
}