package com.aiden.trading.dto.tradingview.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveDrawingReq {

    @JsonProperty("client")
    private String client;
    @JsonProperty("user")
    private String user;

    //id
    @JsonProperty("chart")
    private String chart;
    @JsonProperty("layout")
    private String layout;
    @JsonProperty("state")
    private String state;
}
