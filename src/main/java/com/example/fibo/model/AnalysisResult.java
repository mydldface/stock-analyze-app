package com.example.fibo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {

    private String symbol;

    /**
     * Aktueller Kurs
     */
    private double currentPrice;

    private double prevOpen;

    private double prevHigh;

    private double prevLow;

    private double prevClose;
    /**
     * OHLC für Chart
     */
    private double open;

    private double high;

    private double low;

    private double close;

    /**
     * Fibonacci Levels
     */
    private Map<String, Double> fibonacciLevels;

    /**
     * Candlestick Pattern
     */
    private String pattern;

    /**
     * BUY / SELL / HOLD / STRONG BUY ...
     */
    private String signal;

    /**
     * Trends
     */
    private String shortTrend;

    private String mediumTrend;

    private String longTrend;

    /**
     * Unterstützung / Widerstand
     */
    private double support;

    private double resistance;

    /**
     * Handelsvolumen
     */
    private long volume;
}