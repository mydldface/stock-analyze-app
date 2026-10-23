package com.example.fibo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockData {

    private String symbol;

    private double prevOpen;
    private double prevHigh;
    private double prevLow;
    private double prevClose;
    private long prevVolume;

    private double open;
    private double high;
    private double low;
    private double close;

    private double currentPrice;
    private long volume;
}