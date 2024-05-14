package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MarksResp {

    @JsonProperty("id")
    private List<Integer> id;
    @JsonProperty("time")
    private List<Integer> time;
    @JsonProperty("color")
    private List<String> color;
    @JsonProperty("text")
    private List<String> text;
    @JsonProperty("label")
    private List<String> label;
    @JsonProperty("labelFontColor")
    private List<String> labelFontColor;
    @JsonProperty("minSize")
    private List<Integer> minSize;
}
