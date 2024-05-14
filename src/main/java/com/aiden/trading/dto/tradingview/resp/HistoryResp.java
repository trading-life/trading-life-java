package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HistoryResp {

    @JsonProperty("t")
    private List<Integer> t;
    @JsonProperty("o")
    private List<Double> o;
    @JsonProperty("h")
    private List<Double> h;
    @JsonProperty("l")
    private List<Double> l;
    @JsonProperty("c")
    private List<Double> c;
    @JsonProperty("v")
    private List<Integer> v;
    @JsonProperty("s")
    private String s;
}
