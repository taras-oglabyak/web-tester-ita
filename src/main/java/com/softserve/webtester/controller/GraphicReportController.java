package com.softserve.webtester.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.webtester.dto.ReportFilterDTO;
import com.softserve.webtester.model.ResponseTimeType;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.ReportService;

/**
 * Handles and retrieves graphic building page depending on the URI template. A user must be log-in first he
 * can access this page.
 * @author Viktor Somka
 */
@Controller
@RequestMapping(value = "/reports/graphics")
public class GraphicReportController {

    private static final String SERVICE_NAME = "serviceName";
    private static final String BUILD_VERSIONS = "buildVersions";
    private static final String RESPONSE_TIME_TYPE = "responseTimeType";
    private static final String GRAPHIC_DATA = "graphicData";
    private static final String SLA = "sla";

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private ReportService reportService;

    /**
     * Retrieves page with graphic data report
     * @param reportFilterDTO DTO object is used for filtering graphic building data
     * @param model {@link Model} object
     * @return 'graphic' view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getGraphic(@ModelAttribute ReportFilterDTO reportFilterDTO, Model model) {

        model.addAttribute(SERVICE_NAME, metaDataService.serviceLoadAll());
        model.addAttribute(BUILD_VERSIONS, metaDataService.loadAllBuildVersions());
        model.addAttribute(RESPONSE_TIME_TYPE, ResponseTimeType.values());

        if (reportFilterDTO.getServiceId() != 0 && ArrayUtils.isNotEmpty(reportFilterDTO.getBuildVersionId())) {
            model.addAttribute(GRAPHIC_DATA, reportService.loadReportData(reportFilterDTO));
        }

        if(reportFilterDTO.getServiceId() != 0) {
            model.addAttribute(SLA, metaDataService.serviceLoad(reportFilterDTO.getServiceId()).getSla());
        }
        return "graphic";
    }
}