package com.example.fibo.service;

import com.example.fibo.model.StockData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelImportService {

    public List<StockData> importExcel(
            MultipartFile file) throws Exception {

        List<StockData> result =
                new ArrayList<>();

        Workbook workbook =
                WorkbookFactory.create(
                        file.getInputStream());

        Sheet sheet =
                workbook.getSheetAt(0);

        for (int i = 1;
             i <= sheet.getLastRowNum();
             i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            StockData stock =
                    StockData.builder()
                            .symbol(
                                    row.getCell(0).getStringCellValue())
                            .prevOpen(
                                    row.getCell(1).getNumericCellValue())
                            .prevHigh(
                                    row.getCell(2).getNumericCellValue())
                            .prevLow(
                                    row.getCell(3).getNumericCellValue())
                            .prevClose(
                                    row.getCell(4).getNumericCellValue())
                            .open(
                                    row.getCell(5).getNumericCellValue())
                            .high(
                                    row.getCell(6).getNumericCellValue())
                            .low(
                                    row.getCell(7).getNumericCellValue())
                            .close(
                                    row.getCell(8).getNumericCellValue())
                            .currentPrice(
                                    row.getCell(9).getNumericCellValue())
                            .volume(
                                    (long) row.getCell(10)
                                            .getNumericCellValue())
                            .build();

            result.add(stock);
        }

        workbook.close();

        return result;
    }
}