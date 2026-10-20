package com.example.fibo.service;

import com.example.fibo.model.AnalysisResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {
    public ByteArrayInputStream export(
            List<AnalysisResult> results)
            throws Exception {
        Workbook workbook =
                new XSSFWorkbook();
        Sheet sheet =
                workbook.createSheet("Analysis");
        Row header =
                sheet.createRow(0);
        header.createCell(0)
                .setCellValue("Symbol");
        header.createCell(1)
                .setCellValue("Pattern");
        header.createCell(2)
                .setCellValue("Signal");
        header.createCell(3)
                .setCellValue("Current Price");
        int rowIndex = 1;

        for (AnalysisResult result : results) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0)
                    .setCellValue(result.getSymbol());
            row.createCell(1)
                    .setCellValue(result.getPattern());
            row.createCell(2)
                    .setCellValue(result.getSignal());
            row.createCell(3)
                    .setCellValue(
                            result.getCurrentPrice());
        }

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(
                out.toByteArray());
    }
}