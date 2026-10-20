package com.example.fibo.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class FibonacciService {

    public Map<String, Double> calculate(
            double high,
            double low) {

        double range = high - low;

        Map<String, Double> fibs =
                new LinkedHashMap<>();

        fibs.put("23.6",
                high - range * 0.236);

        fibs.put("38.2",
                high - range * 0.382);

        fibs.put("50",
                high - range * 0.500);

        fibs.put("61.8",
                high - range * 0.618);

        fibs.put("78.6",
                high - range * 0.786);

        return fibs;
    }
}