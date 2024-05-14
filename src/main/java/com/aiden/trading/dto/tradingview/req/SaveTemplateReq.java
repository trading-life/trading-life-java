package com.aiden.trading.dto.tradingview.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveTemplateReq {

    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
}
