package com.example.fibo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    @GetMapping
    public String dashboard() {
        return "index";
    }
}