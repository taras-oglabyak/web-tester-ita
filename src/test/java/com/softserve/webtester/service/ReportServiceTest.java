package com.softserve.webtester.service;

import com.softserve.webtester.dto.ReportDataDTO;
import com.softserve.webtester.dto.ReportFilterDTO;
import com.softserve.webtester.mapper.ReportMapper;
import com.softserve.webtester.model.ResponseTimeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 *  Unit testing of ReportService class with mock object
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    private static final int ONCE = 1;
    private static final int NEVER = 0;

    @Mock
    ReportMapper reportMapper;

    @InjectMocks
    private ReportService reportService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    List<ReportDataDTO> expectedReportDataDTO = new ArrayList<>();

    /**
     * Using a mock ReportMapper object for testing loadWithAvarageResponseTime/loadWithMaxResponseTime methods
     * that consists of setting expectations on the mock object with assertEquals statement and
     * doing the verification of the method calls made to a mock object with verify statement
     */
    @Test
    public void loadWithAvarageResponseTimeTest() {

        when(reportMapper.loadAvg(0, null)).thenReturn(expectedReportDataDTO);
        assertEquals(expectedReportDataDTO, reportMapper.loadAvg(0, null));
        verify(reportMapper, times(ONCE)).loadAvg(0, null);
        verify(reportMapper, times(NEVER)).loadMax(0, null);
    }

    @Test
    public void loadWithMaxResponseTimeTest() {

        when(reportMapper.loadMax(0, null)).thenReturn(expectedReportDataDTO);
        assertEquals(expectedReportDataDTO, reportMapper.loadMax(0, null));
        verify(reportMapper, times(ONCE)).loadMax(0, null);
        verify(reportMapper, times(NEVER)).loadAvg(0, null);
    }

    @Test
    public void loadReportDataTest() {

        List<ReportDataDTO> expectedReportDataDTOMax = new ArrayList<>();
        expectedReportDataDTOMax.add(new ReportDataDTO("s2", 60, "v1.0"));
        expectedReportDataDTOMax.add(new ReportDataDTO("s2", 80, "v1.1"));

        ReportFilterDTO reportFilterDTO = new ReportFilterDTO(0, null, ResponseTimeType.AVERAGE);
        when(reportService.loadReportData(reportFilterDTO)).thenReturn(expectedReportDataDTO);
        assertEquals(expectedReportDataDTO, reportService.loadReportData(reportFilterDTO));

        ReportFilterDTO reportFilterDTOMax = new ReportFilterDTO(0, null, ResponseTimeType.MAX);
        when(reportService.loadReportData(reportFilterDTOMax)).thenReturn(expectedReportDataDTOMax);
        assertEquals(expectedReportDataDTOMax, reportService.loadReportData(reportFilterDTOMax));
    }
}