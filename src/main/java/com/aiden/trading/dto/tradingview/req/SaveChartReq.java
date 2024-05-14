package com.aiden.trading.dto.tradingview.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveChartReq {


    @JsonProperty("resolution")
    private String resolution;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
    @JsonProperty("client")
    private String client;
    @JsonProperty("user")
    private String user;
    @JsonProperty("chart")
    private Integer chart;
}
