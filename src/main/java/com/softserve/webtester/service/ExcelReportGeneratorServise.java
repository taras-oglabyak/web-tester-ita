package com.softserve.webtester.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.webtester.dto.StatisticDataDTO;
import com.softserve.webtester.dto.StatisticFilterDTO;


/**
 * ExcelReportGeneratorServise class implements generating of excel statistic reports
 * The service uses Spring DataSourceTransactionManager for managing transaction with the database and log4j for
 * logging.
 * 
 * @author Yura Lubinec
 */
@Service
public class ExcelReportGeneratorServise {

    private static final Logger LOGGER = Logger.getLogger(ExcelReportGeneratorServise.class);

    private static final int STATIC_COLUMNS_NUMBER = 2;

    @Autowired
    private ReportService reportService;

    /**
     * Generate excel statistic reports
     *
     * @param statisticFilterDTO DTO object using for filtering statistic data instances
     * @return byte array of Byte Output Stream 
     */
    @Transactional
    public byte[] generateExcelReport(StatisticFilterDTO statisticFilterDTO) {

        List<String> buildVersionName = reportService.loadBuildVersionsName(statisticFilterDTO);
        List<StatisticDataDTO> statisticData = reportService.loadStatisticReportData(statisticFilterDTO);
        int bvListsize = buildVersionName.size();
        byte[] xls = null;
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet spreadsheet = workbook.createSheet("StatisticReport");
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            //create merged regions in the spreadsheet
            spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 2, bvListsize + 1));
            spreadsheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            spreadsheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            spreadsheet.addMergedRegion(new CellRangeAddress(0, 1, bvListsize + 2, buildVersionName.size() + 2));

            //set auto size for non merged columns
            for (int l = 0; l <= STATIC_COLUMNS_NUMBER + bvListsize; l++) {
                spreadsheet.autoSizeColumn(l);
            }

            //create cells for the the table heading
            HSSFRow row = spreadsheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Service Name");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("SLA");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("Response time for build version");
            cell.setCellStyle(style);
            cell = row.createCell(2 + bvListsize);
            cell.setCellValue("Average for the last three releases");
            cell.setCellStyle(style);
            row = spreadsheet.createRow(1);

            //create cells with build versions name data
            for (int k = 0; k < bvListsize; k++) {
                cell = row.createCell(STATIC_COLUMNS_NUMBER + k);
                cell.setCellValue(buildVersionName.get(k));
                cell.setCellStyle(style);
            }

            //create rows and cells with table body data
            for (int i = 0; i < statisticData.size(); i++) {
                row = spreadsheet.createRow(i + STATIC_COLUMNS_NUMBER);
                cell = row.createCell(0);
                cell.setCellValue(statisticData.get(i).getServiceName());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(statisticData.get(i).getSla());
                cell.setCellStyle(style);
                for (int j = 0; j < bvListsize; j++) {
                    cell = row.createCell(j + STATIC_COLUMNS_NUMBER);
                    Integer value = statisticData.get(i).getResponseTimes().get(j);
                    cell.setCellValue(value != null ? value : 0);
                    cell.setCellStyle(style);
                }
                cell = row.createCell(2 + bvListsize);
                cell.setCellValue(statisticData.get(i).getAverageResponseTime());
                cell.setCellStyle(style);                             
            }

            //set auto size for merged regions 
            spreadsheet.autoSizeColumn(0, true);
            spreadsheet.autoSizeColumn(1, true);
            spreadsheet.autoSizeColumn(STATIC_COLUMNS_NUMBER + bvListsize, true);

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                workbook.write(baos);
                xls = baos.toByteArray();
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write data to byte array output stream", e);
        }
        return xls;
    }
}