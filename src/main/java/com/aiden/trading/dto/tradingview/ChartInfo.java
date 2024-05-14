package com.aiden.trading.dto.tradingview;

import lombok.Data;

@Data
public class ChartInfo {
    private String content;
    private String name;

    private Integer id;
    private Integer timestamp;
}
