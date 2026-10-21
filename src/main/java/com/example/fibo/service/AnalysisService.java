package com.example.fibo.service;

import com.example.fibo.model.AnalysisResult;
import com.example.fibo.model.StockData;
import com.example.fibo.types.CandlePattern;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService {

    private final FibonacciService fibonacciService;

    private final CandlestickService candlestickService;

    public AnalysisService(
            FibonacciService fibonacciService,
            CandlestickService candlestickService) {

        this.fibonacciService =
                fibonacciService;

        this.candlestickService =
                candlestickService;
    }

    public List<AnalysisResult> analyze(
            List<StockData> stocks) {

        List<AnalysisResult> results =
                new ArrayList<>();

        for (StockData stock : stocks) {

            Map<String, Double> fibs =
                    fibonacciService.calculate(
                            stock.getHigh(),
                            stock.getLow());

            CandlePattern pattern =
                    candlestickService.detect(
                            stock);

            String signal =
                    determineSignal(
                            pattern);

            System.out.println(
                    "Pattern = " + pattern +
                            ", Signal = " + signal
            );

            System.out.println(
                    "Bevor results.add() - Pattern = " + pattern);

            System.out.println(
                    "Bevor results.add() - Signal = " + signal);

            results.add(
                    AnalysisResult.builder()
                            .symbol(stock.getSymbol())
                            .currentPrice(stock.getCurrentPrice())
                            .prevOpen(stock.getPrevOpen())
                            .prevHigh(stock.getPrevHigh())
                            .prevLow(stock.getPrevLow())
                            .prevClose(stock.getPrevClose())
                            .open(stock.getOpen())
                            .high(stock.getHigh())
                            .low(stock.getLow())
                            .close(stock.getClose())
                            .volume(stock.getVolume())
                            .fibonacciLevels(fibs)
                            .pattern(pattern.name())
                            .signal(signal)
                            .support(stock.getLow())
                            .resistance(stock.getHigh())
                            .build()
            );
        }

        return results;
    }

    private String determineSignal(
            CandlePattern pattern) {

        return switch (pattern) {

            case BULLISH_ENGULFING ->
                    "STRONG BUY";

            case BEARISH_ENGULFING ->
                    "STRONG SELL";

            case HAMMER ->
                    "BUY";

            case BULLISH ->
                    "BUY";

            case SHOOTING_STAR ->
                    "SELL";

            case BEARISH ->
                    "SELL";

            case DOJI ->
                    "WATCH";

            default ->
                    "HOLD";
        };
    }
}