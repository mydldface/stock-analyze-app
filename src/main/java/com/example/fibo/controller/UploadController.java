package com.example.fibo.controller;

import com.example.fibo.service.AnalysisService;
import com.example.fibo.service.ExcelImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final ExcelImportService importService;
    private final AnalysisService analysisService;

    @PostMapping("/upload")
    public String upload(
            @RequestParam("file")
            MultipartFile file,
            Model model)
            throws Exception {

        var stocks =
                importService.importExcel(file);

        var results =
                analysisService.analyze(stocks);

        model.addAttribute(
                "results",
                results);

        return "index";
    }
}