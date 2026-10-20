package com.example.fibo.controller;

import com.example.fibo.model.AnalysisResult;
import com.example.fibo.model.StockData;
import com.example.fibo.service.AnalysisService;
import com.example.fibo.service.ExcelExportService;
import com.example.fibo.service.ExcelImportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private final ExcelImportService excelImportService;
    private final AnalysisService analysisService;
    private final ExcelExportService excelExportService;

    private List<AnalysisResult> latestResults =
            new ArrayList<>();

    public StockController(
            ExcelImportService excelImportService,
            AnalysisService analysisService,
            ExcelExportService excelExportService) {

        this.excelImportService = excelImportService;
        this.analysisService = analysisService;
        this.excelExportService = excelExportService;
    }

    /**
     * Redirect von /
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/stocks";
    }

    /**
     * Dashboard
     */
    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute(
                "results",
                latestResults);

        return "index";
    }

    /**
     * Upload über Thymeleaf Form
     */
    @PostMapping("/upload")
    public String upload(
            @RequestParam("file")
            MultipartFile file,
            Model model)
            throws Exception {

        List<StockData> stocks =
                excelImportService.importExcel(file);

        latestResults =
                analysisService.analyze(stocks);

        model.addAttribute(
                "results",
                latestResults);

        return "index";
    }

    /**
     * Upload per AJAX
     */
    @PostMapping("/api/upload")
    @ResponseBody
    public List<AnalysisResult> uploadAjax(
            @RequestParam("file")
            MultipartFile file)
            throws Exception {

        List<StockData> stocks =
                excelImportService.importExcel(file);

        latestResults =
                analysisService.analyze(stocks);

        return latestResults;
    }

    /**
     * Alle Ergebnisse
     */
    @GetMapping("/api/results")
    @ResponseBody
    public List<AnalysisResult> results() {
        return latestResults;
    }

    /**
     * Eine Aktie
     */
    @GetMapping("/api/stock/{symbol}")
    @ResponseBody
    public ResponseEntity<AnalysisResult> stock(
            @PathVariable String symbol) {

        Optional<AnalysisResult> result =
                latestResults
                        .stream()
                        .filter(r ->
                                r.getSymbol()
                                        .equalsIgnoreCase(symbol))
                        .findFirst();

        return result
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Analyse zurücksetzen
     */
    @PostMapping("/clear")
    public String clear() {

        latestResults.clear();

        return "redirect:/stocks";
    }

    /**
     * Excel Export
     */
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
}