package com.aiden.trading.dto.tradingview.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveDrawingTemplateReq {

    @JsonProperty("client")
    private String client;
    @JsonProperty("user")
    private String user;

    @JsonProperty("tool")
    private String tool;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
}
