package com.softserve.webtester.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.webtester.dto.StatisticFilterDTO;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.model.ResponseTimeType;
import com.softserve.webtester.service.ExcelReportGeneratorServise;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.ReportService;

/**
 * Handles and retrieves statistic report page depending on the URI template. A user must be log-in first he
 * can access this page.
 */

@Controller
@RequestMapping(value = "/reports/statistic")
public class StatisticReportController {

    private static final Logger LOGGER = Logger.getLogger(StatisticReportController.class);

    private static final String SERVICE_NAME = "serviceName";
    private static final String BUILD_VERSIONS = "buildVersions";
    private static final String STATISTIC_BUILDVERSIONS = "statisticsBuildVersions";
    private static final String STATISTICS = "statistics";
    private static final String DATAFORMAT = "YYYY-MM-DD HH:mm:ss";
    private static final String RESPONSE_TIME_TYPE = "responseTimeType";

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExcelReportGeneratorServise excelReportGeneratorServise;

    /**
     * Retrieves page with statistic data report of {@link RequestCollection} running.
     * 
     * @param statisticFilterDTO DTO object using for filtering statistic data
     * @param result {@link BindingResult} instance
     * @param model container for statistic data
     * @return 'statistics' view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getStatistic(@Validated @ModelAttribute StatisticFilterDTO statisticFilterDTO, BindingResult result,
            Model model) {
        model.addAttribute(SERVICE_NAME, metaDataService.serviceLoadAllWithoutDeleted());
        List<BuildVersion> buildVersions = metaDataService.loadAllBuildVersions();
        statisticFilterDTO.setBuildVersions(buildVersions);
        model.addAttribute(BUILD_VERSIONS, buildVersions);
        model.addAttribute(RESPONSE_TIME_TYPE, ResponseTimeType.values());
        if (result.hasErrors()) {
            return "statistic/statistics";
        }

        if (ArrayUtils.isNotEmpty(statisticFilterDTO.getServiceId())) {
            model.addAttribute(STATISTIC_BUILDVERSIONS, reportService.loadBuildVersionsName(statisticFilterDTO));
            model.addAttribute(STATISTICS, reportService.loadStatisticReportData(statisticFilterDTO));
        }
        return "statistic/statistics";
    }
    
    /**
     * Handles generating excel statistic report.
     * 
     * @param statisticFilterDTO DTO object using for filtering statistic data
     * @return if success, downloads the statistic report excel file
     * @throws RuntimeException
     */
    
    @RequestMapping(value = "/xls", method = RequestMethod.GET)
    public void xlsStatisticReportExporter(HttpServletResponse response, @ModelAttribute StatisticFilterDTO statisticFilterDTO) {
        if (ArrayUtils.isNotEmpty(statisticFilterDTO.getServiceId())
                && ArrayUtils.isNotEmpty(statisticFilterDTO.getBuildVersionId())) {
            List<BuildVersion> buildVersions = metaDataService.loadAllBuildVersions();
            statisticFilterDTO.setBuildVersions(buildVersions);

            byte[] data = excelReportGeneratorServise.generateExcelReport(statisticFilterDTO);
            String fileName = new SimpleDateFormat(DATAFORMAT).format(new Date()) + ".xls";
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));
            response.setContentType("application/x-download");

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                response.setContentLength(data.length);
                FileCopyUtils.copy(data, outputStream);
            } catch (NullPointerException | IOException e) {
                LOGGER.error("Unable to write data to response output stream", e);
                throw new RuntimeException();
            }
        }
    }
}