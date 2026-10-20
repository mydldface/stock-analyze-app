package com.example.fibo.service;

import com.example.fibo.types.CandlePattern;
import org.springframework.stereotype.Service;

@Service
public class SignalService {

    public String createSignal(
            CandlePattern pattern,
            double currentPrice,
            double fib618) {

        switch (pattern) {

            case BULLISH_ENGULFING:
            case HAMMER:

                return currentPrice > fib618
                        ? "STRONG BUY"
                        : "BUY";

            case BEARISH_ENGULFING:
            case SHOOTING_STAR:

                return "SELL";

            default:

                return "HOLD";
        }
    }
}