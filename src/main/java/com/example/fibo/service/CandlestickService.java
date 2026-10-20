package com.example.fibo.service;

import com.example.fibo.model.StockData;
import com.example.fibo.types.CandlePattern;
import org.springframework.stereotype.Service;

@Service
public class CandlestickService {

    public CandlePattern detect(
            StockData data) {

        double prevOpen =
                data.getPrevOpen();

        double prevClose =
                data.getPrevClose();

        double open =
                data.getOpen();

        double close =
                data.getClose();

        double high =
                data.getHigh();

        double low =
                data.getLow();

        double body =
                Math.abs(close - open);

        double range =
                high - low;

        if (range <= 0) {
            return CandlePattern.NONE;
        }

        double upperShadow =
                high - Math.max(open, close);

        double lowerShadow =
                Math.min(open, close) - low;

        /*
         * DOJI
         */
        if (body <= range * 0.10) {

            System.out.println("Es ist DOJI");

            return CandlePattern.DOJI;
        }

        /*
         * HAMMER
         */
        if (lowerShadow >= body * 2
                && upperShadow <= body) {

            System.out.println("Es ist HAMMER");

            return CandlePattern.HAMMER;
        }

        /*
         * SHOOTING STAR
         */
        if (upperShadow >= body * 2
                && lowerShadow <= body) {

            System.out.println("Es ist SHOOTING_STAR");

            return CandlePattern.SHOOTING_STAR;
        }

        /*
         * Bullish Engulfing
         */
        boolean previousBearish =
                prevClose < prevOpen;

        boolean currentBullish =
                close > open;

        if (previousBearish
                && currentBullish
                && open < prevClose
                && close > prevOpen) {

            System.out.println(
                    "Es ist BULLISH_ENGULFING");

            return CandlePattern.BULLISH_ENGULFING;
        }

        /*
         * Bearish Engulfing
         */
        boolean previousBullish =
                prevClose > prevOpen;

        boolean currentBearish =
                close < open;

        if (previousBullish
                && currentBearish
                && open > prevClose
                && close < prevOpen) {

            System.out.println(
                    "Es ist BEARISH_ENGULFING");

            return CandlePattern.BEARISH_ENGULFING;
        }

        /*
         * Normale Trend-Kerzen
         */

        if (close > open) {

            System.out.println(
                    "Es ist BULLISH");

            return CandlePattern.BULLISH;
        }

        if (close < open) {

            System.out.println(
                    "Es ist BEARISH");

            return CandlePattern.BEARISH;
        }

        return CandlePattern.NONE;
    }
}