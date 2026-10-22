package com.example.fibo.controller;

import com.example.fibo.model.AnalysisResult;
import com.example.fibo.model.StockData;
import com.example.fibo.service.AnalysisService;
import com.example.fibo.service.ExcelExportService;
import com.example.fibo.service.ExcelImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class StockApiController {

    private final ExcelImportService excelImportService;

    private final AnalysisService analysisService;

    private final ExcelExportService excelExportService;

    private List<AnalysisResult> latestResults =
            new ArrayList<>();

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<AnalysisResult> upload(
            @RequestParam MultipartFile file)
            throws Exception {

        List<StockData> stocks =
                excelImportService.importExcel(file);

        latestResults =
                analysisService.analyze(stocks);

        return latestResults;
    }

    @GetMapping("/results")
    public List<AnalysisResult> results() {

        return latestResults;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<AnalysisResult> stock(
            @PathVariable String symbol) {

        return latestResults.stream()
                .filter(r ->
                        r.getSymbol()
                                .equalsIgnoreCase(symbol))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/clear")
    public void clear() {

        latestResults.clear();
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource>
    export() throws Exception {

        ByteArrayInputStream stream =
                excelExportService.export(
                        latestResults);

        HttpHeaders headers =
                new HttpHeaders();

        headers.add(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=analysis.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(
                        new InputStreamResource(stream));
    }

    @PostMapping("/analyze")
    public AnalysisResult analyze(
            @RequestBody StockData stockData) {

        return analysisService
                .analyze(
                        List.of(stockData))
                .getFirst();
    }
}